package com.ProcessSA.ControlTareas.servicio;

import com.ProcessSA.ControlTareas.repositorio.RepositorioProceso;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Clase para definir los servicios asociados a Proceso
 */
@Service
@Transactional(readOnly = true)
public class ServicioProceso {

    public final RepositorioProceso repositorio;

    public ServicioProceso(RepositorioProceso repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Obtiene una lista de todos los procesos
     *
     * @return
     */
    public ArrayList obtenerProcesos() {
        return repositorio.spGetProcesos();

    }

    /**
     * Ingresa o actualiza un registro de Proceso
     *
     * @param codigo
     * @param indice
     * @param nombre
     * @param descripcion
     * @param input_estado
     * @param codigo_ui
     * @param id_disennador
     * @param codigo_proyecto
     * @return
     */
    @Transactional
    public ArrayList registroProceso(Long codigo, Byte indice, String nombre, String descripcion, String input_estado, Long codigo_ui, Long id_disennador, Long codigo_proyecto) {
        return repositorio.spRegProceso(codigo, indice, nombre, descripcion, input_estado, codigo_ui, id_disennador, codigo_proyecto);

    }

    /**
     * Elimina un Proceso
     *
     * @param codigo
     * @return
     */
    @Transactional
    public ArrayList eliminarProceso(Long codigo) {
        return repositorio.spDelProceso(codigo);

    }

    /**
     * Obtiene un registro de un Proceso
     *
     * @param codigo
     * @return
     */
    public ArrayList obtenerProceso(Long codigo) {
        return repositorio.spGetProceso(codigo);

    }
}
