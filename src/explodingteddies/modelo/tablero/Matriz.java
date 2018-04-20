/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package explodingteddies.modelo.tablero;

/**
 *
 * @author jmora
 */
public class Matriz<T> {

    private int tamX, tamY;

    private T[][] values;

    // Inicializamos la matriz con X por Y tamano
    public Matriz(int x, int y, T defaultVal) {
        values = (T[][]) new Object[x][y];
        this.tamX = x;
        this.tamY = y;
        for (int i = 0; i < this.tamX; i++) {
            for (int j = 0; j < this.tamY; j++) {
                this.insert(i, j, defaultVal);
            }
        }
//        Util.log("Matriz inicializada. Tamaño " + this.tamX + "," + this.tamY);
    }

    public T get(int x, int y) {
        return values[x][y];
    }

    // Insertamos T valor en coordenadas X y Y. Metodo sincronizado para evitar que se inserten varios valores en la misma casilla al mismo tiempo.
    public synchronized void insert(int x, int y, T value) {
        values[x][y] = value;
    }

    public int getTamX() {
        return tamX;
    }

    public int getTamY() {
        return tamY;
    }

}
