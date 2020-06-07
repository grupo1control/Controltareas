package com.ProcessSA.ControlTareas.servicio;


import com.ProcessSA.ControlTareas.repositorio.RepositorioContrato;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Clase para definir los servicios asociados a Contrato
 */
@Service
public class ServicioContrato {

    private final RepositorioContrato repositorio;

    public ServicioContrato(RepositorioContrato repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Obtiene una lista de todos los registros de Contrato
     *
     * @return
     */
    public ArrayList obtenerContratos() {
        return repositorio.spGetContratos();
    }

    /**
     * Ingresa o actualiza un registro de Contrato
     *
     * @param rutPersona
     * @param rutEmpresa
     * @param salario
     * @param cargo
     * @param funcion
     * @return
     */
    @Transactional
    public ArrayList registroContrato(String rutPersona, String rutEmpresa, int salario, String cargo, String funcion) {
        return repositorio.spRegContrato(rutPersona, rutEmpresa, salario, cargo, funcion);
    }

    /**
     * Elimina un registro de Contrato
     *
     * @param rutPersona
     * @param rutEmpresa
     * @return
     */
    @Transactional
    public ArrayList eliminarContrato(String rutPersona, String rutEmpresa) {
        return repositorio.spDelContrato(rutPersona, rutEmpresa);
    }

    /**
     * Obtiene los registros de Contrato con los parametros indicados
     *
     * @param rut
     * @return
     */
    public ArrayList obtenerContrato(String rut) {
        return repositorio.spGetContrato(rut);
    }

}
