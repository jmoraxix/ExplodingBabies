/*
 * Programacion Concurrente Cliente Servidor
 * 
 * Melanie Benvides
 * Jose Mora Loria
 * Thomas White
 * 
 * Exploding Teddies
 */
package explodingteddies.modelo.tablero;

/**
 *
 * @author josmora
 */
public enum EstadoBloque {

    CUBIERTO("Cubierto", 0),
    DESCUBIERTO("Descubierto", 1),
    MARCADO("Marcado", 2);

    private String titulo;
    private int codigo;

    private EstadoBloque(String titulo, int codigo) {
        this.titulo = titulo;
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getCodigo() {
        return codigo;
    }

}
