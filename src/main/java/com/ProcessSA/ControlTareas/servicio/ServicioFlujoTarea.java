package com.ProcessSA.ControlTareas.servicio;

import com.ProcessSA.ControlTareas.repositorio.RepositorioFlujoTarea;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Clase para definir los servicios asociados a FlujoTarea
 */
@Service
public class ServicioFlujoTarea {

    private final RepositorioFlujoTarea repositorio;

    public ServicioFlujoTarea(RepositorioFlujoTarea repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Obtiene una lista de todas las FlujosTareas
     *
     * @return
     */
    public ArrayList obtenerFlujosTareas() {
        return repositorio.spGetFlujosT();
    }

    /**
     * Ingresa o actualiza un registro de FlujoTarea
     *
     * @param indice
     * @param codigoTarea
     * @param codigoFuncion
     * @return
     */
    @Transactional
    public ArrayList registroFlujoTarea(Byte indice, Long codigoTarea, Long codigoFuncion) {
        return repositorio.spRegFlujoT(indice, codigoTarea, codigoFuncion);
    }

    /**
     * Elimina un registro de FlujoTarea
     *
     * @param codigoTarea
     * @param codigoFuncion
     * @return
     */
    @Transactional
    public ArrayList eliminarFlujoTarea(Long codigoTarea, Long codigoFuncion) {
        return repositorio.spDelFlujoT(codigoTarea, codigoFuncion);
    }

    /**
     * Obtiene los registros de FlujoTarea con los parametros indicados
     *
     * @param codigoTarea
     * @param codigoFuncion
     * @return
     */
    public ArrayList obtenerFlujoTarea(Long codigoTarea, Long codigoFuncion) {
        return repositorio.spGetFlujoT(codigoTarea, codigoFuncion);
    }

}
