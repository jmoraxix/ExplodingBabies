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

import explodingteddies.cliente.MainCliente;
import explodingteddies.modelo.Dificultad;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author jmora
 */
public class MenuClienteController implements Initializable {

    // Variables de la ventana
    @FXML private Label lblError;
    @FXML private ComboBox<Dificultad> cbDificultad;
    @FXML private TextField txtJugador;
    @FXML private Button btnJugar;
    
    private MainCliente application;

    public void setApp(MainCliente application) {
        this.application = application;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbDificultad.getItems().addAll(Dificultad.values());
        cbDificultad.setValue(Dificultad.FACIL);
        
        lblError.setText("");
        
        btnJugar.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e)
                -> {
            
            if(!txtJugador.getText().equals("")){
                System.out.println("Cambiando a tablero: " + cbDificultad.getValue().toString());
                application.gotoTablero(cbDificultad.getValue());
            } else {
                lblError.setTextFill(Color.FIREBRICK);
                lblError.setText("Favor ingresar su jugador");
            }
        });
    }

}
