/*
 * Programacion Concurrente Cliente Servidor
 * 
 * Melanie Benvides
 * Jose Mora Loria
 * Thomas White
 * 
 * Exploding Teddies
 */
package explodingteddies.cliente.vista;

import explodingteddies.cliente.Cliente;
import explodingteddies.modelo.tablero.Tablero;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author melbe
 */
public abstract class TableroController implements Initializable {
    
    // Variables TCP
    private Cliente cliente;
    
    // Variables juego
    private Tablero tablero;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
