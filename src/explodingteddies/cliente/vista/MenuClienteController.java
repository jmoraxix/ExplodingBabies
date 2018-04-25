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
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author jmora
 */
public class MenuClienteController implements Initializable {

//    @FXML
//    private ImageView btnPeliculas;
    private MainCliente application;

    public void setApp(MainCliente application) {
        this.application = application;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        btnPeliculas.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e)
//                -> {
//            System.out.println("btnPeliculas");
//            application.gotoVerPeliculas();
//        });
    }

}
