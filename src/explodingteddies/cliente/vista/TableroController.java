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
import explodingteddies.modelo.Dificultad;
import explodingteddies.modelo.Partida;
import explodingteddies.modelo.tablero.ContenidoBloque;
import explodingteddies.modelo.tablero.Matriz;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private Partida partida;

    // Variables de las ventanas
    private MainCliente application;

    // Variables de la ventana
    private GridPane MatrizPane;
    private Matriz<ImageView> matriz;

    public void setApp(MainCliente application, Cliente cliente) {
        this.application = application;
        this.cliente = cliente;
    }

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
//        btnJugar.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e)
//                -> {
//            System.out.println("btnPeliculas");
//            application.gotoVerPeliculas();
//        });
    }

    public void crearPartida(Partida partida) {
        this.partida = partida;
        Dificultad dificultad = partida.getDificultad();
        matriz = new Matriz<>(dificultad.getFil(), dificultad.getCol(), new ImageView(getImagen(ContenidoBloque.NORMAL.getImagen())));

        for (int i = 0; i < partida.getDificultad().getFil(); i++) {
            for (int j = 0; j < partida.getDificultad().getCol(); j++) {
                ImageView imagen = new ImageView(getImagen(ContenidoBloque.NORMAL.getImagen()));
                imagen.setFitHeight(25);
                imagen.setFitWidth(25);
                imagen.setPreserveRatio(true);
                MatrizPane.add(imagen, i, j);
                matriz.set(i, j, imagen);
            }
        }
    }

    public void cerrarPartida(Partida partida) {
        this.partida = partida;
        Dificultad dificultad = partida.getDificultad();
        matriz = new Matriz<>(dificultad.getFil(), dificultad.getCol(), new ImageView(getImagen(ContenidoBloque.NORMAL.getImagen())));

        for (int i = 0; i < partida.getDificultad().getFil(); i++) {
            for (int j = 0; j < partida.getDificultad().getCol(); j++) {
                ImageView imagen = new ImageView(getImagen(ContenidoBloque.NORMAL.getImagen()));
                imagen.setFitHeight(25);
                imagen.setFitWidth(25);
                imagen.setPreserveRatio(true);
                MatrizPane.add(imagen, i, j);
                matriz.set(i, j, imagen);
            }
        }
    }

    private Image getImagen(String path) {
        FileInputStream imgBloque = null;
        try {
            imgBloque = new FileInputStream("../../imagenes/" + path);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TableroController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Image(imgBloque);
    }

}
