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
import explodingteddies.modelo.Cronometro;
import explodingteddies.modelo.Dificultad;
import explodingteddies.modelo.Partida;
import explodingteddies.modelo.tablero.ContenidoBloque;
import explodingteddies.modelo.tablero.Matriz;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

/**
 * FXML Controller class
 *
 * @author melbe
 */
public class TableroController implements Initializable {

    // Variables TCP
    private Cliente cliente;

    // Variables juego
    private Partida partida;
    private Dificultad dificultad;
    private Cronometro cronometro = new Cronometro(this);

    // Variables de las ventanas
    private MainCliente application;

    // Variables de la ventana
    private Matriz<ImageView> matriz;
    @FXML
    private GridPane matrixPane;
    @FXML
    private Button btnSalir;
    @FXML
    private Label lblDificultad;
    @FXML
    private Label lblJugador1;
    @FXML
    private Label lblJugador2;
    @FXML
    private Label lblMinas;
    @FXML
    private Label lblTiempo;

    public void setApp(MainCliente application, Cliente cliente, Dificultad dificultad) {
        this.application = application;
        this.cliente = cliente;
        this.dificultad = dificultad;
        lblDificultad.setText(dificultad.toString());
        lblMinas.setText(String.valueOf(dificultad.getCantidadMinas()));
        setTiempo(0);
        System.out.println(dificultad.toString());

        matriz = new Matriz<>(dificultad.getFil(), dificultad.getCol(), new ImageView(getImagen(ContenidoBloque.NORMAL.getImagen())));

        RowConstraints rc = new RowConstraints();
        rc.setPercentHeight(100 / dificultad.getFil());
        rc.setVgrow(Priority.ALWAYS); // allow row to grow
        rc.setFillHeight(true); // ask nodes to fill height for row
        for (int rowIndex = 0; rowIndex < dificultad.getFil(); rowIndex++) {
            matrixPane.getRowConstraints().add(rc);
        }
        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(100 / dificultad.getCol());
        cc.setHgrow(Priority.ALWAYS); // allow column to grow
        cc.setFillWidth(true); // ask nodes to fill space for column
        for (int colIndex = 0; colIndex < dificultad.getCol(); colIndex++) {
            matrixPane.getColumnConstraints().add(cc);
        }
        matrixPane.setStyle("-fx-background-color: white; -fx-grid-lines-visible: true");

        for (int i = 0; i < this.dificultad.getFil(); i++) {
            for (int j = 0; j < this.dificultad.getCol(); j++) {
                ImageView imagen = new ImageView(getImagen(ContenidoBloque.NORMAL.getImagen()));
                imagen.setFitHeight(25);
                imagen.setFitWidth(25);
                imagen.setPreserveRatio(true);
                imagen.setUserData(i + ";" + j);

                imagen.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e)
                        -> {
                    System.out.println("Saliendo del tablero");
                    ImageView view = (ImageView) e.getSource();
                    String valores = (String) view.getUserData();
                    int x = Integer.valueOf(valores.split(";")[0]);
                    int y = Integer.valueOf(valores.split(";")[1]);
                    System.out.println("Enviando jugada: " + x + ", " + y);
                    cliente.enviaJugada(x, y);
                });

                matrixPane.add(imagen, i, j);
                matriz.set(i, j, imagen);
            }
        }
    }

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        lblDificultad.setText("");
        lblJugador1.setText("");
        lblJugador2.setText("");

        btnSalir.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e)
                -> {
            System.out.println("Saliendo del tablero");

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Cerrando partida");
            alert.setHeaderText("La partida esta a punto de ser cerrada");
            alert.setContentText("¿Seguro que quiere cerrar la partida?");

            ButtonType buttonTypeContinue = new ButtonType("Cerrar");
            ButtonType buttonTypeCancel = new ButtonType("Volver", ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeCancel, buttonTypeContinue);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeContinue) {
                application.gotoMenu();
            }
        });

        matrixPane.setMaxSize(822, 544);
    }

    public void crearPartida(Partida partida) {
//        this.partida = partida;
//        Dificultad dificultad = partida.getDificultad();
    }

    public void cerrarPartida(Partida partida) {
//        this.partida = partida;
//        Dificultad dificultad = partida.getDificultad();
//        matriz = new Matriz<>(dificultad.getFil(), dificultad.getCol(), new ImageView(getImagen(ContenidoBloque.NORMAL.getImagen())));
//
//        for (int i = 0; i < partida.getDificultad().getFil(); i++) {
//            for (int j = 0; j < partida.getDificultad().getCol(); j++) {
//                ImageView imagen = new ImageView(getImagen(ContenidoBloque.NORMAL.getImagen()));
//                imagen.setFitHeight(25);
//                imagen.setFitWidth(25);
//                imagen.setPreserveRatio(true);
//                MatrizPane.add(imagen, i, j);
//                matriz.set(i, j, imagen);
//            }
//        }
    }

    private Image getImagen(String path) {
//        FileInputStream imgBloque = null;
//        try {
//            imgBloque = new FileInputStream(path);
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(TableroController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return new Image(this.getClass().getResource(path).toExternalForm());
    }

    public void setTiempo(int tiempo) {
        tiempo *= 1000;
        String hms = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(tiempo) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(tiempo) % TimeUnit.MINUTES.toSeconds(1));
        lblTiempo.setText(hms);
    }

}
