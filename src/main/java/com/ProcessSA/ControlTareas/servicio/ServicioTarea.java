package com.ProcessSA.ControlTareas.servicio;

import com.ProcessSA.ControlTareas.repositorio.RepositorioTarea;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;

/**
 * Clase para definir los servicios asociados a Tarea
 */
@Service
@Transactional(readOnly = true)
public class ServicioTarea {

    public final RepositorioTarea repositorio;

    public ServicioTarea(RepositorioTarea repositorio) { this.repositorio = repositorio; }

    /**
     * Obtiene una lista de todas las Tareas
     *
     * @return
     */
    public ArrayList obtenerTareas() {
        ArrayList lista = repositorio.spGetTareas();
        System.out.println("Lista de resultados:");
        lista.forEach(item -> System.out.println(item));
        return lista;
    }

    /**
     * Ingresa o actualiza un registro de Tarea
     *
     * @param codigo
     * @param nombre
     * @param descripcion
     * @param estado
     * @return
     */
    @Transactional
    public ArrayList registroTarea(Long codigo, String nombre, String descripcion, String estado) {
        ArrayList registro = repositorio.spRegTarea(codigo, nombre, descripcion, estado);
        System.out.println("Glosa de respuesta: " + registro.get(0));
        System.out.println("Código de estado: " + registro.get(1));
        System.out.println("Identificador de salida: " + registro.get(2));
        return registro;
    }

    /**
     * Elimina un registro de Tarea
     *
     * @param codigo
     * @return
     */
    @Transactional
    public ArrayList eliminarTarea(Long codigo) {
        ArrayList retiro = repositorio.spDelTarea(codigo);
        System.out.println("Glosa de respuesta: " + retiro.get(0));
        System.out.println("Código de estado: " + retiro.get(1));
        return retiro;
    }

    /**
     * Obtiene un registro de una Tarea
     *
     * @param codigo
     * @return
     */
    public ArrayList obtenerTarea(Long codigo) {
        ArrayList entidad = repositorio.spGetTarea(codigo);
        System.out.println("Resultado: \n" + entidad);
        return entidad;
    }

}
