/*
 * Programacion Concurrente Cliente Servidor
 *
 * Melanie Benvides
 * Jose Mora Loria
 * Thomas White
 *
 * Exploding Teddies
 */
package explodingteddies.servidor;

import explodingteddies.servidor.vista.VentanaServidorController;
import explodingteddies.util.Util;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * MainServidor Application. This class handles navigation and user session.
 */
public class MainServidor extends Application {

    private Stage stage;
    private final double WINDOW_WIDTH = Util.WIDTH;
    private final double WINDOW_HEIGHT = Util.HEIGHT;

    // Variables de la aplicacion del servidor.
    private Servidor servidor;

    // Ventanas
    private VentanaServidorController ventanaPrincipal;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(MainServidor.class, (java.lang.String[]) null);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            stage = primaryStage;
            stage.setTitle("Exploding Teddies Server");
            stage.setMinWidth(WINDOW_WIDTH);
            stage.setMinHeight(WINDOW_HEIGHT);
            initWindow();
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(MainServidor.class.getName()).log(Level.SEVERE, null, ex);
        }

        servidor = new Servidor(this);
        servidor.start();

        addMessage("Servidor ha iniciado correctamente.");
    }

    public void initWindow() {
        try {
            ventanaPrincipal = (VentanaServidorController) replaceSceneContent("VentanaServidor.fxml");
            ventanaPrincipal.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(MainServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = MainServidor.class.getResourceAsStream("vista/" + fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(MainServidor.class.getResource("vista/" + fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }
        Scene scene = new Scene(page, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setScene(scene);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }

    public void addMessage(String message) {
        this.ventanaPrincipal.addMessage(message);
    }

//    protected void showNotification() {
//        final Stage dialog = new Stage();
//        dialog.initModality(Modality.APPLICATION_MODAL);
//        dialog.initOwner(stage);
//        VBox dialogVbox = new VBox(20);
//        dialogVbox.getChildren().add(new Text("This is a Dialog"));
//        Scene dialogScene = new Scene(dialogVbox, 300, 200);
//        dialog.setScene(dialogScene);
//        dialog.show();
//    }
}
