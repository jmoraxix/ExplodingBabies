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

import explodingteddies.modelo.tablero.CampoMinado;
import explodingteddies.modelo.tablero.ContenidoBloque;
import explodingteddies.modelo.tablero.EstadoBloque;
import explodingteddies.modelo.tablero.EstadoPartida;
import explodingteddies.modelo.tablero.Matriz;
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

    private CampoMinado campoMinado;
    private final Dificultad dificultad;
    private EstadoPartida estadoPartida;

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
    
    public void procesarJugada(int x, int y, boolean clickDer){
        this.campoMinado.procesarJugada(x, y, clickDer);
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }
    
    public EstadoPartida getEstadoPartida() {
        return estadoPartida;
    }

    public int getCantidadJugadores() {
        return this.jugadores.size();
    }
    
    public Jugador getJugador1(){
        return getCantidadJugadores()>0?this.jugadores.get(0):null;
    }
    
    public Jugador getJugador2(){
        return getCantidadJugadores()>1?this.jugadores.get(1):null;
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

    public Dificultad getDificultad() {
        return dificultad;
    }

    public CampoMinado getCampoMinado() {
        return this.campoMinado;
    }
             
    // Getters de las matrices
    public Matriz<ContenidoBloque> getMatrizContenidoBloque() {
        return campoMinado.getMatrizContenidoBloque();
    }

    public Matriz<Integer> getMatrizAdyacencias() {
        return campoMinado.getMatrizAdyacencias();
    }

    public Matriz<EstadoBloque> getMatrizEstadoBloque() {
        return campoMinado.getMatrizEstadoBloque();
    }
    

}
