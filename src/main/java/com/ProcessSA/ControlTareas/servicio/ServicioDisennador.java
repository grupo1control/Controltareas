package com.ProcessSA.ControlTareas.servicio;

import com.ProcessSA.ControlTareas.repositorio.RepositorioDisennador;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Clase para definir los servicios asociados a Tarea
 */
@Service
@Transactional(readOnly = true)
public class ServicioDisennador {

    public final RepositorioDisennador repositorio;

    public ServicioDisennador(RepositorioDisennador repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Obtiene una lista de todos los Diseñadores
     *
     * @return
     */
    public ArrayList obtenerDisennadores() {
        return repositorio.spGetDisennadores();
    }

    /**
     * Ingresa o actualiza un registro de Diseñador
     *
     * @param id
     * @return
     */
    @Transactional
    public ArrayList registroDisennador(Long id) {
        return repositorio.spRegDisennador(id);
    }


    /**
     * Obtiene un registro de un Diseñador
     *
     * @param id
     * @return
     */
    public ArrayList obtenerDisennador(Long id) {
        return repositorio.spGetDisennador(id);
    }

    /**
     * Elimina un Diseñador
     *
     * @param id
     * @return
     */
    @Transactional
    public ArrayList eliminarDisennador(Long id) {
        return repositorio.spDelDisennador(id);
    }
}
