package com.ProcessSA.ControlTareas.repositorio;

import com.ProcessSA.ControlTareas.modelo.Plazo;
import com.ProcessSA.ControlTareas.modelo.PlazoPK;
import com.ProcessSA.ControlTareas.modelo.Tarea;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/*
 * Convensión Java Spring:
 *   public interface RepositorioPlazo implements JpaRepository<[Clase], [tipoDatoID]>(){ }
 */
/**
 * Clase para definir las operaciones de BD relacionadas con PLAZO
 */
@Repository
public class RepositorioPlazo {

    private final EntityManager gestorDeEntidad;

    public RepositorioPlazo(EntityManager gestorDeEntidad) {
        this.gestorDeEntidad = gestorDeEntidad;
    }

    /**
     * Ejecuta el procedimiento almacenado SP_GET_PLAZOS,
     * para obetener una lista de todos los registros de Plazo,
     * y devuelve un objeto ArrayList con los resultados obtenidos
     *
     * @return
     */
    public ArrayList spGetPlazos() {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_GET_PLAZOS");
        // Registrar los parámetros de salida
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_PLAZOS", void.class, ParameterMode.REF_CURSOR);
        // Ejecutar procedimiento
        consultaProcedimiento.execute();
        // Obtener los valores de salida
        String glosa = (String) consultaProcedimiento.getOutputParameterValue("OUT_GLOSA");
        int estado = (int) consultaProcedimiento.getOutputParameterValue("OUT_ESTADO");
        List<?> plazos = obtener((ResultSet) consultaProcedimiento.getOutputParameterValue("OUT_PLAZOS"));
        // Encapsular los resultados
        ArrayList respuesta = new ArrayList<>();
        respuesta.add(glosa);
        respuesta.add(estado);
        plazos.forEach(plazo -> respuesta.add(plazo));
        return respuesta;
    }

    /**
     * Recorre el ResultSet obtenido de un Cursor
     * producto de la ejecución de un Procedimiento Almacenado
     * retonando una lista de objetos de Plazo
     *
     * @param rs
     * @return
     */
    public ArrayList obtener(ResultSet rs) {
        ArrayList lista = new ArrayList<>();
        Plazo entidad;
        PlazoPK pkEntidad;
        try {
            while (rs.next()) {
                entidad = new Plazo();
                pkEntidad = new PlazoPK();

                entidad.setFecha(rs.getDate("fecha"));

                pkEntidad.setContador(rs.getByte("contador"));
                pkEntidad.setFkTarea(
                        (Tarea) new RepositorioTarea(this.gestorDeEntidad)
                                .spGetTarea(
                                        rs.getLong("codigo_TAREA")
                                ).get(2)
                );
                entidad.setPkPlazo(pkEntidad);

                entidad.setCreado(rs.getDate("creado"));

                lista.add(entidad);
                //System.out.println(entidad);
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        //System.out.println(lista);
        return lista;
    }

    /**
     * Ejecuta el procedimiento almacenado SP_GET_PLAZO,
     * para obtener los datos de un Plazo,
     * y devuelve un objeto ArrayList con los resultados obtenidos
     *
     * @return
     */
    public ArrayList spGetPlazo(Long codigoTarea) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_GET_PLAZO");
        // Registrar los parámetros de entrada y salida
        consultaProcedimiento.registerStoredProcedureParameter("IN_CODIGO_TAREA", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_PLAZO", void.class, ParameterMode.REF_CURSOR);
        // Asignar valor de entrada
        consultaProcedimiento.setParameter("IN_CODIGO_TAREA", codigoTarea);
        // Ejecutar procedimiento
        consultaProcedimiento.execute();
        // Obtener los valores de salida
        String glosa = (String) consultaProcedimiento.getOutputParameterValue("OUT_GLOSA");
        int estado = (int) consultaProcedimiento.getOutputParameterValue("OUT_ESTADO");
        List<?> plazo = obtener((ResultSet) consultaProcedimiento.getOutputParameterValue("OUT_PLAZO"));
        // Encapsular los los resultados
        ArrayList respuesta = new ArrayList<>();
        respuesta.add(glosa);
        respuesta.add(estado);
        respuesta.addAll(plazo);
        return respuesta;
    }

    /**
     * Ejecuta el procedimiento almacenado SP_DEL_PLAZO,
     * para eliminar un registro de Plazo,
     * y retorna un objeto ArrayList con los resultados obtenidos
     * @param codigoTarea
     * @param contador
     * @return
     */
    public ArrayList spDelPlazo(Long codigoTarea, Byte contador) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_DEL_PLAZO");
        // Registrar los parámetros de entrada y salida
        consultaProcedimiento.registerStoredProcedureParameter("IN_CODIGO_TAREA", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_CONTADOR", Byte.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        // Asignar valores de entrada
        consultaProcedimiento.setParameter("IN_CODIGO_TAREA", codigoTarea);
        consultaProcedimiento.setParameter("IN_CONTADOR", contador);
        // Ejecutarprocedimiento
        consultaProcedimiento.execute();
        // Obtener valores de salida
        String glosa = (String) consultaProcedimiento.getOutputParameterValue("OUT_GLOSA");
        int estado = (int) consultaProcedimiento.getOutputParameterValue("OUT_ESTADO");
        // Encapsular resultado
        ArrayList respuesta = new ArrayList<>();
        respuesta.add(glosa);
        respuesta.add(estado);
        return respuesta;
    }

    /**
     * Ejecuta el procedimiento almacenado SP_REG_PLAZO,
     * para ingresar o actualizar un registro de Plazo,
     * y retorna un un objeto ArrayList con los resultados obtenidos
     *
     * @param fecha
     * @param codigoTarea
     * @return
     */
    public ArrayList spRegPlazo(Date fecha, Long codigoTarea) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_REG_PLAZO");
        // Registrar los parámetros de entrada y salida
        consultaProcedimiento.registerStoredProcedureParameter("IN_FECHA", Date.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_CODIGO_TAREA", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_COD_SALIDA", Byte.class, ParameterMode.OUT);
        // Asignar valores de entrada
        consultaProcedimiento.setParameter("IN_FECHA", fecha);
        consultaProcedimiento.setParameter("IN_CODIGO_TAREA", codigoTarea);
        // Ejecutar procedimiento
        consultaProcedimiento.execute();
        // Obtener valores de salida
        String glosa = (String) consultaProcedimiento.getOutputParameterValue("OUT_GLOSA");
        int estado = (int) consultaProcedimiento.getOutputParameterValue("OUT_ESTADO");
        Object codigoSalida = consultaProcedimiento.getOutputParameterValue("OUT_COD_SALIDA");
        // Encapsular resultados
        ArrayList respuesta = new ArrayList<>();
        respuesta.add(glosa);
        respuesta.add(estado);
        respuesta.add(codigoSalida);
        return respuesta;
    }

}
