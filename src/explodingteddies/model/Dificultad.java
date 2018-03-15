/*
 * Programacion Concurrente Cliente Servidor
 * 
 * Melanie Benvides
 * Jose Mora Loria
 * Thomas White
 * 
 * Exploding Teddies
 */
package explodingteddies.model;

/**
 *
 * @author josmora
 */
public enum Dificultad {

    FACIL("Facil", 10, 9, 9), MEDIO("Medio", 18, 9, 15), DIFICIL("Dificil", 30, 16, 16), DARK_SOULS();
    
    private String nombre;
    private int cantidadMinas, x, y;

    private Dificultad(String nombre, int cantidadMinas, int x, int y) {
        this.nombre = nombre;
        this.cantidadMinas = cantidadMinas;
        this.x = x;
        this.y = y;
    }

    
    
}
