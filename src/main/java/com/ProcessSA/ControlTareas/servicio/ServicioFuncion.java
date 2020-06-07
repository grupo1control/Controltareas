package com.ProcessSA.ControlTareas.servicio;

import com.ProcessSA.ControlTareas.repositorio.RepositorioFuncion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Clase para definir los servicios asociados a Funcion
 */
@Service
@Transactional(readOnly = true)
public class ServicioFuncion {

    public final RepositorioFuncion repositorio;

    public ServicioFuncion(RepositorioFuncion repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Obtiene una lista de todas las Funciones
     *
     * @return
     */
    public ArrayList obtenerFunciones() {
        return repositorio.spGetFunciones();
    }

    /**
     * Ingresa o actualiza un registro de Funcion
     *
     * @param codigo
     * @param nombre
     * @param descripcion
     * @param estado
     * @return
     */
    @Transactional
    public ArrayList registroFuncion(Long codigo, String nombre, String descripcion, String estado) {
        return repositorio.spRegFuncion(codigo, nombre, descripcion, estado);
    }

    /**
     * Elimina un registro de Funcion
     *
     * @param codigo
     * @return
     */
    @Transactional
    public ArrayList eliminarFuncion(Long codigo) {
        return repositorio.spDelFuncion(codigo);
    }

    /**
     * Obtiene un registro de una Funcion
     *
     * @param codigo
     * @return
     */
    public ArrayList obtenerFuncion(Long codigo) {
        return repositorio.spGetFuncion(codigo);
    }

}
