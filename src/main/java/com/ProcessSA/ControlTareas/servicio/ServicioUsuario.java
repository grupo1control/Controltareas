package com.ProcessSA.ControlTareas.servicio;

import com.ProcessSA.ControlTareas.repositorio.RepositorioUsuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;

/**
 * Clase para definir los servicios asociados a Usuario
 */
@Service
@Transactional(readOnly = true)
public class ServicioUsuario {

    public final RepositorioUsuario repositorio;

    public ServicioUsuario(RepositorioUsuario repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Obtiene una lista de todos los Usuarios
     *
     * @return
     */
    public ArrayList obtenerUsuarios() {
        ArrayList lista = repositorio.spGetUsuarios();
        System.out.println("Lista de resultados:");
        lista.forEach(item -> System.out.println(item));
        return lista;
    }

    /**
     * Ingresa o actualiza un registro de Usuarios
     *
     * @param rut
     * @param nombre
     * @param correo
     * @param clave
     * @return
     */
    @Transactional
    public ArrayList registroUsuario(String rut, String nombre, String correo, String clave) {
        ArrayList registro = repositorio.spRegUsuario(rut, nombre, correo, clave);
        System.out.println("Glosa de respuesta: " + registro.get(0));
        System.out.println("Código de estado: " + registro.get(1));
        System.out.println("Identificador de salida: " + registro.get(2));
        return registro;
    }

    /**
     * Elimina un Usuario
     *
     * @param id
     * @return
     */
    @Transactional
    public ArrayList eliminarUsuario(long id) {
        ArrayList retiro = repositorio.spDelUsuario(id);
        System.out.println("Glosa de respuesta: " + retiro.get(0));
        System.out.println("Código de estado: " + retiro.get(1));
        return retiro;
    }

    /**
     * Obtiene un registro de un Usuario
     *
     * @param id
     * @return
     */
    public ArrayList obtenerUsuario(long id) {
        ArrayList entidad = repositorio.spGetUsuario(id);
        System.out.println("Resultado: \n" + entidad);
        return entidad;
    }

}
