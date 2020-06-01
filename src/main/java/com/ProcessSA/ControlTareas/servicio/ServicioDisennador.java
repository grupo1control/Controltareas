package com.ProcessSA.ControlTareas.servicio;

import com.ProcessSA.ControlTareas.repositorio.RepositorioDisennador;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;

/**
 * Clase para definir los servicios asociados a Tarea
 */
@Service
@Transactional(readOnly = true)
public class ServicioDisennador {

    public final RepositorioDisennador repositorio;

    public ServicioDisennador(RepositorioDisennador repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Obtiene una lista de todos los Diseñadores
     *
     * @return
     */
    public ArrayList obtenerDisennadores() {
        ArrayList lista = repositorio.spGetDisennadores();
        System.out.println("Lista de resultados:");
        lista.forEach(item -> System.out.println(item));
        return lista;
    }

    /**
     * Ingresa o actualiza un registro de Diseñador
     *
     * @param id

     * @return
     */
    @Transactional
    public ArrayList registroDisennador(Long id) {
        ArrayList registro = repositorio.spRegDisennador(id);
        System.out.println("Glosa de respuesta: " + registro.get(0));
        System.out.println("Código de estado: " + registro.get(1));
        System.out.println("Identificador de salida: " + registro.get(2));
        return registro;
    }


    /**
     * Obtiene un registro de un Diseñador
     *
     * @param id
     * @return
     */
    public ArrayList obtenerDisennador(Long id) {
        ArrayList entidad = repositorio.spGetDisennador(id);
        System.out.println("Resultado: \n" + entidad);
        return entidad;
    }

    /**
     * Elimina un Diseñador
     *
     * @param id
     * @return
     */
    @Transactional
    public ArrayList eliminarDisennador(Long id) {
        ArrayList retiro = repositorio.spDelDisennador(id);
        System.out.println("Glosa de respuesta: " + retiro.get(0));
        System.out.println("Código de estado: " + retiro.get(1));
        return retiro;
    }
}
