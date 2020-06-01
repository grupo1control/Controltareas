package com.ProcessSA.ControlTareas.servicio;

import com.ProcessSA.ControlTareas.repositorio.RepositorioFuncionario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;

/**
 * Clase para definir los servicios asociados a Funcionario
 */
@Service
@Transactional(readOnly = true)
public class ServicioFuncionario {

    public final RepositorioFuncionario repositorio;

    public ServicioFuncionario(RepositorioFuncionario repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Obtiene una lista de todos los Funcionarios
     *
     * @return
     */
    public ArrayList obtenerFuncionarios() {
        ArrayList lista = repositorio.spGetFuncionarios();
        System.out.println("Lista de resultados:");
        lista.forEach(item -> System.out.println(item));
        return lista;
    }

    /**
     * Ingresa o actualiza un registro de Funcionario
     *
     * @param id

     * @return
     */
    @Transactional
    public ArrayList registroFuncionario(Long id) {
        ArrayList registro = repositorio.spRegFuncionario(id);
        System.out.println("Glosa de respuesta: " + registro.get(0));
        System.out.println("Código de estado: " + registro.get(1));
        System.out.println("Identificador de salida: " + registro.get(2));
        return registro;
    }


    /**
     * Obtiene un registro de un Funcionario
     *
     * @param id
     * @return
     */
    public ArrayList obtenerFuncionario(Long id) {
        ArrayList entidad = repositorio.spGetFuncionario(id);
        System.out.println("Resultado: \n" + entidad);
        return entidad;
    }

    /**
     * Elimina un Funcionario
     *
     * @param id
     * @return
     */
    @Transactional
    public ArrayList eliminarFuncionario(Long id) {
        ArrayList retiro = repositorio.spDelFuncionario(id);
        System.out.println("Glosa de respuesta: " + retiro.get(0));
        System.out.println("Código de estado: " + retiro.get(1));
        return retiro;
    }

}
