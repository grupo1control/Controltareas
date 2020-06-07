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
        return repositorio.spGetIntegrantes();
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
        return repositorio.spRegIntegrante(codigoUi, idUsuario);
    }


    /**
     * Obtiene un registro de un Integrante
     *
     * @param codigoUi
     * @param idUsuario
     * @return
     */
    public ArrayList obtenerIntegrante(Long codigoUi, Long idUsuario) {
        return repositorio.spGetIntegrante(codigoUi, idUsuario);
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
        return repositorio.spDelIntegrante(codigoUi, idUsuario);
    }
}
