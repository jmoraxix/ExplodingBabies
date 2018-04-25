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

import explodingteddies.modelo.tablero.Tablero;
import java.util.ArrayList;

/**
 *
 * @author jmora
 */
public class Partida {

    private static int CANTIDAD_PARTIDAS = 0;
    private final int codigoPartida;
    private ArrayList<Jugador> jugadores = new ArrayList<>();
    private ArrayList<Jugador> visitas = new ArrayList<>();

    private Tablero tablero;
    private final Dificultad dificultad;

    /**
     *
     * @param dificultad
     */
    public Partida(Dificultad dificultad) {
        this.codigoPartida = ++Partida.CANTIDAD_PARTIDAS;
        this.dificultad = dificultad;
    }

    /**
     *
     * @param dificultad
     * @param jugador
     */
    public Partida(Dificultad dificultad, Jugador jugador) {
        this(dificultad);
        this.jugadores.add(jugador);
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public int getCantidadJugadores() {
        return this.jugadores.size();
    }

    public boolean addJugador(Jugador jugador) {
        if (this.getCantidadJugadores() <= 1) {
            this.jugadores.add(jugador);
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Jugador> getVisitas() {
        return visitas;
    }

    public void addVisita(Jugador visita) {
        this.visitas.add(visita);
    }

    public int getCodigoPartida() {
        return codigoPartida;
    }

}
