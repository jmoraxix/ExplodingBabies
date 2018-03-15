/*
 * Programacion Concurrente Cliente Servidor
 * 
 * Jose David Mora Loria
 * Adrian Ruiz Porras
 * Carlos Rodriguez Otoya
 * 
 */
package explodingteddies.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import proyectocine.util.Util;

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

    /**
     * Creates new form Servidor
     */
    public Servidor() {
        //Inicializa la lista enlazada para almacenar clientes
        //clientes = new ListaEnlazadaClientes();

        try {
            agregarLog("Inicia servidor");
            service = Executors.newCachedThreadPool();
            serverSocket = new ServerSocket(SERVER_PORT);
            service.submit(this);
        } catch (IOException ex) {
            agregarError(ex.toString());
        }

//        new Thread(this);
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

    //Metodos de TCP
    //Metodos para manejo de la interfaz
    /**
     * Agrega un log a la consola y/o interfaz
     *
     * @param msj Mensaje a mostrar
     */
    public synchronized void agregarLog(String msj) {
        Date fecha = new Date(System.currentTimeMillis());
//        this.txtLog = fecha.toString() + " - " + msj + "\n" + txtLog;
//        this.txtCajaLog.setText(txtLog);
        System.out.println(fecha.toString() + " - " + msj + "\n");
    }

    /**
     *
     * @param msj
     */
    public synchronized void agregarError(String msj) {
        Date fecha = new Date(System.currentTimeMillis());
//        this.txtLog = fecha.toString() + " - " + "ERROR: " + msj + "\n" + txtLog;
//        this.txtCajaLog.setText(txtLog);
        System.out.println(fecha.toString() + " - " + "ERROR: " + msj + "\n");
    }
}
