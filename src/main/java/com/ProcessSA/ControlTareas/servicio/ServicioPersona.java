package com.ProcessSA.ControlTareas.servicio;

import com.ProcessSA.ControlTareas.repositorio.RepositorioPersona;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional(readOnly = true)
public class ServicioPersona {
    private final RepositorioPersona repositorio;

    public ServicioPersona(RepositorioPersona repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Obtener una lista de todas las Personas
     *
     * @return
     */
    public ArrayList obtenerPersonas() {
        ArrayList lista = repositorio.spGetPersonas();
        System.out.println("Glosa de respuesta: " + lista.get(0));
        System.out.println("Código de estado: " + lista.get(1));
        System.out.println("Lista de resultados:");
        lista.forEach(item -> System.out.print(item + "   "));
        return lista;
    }
}
