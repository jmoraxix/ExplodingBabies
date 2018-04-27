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
import javafx.scene.Node;
import javafx.scene.control.Button;

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
        Button nuevo = new Button();
        MatrizPane.add(nuevo, 3, 2);
        MatrizPane.addColumn(25, MatrizPane);
        MatrizPane.addRow(0, nuevo);
        
        
        
        
       
    
//        btnJugar.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e)
//                -> {
//            System.out.println("btnPeliculas");
//            application.gotoVerPeliculas();
//        });

    
    }
  
    
}
