package explodingteddies.modelo;

import explodingteddies.modelo.tablero.Tablero;

public class Cronometro extends Thread {

    private boolean running;
    private Tablero tablero;

    // Crea thread para el cronometro
    public Cronometro(Tablero tablero) {
        running = true;
        this.tablero = tablero;
    }

    @Override
    public void run() {
        while (running) {
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                System.err.println("Exception: " + ex.getMessage());
            }
            if (running) {
//                tablero.aumentarTiempo();
            }
        }
    }

    public void detener() {
        running = false;
    }
}
