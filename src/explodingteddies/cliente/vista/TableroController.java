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
import explodingteddies.cliente.MainCliente;
import explodingteddies.modelo.tablero.Tablero;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author melbe
 */


public class TableroController implements Initializable {
    
    // Variables TCP
    private Cliente cliente;
    
    // Variables juego
    private Tablero tablero;

    // Variables de las ventanas
    private MainCliente application;
    // Variable del Panel GEenerico
    private GridPane MatrizPane;

    
    public void setApp(MainCliente application) {
        this.application = application;
    }
    
    
    
    
    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        
        MatrizPane.add(button, 3, 2);
        MatrizPane.addColumn(25, MatrizPane);
        
        
        
        
       
    
//        btnJugar.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e)
//                -> {
//            System.out.println("btnPeliculas");
//            application.gotoVerPeliculas();
//        });

    
    }
  
    
}
