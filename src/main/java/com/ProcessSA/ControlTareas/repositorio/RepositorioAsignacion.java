package com.ProcessSA.ControlTareas.repositorio;

import com.ProcessSA.ControlTareas.modelo.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/*
 * Convensión Java Spring:
 *   public interface RepositorioAsignacion implements JpaRepository<[Clase], [tipoDatoID]>(){ }
 */
/**
 * Clase para definir las operaciones de BD relacionadas con Asignacion
 */
@Repository

public class RepositorioAsignacion {
    private final EntityManager gestorDeEntidad;

    public RepositorioAsignacion(EntityManager gestorDeEntidad) {
        this.gestorDeEntidad = gestorDeEntidad;
    }

    /**
     * Ejecuta el procedimiento almacenado SP_GET_ASIGNACIONES,
     * para obetener una lista de todos los registros de Asignacion,
     * y devuelve un objeto ArrayList con los resultados obtenidos
     *
     * @return
     */
    public ArrayList spGetAsignaciones() {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_GET_ASIGNACIONES");
        // Registrar los parámetros de salida
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ASIGNACIONES", void.class, ParameterMode.REF_CURSOR);
        // Ejecutar procedimiento
        consultaProcedimiento.execute();
        // Obtener los valores de salida
        String glosa = (String) consultaProcedimiento.getOutputParameterValue("OUT_GLOSA");
        int estado = (int) consultaProcedimiento.getOutputParameterValue("OUT_ESTADO");
        List<?> asignaciones = obtener((ResultSet) consultaProcedimiento.getOutputParameterValue("OUT_ASIGNACIONES"));
        // Encapsular los resultados
        ArrayList respuesta = new ArrayList<>();
        respuesta.add(glosa);
        respuesta.add(estado);
        asignaciones.forEach(asignacion -> respuesta.add(asignacion));
        return respuesta;
    }

    /**
     * Recorre el ResultSet obtenido de un Cursor
     * producto de la ejecución de un Procedimiento Almacenado
     * retonando una lista de objetos de Asignaacion
     *
     * @param rs
     * @return
     */
    public ArrayList obtener(ResultSet rs) {
        ArrayList lista = new ArrayList<>();
        Asignacion entidad;
        AsignacionPK pkEntidad;
        try {
            while (rs.next()) {

                entidad = new Asignacion();
                pkEntidad = new AsignacionPK();

                entidad.setRol(rs.getString("rol"));
                entidad.setEstado(rs.getString("estado"));
                entidad.setNota(rs.getString("nota"));

                pkEntidad.setFkTarea(
                        (Tarea) new RepositorioTarea(this.gestorDeEntidad)
                                .spGetTarea(
                                        rs.getLong("codigo_TAREA")
                                ).get(2)
                );


                pkEntidad.setFkIntegrante(
                        (Integrante) new RepositorioIntegrante(this.gestorDeEntidad)
                                .spGetIntegrante(
                                        rs.getLong("codigo_UI"),
                                        rs.getLong("id_USUARIO")
                                ).get(2)
                );

                entidad.setPkAsignacion(pkEntidad);
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
     * Ejecuta el procedimiento almacenado SP_GET_ASIGNACION,
     * para obtener datos de Asignacion,
     * y devuelve un objeto ArrayList con los resultados obtenidos
     * @param idUsuario
     * @param codigoUi
     * @param codigoTarea
     * @return
     */
    public ArrayList spGetAsignacion(Long idUsuario, Long codigoUi, Long codigoTarea) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_GET_ASIGNACION");
        // Registrar los parámetros de entrada y salida
        consultaProcedimiento.registerStoredProcedureParameter("IN_ID_USUARIO", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_CODIGO_UI", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_CODIGO_TAREA", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ASIGNACION", void.class, ParameterMode.REF_CURSOR);
        // Asignar valor de entrada
        consultaProcedimiento.setParameter("IN_ID_USUARIO", idUsuario);
        consultaProcedimiento.setParameter("IN_CODIGO_UI", codigoUi);
        consultaProcedimiento.setParameter("IN_CODIGO_TAREA", codigoTarea);
        // Ejecutar procedimiento
        consultaProcedimiento.execute();
        // Obtener los valores de salida
        String glosa = (String) consultaProcedimiento.getOutputParameterValue("OUT_GLOSA");
        int estado = (int) consultaProcedimiento.getOutputParameterValue("OUT_ESTADO");
        List<?> asignacion = obtener((ResultSet) consultaProcedimiento.getOutputParameterValue("OUT_ASIGNACION"));
        // Encapsular los los resultados
        ArrayList respuesta = new ArrayList<>();
        respuesta.add(glosa);
        respuesta.add(estado);
        respuesta.addAll(asignacion);
        return respuesta;
    }

    /**
     * Ejecuta el procedimiento almacenado SP_DEL_ASIGNACION,
     * para eliminar un registro de Asignacion,
     * y retorna un objeto ArrayList con los resultados obtenidos
     *
     * @param idUsuario
     * @param codigoUi
     * @param codigoTarea
     * @return
     */
    public ArrayList spDelAsignacion(Long idUsuario, Long codigoUi, Long codigoTarea) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_DEL_ASIGNACION");
        // Registrar los parámetros de entrada y salida
        consultaProcedimiento.registerStoredProcedureParameter("IN_ID_USUARIO", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_CODIGO_UI", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_CODIGO_TAREA", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        // Asignar valores de entrada
        consultaProcedimiento.setParameter("IN_ID_USUARIO", idUsuario);
        consultaProcedimiento.setParameter("IN_CODIGO_UI", codigoUi);
        consultaProcedimiento.setParameter("IN_CODIGO_TAREA", codigoTarea);
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
     * Ejecuta el procedimiento almacenado SP_REG_ASIGNACION,
     * para ingresar o actualizar un registro de Asignacion,
     * y retorna un un objeto ArrayList con los resultados obtenidos
     *
     * @param codigoTarea
     * @param codigoUi
     * @param idUsuario
     * @param rol
     * @param inputEstado
     * @param nota
     * @return
     */
    public ArrayList spRegAsignacion(Long codigoTarea, Long codigoUi, Long idUsuario, String rol, String inputEstado, String nota) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_REG_ASIGNACION");
        // Registrar los parámetros de entrada y salida
        consultaProcedimiento.registerStoredProcedureParameter("IN_CODIGO_TAREA", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_CODIGO_UI", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_ID_USUARIO", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_ROL", String.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_ESTADO", String.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_NOTA", String.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        // Asignar valores de entrada
        consultaProcedimiento.setParameter("IN_CODIGO_TAREA", codigoTarea);
        consultaProcedimiento.setParameter("IN_CODIGO_UI", codigoUi);
        consultaProcedimiento.setParameter("IN_ID_USUARIO", idUsuario);
        consultaProcedimiento.setParameter("IN_ROL", rol);
        consultaProcedimiento.setParameter("IN_ESTADO", inputEstado);
        consultaProcedimiento.setParameter("IN_NOTA", nota);
        // Ejecutar procedimiento
        consultaProcedimiento.execute();
        // Obtener valores de salida
        String glosa = (String) consultaProcedimiento.getOutputParameterValue("OUT_GLOSA");
        int estado = (int) consultaProcedimiento.getOutputParameterValue("OUT_ESTADO");
        // Encapsular resultados
        ArrayList respuesta = new ArrayList<>();
        respuesta.add(glosa);
        respuesta.add(estado);
        return respuesta;
    }
}
