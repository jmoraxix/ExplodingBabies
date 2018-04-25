package explodingteddies.modelo.tablero;

import explodingteddies.modelo.Dificultad;

/**
 *
 * @author jmora
 */
public class Tablero {

    // Variables del juego
    private final Dificultad dificultad;
    private final CampoMinado campoMinado;
    private EstadoPartida estadoPartida;

    // Constructor de tablero vacio
    /**
     *
     * @param dificultad
     */
    public Tablero(Dificultad dificultad) {
        this.dificultad = dificultad;
        campoMinado = new CampoMinado(this, dificultad);
        estadoPartida = EstadoPartida.JUGANDO;
    }

    /**
     *
     * @param estado
     */
    public void detenerPartida(EstadoPartida estado) {
        switch (estado) {
            case GANA:
                this.estadoPartida = EstadoPartida.GANA;
                break;
            case PIERDE:
                this.estadoPartida = EstadoPartida.PIERDE;
                break;
        }
    }

    public Dificultad getDificultad() {
        return dificultad;
    }

    public CampoMinado getCampoMinado() {
        return campoMinado;
    }

    public EstadoPartida getEstadoPartida() {
        return estadoPartida;
    }
}
