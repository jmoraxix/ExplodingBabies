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
public enum ContenidoBloque {
    
    NORMAL("Normal", "bloque.gif"), 
    PRESIONADO("Presionado", "bloquePresionado.gif"), 
    MINA("Mina", "mina.gif"), 
    MARCA("Marca", "marca.gif"), 
    NO_MINA("No Mina", "noMina.gif");
  
    private String titulo;
    private String imagen;

    private ContenidoBloque(String titulo, String imagen) {
        this.titulo = titulo;
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getImagen() {
        return imagen;
    }   
    
}
