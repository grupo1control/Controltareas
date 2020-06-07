package com.ProcessSA.ControlTareas.servicio;

import com.ProcessSA.ControlTareas.repositorio.RepositorioUnidadInterna;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Clase para definir los servicios asociados a Unidad Interna
 */
@Service
@Transactional(readOnly = true)
public class ServicioUnidadInterna {

    public final RepositorioUnidadInterna repositorio;

    public ServicioUnidadInterna(RepositorioUnidadInterna repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Obtiene una lista de las Unidades Internas
     *
     * @return
     */
    public ArrayList obtenerUnidadesInternas() {
        return repositorio.spGetAllUi();
    }

    /**
     * Ingresa o actualiza un registro de UI
     *
     * @param codigo
     * @param nombre
     * @param descripcion
     * @return
     */
    @Transactional
    public ArrayList registroUnidadInterna(Long codigo, String nombre, String descripcion) {
        return repositorio.spRegUi(codigo, nombre, descripcion);

    }

    /**
     * Elimina un registro de Unidad de Interna
     *
     * @param codigo
     * @return
     */
    @Transactional
    public ArrayList eliminarUnidadInterna(Long codigo) {
        return repositorio.spDelUi(codigo);

    }

    /**
     * Obtiene un registro de Unidad Interna
     *
     * @param codigo
     * @return
     */
    public ArrayList obtenerUnidadInterna(Long codigo) {
        return repositorio.spGetUi(codigo);

    }
}
