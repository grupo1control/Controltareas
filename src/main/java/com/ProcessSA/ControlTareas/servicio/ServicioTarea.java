package com.ProcessSA.ControlTareas.servicio;

import com.ProcessSA.ControlTareas.repositorio.RepositorioTarea;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Clase para definir los servicios asociados a Tarea
 */
@Service
@Transactional(readOnly = true)
public class ServicioTarea {

    public final RepositorioTarea repositorio;

    public ServicioTarea(RepositorioTarea repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Obtiene una lista de todas las Tareas
     *
     * @return
     */
    public ArrayList obtenerTareas() {
        return repositorio.spGetTareas();

    }

    /**
     * Ingresa o actualiza un registro de Tarea
     *
     * @param codigo
     * @param nombre
     * @param descripcion
     * @param estado
     * @return
     */
    @Transactional
    public ArrayList registroTarea(Long codigo, String nombre, String descripcion, String estado) {
        return repositorio.spRegTarea(codigo, nombre, descripcion, estado);

    }

    /**
     * Elimina un registro de Tarea
     *
     * @param codigo
     * @return
     */
    @Transactional
    public ArrayList eliminarTarea(Long codigo) {
        return repositorio.spDelTarea(codigo);

    }

    /**
     * Obtiene un registro de una Tarea
     *
     * @param codigo
     * @return
     */
    public ArrayList obtenerTarea(Long codigo) {
        return repositorio.spGetTarea(codigo);

    }

}
