/*
 * Programacion Concurrente Cliente Servidor
 * 
 * Melanie Benvides
 * Jose Mora Loria
 * Thomas White
 * 
 * Exploding Teddies
 */
package explodingteddies.modelo;

/**
 *
 * @author jdmoralo
 */
public enum Notificacion {
    
    // TODO Cambiar notificaciones

    CREA_USUARIO(1),
    CREA_USUARIO_RESULTADO(2),
    INICIA_SESION(3),
    INICIA_SESION_RESULTADO(4),
    PEDIR_RESERVACIONES(5),
    ENVIA_RESERVACIONES(6),
    ELIMINA_RESERVACION(7),
    PEDIR_PELICULA(8),
    ENVIA_PELICULA(9),
    PEDIR_HORARIOS(10),
    ENVIA_HORARIOS(11),
    PEDIR_COMIDA(12),
    ENVIA_COMIDA(13),
    CREAR_RESERVACION(14),
    RESERVACION_COMPLETADA(15);

    private final int valor;

    private Notificacion(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public static Notificacion convertirValor(int valor) {
        Notificacion resultado = null;

        switch (valor) {
            case 1:
                resultado = CREA_USUARIO;
                break;
            case 2:
                resultado = CREA_USUARIO_RESULTADO;
                break;
            case 3:
                resultado = INICIA_SESION;
                break;
            case 4:
                resultado = INICIA_SESION_RESULTADO;
                break;
            case 5:
                resultado = PEDIR_RESERVACIONES;
                break;
            case 6:
                resultado = ENVIA_RESERVACIONES;
                break;
            case 7:
                resultado = ELIMINA_RESERVACION;
                break;
            case 8:
                resultado = PEDIR_PELICULA;
                break;
            case 9:
                resultado = ENVIA_PELICULA;
                break;
            case 10:
                resultado = PEDIR_HORARIOS;
                break;
            case 11:
                resultado = ENVIA_HORARIOS;
                break;
            case 12:
                resultado = PEDIR_COMIDA;
                break;
            case 13:
                resultado = ENVIA_COMIDA;
                break;
            case 14:
                resultado = CREAR_RESERVACION;
                break;
            case 15:
                resultado = RESERVACION_COMPLETADA;
                break;
            default:
                break;
        }

        return resultado;
    }

    @Override
    public String toString() {
        return String.valueOf(valor);
    }

//  Switch para ver que tipo de notificacion se recibio
//    switch (Notificacion.convertirValor(Integer.parseInt(datos[0]))) {
//            case CREA_USUARIO:
//                break;
//            case CREA_USUARIO_RESULTADO:
//                break;
//            case INICIA_SESION:
//                break;
//            case INICIA_SESION_RESULTADO:
//                break;
//            case PEDIR_RESERVACIONES:
//                break;
//            case ENVIA_RESERVACIONES:
//                break;
//            case ELIMINA_RESERVACION:
//                break;
//            case PEDIR_PELICULA:
//                break;
//            case ENVIA_PELICULA:
//                break;
//            case PEDIR_HORARIOS:
//                break;
//            case ENVIA_HORARIOS:
//                break;
//            case PEDIR_COMIDA:
//                break;
//            case ENVIA_COMIDA:
//                break;
//            case CREAR_RESERVACION:
//                break;
//            case RESERVACION_COMPLETADA:
//                break;
//        default:
//            throw new AssertionError(Notificacion.convertirValor(Integer.parseInt(datos[0])).name());
//    }
}
