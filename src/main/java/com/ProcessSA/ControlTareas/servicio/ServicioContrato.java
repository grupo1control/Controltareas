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

    public ServicioContrato(RepositorioContrato repositorio) { this.repositorio = repositorio; }

    /**
     * Obtiene una lista de todos los registros de Contrato
     *
     * @return
     */
    public ArrayList obtenerContratos() {
        ArrayList lista = repositorio.spGetContratos();
        System.out.println("Glosa de respuesta: " + lista.get(0));
        System.out.println("Código de estado: " + lista.get(1));
        System.out.println("Lista de resultados:");
        lista.forEach(item -> System.out.println(item));
        return lista;
    }

    /**
     *  Ingresa o actualiza un registro de Contrato
     * @param rutPersona
     * @param rutEmpresa
     * @param salario
     * @param cargo
     * @param funcion
     * @return
     */
    @Transactional
    public ArrayList registroContrato(String rutPersona, String rutEmpresa, int salario, String cargo, String funcion) {
        ArrayList registro = repositorio.spRegContrato(rutPersona, rutEmpresa, salario, cargo, funcion);
        System.out.println("Glosa de respuesta: " + registro.get(0));
        System.out.println("Código de estado: " + registro.get(1));
        return registro;
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
        ArrayList retiro = repositorio.spDelContrato(rutPersona, rutEmpresa);
        System.out.println("Glosa de respuesta: " + retiro.get(0));
        System.out.println("Código de estado: " + retiro.get(1));
        return retiro;
    }

    /**
     * Obtiene los registros de Contrato con los parametros indicados
     * @param rut
     * @return
     */
    public ArrayList obtenerContrato(String rut) {
        ArrayList entidad = repositorio.spGetContrato(rut);
        System.out.println("Glosa de respuesta: " + entidad.get(0));
        System.out.println("Código de estado: " + entidad.get(1));
        System.out.println("Resultado: \n" + entidad.get(2));
        entidad.forEach(item -> System.out.println(item));
        return entidad;
    }

}
