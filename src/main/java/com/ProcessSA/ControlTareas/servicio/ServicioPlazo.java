package com.ProcessSA.ControlTareas.servicio;

import com.ProcessSA.ControlTareas.repositorio.RepositorioPlazo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Clase para definir los servicios asociados a Plazo
 */
@Service
@Transactional(readOnly = true)
public class ServicioPlazo {

    public final RepositorioPlazo repositorio;

    public ServicioPlazo(RepositorioPlazo repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Obtiene una lista de todos los Plazos
     *
     * @return
     */
    public ArrayList obtenerPlazos() {
        return repositorio.spGetPlazos();

    }

    /**
     * Ingresa un registro de Plazo
     *
     * @param fecha
     * @param codigoTarea
     * @return
     */
    @Transactional
    public ArrayList registroPlazo(Date fecha, Long codigoTarea) {
        return repositorio.spRegPlazo(fecha, codigoTarea);

    }

    /**
     * Elimina un registro de Plazo
     *
     * @param codigoTarea
     * @param contador
     * @return
     */
    @Transactional
    public ArrayList eliminarPlazo(Long codigoTarea, Byte contador) {
        return repositorio.spDelPlazo(codigoTarea, contador);

    }

    /**
     * Obtiene un registro de un Plazo
     *
     * @param codigoTarea
     * @return
     */
    public ArrayList obtenerPlazo(Long codigoTarea) {
        return repositorio.spGetPlazo(codigoTarea);

    }

}
