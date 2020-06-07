package com.ProcessSA.ControlTareas.servicio;

import com.ProcessSA.ControlTareas.repositorio.RepositorioAdministrador;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Clase para definir los servicios asociados a Administrador
 */
@Service
@Transactional(readOnly = true)
public class ServicioAdministrador {

    public final RepositorioAdministrador repositorio;

    public ServicioAdministrador(RepositorioAdministrador repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Obtiene una lista de todos los Administradores
     *
     * @return
     */
    public ArrayList obtenerAdministradores() {
        return repositorio.spGetAdministradores();
    }

    /**
     * Ingresa o actualiza un registro de Administrador
     *
     * @param id
     * @return
     */
    @Transactional
    public ArrayList registroAdministrador(Long id) {
        return repositorio.spRegAdministrador(id);
    }


    /**
     * Obtiene un registro de un Administrador
     *
     * @param id
     * @return
     */
    public ArrayList obtenerAdministrador(Long id) {
        return repositorio.spGetAdministrador(id);
    }

    /**
     * Elimina un Administrador
     *
     * @param id
     * @return
     */
    @Transactional
    public ArrayList eliminarAdministrador(Long id) {
        return repositorio.spDelAdministrador(id);
    }
}
