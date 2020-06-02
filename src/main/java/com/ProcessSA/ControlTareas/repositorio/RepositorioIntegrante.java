package com.ProcessSA.ControlTareas.repositorio;

import com.ProcessSA.ControlTareas.modelo.Integrante;
import com.ProcessSA.ControlTareas.modelo.IntegrantePK;
import com.ProcessSA.ControlTareas.modelo.UnidadInterna;
import com.ProcessSA.ControlTareas.modelo.Usuario;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/*
 * Convensión Java Spring:
 *   public interface RepositorioIntegrante implements JpaRepository<[Clase], [tipoDatoID]>(){ }
 */
/**
 * Clase para definir las operaciones de BD relacionadas con INTEGRANTE
 */
@Repository
public class RepositorioIntegrante {

    private final EntityManager gestorDeEntidad;

    public RepositorioIntegrante(EntityManager gestorDeEntidad) {
        this.gestorDeEntidad = gestorDeEntidad;
    }

    /**
     * Ejecuta el procedimiento almacenado SP_GET_INTEGRANTES,
     * para obetener una lista de todos los registros de Integrante,
     * y devuelve un objeto ArrayList con los resultados obtenidos
     *
     * @return
     */
    public ArrayList spGetIntegrantes() {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_GET_INTEGRANTES");
        // Registrar los parámetros de salida
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_INTEGRANTES", void.class, ParameterMode.REF_CURSOR);
        // Ejecutar procedimiento
        consultaProcedimiento.execute();
        // Obtener los valores de salida
        String glosa = (String) consultaProcedimiento.getOutputParameterValue("OUT_GLOSA");
        int estado = (int) consultaProcedimiento.getOutputParameterValue("OUT_ESTADO");
        List<?> integrantes = obtener((ResultSet) consultaProcedimiento.getOutputParameterValue("OUT_INTEGRANTES"));
        // Encapsular los resultados
        ArrayList respuesta = new ArrayList<>();
        respuesta.add(glosa);
        respuesta.add(estado);
        integrantes.forEach(integrante -> respuesta.add(integrante));
        return respuesta;
    }

    /**
     * Recorre el ResultSet obtenido de un Cursor
     * producto de la ejecución de un Procedimiento Almacenado
     * retonando una lista de objetos de Integrantes
     *
     * @param rs
     * @return
     */
    public ArrayList obtener(ResultSet rs) {
        ArrayList lista = new ArrayList<>();
        Integrante entidad;
        IntegrantePK pkEntidad;
        try {
            while (rs.next()) {

                entidad = new Integrante();
                pkEntidad = new IntegrantePK();

                pkEntidad.setFkUsuario(
                        (Usuario) new RepositorioUsuario(this.gestorDeEntidad)
                                .spGetUsuario(
                                        rs.getLong("id_USUARIO")
                                ).get(2)
                );


                pkEntidad.setFkUnidadInterna(
                        (UnidadInterna) new RepositorioUnidadInterna(this.gestorDeEntidad)
                                .spGetUi(
                                        rs.getLong("codigo_UI")
                                ).get(2)
                );

                entidad.setPkIntegrante(pkEntidad);
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
     * Ejecuta el procedimiento almacenado SP_GET_INTEGRANTE,
     * para obtener datos de Integrante,
     * y devuelve un objeto ArrayList con los resultados obtenidos
     *
     * @param codigoUi
     * @param idUsuario
     * @return
     */
    public ArrayList spGetIntegrante(Long codigoUi, Long idUsuario) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_GET_INTEGRANTE");
        // Registrar los parámetros de entrada y salida
        consultaProcedimiento.registerStoredProcedureParameter("IN_CODIGO_UI", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_ID_USUARIO", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_INTEGRANTE", void.class, ParameterMode.REF_CURSOR);
        // Asignar valor de entrada
        consultaProcedimiento.setParameter("IN_CODIGO_UI", codigoUi);
        consultaProcedimiento.setParameter("IN_ID_USUARIO", idUsuario);
        // Ejecutar procedimiento
        consultaProcedimiento.execute();
        // Obtener los valores de salida
        String glosa = (String) consultaProcedimiento.getOutputParameterValue("OUT_GLOSA");
        int estado = (int) consultaProcedimiento.getOutputParameterValue("OUT_ESTADO");
        List<?> integrante = obtener((ResultSet) consultaProcedimiento.getOutputParameterValue("OUT_INTEGRANTE"));
        // Encapsular los los resultados
        ArrayList respuesta = new ArrayList<>();
        respuesta.add(glosa);
        respuesta.add(estado);
        respuesta.addAll(integrante);
        return respuesta;
    }

    /**
     * Ejecuta el procedimiento almacenado SP_DEL_INTEGRANTE,
     * para eliminar un registro de Integrante,
     * y retorna un objeto ArrayList con los resultados obtenidos
     *
     * @param codigoUi
     * @param idUsuario
     * @return
     */
    public ArrayList spDelIntegrante(Long codigoUi, Long idUsuario) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_DEL_INTEGRANTE");
        // Registrar los parámetros de entrada y salida
        consultaProcedimiento.registerStoredProcedureParameter("IN_CODIGO_UI", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_ID_USUARIO", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        // Asignar valores de entrada
        consultaProcedimiento.setParameter("IN_CODIGO_UI", codigoUi);
        consultaProcedimiento.setParameter("IN_ID_USUARIO", idUsuario);
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
     * Ejecuta el procedimiento almacenado SP_REG_INTEGRANTE,
     * para ingresar o actualizar un registro de Integrante,
     * y retorna un un objeto ArrayList con los resultados obtenidos
     *
     * @param codigoUi
     * @param idUsuario
     * @return
     */
    public ArrayList spRegIntegrante(Long codigoUi, Long idUsuario) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_REG_INTEGRANTE");
        // Registrar los parámetros de entrada y salida
        consultaProcedimiento.registerStoredProcedureParameter("IN_CODIGO_UI", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_ID_USUARIO", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        // Asignar valores de entrada
        consultaProcedimiento.setParameter("IN_CODIGO_UI", codigoUi);
        consultaProcedimiento.setParameter("IN_ID_USUARIO", idUsuario);
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
}
