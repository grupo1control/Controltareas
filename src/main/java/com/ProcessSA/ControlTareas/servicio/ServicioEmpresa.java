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
        ArrayList lista = repositorio.spGetEmpresas();
        System.out.println("Lista de resultados:");
        lista.forEach(item -> System.out.println(item));
        return lista;
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
        ArrayList registro = repositorio.spRegEmpresa(rut, nombre, razonSocial, giroComercial);
        System.out.println("Glosa de respuesta: " + registro.get(0));
        System.out.println("Código de estado: " + registro.get(1));
        System.out.println("Identificador de salida: " + registro.get(2));
        return registro;
    }

    /**
     * Elimina un registro de Empresa
     *
     * @param rut
     * @return
     */
    @Transactional
    public ArrayList eliminarEmpresa(String rut) {
        ArrayList retiro = repositorio.spDelEmpresa(rut);
        System.out.println("Glosa de respuesta: " + retiro.get(0));
        System.out.println("Código de estado: " + retiro.get(1));
        return retiro;
    }

    /**
     * Obtiene un registro de una Empresa
     *
     * @param rut
     * @return
     */
    public ArrayList obtenerEmpresa(String rut) {
        ArrayList entidad = repositorio.spGetEmpresa(rut);
        System.out.println("Resultado: \n" + entidad);
        return entidad;
    }

}
