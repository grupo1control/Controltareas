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

    public ServicioUnidadInterna(RepositorioUnidadInterna repositorio) { this.repositorio = repositorio; }

    /**
     * Obtiene una lista de las Unidades Internas
     *
     * @return
     */
    public ArrayList obtenerUnidadesInternas() {
        ArrayList lista = repositorio.spGetAllUi();
        System.out.println("Lista de resultados:");
        lista.forEach(item -> System.out.println(item));
        return lista;
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
        ArrayList registro = repositorio.spRegUi(codigo, nombre, descripcion);
        System.out.println("Glosa de respuesta: " + registro.get(0));
        System.out.println("Código de estado: " + registro.get(1));
        System.out.println("Identificador de salida: " + registro.get(2));
        return registro;
    }

    /**
     * Elimina un registro de Unidad de Interna
     *
     * @param codigo
     * @return
     */
    @Transactional
    public ArrayList eliminarUnidadInterna(long codigo) {
        ArrayList retiro = repositorio.spDelUi(codigo);
        System.out.println("Glosa de respuesta: " + retiro.get(0));
        System.out.println("Código de estado: " + retiro.get(1));
        return retiro;
    }

    /**
     * Obtiene un registro de Unidad Interna
     *
     * @param codigo
     * @return
     */
    public ArrayList obtenerUnidadInterna(Long codigo) {
        ArrayList entidad = repositorio.spGetUi(codigo);
        System.out.println("Resultado: \n" + entidad);
        return entidad;
    }
}
