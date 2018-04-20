package explodingteddies.modelo.tablero;


import explodingteddies.modelo.Cronometro;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Tablero extends JFrame implements ActionListener {
    // Variables de la clase

    private int xBorde = 6;
    private int yBorde = 57;
    private Container c;
    private CampoMinado cm;
    private ImageIcon[] imagenesBoton;
    public static final int GANA = 0, PIERDE = 1, NORMAL = 2, ASOMBRA = 3;
    private JButton nuevoJuego;
    private int bordeX, bordeY, defaultX, defaultY;
    private boolean defaultXYSet;
    private int yControlSize = 56;
    private int nMinas = 0;
    private Marcador marcadorMinas, marcadorTiempo;
    private int tiempo;
    private Cronometro cronometro;
    private int cmFil = 9, cmCol = 9, cmMinas = 10;
    private boolean jugando = false;
    private BarraMenu menu;
    // Elementos en la ventana de pedir datos
    private JPanel panelito;
    private JLabel filasL, columnasL, minasL;
    private JTextField filasT, columnasT, minasT;
    private JRadioButton porcentajeRB, minasRB;
    private ButtonGroup minasBG;
    // Instrucciones y Acerca de...
    private JTextArea instruccionesTA;
    private ImageIcon imagenLogo;

    // TODO constructor
    public Tablero() {
        super("Busca Minas");
        bordeX = 10;
        bordeY = 10;
        c = getContentPane();
        c.setLayout(null);
        cm = new CampoMinado(this, cmFil, cmCol, cmMinas);
        cm.setBounds(bordeX, bordeY + yControlSize, cm.getWidth(), cm.getHeight());
        c.add(cm);
        
        defaultXYSet = false;

        menu = new BarraMenu();
        setJMenuBar(menu);
        
        // Elementos del boton de estado del jugador.
        imagenesBoton = new ImageIcon[4];
        try {
            imagenesBoton[GANA] = new ImageIcon(ImageIO.read(getClass().getResource("images/1a.gif")));
            imagenesBoton[PIERDE] = new ImageIcon(ImageIO.read(getClass().getResource("images/bones.gif")));
            imagenesBoton[NORMAL] = new ImageIcon(ImageIO.read(getClass().getResource("images/biggrin.gif")));
            imagenesBoton[ASOMBRA] = new ImageIcon(ImageIO.read(getClass().getResource("images/tic.gif")));

            imagenLogo = new ImageIcon(ImageIO.read(getClass().getResource("images/logo.gif")));
        } catch (IOException ex) {
            Logger.getLogger(Tablero.class.getName()).log(Level.SEVERE, null, ex);
        }

        nuevoJuego = new JButton(imagenesBoton[GANA]);
        nuevoJuego.addActionListener(this);
        nuevoJuego.setHorizontalAlignment(SwingConstants.CENTER);
        nuevoJuego.setVerticalAlignment(SwingConstants.CENTER);
        nuevoJuego.setFocusPainted(false);

        marcadorMinas = new Marcador(3);

        marcadorTiempo = new Marcador(3);

        cronometro = new Cronometro("Cronometro", this);
        cronometro.start();

        setNivel(10, 10, 15);

        // Elementos del menu de seleccion de nivel
        panelito = new JPanel();
        panelito.setLayout(new GridLayout(4, 2));

        filasL = new JLabel("Filas (10-30): ");
        panelito.add(filasL);
        filasT = new JTextField();
        panelito.add(filasT);
        columnasL = new JLabel("Columnas (10-30): ");
        panelito.add(columnasL);
        columnasT = new JTextField();
        panelito.add(columnasT);
        minasL = new JLabel("Minas (10-75%): ");
        panelito.add(minasL);
        minasT = new JTextField();
        panelito.add(minasT);
        minasBG = new ButtonGroup();
        porcentajeRB = new JRadioButton("Porcentaje");
        porcentajeRB.setSelected(true);
        minasBG.add(porcentajeRB);
        panelito.add(porcentajeRB);
        minasRB = new JRadioButton("Minas");
        minasBG.add(minasRB);
        panelito.add(minasRB);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
    //TODO Interfaz.
    private void ajustarTamano() {
        setVisible(false);

        c.removeAll();

        cm = null;
        cm = new CampoMinado(this, cmFil, cmCol, cmMinas);
        cm.setBounds(bordeX, bordeY + yControlSize, cm.getWidth(), cm.getHeight());
        c.add(cm);

        // Coloca la ventana centrada en la pantalla//*/
        Dimension screenRes = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenRes.getWidth();
        int screenHeight = (int) screenRes.getHeight();
        int width = cm.getWidth() + xBorde + bordeX * 2;
        int height = cm.getHeight() + yBorde + yControlSize + bordeY * 2;
        
        // Calculate default window position
        if (!defaultXYSet) {
            defaultX = (screenWidth-width)/2;
            defaultY = (screenHeight-height)/2;
            defaultXYSet = true;
        }
        int curX = getX();
        int curY = getY();
        
        int xPos = 0;
        int yPos = 0;
        
        if (curX>0&&curX!=defaultX) {
            xPos = curX;
        }
        else {
            xPos = (screenWidth-width)/2;
            defaultX = xPos;
        }
        if (curY>0&&curY!=defaultY) {
            yPos = curY;
        }
        else {
            yPos = (screenHeight-height)/2;
            defaultY = yPos;
        }
        
        setBounds(xPos, yPos, width, height);

        nuevoJuego.setBounds((getWidth() - 36) / 2 - xBorde / 2, 10, 36, 36);
        c.add(nuevoJuego);

        marcadorMinas.setBounds(10, 10, marcadorMinas.getWidth(), marcadorMinas.getHeight());
        c.add(marcadorMinas);

        marcadorTiempo.setBounds(getWidth() - bordeX - marcadorTiempo.getWidth() - xBorde, 10, marcadorMinas.getWidth(), marcadorMinas.getHeight());
        c.add(marcadorTiempo);

        setResizable(false);
        setVisible(true);

        nuevoJuego();
    }
    //TODO Logica
    private void actualizarNMinas() {
        marcadorMinas.setMarcador(nMinas);
        marcadorMinas.repaint();
    }
    //TODO Logica
    public void setNMinas(int minas) {
        nMinas = minas;
        actualizarNMinas();
    }
    //TODO Logica
    public void aumentarNMinas() {
        nMinas++;
        actualizarNMinas();
    }
    //TODO Logica
    public void disminuirNMinas() {
        nMinas--;
        actualizarNMinas();
    }
    //TODO Logica
    public void iniciarTiempo() {
        cronometro.iniciar();
        jugando = true;
    }
    //TODO Logica
    public void aumentarTiempo() {
        if (jugando) {
            tiempo++;
            marcadorTiempo.setMarcador(tiempo);
            marcadorTiempo.repaint();
        }
    }
    //TODO Logica
    public void detenerTiempo() {
        jugando = false;
        cronometro.detener();
    }
    //TODO Logica
    public void reiniciarCronometro() {
        tiempo = 0;
        marcadorTiempo.setMarcador(tiempo);
        marcadorTiempo.repaint();
    }
    //TODO Interfaz
    public void paint(Graphics g) {
        c.repaint();
        menu.repaint();
        c.setBackground(new Color(192, 192, 192));

        g.setColor(Color.white);
        g.fillRect(xBorde / 2 + 0, yBorde / 2 + 0, 3, getHeight());
        g.fillRect(xBorde / 2 + 0, yBorde / 2 + 0, getWidth(), 3);

        g.setColor(Color.darkGray);
        g.fillRect(xBorde / 2 + getWidth() - 3, yBorde / 2 + 3, 3, getHeight() - 3);
        g.fillRect(xBorde / 2 + 0, yBorde / 2 + getHeight() - 3, getWidth(), 3);
    }
    //TODO Interfaz
    public void cambiarImagen(int nImagen) {
        nuevoJuego.setIcon(imagenesBoton[nImagen]);
    }
    //TODO Interfaz
    public void actionPerformed(ActionEvent e) {
        nuevoJuego();
    }
    //TODO Logica
    private void setNivel(int f, int c, int m) {
        cmFil = f;
        cmCol = c;
        cmMinas = m;
        ajustarTamano();
    }
    //TODO Interfaz
    private void nuevoJuego() {
        cm.iniciarJuego();
        detenerTiempo();
    }
    //TODO Interfaz
    private class BarraMenu extends JMenuBar implements ActionListener {

        private JMenu juegoM;
        private JMenuItem principianteMI, intermedioMI, avanzadoMI;
        private JMenuItem nuevoMI, salirMI;

        public BarraMenu() {
            juegoM = new JMenu("Juego");
            juegoM.setMnemonic('J');

            nuevoMI = new JMenuItem("Nuevo");
            nuevoMI.setMnemonic('N');
            nuevoMI.addActionListener(this);
            nuevoMI.setAccelerator(KeyStroke.getKeyStroke("control N"));
            juegoM.add(nuevoMI);
            juegoM.addSeparator();

            principianteMI = new JMenuItem("Principiante");
            principianteMI.setMnemonic('P');
            principianteMI.addActionListener(this);
            principianteMI.setAccelerator(KeyStroke.getKeyStroke("control 1"));
            juegoM.add(principianteMI);

            intermedioMI = new JMenuItem("Intermedio");
            intermedioMI.setMnemonic('I');
            intermedioMI.addActionListener(this);
            intermedioMI.setAccelerator(KeyStroke.getKeyStroke("control 2"));
            juegoM.add(intermedioMI);

            avanzadoMI = new JMenuItem("Avanzado");
            avanzadoMI.setMnemonic('A');
            avanzadoMI.addActionListener(this);
            avanzadoMI.setAccelerator(KeyStroke.getKeyStroke("control 3"));
            juegoM.add(avanzadoMI);
            juegoM.addSeparator();

            salirMI = new JMenuItem("Salir");
            salirMI.setMnemonic('S');
            salirMI.addActionListener(this);
            salirMI.setAccelerator(KeyStroke.getKeyStroke("control W"));
            juegoM.add(salirMI);

            add(juegoM);
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == nuevoMI) {
                nuevoJuego();
            } else if (e.getSource() == principianteMI) {
                setNivel(10, 10, 15);
            } else if (e.getSource() == intermedioMI) {
                setNivel(16, 16, 40);
            } else if (e.getSource() == avanzadoMI) {
                setNivel(16, 30, 99);
            } else if (e.getSource() == salirMI) {
                System.exit(0);
            } 
        }
    }
}