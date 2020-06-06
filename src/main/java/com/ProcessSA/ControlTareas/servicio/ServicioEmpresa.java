package com.ProcessSA.ControlTareas.servicio;

import com.ProcessSA.ControlTareas.repositorio.RepositorioEmpresa;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;


/**
 * Clase para definir los servicio asociados a Empresa
 */
@Service
@Transactional(readOnly = true)
public class ServicioEmpresa {

    private final RepositorioEmpresa repositorio;

    public ServicioEmpresa(RepositorioEmpresa repositorio) { this.repositorio = repositorio; }

    /**
     * Obtiene una lista de todas las Empresas
     *
     * @return
     */
    public ArrayList obtenerEmpresas() {
        return repositorio.spGetEmpresas();
    }


    /**
     * Ingresa o actualiza un registro de Empresa
     *
     * @param rut
     * @param nombre
     * @param razonSocial
     * @param giroComercial
     * @return
     */
    @Transactional
    public ArrayList registroEmpresa(String rut, String nombre, String razonSocial, String giroComercial) {
        return repositorio.spRegEmpresa(rut, nombre, razonSocial, giroComercial);
    }

    /**
     * Elimina un registro de Empresa
     *
     * @param rut
     * @return
     */
    @Transactional//(propagation = Propagation.REQUIRED, timeout = 30, rollbackFor = errorGenerico.class)
    public ArrayList eliminarEmpresa(String rut) {
        return repositorio.spDelEmpresa(rut);
    }

    /**
     * Obtiene un registro de una Empresa
     *
     * @param rut
     * @return
     */
    public ArrayList obtenerEmpresa(String rut) {
        return repositorio.spGetEmpresa(rut);
    }

}
