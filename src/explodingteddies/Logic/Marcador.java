package explodingteddies.Logic;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Marcador extends JPanel {

    private int digits;
    private ImageIcon[] digitos;
    private int marcador;
    private int xBorde = 6;
    private int yBorde = 6;
    private int digitXSize = 19;
    private int digitYSize = 30;
    
    //TODO Interfaz
    //Consigue los paths de las imaegenes para el marcador
    public Marcador(int nDigitos) {
        digits = nDigitos;
        digitos = new ImageIcon[11];
        try {
            digitos[0] = new ImageIcon(ImageIO.read(getClass().getResource("images/digitos/d0.gif")));
            digitos[1] = new ImageIcon(ImageIO.read(getClass().getResource("images/digitos/d1.gif")));
            digitos[2] = new ImageIcon(ImageIO.read(getClass().getResource("images/digitos/d2.gif")));
            digitos[3] = new ImageIcon(ImageIO.read(getClass().getResource("images/digitos/d3.gif")));
            digitos[4] = new ImageIcon(ImageIO.read(getClass().getResource("images/digitos/d4.gif")));
            digitos[5] = new ImageIcon(ImageIO.read(getClass().getResource("images/digitos/d5.gif")));
            digitos[6] = new ImageIcon(ImageIO.read(getClass().getResource("images/digitos/d6.gif")));
            digitos[7] = new ImageIcon(ImageIO.read(getClass().getResource("images/digitos/d7.gif")));
            digitos[8] = new ImageIcon(ImageIO.read(getClass().getResource("images/digitos/d8.gif")));
            digitos[9] = new ImageIcon(ImageIO.read(getClass().getResource("images/digitos/d9.gif")));
            digitos[10] = new ImageIcon(ImageIO.read(getClass().getResource("images/digitos/dmenos.gif")));
        } catch (IOException ex) {
            Logger.getLogger(Marcador.class.getName()).log(Level.SEVERE, null, ex);
        }

        int width = digits * digitXSize + xBorde;
        int height = digitYSize + yBorde;
        setBounds(0, 0, width, height);
    }
    //TODO Interfaz
    public void setMarcador(int m) {
        marcador = m;
    }
    //TODO Interfaz
    public void paint(Graphics g) {
        super.paintComponent(g);

        int[] d = new int[digits];
        int divisor = 1;
        int modulo = 10;

        for (int i = d.length - 1; i > 0; i--) {
            d[i] = Math.abs((marcador % modulo) / divisor);
            modulo *= 10;
            divisor *= 10;
        }
        if (marcador >= 0) {
            d[0] = (marcador % modulo) / divisor;
        } else {
            d[0] = 10;
        }

        for (int i = 0; i < d.length; i++) {
            digitos[d[i]].paintIcon(this, g, xBorde / 2 + i * digitXSize, yBorde / 2);
        }
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, xBorde / 2, getHeight());
        g.fillRect(0, 0, getWidth(), yBorde / 2);

        g.setColor(Color.white);
        g.fillRect(getWidth() - xBorde / 2, 0, xBorde / 2, getHeight() - yBorde / 2);
        g.fillRect(xBorde / 2, getHeight() - yBorde / 2, getWidth() - xBorde / 2, yBorde / 2);
    }
}