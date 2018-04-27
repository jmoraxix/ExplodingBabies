/*
 * Programacion Concurrente Cliente Servidor
 * 
 * Melanie Benvides
 * Jose Mora Loria
 * Thomas White
 * 
 * Exploding Teddies
 */
package explodingteddies.cliente;

import explodingteddies.modelo.Dificultad;
import explodingteddies.modelo.Jugador;
import explodingteddies.modelo.Notificacion;
import static explodingteddies.modelo.Notificacion.*;
import explodingteddies.modelo.Partida;
import explodingteddies.servidor.Servidor;
import explodingteddies.util.Util;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cliente TCP que se conecta con el servidor
 *
 * @author jmora
 */
public class Cliente extends Thread {

    // Variables TCP
    protected static String SERVER_IP = Util.SERVER_IP;
    protected static int SERVER_PORT = Util.SERVER_PORT;
    protected ObjectOutputStream out;
    protected ObjectInputStream in;
    protected Socket socket;

    // Variables de la aplicacion
    private MainCliente application;
    private Partida partida;
    private Jugador jugador;

    /**
     * Declara un nuevo cliente. Crea la conexion con el servidor y define los streams
     *
     * @param application Recibe la aplicación principal para interactuar con el usuario
     */
    public Cliente(MainCliente application) {
        this.application = application;

        try {
            System.out.println("Conecting to: " + SERVER_IP + ":" + SERVER_PORT);
            socket = new Socket(SERVER_IP, SERVER_PORT);
            createStream();

        } catch (IOException ex) {
            Logger.getLogger(Cliente.class
                    .getName()).log(Level.SEVERE, null, ex);
            //TODO Arreglar notificacion
            //JOptionPane.showInputDialog(this, "No se ha podido conectarse con el servidor", "Error al conectarse con el servidor", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Recibe un dato de entrada
                String entrada = in.readUTF();
//                System.out.println(entrada);
                String[] datos = entrada.split(";"); // Divide los datos de la entrada en cada ';'

                switch (Notificacion.convertirValor(Integer.parseInt(datos[0]))) {
                    case ABRE_PARTIDA:
                        abrirPartida(datos[1]);
                        break;
                    case CIERRA_PARTIDA:
                        cerrarPartida(datos[1]);
                        break;
                    case ENVIA_JUGADA:
//                        recibeJugada(datos[1]);
                        break;
                    case TERMINA_PARTIDA:
//                        terminaPartida(datos[1]);
                        break;
                    default:
                        break;
                }
            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //Metodos de TCP
    private void createStream() {
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

        } catch (IOException ex) {
            Logger.getLogger(Cliente.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void entraUsuario(Dificultad dificultad, String usuario) {
        try {
            out.writeUTF(Notificacion.ENTRA_USUARIO + ";" + dificultad.toString() + ";" + usuario + "\n");
            out.flush();
            //System.out.println("notificarCambioColaACliente");
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void abrirPartida(String json) {
        Partida partida = Util.getGson().fromJson(json, Partida.class);
    }

    private void cerrarPartida(String json) {
        Partida partida = Util.getGson().fromJson(json, Partida.class);

    }
}
