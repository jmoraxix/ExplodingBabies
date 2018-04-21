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
public class Jugador {

    //Variables
    private String jugador;

    //Constructor
    public Jugador(String usuario) {
        this.jugador = usuario;
    }

    public String getJugador() {
        return jugador;
    }

    public void setJugador(String jugador) {
        this.jugador = jugador;
    }
}
