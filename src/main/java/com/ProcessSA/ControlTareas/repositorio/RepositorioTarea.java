package com.ProcessSA.ControlTareas.repositorio;

import com.ProcessSA.ControlTareas.modelo.Tarea;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/*
 * Convensión Java Spring:
 *   public interface RepositorioTarea implements JpaRepository<[Clase], [tipoDatoID]>(){ }
 */
/**
 * Clase para definir las operaciones de BD relacionadas con TAREA
 */
@Repository
public class RepositorioTarea {

    private final EntityManager gestorDeEntidad;

    public RepositorioTarea(EntityManager gestorDeEntidad) {
        this.gestorDeEntidad = gestorDeEntidad;
    }

    /**
     * Ejecuta el procedimiento almacenado SP_GET_TAREAS,
     * para obetener una lista de todos los registros de Tarea,
     * y devuelve un objeto ArrayList con los resultados obtenidos
     *
     * @return
     */
    public ArrayList spGetTareas() {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_GET_TAREAS");
        // Registrar los parámetros de salida
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_TAREAS", void.class, ParameterMode.REF_CURSOR);
        // Ejecutar procedimiento
        consultaProcedimiento.execute();
        // Obtener los valores de salida
        String glosa = (String) consultaProcedimiento.getOutputParameterValue("OUT_GLOSA");
        int estado = (int) consultaProcedimiento.getOutputParameterValue("OUT_ESTADO");
        List<?> tareas = obtener((ResultSet) consultaProcedimiento.getOutputParameterValue("OUT_TAREAS"));
        // Encapsular los resultados
        ArrayList respuesta = new ArrayList<>();
        respuesta.add(glosa);
        respuesta.add(estado);
        tareas.forEach(tarea -> respuesta.add(tarea));
        return respuesta;
    }

    /**
     * Recorre el ResultSet obtenido de un Cursor
     * producto de la ejecución de un Procedimiento Almacenado
     * retonando una lista de objetos de Tarea
     *
     * @param rs
     * @return
     */
    public ArrayList obtener(ResultSet rs) {
        ArrayList lista = new ArrayList<>();
        Tarea entidad;
        try {
            while (rs.next()) {
                entidad = new Tarea();
                entidad.setCodigo(rs.getLong("codigo"));
                entidad.setNombre(rs.getString("nombre"));
                entidad.setDescripcion(rs.getString("descripcion"));
                entidad.setEstado(rs.getString("estado"));
                entidad.setCreada(rs.getDate("creada"));
                entidad.setModificada(rs.getDate("modificada"));
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
     * Ejecuta el procedimiento almacenado SP_GET_TAREA,
     * para obtener los datos de una Tarea,
     * y devuelve un objeto ArrayList con los resultados obtenidos
     *
     * @return
     */
    public ArrayList spGetTarea(Long codigo) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_GET_TAREA");
        // Registrar los parámetros de entrada y salida
        consultaProcedimiento.registerStoredProcedureParameter("IN_CODIGO", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_TAREA", void.class, ParameterMode.REF_CURSOR);
        // Asignar valor de entrada
        consultaProcedimiento.setParameter("IN_CODIGO", codigo);
        // Ejecutar procedimiento
        consultaProcedimiento.execute();
        // Obtener los valores de salida
        String glosa = (String) consultaProcedimiento.getOutputParameterValue("OUT_GLOSA");
        int estado = (int) consultaProcedimiento.getOutputParameterValue("OUT_ESTADO");
        List<?> tarea = obtener((ResultSet) consultaProcedimiento.getOutputParameterValue("OUT_TAREA"));
        // Encapsular los los resultados
        ArrayList respuesta = new ArrayList<>();
        respuesta.add(glosa);
        respuesta.add(estado);
        respuesta.addAll(tarea);
        return respuesta;
    }

    /**
     * Ejecuta el procedimiento almacenado SP_DEL_TAREA,
     * para eliminar un registro de Tarea,
     * y retorna un objeto ArrayList con los resultados obtenidos
     * @param codigo
     * @return
     */
    public ArrayList spDelTarea(Long codigo) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_DEL_TAREA");
        // Registrar los parámetros de entrada y salida
        consultaProcedimiento.registerStoredProcedureParameter("IN_CODIGO", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        // Asignar valores de entrada
        consultaProcedimiento.setParameter("IN_CODIGO", codigo);
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
     * Ejecuta el procedimiento almacenado SP_REG_TAREA,
     * para ingresar o actualizar un registro de Tarea,
     * y retorna un un objeto ArrayList con los resultados obtenidos
     *
     * @param codigo
     * @param nombre
     * @param descripcion
     * @param inputEstado
     * @return
     */
    public ArrayList spRegTarea(Long codigo, String nombre, String descripcion, String inputEstado) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_REG_TAREA");
        // Registrar los parámetros de entrada y salida
        consultaProcedimiento.registerStoredProcedureParameter("IN_CODIGO", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_NOMBRE", String.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_DESCRIPCION", String.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_ESTADO", String.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_COD_SALIDA", Long.class, ParameterMode.OUT);
        // Asignar valores de entrada
        consultaProcedimiento.setParameter("IN_CODIGO", codigo);
        consultaProcedimiento.setParameter("IN_NOMBRE", nombre);
        consultaProcedimiento.setParameter("IN_DESCRIPCION", descripcion);
        consultaProcedimiento.setParameter("IN_ESTADO", inputEstado);
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
