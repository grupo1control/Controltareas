package com.ProcessSA.ControlTareas.servicio;

import com.ProcessSA.ControlTareas.repositorio.RepositorioFuncion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;

/**
 * Clase para definir los servicios asociados a Funcion
 */
@Service
@Transactional(readOnly = true)
public class ServicioFuncion {

    public final RepositorioFuncion repositorio;

    public ServicioFuncion(RepositorioFuncion repositorio) { this.repositorio = repositorio; }

    /**
     * Obtiene una lista de todas las Funciones
     *
     * @return
     */
    public ArrayList obtenerFunciones() {
        ArrayList lista = repositorio.spGetFunciones();
        System.out.println("Lista de resultados:");
        lista.forEach(item -> System.out.println(item));
        return lista;
    }

    /**
     * Ingresa o actualiza un registro de Funcion
     *
     * @param codigo
     * @param nombre
     * @param descripcion
     * @param estado
     * @return
     */
    @Transactional
    public ArrayList registroFuncion(Long codigo, String nombre, String descripcion, String estado) {
        ArrayList registro = repositorio.spRegFuncion(codigo, nombre, descripcion, estado);
        System.out.println("Glosa de respuesta: " + registro.get(0));
        System.out.println("Código de estado: " + registro.get(1));
        System.out.println("Identificador de salida: " + registro.get(2));
        return registro;
    }

    /**
     * Elimina un registro de Funcion
     *
     * @param codigo
     * @return
     */
    @Transactional
    public ArrayList eliminarFuncion(Long codigo) {
        ArrayList retiro = repositorio.spDelFuncion(codigo);
        System.out.println("Glosa de respuesta: " + retiro.get(0));
        System.out.println("Código de estado: " + retiro.get(1));
        return retiro;
    }

    /**
     * Obtiene un registro de una Funcion
     *
     * @param codigo
     * @return
     */
    public ArrayList obtenerFuncion(Long codigo) {
        ArrayList entidad = repositorio.spGetFuncion(codigo);
        System.out.println("Resultado: \n" + entidad);
        return entidad;
    }

}
