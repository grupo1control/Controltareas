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
 *   public interface RepositorioProceso implements JpaRepository<[Clase], [tipoDatoID]>(){ }
 */

/**
 * Clase para definir las operaciones de BD relacionadas con PROCESO
 */
@Repository
public class RepositorioProceso {
    private final EntityManager gestorDeEntidad;

    public RepositorioProceso(EntityManager gestorDeEntidad) {
        this.gestorDeEntidad = gestorDeEntidad;
    }

    /**
     * Ejecuta el procedimiento almacenado SP_GET_PROCESOS,
     * para obetener una lista de todos los registros de Procesos,
     * y devuelve un objeto ArrayList con los resultados obtenidos
     *
     * @return
     */
    public ArrayList spGetProcesos() {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_GET_PROCESOS");
        // Registrar los parámetros de salida
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_FLUJOS", void.class, ParameterMode.REF_CURSOR);
        // Ejecutar procedimiento
        consultaProcedimiento.execute();
        // Obtener los valores de salida
        String glosa = (String) consultaProcedimiento.getOutputParameterValue("OUT_GLOSA");
        int estado = (int) consultaProcedimiento.getOutputParameterValue("OUT_ESTADO");
        List<?> procesos = obtener((ResultSet) consultaProcedimiento.getOutputParameterValue("OUT_PROCESOS"));
        // Encapsular los resultados
        ArrayList respuesta = new ArrayList<>();
        respuesta.add(glosa);
        respuesta.add(estado);
        procesos.forEach(proceso -> respuesta.add(proceso));
        return respuesta;
    }

    /**
     * Recorre el ResultSet obtenido de un Cursor
     * producto de la ejecución de un Procedimiento Almacenado
     * retonando una lista de objetos de Proceso
     *
     * @param rs
     * @return
     */
    public ArrayList obtener(ResultSet rs) {
        ArrayList lista = new ArrayList<>();
        Proceso entidad;
        try {
            while (rs.next()) {

                entidad = new Proceso();

                entidad.setCodigo(rs.getLong("id"));
                entidad.setIndice(rs.getByte("indice"));
                entidad.setNombre(rs.getString("nombre"));
                entidad.setDescripcion(rs.getString("descripcion"));
                entidad.setEstado(rs.getString("estado"));

                entidad.setUnidadInternaFk(
                        (UnidadInterna) new RepositorioUnidadInterna(this.gestorDeEntidad)
                                .spGetUi(
                                        rs.getLong("codigo_UI")
                                ).get(2)
                );


                entidad.setDisennadorFk(
                        (Disennador) new RepositorioDisennador(this.gestorDeEntidad)
                                .spGetDisennador(
                                        rs.getLong("id_DISENNADOR")
                                ).get(2)
                );

                entidad.setProyectoFk(
                        (Proyecto) new RepositorioProyecto(this.gestorDeEntidad)
                                .spGetProyecto(
                                        rs.getLong("codigo_PROYECTO")
                                ).get(2)
                );


                entidad.setCreado(rs.getDate("creado"));
                entidad.setModificado(rs.getDate("modificado"));
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
     * Ejecuta el procedimiento almacenado SP_GET_PROCESO,
     * para obtener los datos de Proceso,
     * y devuelve un objeto ArrayList con los resultados obtenidos
     *
     * @param codigo
     * @return
     */
    public ArrayList spGetProceso(Long codigo) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_GET_PROCESO");
        // Registrar los parámetros de entrada y salida
        consultaProcedimiento.registerStoredProcedureParameter("IN_CODIGO", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_PROCESO", void.class, ParameterMode.REF_CURSOR);
        // Asignar valor de entrada
        consultaProcedimiento.setParameter("IN_CODIGO", codigo);
        // Ejecutar procedimiento
        consultaProcedimiento.execute();
        // Obtener los valores de salida
        String glosa = (String) consultaProcedimiento.getOutputParameterValue("OUT_GLOSA");
        int estado = (int) consultaProcedimiento.getOutputParameterValue("OUT_ESTADO");
        List<?> proceso = obtener((ResultSet) consultaProcedimiento.getOutputParameterValue("OUT_PROCESO"));
        // Encapsular los los resultados
        ArrayList respuesta = new ArrayList<>();
        respuesta.add(glosa);
        respuesta.add(estado);
        respuesta.addAll(proceso);
        return respuesta;
    }

    /**
     * Ejecuta el procedimiento almacenado SP_DEL_PROCESO,
     * para eliminar un registro de Proceso,
     * y retorna un objeto ArrayList con los resultados obtenidos
     *
     * @param codigo
     * @return
     */
    public ArrayList spDelProceso(Long codigo) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_DEL_PROCESO");
        // Registrar los parámetros de entrada y salida
        consultaProcedimiento.registerStoredProcedureParameter("IN_CODIGO", byte.class, ParameterMode.IN);
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
     * Ejecuta el procedimiento almacenado SP_REG_PROCESO,
     * para ingresar o actualizar un registro de Proceso,
     * y retorna un un objeto ArrayList con los resultados obtenidos
     *
     * @param codigo
     * @param indice
     * @param nombre
     * @param descripcion
     * @param input_estado
     * @param codigo_ui
     * @param id_disennador
     * @param codigo_proyecto
     * @return
     */
    public ArrayList spRegProceso(Long codigo, Byte indice, String nombre, String descripcion, String input_estado, Long codigo_ui, Long id_disennador, Long codigo_proyecto) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_REG_DISENNADOR");
        // Registrar los parámetros de entrada y salida
        consultaProcedimiento.registerStoredProcedureParameter("IN_CODIGO", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_INDICE", Byte.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_NOMBRE", String.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_DESCRIPCION", String.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_INPUT_ESTADO", String.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_CODIGO_UI", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_ID_DISENNADOR", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_CODIGO_PROYECTO", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_INDEX_SALIDA", byte.class, ParameterMode.OUT);
        // Asignar valores de entrada
        consultaProcedimiento.setParameter("IN_CODIGO", codigo);
        consultaProcedimiento.setParameter("IN_INDICE", indice);
        consultaProcedimiento.setParameter("IN_NOMBRE", nombre);
        consultaProcedimiento.setParameter("IN_DESCRIPCION", descripcion);
        consultaProcedimiento.setParameter("IN_INPUT_ESTADO", input_estado);
        consultaProcedimiento.setParameter("IN_CODIGO_UI", codigo_ui);
        consultaProcedimiento.setParameter("IN_DISENNADOR", id_disennador);
        consultaProcedimiento.setParameter("IN_CODIGO_PROYECTO", codigo_proyecto);
        // Ejecutar procedimiento
        consultaProcedimiento.execute();
        // Obtener valores de salida
        String glosa = (String) consultaProcedimiento.getOutputParameterValue("OUT_GLOSA");
        int estado = (int) consultaProcedimiento.getOutputParameterValue("OUT_ESTADO");
        byte codigoSalida = (byte) consultaProcedimiento.getOutputParameterValue("OUT_INDEX_SALIDA");
        // Encapsular resultados
        ArrayList respuesta = new ArrayList<>();
        respuesta.add(glosa);
        respuesta.add(estado);
        respuesta.add(codigoSalida);
        return respuesta;
    }
}
