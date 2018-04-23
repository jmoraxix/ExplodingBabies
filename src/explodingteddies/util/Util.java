/*
 * Programacion Concurrente Cliente Servidor
 * 
 * Melanie Benvides
 * Jose Mora Loria
 * Thomas White
 * 
 * Exploding Teddies
 */
package explodingteddies.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import explodingteddies.modelo.Jugador;

/**
 *
 * @author josmora
 */
public class Util {

    //Variables de las ventanas
    public static int WIDTH = 650;
    public static int HEIGHT = 550;

    //Variables de TCP
    public static int SERVER_PORT = 2356;
    public static String SERVER_IP = "localhost";

    //Variables globales de la aplicación
    public static Jugador CURRENT_USER = null;

    private static Gson gson = new GsonBuilder().create();

    public static Gson getGson() {
        return gson;
    }

}
