package com.ProcessSA.ControlTareas.servicio;

import com.ProcessSA.ControlTareas.repositorio.RepositorioPlazo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Date;
import java.util.ArrayList;

/**
 * Clase para definir los servicios asociados a Plazo
 */
@Service
@Transactional(readOnly = true)
public class ServicioPlazo {

    public final RepositorioPlazo repositorio;

    public ServicioPlazo(RepositorioPlazo repositorio) { this.repositorio = repositorio; }

    /**
     * Obtiene una lista de todos los Plazos
     *
     * @return
     */
    public ArrayList obtenerPlazos() {
        ArrayList lista = repositorio.spGetPlazos();
        System.out.println("Lista de resultados:");
        lista.forEach(item -> System.out.println(item));
        return lista;
    }

    /**
     * Ingresa un registro de Plazo
     *
     * @param fecha
     * @param codigoTarea
     * @return
     */
    @Transactional
    public ArrayList registroPlazo(Date fecha, Long codigoTarea) {
        ArrayList registro = repositorio.spRegPlazo(fecha, codigoTarea);
        System.out.println("Glosa de respuesta: " + registro.get(0));
        System.out.println("Código de estado: " + registro.get(1));
        System.out.println("Identificador de salida: " + registro.get(2));
        return registro;
    }

    /**
     * Elimina un registro de Plazo
     *
     * @param codigoTarea
     * @param contador
     * @return
     */
    @Transactional
    public ArrayList eliminarPlazo(Long codigoTarea, Byte contador) {
        ArrayList retiro = repositorio.spDelPlazo(codigoTarea, contador);
        System.out.println("Glosa de respuesta: " + retiro.get(0));
        System.out.println("Código de estado: " + retiro.get(1));
        return retiro;
    }

    /**
     * Obtiene un registro de un Plazo
     *
     * @param codigoTarea
     * @return
     */
    public ArrayList obtenerPlazo(Long codigoTarea) {
        ArrayList entidad = repositorio.spGetPlazo(codigoTarea);
        System.out.println("Resultado: \n" + entidad);
        return entidad;
    }

}
