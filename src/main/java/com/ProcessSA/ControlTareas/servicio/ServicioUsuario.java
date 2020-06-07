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
        return repositorio.spGetUsuarios();

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
        return repositorio.spRegUsuario(rut, nombre, correo, clave);

    }

    /**
     * Elimina un Usuario
     *
     * @param id
     * @return
     */
    @Transactional
    public ArrayList eliminarUsuario(Long id) {
        return repositorio.spDelUsuario(id);

    }

    /**
     * Obtiene un registro de un Usuario
     *
     * @param id
     * @return
     */
    public ArrayList obtenerUsuario(Long id) {
        return repositorio.spGetUsuario(id);

    }

}
