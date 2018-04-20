package explodingteddies.modelo.tablero;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CampoMinado extends JPanel implements MouseListener, MouseMotionListener {
    // Variables de la clase

    private char[][] matriz;
    private int cantidadMinas;
    private int camposDescubiertos;
    private Bloque[][] bloques;
    private int tamBloque = 25;
    private int xBorde = 6;
    private int yBorde = 6;
    private boolean jugando = true;
    private boolean boton1pres, boton2pres;
    private int fil, col;
    private Tablero bm;
    private boolean firstClick;
    private int xActual, yActual;
    
    //TODO Interfaz inicializa la ventana
    public CampoMinado(Tablero buscaMinas, int filas, int columnas, int cantidad) {
        bm = buscaMinas;
        fil = filas;
        col = columnas;
        setLayout(null);
        // Crea las matrices
        matriz = new char[fil][col];
        bloques = new Bloque[fil][col];
        // Llama a la inicializaci�n de cada bloque
        inicializaBloques();
        cantidadMinas = cantidad;

        /* Coloca la ventana centrada en la pantalla */
        int width = col * tamBloque + xBorde;
        int height = fil * tamBloque + yBorde;
        setBounds(0, 0, width, height);

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    //TODO Interfaz
    private void inicializaBloques() {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                bloques[i][j] = new Bloque(this, Bloque.EMPTY, i, j, tamBloque, xBorde / 2, yBorde / 2);
            }
        }
    }
    //TODO Interfaz
    // Inicia un juego nuevo
    public void iniciarJuego() {
        reiniciarContadores();
        vaciarMatriz();
        llenarMatriz();
        //imprimirMatriz();
        repaint();
        bm.cambiarImagen(Tablero.NORMAL);
        bm.setNMinas(cantidadMinas);
        bm.reiniciarCronometro();
        jugando = true;
        firstClick = true;
    }
    //TODO Logica
    // Se encarga de que la matriz est� lista para jugar
    private void llenarMatriz() {
        ponerMinas();
        calcularNumeros();
    }
    //TODO Logica
    // Coloca las minas al azar, seg�n el porcentaje establecido.
    private void ponerMinas() {
        int contador = 0;
        while (contador < cantidadMinas) {
            int i = (int) (Math.random() * fil);
            int j = (int) (Math.random() * col);
            if (matriz[i][j] != Bloque.MINA) {
                matriz[i][j] = Bloque.MINA;
                bloques[i][j].setContent(Bloque.MINA);
                contador++;
            }
        }
    }
    //TODO Logica
    // Coloca el n�mero de minas adyacentes en las celdas que no tienen minas.
    private void calcularNumeros() {
        int[] xvars = {-1, 0, 1, -1, 1, -1, 0, 1};
        int[] yvars = {-1, -1, -1, 0, 0, 1, 1, 1};
        // Recorre la matriz
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (matriz[i][j] != Bloque.MINA) {
                    int nMinas = 0;
                    for (int v = 0; v < xvars.length; v++) {
                        // Verifica para evitar un ArrayIndexOutOfBoundsException
                        if ((i + xvars[v] >= 0) && (i + xvars[v] < matriz.length)
                                && (j + yvars[v] >= 0) && (j + yvars[v] < matriz[i].length)
                                && (matriz[i + xvars[v]][j + yvars[v]] == Bloque.MINA)) {
                            nMinas++;
                        }
                    }
                    matriz[i][j] = (Integer.toString(nMinas).charAt(0));
                    bloques[i][j].setContent(Integer.toString(nMinas).charAt(0));
                }
            }
        }
    }
    //TODO Logica
    // Reinicia los contadores del juego
    private void reiniciarContadores() {
        jugando = false;
        camposDescubiertos = 0;
        boton1pres = false;
        boton2pres = false;
        xActual = -1;
        yActual = -1;
    }
    
    //TODO Logica
    // Vac�a la matriz
    private void vaciarMatriz() {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                matriz[i][j] = Bloque.EMPTY;
                bloques[i][j].setContent(Bloque.EMPTY);
                bloques[i][j].setCubierto(true);
            }
        }
    }
    
    //TODO Interfaz
    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                bloques[i][j].pintar(g);
            }
        }
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, xBorde / 2, getHeight());
        g.fillRect(0, 0, getWidth(), yBorde / 2);

        g.setColor(Color.white);
        g.fillRect(getWidth() - xBorde / 2, 0, xBorde / 2, getHeight() - yBorde / 2);
        g.fillRect(xBorde / 2, getHeight() - yBorde / 2, getWidth() - xBorde / 2, yBorde / 2);
    }
    
    //TODO Interfaz
    // Hace visible un campo y su contenido
    public void descubrirCampo(int x, int y) {
        if (jugando) {
            boolean pierde = false;
            int[] xvars = {-1, 0, 1, -1, 1, -1, 0, 1};
            int[] yvars = {-1, -1, -1, 0, 0, 1, 1, 1};

            if (bloques[x][y].getCubierto()) {
                if (bloques[x][y].getContent() != Bloque.FLAG) {
                    bloques[x][y].setCubierto(false);
                    camposDescubiertos++;
                }

                if (bloques[x][y].getContent() == Bloque.MINA) {
                    bloques[x][y].setContent(Bloque.PIERDE);
                    jugarDeNuevo();
                    pierde = true;
                    mostrarMinas();
                    bm.cambiarImagen(Tablero.PIERDE);
                } else if (bloques[x][y].getContent() == Bloque.ZERO) {
                    for (int v = 0; v < xvars.length; v++) {
                        // Verifica para evitar un ArrayIndexOutOfBoundsException
                        if ((x + xvars[v] >= 0) && (x + xvars[v] < matriz.length)
                                && (y + yvars[v] >= 0) && (y + yvars[v] < matriz[x].length)
                                && (bloques[x + xvars[v]][y + yvars[v]].getCubierto())) {
                            descubrirCampo(x + xvars[v], y + yvars[v]);
                        }
                    }
                }
            }
            repaint();
            if (!pierde) {
                verificarSiGano();
            }
        }
    }
    //TODO Logica
    private void verificarSiGano() {
        if (fil * col - camposDescubiertos == cantidadMinas) {
            jugarDeNuevo();
            marcarMinas();
            //JOptionPane.showMessageDialog(null, "Gan�\n�Con esa cara qui�n lo hubiera pensado!");
            bm.cambiarImagen(Tablero.GANA);
        } else {
            bm.cambiarImagen(Tablero.NORMAL);
        }
    }
    //TODO Interfaz
    private void mostrarMinas() {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (bloques[i][j].getContent() == Bloque.FLAG && matriz[i][j] != Bloque.MINA) {
                    bloques[i][j].setContent(Bloque.NO_MINA);
                    bloques[i][j].setCubierto(false);
                    bm.aumentarNMinas();
                }
                if (matriz[i][j] == Bloque.MINA && bloques[i][j].getContent() != Bloque.FLAG) {
                    bloques[i][j].setCubierto(false);
                }
            }
        }
        repaint();
    }
    //TODO Interfaz
    public void jugarDeNuevo() {
        jugando = false;
        bm.detenerTiempo();
    }
    //TODO Interfaz
    public void marcarMinas() {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (matriz[i][j] == Bloque.MINA && bloques[i][j].getContent() != Bloque.FLAG) {
                    marcarCampo(i, j);
                }
            }
        }
        repaint();
    }
    //TODO Interfaz
    public void marcarCampo(int x, int y) {
        if (bloques[x][y].getCubierto()) {
            if (bloques[x][y].getContent() == Bloque.FLAG) {
                bloques[x][y].setContent(matriz[x][y]);
                bm.aumentarNMinas();
            } else {
                bloques[x][y].setContent(Bloque.FLAG);
                bm.disminuirNMinas();
            }
            repaint();
        }
    }
    //TODO Interfaz
    public void revelarCampos(int x, int y) {
        int[] xvars = {-1, 0, 1, -1, 1, -1, 0, 1};
        int[] yvars = {-1, -1, -1, 0, 0, 1, 1, 1};

        int numMarcas = 0;

        if (!bloques[x][y].getCubierto() && Character.isDigit(bloques[x][y].getContent()) && bloques[x][y].getContent() != Bloque.ZERO) {
            for (int v = 0; v < xvars.length; v++) {
                // Verifica para evitar un ArrayIndexOutOfBoundsException
                if ((x + xvars[v] >= 0) && (x + xvars[v] < matriz.length) && (y + yvars[v] >= 0) && (y + yvars[v] < matriz[x].length) && (bloques[x + xvars[v]][y + yvars[v]].getContent() == Bloque.FLAG)) {
                    numMarcas++;
                }
            }
            if (numMarcas == Integer.parseInt("" + bloques[x][y].getContent())) {
                for (int v = 0; v < xvars.length; v++) {
                    // Verifica para evitar un ArrayIndexOutOfBoundsException
                    if ((x + xvars[v] >= 0) && (x + xvars[v] < matriz.length) && (y + yvars[v] >= 0) && (y + yvars[v] < matriz[x].length) && (bloques[x + xvars[v]][y + yvars[v]].getCubierto())) {
                        descubrirCampo(x + xvars[v], y + yvars[v]);
                    }
                }
                repaint();
            } else {
                bm.cambiarImagen(Tablero.NORMAL);
            }
        } else {
            bm.cambiarImagen(Tablero.NORMAL);
            if (x == xActual && y == yActual) {
                if (bloques[x][y].getCubierto() && bloques[x][y].getContent() != Bloque.FLAG) {
                    bloques[x][y].setContent(matriz[x][y]);
                    repaint();
                }
            }
        }
    }

    // imprime la Matriz en la consola del sistema
    private void imprimirMatriz() {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("\nCantidad Total de minas: " + cantidadMinas);
    }

    /**
     * ***********************************
     */
    /* METODOS DE LA CLASE MOUSE LISTENER */
    /**
     * ***********************************
     */
    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
        if (boton1pres || boton2pres) {
            bm.cambiarImagen(Tablero.NORMAL);
        }
        if (xActual >= 0 && xActual < fil && yActual >= 0 && yActual < col && bloques[xActual][yActual].getContent() != Bloque.FLAG) {
            bloques[xActual][yActual].setContent(matriz[xActual][yActual]);
            repaint();
        }
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        if (jugando && ((boton1pres && boton2pres) || (boton1pres && !boton2pres))) {
            int y = (e.getX() - xBorde / 2) / tamBloque;
            int x = (e.getY() - yBorde / 2) / tamBloque;
            //System.out.println("Boton: "+e.getButton()+", x = "+x+", y = "+y+", xA = "+x+", yA = "+y);

            if (x >= 0 && x < fil && y >= 0 && y < col) {
                if (x != xActual || y != yActual) {
                    if (xActual >= 0 && xActual < fil && yActual >= 0 && yActual < col && bloques[xActual][yActual].getCubierto() && bloques[xActual][yActual].getContent() != Bloque.FLAG) {
                        bloques[xActual][yActual].setContent(matriz[xActual][yActual]);
                    }
                    xActual = x;
                    yActual = y;
                    if (bloques[xActual][yActual].getCubierto() && bloques[xActual][yActual].getContent() != Bloque.FLAG) {
                        bloques[xActual][yActual].setContent(Bloque.DOWN);
                    }
                    repaint();
                }
            }
        }
    }

    public void mousePressed(MouseEvent e) {
        if (jugando) {
            //System.out.println(""+e.getButton());
            int y = (e.getX() - xBorde / 2) / tamBloque;
            int x = (e.getY() - yBorde / 2) / tamBloque;

            if (e.getButton() == e.BUTTON1) {
                if (x >= 0 && x < fil && y >= 0 && y < col) {
                    if (bloques[x][y].getContent() != Bloque.FLAG && bloques[x][y].getCubierto()) {
                        xActual = x;
                        yActual = y;
                        bloques[x][y].setContent(Bloque.DOWN);
                        repaint();
                    }
                }
                bm.cambiarImagen(Tablero.ASOMBRA);
                boton1pres = true;
            } else {
                boton2pres = true;
            }
        }//*/
    }

    public void mouseReleased(MouseEvent e) {
        if (jugando) {
            //System.out.println(""+e.getButton());
            int y = (e.getX() - xBorde / 2) / tamBloque;
            int x = (e.getY() - yBorde / 2) / tamBloque;

            if (x >= 0 && x < fil && y >= 0 && y < col) {
                if (firstClick) {
                    bm.iniciarTiempo();
                    firstClick = false;
                }
                if (e.getButton() == e.BUTTON1 && boton1pres) {
                    boton1pres = false;
                    if (bloques[x][y].getCubierto() && bloques[x][y].getContent() != Bloque.FLAG) {
                        bloques[x][y].setContent(matriz[x][y]);
                        xActual = yActual = -1;
                        repaint();
                    }
                    if (boton2pres || e.isShiftDown()) {
                        boton2pres = false;
                        revelarCampos(x, y);
                    } else {
                        descubrirCampo(x, y);
                    }
                } else if (boton2pres) {
                    boton2pres = false;
                    if (boton1pres) {
                        if (bloques[x][y].getCubierto()) {
                            bloques[x][y].setContent(matriz[x][y]);
                        }
                        boton1pres = false;
                        revelarCampos(x, y);
                    } else {
                        marcarCampo(x, y);
                    }
                }
            } else {
                if (xActual >= 0 && xActual < fil && yActual >= 0 && yActual < col && bloques[xActual][yActual].getCubierto() && bloques[xActual][yActual].getContent() != Bloque.FLAG) {
                    bloques[xActual][yActual].setContent(matriz[xActual][yActual]);
                    repaint();
                }
                bm.cambiarImagen(Tablero.NORMAL);
            }
        }//*/
    }
}