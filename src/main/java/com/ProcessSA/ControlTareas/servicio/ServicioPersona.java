package com.ProcessSA.ControlTareas.servicio;

import com.ProcessSA.ControlTareas.repositorio.RepositorioPersona;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.sql.Date;

/**
 * Clase para definir los servicio asociados a Persona
 */
@Service
@Transactional(readOnly = true)
public class ServicioPersona {
    private final RepositorioPersona repositorio;

    public ServicioPersona(RepositorioPersona repositorio) { this.repositorio = repositorio; }

    /**
     * Obtiene una lista de todas las Personas
     *
     * @return
     */
    public ArrayList obtenerPersonas() {
        ArrayList lista = repositorio.spGetPersonas();
        System.out.println("Glosa de respuesta: " + lista.get(0));
        System.out.println("Código de estado: " + lista.get(1));
        System.out.println("Lista de resultados:");
        lista.forEach(item -> System.out.println(item));
        return lista;
    }

    /**
     * Ingresa o actualiza un registro de Persona
     *
     * @param rut
     * @param nombre
     * @param apellido
     * @param natalicio
     * @return
     */
    @Transactional
    public ArrayList registroPersona(String rut, String nombre, String apellido, Date natalicio) {
        ArrayList registro = repositorio.spRegPersona(rut, nombre, apellido, natalicio);
        System.out.println("Glosa de respuesta: " + registro.get(0));
        System.out.println("Código de estado: " + registro.get(1));
        System.out.println("Identificador de salida: " + registro.get(2));
        return registro;
    }

    /**
     * Elimina un registro de Persona
     *
     * @param rut
     * @return
     */
    @Transactional
    public ArrayList eliminarPersona(String rut) {
        ArrayList retiro = repositorio.spDelPersona(rut);
        System.out.println("Glosa de respuesta: " + retiro.get(0));
        System.out.println("Código de estado: " + retiro.get(1));
        return retiro;
    }

    /**
     * Obtiene un registro de una Persona
     *
     * @param rut
     * @return
     */
    public ArrayList obtenerPersona(String rut) {
        ArrayList entidad = repositorio.spGetPersona(rut);
        System.out.println("Glosa de respuesta: " + entidad.get(0));
        System.out.println("Código de estado: " + entidad.get(1));
        System.out.println("Resultado: \n" + entidad.get(2));
        return entidad;
    }

}
