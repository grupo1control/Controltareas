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
        return repositorio.spGetFuncionarios();
    }

    /**
     * Ingresa o actualiza un registro de Funcionario
     *
     * @param id
     * @return
     */
    @Transactional
    public ArrayList registroFuncionario(Long id) {
        return repositorio.spRegFuncionario(id);
    }


    /**
     * Obtiene un registro de un Funcionario
     *
     * @param id
     * @return
     */
    public ArrayList obtenerFuncionario(Long id) {
        return repositorio.spGetFuncionario(id);
    }

    /**
     * Elimina un Funcionario
     *
     * @param id
     * @return
     */
    @Transactional
    public ArrayList eliminarFuncionario(Long id) {
        return repositorio.spDelFuncionario(id);
    }

}
