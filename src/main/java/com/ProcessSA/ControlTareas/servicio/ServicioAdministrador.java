package com.ProcessSA.ControlTareas.servicio;

import com.ProcessSA.ControlTareas.repositorio.RepositorioAdministrador;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;

/**
 * Clase para definir los servicios asociados a Tarea
 */
@Service
@Transactional(readOnly = true)

public class ServicioAdministrador {
    public final RepositorioAdministrador repositorio;

    public ServicioAdministrador(RepositorioAdministrador repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Obtiene una lista de todos los Administradores
     *
     * @return
     */
    public ArrayList obtenerAdministradores() {
        ArrayList lista = repositorio.spGetAdministradores();
        System.out.println("Lista de resultados:");
        lista.forEach(item -> System.out.println(item));
        return lista;
    }

    /**
     * Ingresa o actualiza un registro de Administrador
     *
     * @param id
     * @param creado

     * @return
     */
    @Transactional
    public ArrayList registroAdministrador(long id, Date creado) {
        ArrayList registro = repositorio.spRegAdministrador(id, creado);
        System.out.println("Glosa de respuesta: " + registro.get(0));
        System.out.println("Código de estado: " + registro.get(1));
        System.out.println("Identificador de salida: " + registro.get(2));
        return registro;
    }


    /**
     * Obtiene un registro de un Administrador
     *
     * @param id
     * @return
     */
    public ArrayList obtenerAdministrador(long id) {
        ArrayList entidad = repositorio.spGetAdministrador(id);
        System.out.println("Resultado: \n" + entidad);
        return entidad;
    }
}
