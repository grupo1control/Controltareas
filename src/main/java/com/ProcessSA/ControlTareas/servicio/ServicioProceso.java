package com.ProcessSA.ControlTareas.servicio;

import com.ProcessSA.ControlTareas.repositorio.RepositorioProceso;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;

/**
 * Clase para definir los servicios asociados a Proceso
 */
@Service
@Transactional(readOnly = true)
public class ServicioProceso {

    public final RepositorioProceso repositorio;

    public ServicioProceso (RepositorioProceso repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Obtiene una lista de todos los procesos
     *
     * @return
     */
    public ArrayList obtenerProcesos() {
        ArrayList lista = repositorio.spGetProcesos();
        System.out.println("Lista de resultados:");
        lista.forEach(item -> System.out.println(item));
        return lista;
    }

    /**
     * Ingresa o actualiza un registro de Proceso
     *
     * @param codigo
     * @param indice
     * @param nombre
     * @param descripcion
     * @param input_estado
     * @param codigo_ui
     * @param id_disennador
     * @param codigo_proyecto

     * @return
     */
    @Transactional
    public ArrayList registroProceso(Long codigo, Byte indice, String nombre, String descripcion,String input_estado, Long codigo_ui,Long id_disennador, Long codigo_proyecto) {
        ArrayList registro = repositorio.spRegProceso(codigo, indice, nombre, descripcion,input_estado, codigo_ui,id_disennador, codigo_proyecto);
        System.out.println("Glosa de respuesta: " + registro.get(0));
        System.out.println("Código de estado: " + registro.get(1));
        System.out.println("Identificador de salida: " + registro.get(2));
        return registro;
    }

    /**
     * Elimina un Proceso
     * @param codigo
     * @return
     */
    @Transactional
    public ArrayList eliminarProceso(Long codigo) {
        ArrayList retiro = repositorio.spDelProceso(codigo);
        System.out.println("Glosa de respuesta: " + retiro.get(0));
        System.out.println("Código de estado: " + retiro.get(1));
        return retiro;
    }

    /**
     * Obtiene un registro de un Proceso
     *
     * @param codigo
     * @return
     */
    public ArrayList obtenerProceso(Long codigo) {
        ArrayList entidad = repositorio.spGetProceso(codigo);
        System.out.println("Resultado: \n" + entidad);
        return entidad;
    }
}
