package com.ProcessSA.ControlTareas.servicio;

import com.ProcessSA.ControlTareas.repositorio.RepositorioFlujoFuncion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Clase para definir los servicios asociados a FlujoFuncion
 */
@Service
public class ServicioFlujoFuncion {
    private final RepositorioFlujoFuncion repositorio;

    public ServicioFlujoFuncion(RepositorioFlujoFuncion repositorio){ this.repositorio = repositorio; }

    /**
     * Obtiene una lista de todas las FlujosFunciones
     *
     * @return
     */
    public ArrayList obtenerFlujosFunciones() {
        ArrayList lista = repositorio.spGetFlujosF();
        System.out.println("Lista de resultados:");
        lista.forEach(item -> System.out.println(item));
        return lista;
    }

    /**
     * Ingresa o actualiza un registro de FlujoFuncion
     *
     * @param indice
     * @param codigoProceso
     * @param codigoFuncion
     * @return
     */
    @Transactional
    public ArrayList registroFlujoFuncion(Byte indice, Long codigoProceso, Long codigoFuncion) {
        ArrayList registro = repositorio.spRegFlujoF(indice, codigoProceso, codigoFuncion);
        System.out.println("Glosa de respuesta: " + registro.get(0));
        System.out.println("Código de estado: " + registro.get(1));
        System.out.println("Identificador de salida: " + registro.get(2));
        return registro;
    }

    /**
     * Elimina un registro de FlujoFuncion
     *
     * @param codigoProceso
     * @param codigoFuncion
     * @return
     */
    @Transactional
    public ArrayList eliminarFlujoFuncion(Long codigoProceso, Long codigoFuncion) {
        ArrayList retiro = repositorio.spDelFlujoF(codigoProceso, codigoFuncion);
        System.out.println("Glosa de respuesta: " + retiro.get(0));
        System.out.println("Código de estado: " + retiro.get(1));
        return retiro;
    }

    /**
     * Obtiene los registros de FlujoFuncion con los parametros indicados
     * @param codigoProceso
     * @param codigoFuncion
     * @return
     */
    public ArrayList obtenerFlujoFuncion(Long codigoFuncion, Long codigoProceso) {
        ArrayList entidad = repositorio.spGetFlujoF(codigoFuncion, codigoProceso);
        System.out.println("Resultado: \n" + entidad);
        return entidad;
    }
}
