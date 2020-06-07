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

    public ServicioPersona(RepositorioPersona repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Obtiene una lista de todas las Personas
     *
     * @return
     */
    public ArrayList obtenerPersonas() {
        return repositorio.spGetPersonas();

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
        return repositorio.spRegPersona(rut, nombre, apellido, natalicio);

    }

    /**
     * Elimina un registro de Persona
     *
     * @param rut
     * @return
     */
    @Transactional
    public ArrayList eliminarPersona(String rut) {
        return repositorio.spDelPersona(rut);

    }

    /**
     * Obtiene un registro de una Persona
     *
     * @param rut
     * @return
     */
    public ArrayList obtenerPersona(String rut) {
        return repositorio.spGetPersona(rut);

    }

}
