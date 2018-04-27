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
    private int cantidadMinas, fil, col;

    private Dificultad(String nombre, int codigo, int cantidadMinas, int fil, int col) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.cantidadMinas = cantidadMinas;
        this.fil = fil;
        this.col = col;
    }

    public static Dificultad convertirValor(int codigo) {
        Dificultad resultado = null;

        switch (codigo) {
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

    @Override
    public String toString() {
        return nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCodigo() {
        return codigo;
    }

    public int getCantidadMinas() {
        return cantidadMinas;
    }

    public int getFil() {
        return fil;
    }

    public int getCol() {
        return col;
    }

}
