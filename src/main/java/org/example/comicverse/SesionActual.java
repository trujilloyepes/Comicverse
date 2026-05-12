package org.example.comicverse;

import model.Perfil;
import model.Usuario;

/**
 * Clase que guarda el usuario y el perfil que han iniciado sesión.
 * Al ser estática, está disponible desde cualquier ventana de la app.
 */
public class SesionActual {

    // Usuario que ha iniciado sesión
    private static Usuario usuario;

    // Perfil que está usando la app en este momento
    private static Perfil perfil;

    public static Usuario getUsuario() { return usuario; }
    public static void setUsuario(Usuario usuario) { SesionActual.usuario = usuario; }

    public static Perfil getPerfil() { return perfil; }
    public static void setPerfil(Perfil perfil) { SesionActual.perfil = perfil; }
}
