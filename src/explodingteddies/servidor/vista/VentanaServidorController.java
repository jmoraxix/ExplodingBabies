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
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author jmora
 */
public class VentanaServidorController implements Initializable {

    private MainServidor application;
    @FXML
    private TextArea txtPanel;

    public void setApp(MainServidor application) {
        this.application = application;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.txtPanel.setWrapText(false);
    }

    public synchronized void addMessage(String message) {
        this.txtPanel.appendText(new Date() + " - " + message + "\n");
    }
}
