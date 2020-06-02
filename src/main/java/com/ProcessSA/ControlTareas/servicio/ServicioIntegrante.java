package com.ProcessSA.ControlTareas.servicio;


import com.ProcessSA.ControlTareas.repositorio.RepositorioIntegrante;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Clase para definir los servicios asociados a Integrante
 */
@Service
@Transactional(readOnly = true)
public class ServicioIntegrante {
    public final RepositorioIntegrante repositorio;

    public ServicioIntegrante(RepositorioIntegrante repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Obtiene una lista de todos los Integrantes
     *
     * @return
     */
    public ArrayList obtenerIntegrantes() {
        ArrayList lista = repositorio.spGetIntegrantes();
        System.out.println("Lista de resultados:");
        lista.forEach(item -> System.out.println(item));
        return lista;
    }

    /**
     * Ingresa un registro de Integrante
     *
     * @param codigoUi
     * @param idUsuario
     * @return
     */
    @Transactional
    public ArrayList registroIntegrante(Long codigoUi, Long idUsuario) {
        ArrayList registro = repositorio.spRegIntegrante(codigoUi, idUsuario);
        System.out.println("Glosa de respuesta: " + registro.get(0));
        System.out.println("Código de estado: " + registro.get(1));
        return registro;
    }


    /**
     * Obtiene un registro de un Integrante
     *
     * @param codigoUi
     * @param idUsuario
     * @return
     */
    public ArrayList obtenerIntegrante(Long codigoUi, Long idUsuario) {
        ArrayList entidad = repositorio.spGetIntegrante(codigoUi, idUsuario);
        System.out.println("Resultado: \n" + entidad);
        return entidad;
    }

    /**
     * Elimina un Integrante
     *
     * @param codigoUi
     * @param idUsuario
     * @return
     */
    @Transactional
    public ArrayList eliminarIntegrante(Long codigoUi, Long idUsuario) {
        ArrayList retiro = repositorio.spDelIntegrante(codigoUi, idUsuario);
        System.out.println("Glosa de respuesta: " + retiro.get(0));
        System.out.println("Código de estado: " + retiro.get(1));
        return retiro;
    }
}
