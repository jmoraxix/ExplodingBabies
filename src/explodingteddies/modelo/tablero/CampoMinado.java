package explodingteddies.modelo.tablero;


import explodingteddies.modelo.Dificultad;

public class CampoMinado {
 
    // Variables del juego
    private Tablero tablero;
    private Matriz<ContenidoBloque> matrizContenidoBloque;
    private Matriz<EstadoBloque> matrizEstadoBloque;
    private final Dificultad dificultad;
    private boolean jugando = true;
    private int minasEcontradas;
//    private boolean firstClick;
//    private int xActual, yActual;
    
    public CampoMinado(Tablero tablero, Dificultad dificultad) {
        this.tablero = tablero;
        this.dificultad = dificultad;
        
        matrizContenidoBloque = new Matriz<>(dificultad.getFil(), dificultad.getCol(), ContenidoBloque.NORMAL);
        matrizEstadoBloque = new Matriz<>(dificultad.getFil(), dificultad.getCol(), EstadoBloque.CUBIERTO);
        
        ponerMinas();
        calcularNumeros();
    }
    
    // Coloca las minas al azar.
    private void ponerMinas() {
        int contador = 0;
        while (contador < dificultad.getCantidadMinas()) {
            int i = (int) (Math.random() * dificultad.getFil());
            int j = (int) (Math.random() * dificultad.getCol());
            if (matrizContenidoBloque.get(i, j) != ContenidoBloque.MINA) {
                matrizContenidoBloque.set(i, j, ContenidoBloque.MINA);
                contador++;
            }
        }
    }
    //TODO Logica
    // Coloca el numero de minas adyacentes en las celdas que no tienen minas.
    private void calcularNumeros() {
        int[] xvars = {-1, 0, 1, -1, 1, -1, 0, 1};
        int[] yvars = {-1, -1, -1, 0, 0, 1, 1, 1};
        // Recorre la matriz
        for (int i = 0; i < matrizContenidoBloque.length; i++) {
            for (int j = 0; j < matrizContenidoBloque[i].length; j++) {
                if (matrizContenidoBloque[i][j] != Bloque.MINA) {
                    int nMinas = 0;
                    for (int v = 0; v < xvars.length; v++) {
                        // Verifica para evitar un ArrayIndexOutOfBoundsException
                        if ((i + xvars[v] >= 0) && (i + xvars[v] < matrizContenidoBloque.length)
                                && (j + yvars[v] >= 0) && (j + yvars[v] < matrizContenidoBloque[i].length)
                                && (matrizContenidoBloque[i + xvars[v]][j + yvars[v]] == Bloque.MINA)) {
                            nMinas++;
                        }
                    }
                    matrizContenidoBloque[i][j] = (Integer.toString(nMinas).charAt(0));
                    bloques[i][j].setContent(Integer.toString(nMinas).charAt(0));
                }
            }
        }
    }
    //TODO Logica
    // Reinicia los contadores del juego
    private void reiniciarContadores() {
        jugando = false;
        minasEcontradas = 0;
        xActual = -1;
        yActual = -1;
    }
    
    // Hace visible un campo y su contenido
    public void descubrirCampo(int x, int y) {
        if (jugando) {
            boolean pierde = false;
            int[] xvars = {-1, 0, 1, -1, 1, -1, 0, 1};
            int[] yvars = {-1, -1, -1, 0, 0, 1, 1, 1};

            if (matrizEstadoBloque.get(x, y) == EstadoBloque.CUBIERTO) {
                if (matrizContenidoBloque.get(x, y) == ContenidoBloque.MINA) {
                    matrizEstadoBloque.set(x, y, EstadoBloque.DESCUBIERTO);
                    pierde = true;
                } else if (matrizContenidoBloque.get(x, y) != ContenidoBloque.MARCA) {
                    matrizEstadoBloque.set(x, y, EstadoBloque.DESCUBIERTO);
                    minasEcontradas++;
                } else if (matrizContenidoBloque.get(x, y) == EstadoBloque..ZERO) {
                    for (int v = 0; v < xvars.length; v++) {
                        // Verifica para evitar un ArrayIndexOutOfBoundsException
                        if ((x + xvars[v] >= 0) && (x + xvars[v] < matrizContenidoBloque.length)
                                && (y + yvars[v] >= 0) && (y + yvars[v] < matrizContenidoBloque[x].length)
                                && (bloques[x + xvars[v]][y + yvars[v]].getCubierto())) {
                            descubrirCampo(x + xvars[v], y + yvars[v]);
                        }
                    }
                }
            }
            if (pierde) {
                marcarMinas();
                tablero.detenerPartida(EstadoPartida.GANA);
            } else if (dificultad.getCantidadMinas() == minasEcontradas) {
                marcarMinas();
                tablero.detenerPartida(EstadoPartida.GANA);
            } 
        }
    }
    //TODO Interfaz
    private void mostrarMinas() {
        for (int i = 0; i < dificultad.getFil(); i++) {
            for (int j = 0; j < dificultad.getCol(); j++) {
                if (matrizContenidoBloque.get(i, j) == Bloque.FLAG && matrizContenidoBloque[i][j] != Bloque.MINA) {
                    bloques[i][j].setContent(Bloque.NO_MINA);
                    bloques[i][j].setCubierto(false);
                    tablero.aumentarNMinas();
                }
                if (matrizContenidoBloque[i][j] == Bloque.MINA && bloques[i][j].getContent() != Bloque.FLAG) {
                    bloques[i][j].setCubierto(false);
                }
            }
        }
    }
    //TODO Interfaz
    public void marcarMinas() {
        for (int i = 0; i < matrizContenidoBloque.length; i++) {
            for (int j = 0; j < matrizContenidoBloque[i].length; j++) {
                if (matrizContenidoBloque[i][j] == Bloque.MINA && bloques[i][j].getContent() != Bloque.FLAG) {
                    marcarCampo(i, j);
                }
            }
        }
    }
    
    public void marcarCampo(int x, int y) {
        if (matrizEstadoBloque.get(x, y) == EstadoBloque.CUBIERTO) {
            if (matrizEstadoBloque.get(x, y) == EstadoBloque.MARCADO) {
                matrizEstadoBloque.set(x, y, EstadoBloque.CUBIERTO) ;
                minasEcontradas--;
            } else {
                matrizEstadoBloque.set(x, y, EstadoBloque.MARCADO) ;
                minasEcontradas++;
            }
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
                if ((x + xvars[v] >= 0) && (x + xvars[v] < matrizContenidoBloque.length) && (y + yvars[v] >= 0) && (y + yvars[v] < matrizContenidoBloque[x].length) && (bloques[x + xvars[v]][y + yvars[v]].getContent() == Bloque.FLAG)) {
                    numMarcas++;
                }
            }
            if (numMarcas == Integer.parseInt("" + bloques[x][y].getContent())) {
                for (int v = 0; v < xvars.length; v++) {
                    // Verifica para evitar un ArrayIndexOutOfBoundsException
                    if ((x + xvars[v] >= 0) && (x + xvars[v] < matrizContenidoBloque.length) && (y + yvars[v] >= 0) && (y + yvars[v] < matrizContenidoBloque[x].length) && (bloques[x + xvars[v]][y + yvars[v]].getCubierto())) {
                        descubrirCampo(x + xvars[v], y + yvars[v]);
                    }
                }
            } else {
                tablero.cambiarEstadoJuego(Tablero.NORMAL);
            }
        } else {
            tablero.cambiarEstadoJuego(Tablero.NORMAL);
            if (x == xActual && y == yActual) {
                if (bloques[x][y].getCubierto() && bloques[x][y].getContent() != Bloque.FLAG) {
                    bloques[x][y].setContent(matrizContenidoBloque[x][y]);
                }
            }
        }
    }
}