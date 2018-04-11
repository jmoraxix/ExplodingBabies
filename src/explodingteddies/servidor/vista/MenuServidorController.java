/*
 * Programacion Concurrente Cliente Servidor
 * 
 * Melanie Benvides
 * Jose Mora Loria
 * Thomas White
 * 
 * Exploding Teddies
 */
package explodingteddies.servidor.vista;

import explodingteddies.servidor.MainServidor;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author jmora
 */
public class MenuServidorController implements Initializable {

    @FXML
    private ImageView btnPeliculas;
    @FXML
    private ImageView btnSalas;
    @FXML
    private ImageView btnComida;
    @FXML
    private ImageView btnReservaciones;

    private MainServidor application;

    public void setApp(MainServidor application) {
        this.application = application;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnPeliculas.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e)
                -> {
            System.out.println("btnPeliculas");
            application.gotoPeliculas();
        });
        btnSalas.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e)
                -> {
            System.out.println("btnSalas");
            application.gotoSalas();
        });
        btnComida.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e)
                -> {
            System.out.println("btnComida");
        });
        btnReservaciones.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e)
                -> {
            System.out.println("btnReservaciones");
        });
    }

}
