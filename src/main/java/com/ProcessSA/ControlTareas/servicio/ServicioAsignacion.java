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
        ArrayList lista = repositorio.spGetAsignaciones();
        System.out.println("Lista de resultados:");
        lista.forEach(item -> System.out.println(item));
        return lista;
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
        ArrayList registro = repositorio.spRegAsignacion(codigoTarea, codigoUi, idUsuario, rol, estado, nota);
        System.out.println("Glosa de respuesta: " + registro.get(0));
        System.out.println("Código de estado: " + registro.get(1));
        return registro;
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
        ArrayList entidad = repositorio.spGetAsignacion(idUsuario, codigoUI, codigoTarea);
        System.out.println("Resultado: \n" + entidad);
        return entidad;
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
        ArrayList retiro = repositorio.spDelAsignacion(idUsuario, codigoUi, codigoTarea);
        System.out.println("Glosa de respuesta: " + retiro.get(0));
        System.out.println("Código de estado: " + retiro.get(1));
        return retiro;
    }

}
