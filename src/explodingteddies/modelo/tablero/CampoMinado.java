package explodingteddies.modelo.tablero;

import explodingteddies.modelo.Dificultad;
import explodingteddies.modelo.Partida;

/**
 *
 * @author jmora
 */
public class CampoMinado {

    // Variables del juego
    private Partida partida;
    private Matriz<ContenidoBloque> matrizContenidoBloque;
    private final Matriz<Integer> matrizAdyacencias;
    private Matriz<EstadoBloque> matrizEstadoBloque;
    private final Dificultad dificultad;
    private boolean jugando = true;
    private int minasEcontradas;
    private int camposDescubiertos;

    // Pares ordenados para revisar adyacencias
    private final int[] xvars = {-1, 0, 1, -1, 1, -1, 0, 1};
    private final int[] yvars = {-1, -1, -1, 0, 0, 1, 1, 1};

    /**
     *
     * @param tablero
     * @param dificultad
     */
    public CampoMinado(Partida partida, Dificultad dificultad) {
        this.partida = partida;
        this.dificultad = dificultad;

        this.minasEcontradas = 0;
        this.camposDescubiertos = 0;

        matrizContenidoBloque = new Matriz<>(dificultad.getFil(), dificultad.getCol(), ContenidoBloque.NORMAL);
        matrizAdyacencias = new Matriz<>(dificultad.getFil(), dificultad.getCol(), 0);
        matrizEstadoBloque = new Matriz<>(dificultad.getFil(), dificultad.getCol(), EstadoBloque.CUBIERTO);

        ponerMinas();
        calcularNumeros();
    }

    // Coloca las minas al azar.
    private void ponerMinas() {
        int contador = 0;
        while (contador < dificultad.getCantidadMinas()) {
            int i = (int) (Math.random() * dificultad.getFil());
            int j = (int) (Math.random() * dificultad.getCol());
            if (matrizContenidoBloque.get(i, j) != ContenidoBloque.MINA) {
                matrizContenidoBloque.set(i, j, ContenidoBloque.MINA);
                matrizAdyacencias.set(i, j, -1); // -1 en la adyacencia indica que hay una mina
                contador++;
            }
        }
    }

    // Coloca el numero de minas cercanas en las celdas que no tienen minas.
    private void calcularNumeros() {
        for (int i = 0; i < dificultad.getFil(); i++) {
            for (int j = 0; j < dificultad.getCol(); j++) {

                if (matrizContenidoBloque.get(i, j) != ContenidoBloque.MINA) {
                    int cantMinas = 0;
                    for (int v = 0; v < xvars.length; v++) {
                        // Verifica para evitar un ArrayIndexOutOfBoundsException
                        if ((i + xvars[v] >= 0) && (i + xvars[v] < dificultad.getFil())
                                && (j + yvars[v] >= 0) && (j + yvars[v] < dificultad.getCol())
                                && (matrizContenidoBloque.get(i + xvars[v], j + yvars[v]) == ContenidoBloque.MINA)) {
                            cantMinas++;
                        }
                    }

                    matrizAdyacencias.set(i, j, cantMinas);
                }
            }
        }
    }

    // Hace visible un campo y su contenido
    /**
     *
     * @param x
     * @param y
     */
    public void descubrirCampo(int x, int y) {
        if (jugando) {
            boolean pierde = false;

            if (matrizEstadoBloque.get(x, y) == EstadoBloque.CUBIERTO) {
                // Descubre el campo si no tiene bandera
                if (matrizContenidoBloque.get(x, y) != ContenidoBloque.MARCA) {
                    matrizEstadoBloque.set(x, y, EstadoBloque.DESCUBIERTO);
                    camposDescubiertos++;
                }

                if (matrizContenidoBloque.get(x, y) == ContenidoBloque.MINA) {
                    matrizEstadoBloque.set(x, y, EstadoBloque.DESCUBIERTO);
                    pierde = true;
                } else if (matrizAdyacencias.get(x, y) == 0) {
                    for (int v = 0; v < xvars.length; v++) {
                        // Verifica para evitar un ArrayIndexOutOfBoundsException
                        if ((x + xvars[v] >= 0) && (x + xvars[v] < dificultad.getFil())
                                && (y + yvars[v] >= 0) && (y + yvars[v] < dificultad.getCol())
                                && (matrizEstadoBloque.get(x + xvars[v], y + yvars[v]) == EstadoBloque.CUBIERTO)) {
                            descubrirCampo(x + xvars[v], y + yvars[v]);
                        }
                    }
                }
            }
            if (pierde) {
                mostrarMinas();
                partida.detenerPartida(EstadoPartida.PIERDE);
            } else if (dificultad.getCantidadMinas() == dificultad.getFil() * dificultad.getCol() - camposDescubiertos) {
                marcarMinas();
                partida.detenerPartida(EstadoPartida.GANA);
            }
        }
    }

    private void mostrarMinas() {
        for (int i = 0; i < dificultad.getFil(); i++) {
            for (int j = 0; j < dificultad.getCol(); j++) {
                if (matrizEstadoBloque.get(i, j) == EstadoBloque.MARCADO
                        && matrizContenidoBloque.get(i, j) != ContenidoBloque.MINA) {
                    matrizContenidoBloque.set(i, j, ContenidoBloque.NO_MINA);
                    matrizEstadoBloque.set(i, j, EstadoBloque.DESCUBIERTO);
                    minasEcontradas--;
                } else if (matrizEstadoBloque.get(i, j) != EstadoBloque.MARCADO
                        && matrizContenidoBloque.get(i, j) == ContenidoBloque.MINA) {
                    matrizEstadoBloque.set(i, j, EstadoBloque.DESCUBIERTO);
                }
            }
        }
    }

    /**
     *
     */
    public void marcarMinas() {
        for (int i = 0; i < dificultad.getFil(); i++) {
            for (int j = 0; j < dificultad.getCol(); j++) {
                if (matrizContenidoBloque.get(i, j) == ContenidoBloque.MINA && matrizEstadoBloque.get(i, j) == EstadoBloque.MARCADO) {
                    marcarCampo(i, j);
                }
            }
        }
    }

    // TODO Revisar logica
    /**
     *
     * @param x
     * @param y
     */
    public void marcarCampo(int x, int y) {
        if (matrizEstadoBloque.get(x, y) == EstadoBloque.CUBIERTO) {
            if (matrizEstadoBloque.get(x, y) == EstadoBloque.MARCADO) {
                matrizEstadoBloque.set(x, y, EstadoBloque.CUBIERTO);
                minasEcontradas--;
            } else {
                matrizEstadoBloque.set(x, y, EstadoBloque.MARCADO);
                minasEcontradas++;
            }
        }
    }

    /**
     *
     * @param x
     * @param y
     */
    public void revelarCampos(int x, int y) {
        int cantMarcas = 0;

        if (matrizEstadoBloque.get(x, y) == EstadoBloque.DESCUBIERTO && matrizAdyacencias.get(x, y) > 0) {
            for (int v = 0; v < xvars.length; v++) {
                // Verifica para evitar un ArrayIndexOutOfBoundsException
                if ((x + xvars[v] >= 0) && (x + xvars[v] < dificultad.getFil())
                        && (y + yvars[v] >= 0) && (y + yvars[v] < dificultad.getCol())
                        && (matrizEstadoBloque.get(x + xvars[v], y + yvars[v]) == EstadoBloque.MARCADO)) {
                    cantMarcas++;
                }
            }
            if (cantMarcas == matrizAdyacencias.get(x, y)) {
                for (int v = 0; v < xvars.length; v++) {
                    // Verifica para evitar un ArrayIndexOutOfBoundsException
                    if ((x + xvars[v] >= 0) && (x + xvars[v] < dificultad.getCol())
                            && (y + yvars[v] >= 0) && (y + yvars[v] < dificultad.getCol())
                            && (matrizEstadoBloque.get(x + xvars[v], y + yvars[v]) == EstadoBloque.CUBIERTO)) {
                        descubrirCampo(x + xvars[v], y + yvars[v]);
                    }
                }
            }
        } // TODO Revisar logica 
    }

    // Metodo que recibe la jugada
    /**
     *
     * @param x
     * @param y
     * @param clickDer
     */
    public void procesarJugada(int x, int y, boolean clickDer) {
        if (clickDer) {
            marcarCampo(x, y);
        } else {
            revelarCampos(x, y);
        }
    }
         
    // Getters de las matrices
    public Matriz<ContenidoBloque> getMatrizContenidoBloque() {
        return matrizContenidoBloque;
    }

    public Matriz<Integer> getMatrizAdyacencias() {
        return matrizAdyacencias;
    }

    public Matriz<EstadoBloque> getMatrizEstadoBloque() {
        return matrizEstadoBloque;
    }

    public int getMinasEcontradas() {
        return minasEcontradas;
    }
}
