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

    // Variables de la partida;
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
//                System.out.println(entrada);
                String[] datos = entrada.split(";"); // Divide los datos de la entrada en cada ';'

                switch (Notificacion.convertirValor(Integer.parseInt(datos[0]))) {
                    case CLIENTE_ENTRA_USUARIO:
                        entraUsuario(datos[1], datos[2]);
                        break;
                    case CLIENTE_HACE_JUGADA:
                        procesaJugada(datos[1], datos[2], datos[3]);
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
            mensaje = Notificacion.SERVIDOR_ABRE_PARTIDA.getValor() + ";";
        } else {
            mensaje = Notificacion.SERVIDOR_CIERRA_PARTIDA.getValor() + ";";
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

    private void procesaJugada(String strX, String strY, String strClickDer) {
        int x = Integer.valueOf(strX);
        int y = Integer.valueOf(strY);
        boolean clickDer = strClickDer.equals("1");
        partida.procesarJugada(x, y, clickDer);

        switch (partida.getEstadoPartida()) {
            case GANA:
            case PIERDE:
                break;
            case JUGANDO:
            default:
                Jugador rival = jugador;
                for (Jugador j : partida.getJugadores()) {
                    if (!j.equals(this.jugador)) {
                        rival = j;
                    }
                }
                for (ClienteServidor cliente : servidor.getClientes()) {
                    for (Jugador j : cliente.getPartida().getJugadores()) {
                        if (j.equals(rival)) {
                            cliente.enviaJugada(jugador, partida);
                        }
                    }
                }
                this.enviaJugada(jugador, this.partida);
                break;
        }
    }

    public void enviaJugada(Jugador j, Partida p) {
        try {
            //System.out.println("notificarCambioColaACliente, " + notificacion.getValor());
            out.writeUTF(Notificacion.SERVIDOR_ENVIA_JUGADA + ";" + j.getJugador() + ";" + Util.getGson().toJson(partida) + "\n");
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
