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
import explodingteddies.modelo.Jugador;
import explodingteddies.modelo.Partida;
import explodingteddies.modelo.tablero.ContenidoBloque;
import explodingteddies.modelo.tablero.EstadoBloque;
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
    private Jugador jugador1, jugador2;
    private Jugador turno, yo;
    private boolean partidaLista = false;
    private Matriz<ImageView> matrizImagenes;
    private Matriz<Label> matrizLabelAdyacencia;

    // Variables de las ventanas
    private MainCliente application;

    // Variables de la ventana
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
        matrixPane.setMinSize(822, 544);
    }

    public void setApp(MainCliente application, Cliente cliente, Dificultad dificultad, Jugador jugador) {
        // Definimos variables pendientes
        this.application = application;
        this.cliente = cliente;
        this.dificultad = dificultad;
        lblDificultad.setText(dificultad.toString());
        lblMinas.setText(String.valueOf(dificultad.getCantidadMinas()));
        setTiempo(0);
        yo = jugador;
        turno = yo;
        lblJugador1.setText(yo.getJugador());
        lblJugador2.setText("Esperando jugador");

        matrizImagenes = new Matriz<>(dificultad.getFil(), dificultad.getCol(), getImagen(ContenidoBloque.NORMAL.getImagen()));
        matrizLabelAdyacencia = new Matriz<>(dificultad.getFil(), dificultad.getCol(), new Label());

        matrixPane.getRowConstraints().remove(0, matrixPane.getRowConstraints().size());
        matrixPane.getColumnConstraints().remove(0, matrixPane.getColumnConstraints().size());

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
//        matrixPane.setStyle("-fx-background-color: white; -fx-grid-lines-visible: true");

        for (int i = 0; i < this.dificultad.getFil(); i++) {
            for (int j = 0; j < this.dificultad.getCol(); j++) {
                ImageView imagen = getImagen(ContenidoBloque.NORMAL.getImagen());
                imagen.setUserData(i + ";" + j);
                imagen.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e)
                        -> {
                    ImageView view = (ImageView) e.getSource();
                    String valores = (String) view.getUserData();
                    int x = Integer.valueOf(valores.split(";")[0]);
                    int y = Integer.valueOf(valores.split(";")[1]);
                    System.out.println("Enviando jugada: " + x + ", " + y);
                    Boolean clickDer = e.isSecondaryButtonDown();
                    enviarJugada(x, y, clickDer);
                });

                Label lbl = new Label("");

                matrixPane.add(imagen, j, i);
                matrixPane.add(lbl, j, i);
                matrizImagenes.set(i, j, imagen);
                matrizLabelAdyacencia.set(i, j, lbl);
            }
        }
    }

    // Inicializamos las varibles con la partida generada por el servidor
    public void crearPartida(Partida partidaNueva) {
        this.partida = partidaNueva;
        dificultad = partida.getDificultad();
    }

    // Inicializamos las variables con el nuevo jugador e iniciamos el juego
    public void cerrarPartida(Partida partidaNueva) {
        // Actualizamos variables
        this.partida = partidaNueva;
        dificultad = partida.getDificultad();

        // Definimos jugadores en base a la nueva partida
        jugador1 = partida.getJugador1();
        jugador2 = partida.getJugador2();
        this.lblJugador1.setText(jugador1.getJugador());
        this.lblJugador2.setText(jugador2.getJugador());
        turno = jugador1;
        lblJugador1.setId("turnoActual");
        lblJugador2.setId("normal");

        this.partidaLista = true;
        cronometro.start();
    }

    private void enviarJugada(int x, int y, boolean clickDer) {
        if (partidaLista && turno == yo) {
            cliente.enviaJugada(x, y, clickDer);
            cambioTurno();
        }
    }

    // Recibimos una nueva matriz y la mapeamos con el tablero actual
    public void recibeJugada(Jugador jugadorRecibido, Partida partidaNueva) {
        this.partida = partidaNueva;
        lblMinas.setText(String.valueOf(dificultad.getCantidadMinas() - partida.getCampoMinado().getMinasEcontradas()));

        for (int i = 0; i < this.dificultad.getFil(); i++) {
            for (int j = 0; j < this.dificultad.getCol(); j++) {
                ImageView imagen = matrizImagenes.get(i, j);
                Label lbl = matrizLabelAdyacencia.get(i, j);

                if (partida.getMatrizEstadoBloque().get(i, j) == EstadoBloque.DESCUBIERTO) {
                    imagen.setImage(new Image(partida.getMatrizContenidoBloque().get(i, j).getImagen()));
                    if (partida.getMatrizContenidoBloque().get(i, j) == ContenidoBloque.PRESIONADO) {
                        lbl.setText(String.valueOf(partida.getMatrizAdyacencias().get(i, j)));
                    }
                } else if (partida.getMatrizEstadoBloque().get(i, j) == EstadoBloque.MARCADO) {
                    imagen.setImage(new Image(ContenidoBloque.MARCA.getImagen()));
                }
            }
        }

        if (jugadorRecibido != yo) {
            cambioTurno();
        }
    }

    private void cambioTurno() {
        if (turno == jugador1) {
            turno = jugador2;
            lblJugador1.setId("normal");
            lblJugador2.setId("turnoActual");
        } else {
            turno = jugador1;
            lblJugador1.setId("turnoActual");
            lblJugador2.setId("normal");
        }
    }

    private ImageView getImagen(String path) {
        ImageView imagen = new ImageView(this.getClass().getResource(path).toExternalForm());
        imagen.setFitHeight(25);
        imagen.setFitWidth(25);
        imagen.setPreserveRatio(true);
        imagen.setStyle("-fx-background-color: white; -fx-grid-lines-visible: true");
        return imagen;
    }

    public void setTiempo(int tiempo) {
        tiempo *= 1000;
        String hms = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(tiempo) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(tiempo) % TimeUnit.MINUTES.toSeconds(1));
        lblTiempo.setText(hms);
    }
}
