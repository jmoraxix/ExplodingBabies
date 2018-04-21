/*
 * Programacion Concurrente Cliente Servidor
 * 
 * Melanie Benvides
 * Jose Mora Loria
 * Thomas White
 * 
 * Exploding Teddies
 */
package explodingteddies.modelo.tablero;

/**
 *
 * @author josmora
 */
public enum EstadoPartida {
    
    JUGANDO("Jugando"),
    PIERDE("Sigue participando"),
    GANA("Ganador");
    
    private String estadoPartida;

    private EstadoPartida(String estadoPartida) {
        this.estadoPartida = estadoPartida;
    }

    public String getEstadoPartida() {
        return estadoPartida;
    }

    @Override
    public String toString() {
        return estadoPartida;
    }
    
    
    
}
