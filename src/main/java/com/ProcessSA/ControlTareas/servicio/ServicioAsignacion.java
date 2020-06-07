package com.ProcessSA.ControlTareas.servicio;


import com.ProcessSA.ControlTareas.repositorio.RepositorioAsignacion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;


/**
 * Clase para definir los servicios asociados a Asignacion
 */
@Service
@Transactional(readOnly = true)
public class ServicioAsignacion {

    public final RepositorioAsignacion repositorio;

    public ServicioAsignacion(RepositorioAsignacion repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Obtiene una lista de todas las Asignaciones
     *
     * @return
     */
    public ArrayList obtenerAsignaciones() {
        return repositorio.spGetAsignaciones();
    }

    /**
     * Ingresa o actualiza un registro de Asignacion
     *
     * @param codigoTarea
     * @param codigoUi
     * @param idUsuario
     * @param rol
     * @param estado
     * @param nota
     * @return
     */
    @Transactional
    public ArrayList registroAsignacion(Long codigoTarea, Long codigoUi, Long idUsuario, String rol, String estado, String nota) {
        return repositorio.spRegAsignacion(codigoTarea, codigoUi, idUsuario, rol, estado, nota);
    }

    /**
     * Obtiene un registro de un Asignacion
     *
     * @param idUsuario
     * @param codigoUI
     * @param codigoTarea
     * @return
     */
    public ArrayList obtenerAsignacion(Long idUsuario, Long codigoUI, Long codigoTarea) {
        return repositorio.spGetAsignacion(idUsuario, codigoUI, codigoTarea);
    }

    /**
     * Elimina una Asignacion
     *
     * @param idUsuario
     * @param codigoUi
     * @param codigoTarea
     * @return
     */
    @Transactional
    public ArrayList eliminarAsignacion(Long idUsuario, Long codigoUi, Long codigoTarea) {
        return repositorio.spDelAsignacion(idUsuario, codigoUi, codigoTarea);
    }

}
