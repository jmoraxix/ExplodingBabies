package explodingteddies.modelo;

import explodingteddies.cliente.vista.TableroController;

public class Cronometro extends Thread {

    private boolean running;
    private boolean startTimer;
    private TableroController tablero;
    private int tiempo;

    // Crea thread para el cronometro
    public Cronometro(TableroController tablero) {
        tiempo = 0;
        running = true;
        startTimer = false;
        this.tablero = tablero;
    }

    @Override
    public void run() {
        while (running) {
            while (startTimer) {
                try {
                    sleep(1000);
                } catch (InterruptedException ex) {
                    System.err.println("Exception: " + ex.getMessage());
                }
                if (running) {
                    tablero.setTiempo(++tiempo);
                }
            }
        }
    }
    
    public void iniciarCronometro(){
        this.startTimer = true;
    }

    public void detenerCronometro() {
        this.running = false;
    }
}
