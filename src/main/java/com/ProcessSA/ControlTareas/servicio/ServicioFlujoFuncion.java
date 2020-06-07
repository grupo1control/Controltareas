package com.ProcessSA.ControlTareas.servicio;

import com.ProcessSA.ControlTareas.repositorio.RepositorioFlujoFuncion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Clase para definir los servicios asociados a FlujoFuncion
 */
@Service
public class ServicioFlujoFuncion {
    private final RepositorioFlujoFuncion repositorio;

    public ServicioFlujoFuncion(RepositorioFlujoFuncion repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Obtiene una lista de todas las FlujosFunciones
     *
     * @return
     */
    public ArrayList obtenerFlujosFunciones() {
        return repositorio.spGetFlujosF();
    }

    /**
     * Ingresa o actualiza un registro de FlujoFuncion
     *
     * @param indice
     * @param codigoProceso
     * @param codigoFuncion
     * @return
     */
    @Transactional
    public ArrayList registroFlujoFuncion(Byte indice, Long codigoProceso, Long codigoFuncion) {
        return repositorio.spRegFlujoF(indice, codigoProceso, codigoFuncion);
    }

    /**
     * Elimina un registro de FlujoFuncion
     *
     * @param codigoProceso
     * @param codigoFuncion
     * @return
     */
    @Transactional
    public ArrayList eliminarFlujoFuncion(Long codigoProceso, Long codigoFuncion) {
        return repositorio.spDelFlujoF(codigoProceso, codigoFuncion);
    }

    /**
     * Obtiene los registros de FlujoFuncion con los parametros indicados
     *
     * @param codigoProceso
     * @param codigoFuncion
     * @return
     */
    public ArrayList obtenerFlujoFuncion(Long codigoFuncion, Long codigoProceso) {
        return repositorio.spGetFlujoF(codigoFuncion, codigoProceso);
    }
}
