package explodingteddies.Logic;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Bloque {

    private ImageIcon bloque;
    private ImageIcon bloqueDown;
    private ImageIcon mina;
    private ImageIcon marca;
    private ImageIcon noMina;
    private char content;
    private Color[] colores;
    private CampoMinado cm;
    private boolean cubierto;
    private Color grisClaro = new Color(212, 212, 212);
    private int x, y;
    private int tamanoBloque;
    private int bordeX, bordeY;
    public static final char MINA = 'M', NO_MINA = 'N', PIERDE = 'P', ZERO = '0', DOWN = 'D', FLAG = 'C', EMPTY = ' ';
    
    //TODO Interfaz
    //Asigna los paths de cada foto a las variables ImageIcon definidas anteriormente
    public Bloque(CampoMinado c, char cont, int i, int j, int tb, int xBorde, int yBorde) {
        try {
            bloque = new ImageIcon(ImageIO.read(getClass().getResource("images/bloque.gif")));
            bloqueDown = new ImageIcon(ImageIO.read(getClass().getResource("images/bloqueDown.gif")));
            mina = new ImageIcon(ImageIO.read(getClass().getResource("images/mina.gif")));
            marca = new ImageIcon(ImageIO.read(getClass().getResource("images/marca.gif")));
            noMina = new ImageIcon(ImageIO.read(getClass().getResource("images/nomina.gif")));
        } catch (IOException ex) {
            Logger.getLogger(Bloque.class.getName()).log(Level.SEVERE, null, ex);
        }
        content = cont;
        cm = c;
        cubierto = true;
        x = i;
        y = j;
        colores = new Color[9];
        inicializarColores();
        tamanoBloque = tb;
        bordeX = xBorde;
        bordeY = yBorde;
    }
    //TODO Interfaz
    //Agrega colores al array definido anteriormente
    private void inicializarColores() {
        colores[0] = grisClaro;
        colores[1] = Color.blue;
        colores[2] = new Color(0, 160, 0);
        colores[3] = Color.red;
        colores[4] = new Color(12, 53, 105);
        colores[5] = new Color(105, 53, 12);
        colores[6] = new Color(0, 192, 128);
        colores[7] = Color.black;
        colores[8] = Color.darkGray;
    }

    public void setContent(char c) {
        content = c;
    }

    public char getContent() {
        return content;
    }

    public void setCubierto(boolean cub) {
        cubierto = cub;
    }

    public boolean getCubierto() {
        return cubierto;
    }
    //TODO Interfaz
    //Pinta la ventana con cada bloque en la ventana dependiendo de su caracteristica.
    public void pintar(Graphics g) {
        if (cubierto) {
            if (content == FLAG) {
                marca.paintIcon(cm, g, y * tamanoBloque + bordeX, x * tamanoBloque + bordeY);
            } else if (content == DOWN) {
                g.setColor(grisClaro);
                g.fillRect(y * tamanoBloque + bordeX, x * tamanoBloque + bordeY, tamanoBloque, tamanoBloque);
            } else {
                bloque.paintIcon(cm, g, y * tamanoBloque + bordeX, x * tamanoBloque + bordeY);
            }
        } else {
            if (content == PIERDE) {
                g.setColor(Color.red);
                g.fillRect(y * tamanoBloque + bordeX, x * tamanoBloque + bordeY, tamanoBloque, tamanoBloque);
            } else {
                g.setColor(grisClaro);
                g.fillRect(y * tamanoBloque + bordeX, x * tamanoBloque + bordeY, tamanoBloque, tamanoBloque);
            }
            switch (content) {
                case MINA:
                    mina.paintIcon(cm, g, y * tamanoBloque + bordeX, x * tamanoBloque + bordeY);
                    break;
                case NO_MINA:
                    noMina.paintIcon(cm, g, y * tamanoBloque + bordeX, x * tamanoBloque + bordeY);
                    break;
                case PIERDE:
                    mina.paintIcon(cm, g, y * tamanoBloque + bordeX, x * tamanoBloque + bordeY);
                    break;
                case ZERO:
                    break;
                default:
                    g.setFont(new Font("Verdana", Font.BOLD, 18));
                    g.setColor(colores[Integer.parseInt("" + content)]);
                    g.drawString("" + content, y * tamanoBloque + 4 + bordeX, x * tamanoBloque + 20 + bordeY);
                    break;
            }
        }
        g.setColor(Color.darkGray);
        g.drawRect(y * tamanoBloque + bordeX, x * tamanoBloque + bordeY, tamanoBloque - 1, tamanoBloque - 1);//*/
    }
}