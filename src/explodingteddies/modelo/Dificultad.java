/*
 * Programacion Concurrente Cliente Servidor
 *
 * Melanie Benvides
 * Jose Mora Loria
 * Thomas White
 *
 * Exploding Teddies
 */
package explodingteddies.modelo;

/**
 *
 * @author josmora
 */
public enum Dificultad {

    FACIL("Facil", 1, 10, 9, 9),
    MEDIO("Medio", 2, 18, 9, 16),
    DIFICIL("Dificil", 3, 30, 16, 16),
    DARK_SOULS("Dark Souls", 4, 180, 16, 16),
    CHUCK_NORRIS("Chuck Norris", 5, 255, 16, 16);

    private String nombre;
    private int codigo;
    private int cantidadMinas, x, y;

    private Dificultad(String nombre, int codigo, int cantidadMinas, int x, int y) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.cantidadMinas = cantidadMinas;
        this.x = x;
        this.y = y;
    }

    public static Dificultad convertirValor(int valor) {
        Dificultad resultado = null;

        switch (valor) {
            case 1:
                resultado = FACIL;
                break;
            case 2:
                resultado = MEDIO;
                break;
            case 3:
                resultado = DIFICIL;
                break;
            case 4:
                resultado = DARK_SOULS;
                break;
            case 5:
                resultado = CHUCK_NORRIS;
                break;
            default:
                break;
        }

        return resultado;
    }
}
