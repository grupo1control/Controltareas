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
     * @param inputstado
     * @param nota
     * @return
     */
    @Transactional
    public ArrayList registroAsignacion(Long codigoTarea, Long codigoUi, Long idUsuario, String rol, String inputstado, String nota) {
        ArrayList registro = repositorio.spRegAsignacion(codigoTarea, codigoUi, idUsuario, rol, inputstado, nota);
        System.out.println("Glosa de respuesta: " + registro.get(0));
        System.out.println("Código de estado: " + registro.get(1));
        System.out.println("Identificador de salida: " + registro.get(2));
        return registro;
    }


    /**
     * Obtiene un registro de un Asignacion
     *
     * @param id
     * @return
     */
    public ArrayList obtenerAsignacion(long id) {
        ArrayList entidad = repositorio.spGetAsignacion(id);
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
