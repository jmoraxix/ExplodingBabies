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

/**
 *
 * @author jdmoralo
 */
public enum Notificacion {

    CLIENTE_ENTRA_USUARIO(1),
    SERVIDOR_ABRE_PARTIDA(2),
    SERVIDOR_CIERRA_PARTIDA(3),
    CLIENTE_HACE_JUGADA(4),
    SERVIDOR_ENVIA_JUGADA(5),
    SERVIDOR_TERMINA_PARTIDA(6),
    ENTRA_VISITA(7),
    ERROR(8);

    private final int valor;

    private Notificacion(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public static Notificacion convertirValor(int valor) {
        Notificacion resultado = null;

        switch (valor) {
            case 1:
                resultado = CLIENTE_ENTRA_USUARIO;
                break;
            case 2:
                resultado = SERVIDOR_ABRE_PARTIDA;
                break;
            case 3:
                resultado = SERVIDOR_CIERRA_PARTIDA;
                break;
            case 4:
                resultado = CLIENTE_HACE_JUGADA;
                break;
            case 5:
                resultado = SERVIDOR_ENVIA_JUGADA;
                break;
            case 6:
                resultado = SERVIDOR_TERMINA_PARTIDA;
                break;
            case 7:
                resultado = ENTRA_VISITA;
                break;
            case 8:
                resultado = ERROR;
                break;
            default:
                break;
        }

        return resultado;
    }

    @Override
    public String toString() {
        return String.valueOf(valor);
    }
}
