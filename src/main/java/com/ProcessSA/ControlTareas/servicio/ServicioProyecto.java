package com.ProcessSA.ControlTareas.servicio;

import com.ProcessSA.ControlTareas.repositorio.RepositorioProyecto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Clase para definir los servicios asociados a Proyecto
 */
@Service
@Transactional(readOnly = true)
public class ServicioProyecto {

    public final RepositorioProyecto repositorio;

    public ServicioProyecto(RepositorioProyecto repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Obtiene una lista de todos los proyectos
     *
     * @return
     */
    public ArrayList obtenerProyectos() {
        ArrayList lista = repositorio.spGetProyectos();
        System.out.println("Lista de resultados:");
        lista.forEach(item -> System.out.println(item));
        return lista;
    }

    /**
     * Ingresa o actualiza un registro de Proyecto
     *
     * @param codigo
     * @param nombre
     * @param inputEstado
     * @param rut_empresa
     * @param id_administrador

     * @return
     */
    @Transactional
    public ArrayList registroProyecto(long codigo, String nombre, String inputEstado, String rut_empresa, long id_administrador) {
        ArrayList registro = repositorio.spRegProyecto(codigo, nombre, inputEstado,rut_empresa,id_administrador);
        System.out.println("Glosa de respuesta: " + registro.get(0));
        System.out.println("Código de estado: " + registro.get(1));
        System.out.println("Identificador de salida: " + registro.get(2));
        return registro;
    }

    /**
     * Elimina un Proyecto
     *
     * @param codigo
     * @return
     */
    @Transactional
    public ArrayList eliminarProyecto(long codigo) {
        ArrayList retiro = repositorio.spDelProyecto(codigo);
        System.out.println("Glosa de respuesta: " + retiro.get(0));
        System.out.println("Código de estado: " + retiro.get(1));
        return retiro;
    }

    /**
     * Obtiene un registro de un Proyecto
     *
     * @param codigo
     * @return
     */
    public ArrayList obtenerProyecto(long codigo) {
        ArrayList entidad = repositorio.spGetProyecto(codigo);
        System.out.println("Resultado: \n" + entidad);
        return entidad;
    }
}
