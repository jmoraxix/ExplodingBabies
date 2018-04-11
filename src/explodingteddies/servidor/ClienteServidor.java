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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import explodingteddies.model.Notificacion;

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
                    case CREA_USUARIO:
                        break;
                    case INICIA_SESION:
                        break;
                    case PEDIR_RESERVACIONES:
                        break;
                    case ELIMINA_RESERVACION:
                        break;
                    case PEDIR_PELICULA:
                        break;
                    case PEDIR_HORARIOS:
                        break;
                    case PEDIR_COMIDA:
                        break;
                    case CREAR_RESERVACION:
                        break;
                    default:
                        throw new AssertionError(Notificacion.convertirValor(Integer.parseInt(datos[0])).name());
                }

            } catch (IOException ex) {
                //Logger.getLogger(ClienteServidor.class.getName()).log(Level.SEVERE, null, ex);
                //System.out.println("Cliente desconectado");
                servidor.agregarLog("Cliente desconectado");
                isRunning = false;
            }
        }
    }

    public void notificarCambioColaACliente(Notificacion notificacion, final String cantidad) {
        try {
            //System.out.println("notificarCambioColaACliente, " + notificacion.getValor());
            out.writeUTF(notificacion.getValor() + ";" + cantidad + "\n");
            out.flush();
            //System.out.println("notificarCambioColaACliente");
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
