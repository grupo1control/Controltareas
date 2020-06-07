package com.ProcessSA.ControlTareas.servicio;

import com.ProcessSA.ControlTareas.repositorio.RepositorioProyecto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Clase para definir los servicios asociados a Proyecto
 */
@Service
@Transactional(readOnly = true)
public class ServicioProyecto {

    public final RepositorioProyecto repositorio;

    public ServicioProyecto(RepositorioProyecto repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Obtiene una lista de todos los proyectos
     *
     * @return
     */
    public ArrayList obtenerProyectos() {
        return repositorio.spGetProyectos();

    }

    /**
     * Ingresa o actualiza un registro de Proyecto
     *
     * @param codigo
     * @param nombre
     * @param inputEstado
     * @param rut_empresa
     * @param id_administrador
     * @return
     */
    @Transactional
    public ArrayList registroProyecto(Long codigo, String nombre, String inputEstado, String rut_empresa, Long id_administrador) {
        return repositorio.spRegProyecto(codigo, nombre, inputEstado, rut_empresa, id_administrador);

    }

    /**
     * Elimina un Proyecto
     *
     * @param codigo
     * @return
     */
    @Transactional
    public ArrayList eliminarProyecto(Long codigo) {
        return repositorio.spDelProyecto(codigo);

    }

    /**
     * Obtiene un registro de un Proyecto
     *
     * @param codigo
     * @return
     */
    public ArrayList obtenerProyecto(Long codigo) {
        return repositorio.spGetProyecto(codigo);

    }

}
