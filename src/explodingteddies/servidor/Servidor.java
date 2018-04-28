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

import explodingteddies.modelo.Partida;
import explodingteddies.util.Util;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author jmora
 */
public class Servidor extends Thread {

    //Variables de TCP
    private static int SERVER_PORT = Util.SERVER_PORT;
    private ServerSocket serverSocket;
    private Socket socket;
    private ExecutorService service;
    private ArrayList<ClienteServidor> clientes = new ArrayList<>();

    // Variables de la aplicacion del servidor.
    private MainServidor app;
    private ArrayList<Partida> partidas = new ArrayList<>();

    /**
     * Creates new form Servidor
     */
    public Servidor(MainServidor app) {
        this.app = app;

        try {
            agregarLog("Inicia servidor");
            service = Executors.newCachedThreadPool();
            serverSocket = new ServerSocket(SERVER_PORT);
            service.submit(this);
        } catch (IOException ex) {
            agregarError(ex.toString());
        }
    }

    @Override
    public void run() {
        agregarClientes();
    }

    private void agregarClientes() {
        try {
            while (true) {
                socket = serverSocket.accept();
                agregarLog("Cliente entrante");
                ClienteServidor client = new ClienteServidor(this, socket);
                service.submit(client);
                this.clientes.add(client);
                agregarLog("Cliente agregado");
            }
        } catch (IOException ex) {
            agregarError(ex.toString());
        }
    }

    // Getters & setters
    public ArrayList<ClienteServidor> getClientes() {
        return clientes;
    }

    public ArrayList<Partida> getPartidas() {
        return partidas;
    }

    public void addPartida(Partida partida) {
        this.partidas.add(partida);
    }
    
    

    /**
     * Agrega un log a la consola y/o interfaz
     *
     * @param msj Mensaje a mostrar
     */
    public synchronized void agregarLog(String msj) {
        app.addMessage(msj);
    }

    /**
     *
     * @param msj
     */
    public synchronized void agregarError(String msj) {
        app.addMessage("ERROR: " + msj);
    }
}
