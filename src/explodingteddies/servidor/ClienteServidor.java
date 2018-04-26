/*
 * Programacion Concurrente Cliente Servidor
 *
 * Melanie Benvides
 * Jose Mora Loria
 * Thomas White
 *
 * Exploding Teddies
 */
package explodingteddies.servidor;

import explodingteddies.modelo.Dificultad;
import explodingteddies.modelo.Jugador;
import explodingteddies.modelo.Notificacion;
import explodingteddies.modelo.Partida;
import explodingteddies.util.Util;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jmora
 */
public class ClienteServidor extends Thread {

    private ObjectOutputStream out;
    private ObjectInputStream in;
    private final Socket socket;
    private final Servidor servidor;
    private boolean isRunning = true;

    // Variables partida;
    private Partida partida = null;
    private Jugador jugador = null;

    /**
     *
     * @param servidor
     * @param socket
     */
    public ClienteServidor(final Servidor servidor, final Socket socket) {
        this.servidor = servidor;
        this.socket = socket;
        createStream();
    }

    private void createStream() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                // Recibe un dato de entrada
                String entrada = in.readUTF();
                System.out.println(entrada);
                String[] datos = entrada.split(";"); // Divide los datos de la entrada en cada ';'

                switch (Notificacion.convertirValor(Integer.parseInt(datos[0]))) {
                    case ENTRA_USUARIO:
                        entraUsuario(datos[1], datos[2]);
                        break;
                    case HACE_JUGADA:
                        break;
                    case ENTRA_VISITA:
                        break;
                    default:
                        throw new AssertionError(Notificacion.convertirValor(Integer.parseInt(datos[0])).name());
                }
            } catch (IOException ex) {
                servidor.agregarLog("Cliente desconectado");
                isRunning = false;
            }
        }
    }

    /**
     *
     * @param strDif
     * @param strJugador
     */
    public void entraUsuario(final String strDif, final String strJugador) {
        Dificultad dificultad = Dificultad.convertirValor(Integer.valueOf(strDif));

        boolean existePartida = false;
        for (Partida p : servidor.getPartidas()) {
            for (Jugador j : p.getJugadores()) {
                if (j.getJugador().equals(strJugador)) {
                    existePartida = true;
                    partida = p;
                    jugador = j;
                }
            }
        }

        String mensaje = "";
        if (!existePartida) {
            partida = new Partida(dificultad);
            jugador = new Jugador(strJugador);
            mensaje = Notificacion.ABRE_PARTIDA.getValor() + ";";
        } else {
            mensaje = Notificacion.CIERRA_PARTIDA.getValor() + ";";
        }

        try {
            //System.out.println("notificarCambioColaACliente, " + notificacion.getValor());
            out.writeUTF(mensaje + Util.getGson().toJson(partida) + "\n");
            out.flush();
            //System.out.println("notificarCambioColaACliente");
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Getters y setters
    public Partida getPartida() {
        return partida;
    }

    public Jugador getJugador() {
        return jugador;
    }

}
