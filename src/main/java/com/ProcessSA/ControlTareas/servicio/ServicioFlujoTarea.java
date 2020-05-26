package com.ProcessSA.ControlTareas.servicio;

import com.ProcessSA.ControlTareas.repositorio.RepositorioFlujoTarea;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Clase para definir los servicios asociados a FlujoTarea
 */
@Service
public class ServicioFlujoTarea {

    private final RepositorioFlujoTarea repositorio;

    public ServicioFlujoTarea(RepositorioFlujoTarea repositorio){ this.repositorio = repositorio; }

    /**
     * Obtiene una lista de todas las FlujosTareas
     *
     * @return
     */
    public ArrayList obtenerFlujosTareas() {
        ArrayList lista = repositorio.spGetFlujosT();
        System.out.println("Glosa de respuesta: " + lista.get(0));
        System.out.println("Código de estado: " + lista.get(1));
        System.out.println("Lista de resultados:");
        lista.forEach(item -> System.out.println(item));
        return lista;
    }

    /**
     * Ingresa o actualiza un registro de FlujoTarea
     *
     * @param indice
     * @param codigoTarea
     * @param codigoFuncion
     * @return
             */
    @Transactional
    public ArrayList registroFlujoTarea(byte indice, Long codigoTarea, Long codigoFuncion) {
        ArrayList registro = repositorio.spRegFlujoT(indice, codigoTarea, codigoFuncion);
        System.out.println("Glosa de respuesta: " + registro.get(0));
        System.out.println("Código de estado: " + registro.get(1));
        System.out.println("Identificador de salida: " + registro.get(2));
        return registro;
    }

    /**
     * Elimina un registro de FlujoTarea
     *
     * @param indice
     * @param codigoTarea
     * @param codigoFuncion
     * @return
     */
    @Transactional
    public ArrayList eliminarFlujoTarea(byte indice, Long codigoTarea, Long codigoFuncion) {
        ArrayList retiro = repositorio.spDelFlujoT(indice, codigoTarea, codigoFuncion);
        System.out.println("Glosa de respuesta: " + retiro.get(0));
        System.out.println("Código de estado: " + retiro.get(1));
        return retiro;
    }

    /**
     * Obtiene los registros de FlujoTarea con los parametros indicados
     * @param codigoTarea
     * @param codigoFuncion
     * @return
     */
    public ArrayList obtenerFlujoTarea(Long codigoTarea, Long codigoFuncion) {
        ArrayList entidad = repositorio.spGetFlujoT(codigoTarea, codigoFuncion);
        System.out.println("Glosa de respuesta: " + entidad.get(0));
        System.out.println("Código de estado: " + entidad.get(1));
        System.out.println("Resultado: \n" + entidad.get(2));
        entidad.forEach(item -> System.out.println(item));
        return entidad;
    }

}
