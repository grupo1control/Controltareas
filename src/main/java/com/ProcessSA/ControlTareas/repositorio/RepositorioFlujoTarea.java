package com.ProcessSA.ControlTareas.repositorio;

import com.ProcessSA.ControlTareas.modelo.FlujoTarea;
import com.ProcessSA.ControlTareas.modelo.FlujoTareaPK;
import com.ProcessSA.ControlTareas.modelo.Funcion;
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
 *   public interface RepositorioFlujoTarea implements JpaRepository<[Clase], [tipoDatoID]>(){ }
 */
/**
 * Clase para definir las operaciones de BD relacionadas con FLUJO_TAREA
 */
@Repository
public class RepositorioFlujoTarea {

    private final EntityManager gestorDeEntidad;

    public RepositorioFlujoTarea(EntityManager gestorDeEntidad) {
        this.gestorDeEntidad = gestorDeEntidad;
    }

    /**
     * Ejecuta el procedimiento almacenado SP_GET_FLUJOS_T,
     * para obetener una lista de todos los registros de FlujoTarea,
     * y devuelve un objeto ArrayList con los resultados obtenidos
     *
     * @return
     */
    public ArrayList spGetFlujosT() {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_GET_FLUJOS_T");
        // Registrar los parámetros de salida
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_FLUJOS", void.class, ParameterMode.REF_CURSOR);
        // Ejecutar procedimiento
        consultaProcedimiento.execute();
        // Obtener los valores de salida
        String glosa = (String) consultaProcedimiento.getOutputParameterValue("OUT_GLOSA");
        int estado = (int) consultaProcedimiento.getOutputParameterValue("OUT_ESTADO");
        List<?> flujos = obtener((ResultSet) consultaProcedimiento.getOutputParameterValue("OUT_FLUJOS"));
        // Encapsular los resultados
        ArrayList respuesta = new ArrayList<>();
        respuesta.add(glosa);
        respuesta.add(estado);
        flujos.forEach(flujo -> respuesta.add(flujo));
        return respuesta;
    }

    /**
     * Recorre el ResultSet obtenido de un Cursor
     * producto de la ejecución de un Procedimiento Almacenado
     * retonando una lista de objetos de FlujoTarea
     *
     * @param rs
     * @return
     */
    public ArrayList obtener(ResultSet rs) {
        ArrayList lista = new ArrayList<>();
        FlujoTarea entidad;
        FlujoTareaPK pkEntidad;
        try {
            while (rs.next()) {

                entidad = new FlujoTarea();
                pkEntidad = new FlujoTareaPK();

                // Por validar
                pkEntidad.setIndice(rs.getByte("indice"));
                pkEntidad.setFkTarea(
                        (Tarea) new RepositorioTarea(this.gestorDeEntidad)
                                .spGetTarea(
                                        rs.getLong("codigo_TAREA")
                                ).get(2)
                );


                pkEntidad.setFkFuncion(
                        (Funcion) new RepositorioFuncion(this.gestorDeEntidad)
                                .spGetFuncion(
                                        rs.getLong("codigo_FUNCION")
                                ).get(2)
                );

                entidad.setPkFlujoTarea(pkEntidad);
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
     * Ejecuta el procedimiento almacenado SP_GET_FLUJO_T,
     * para obtener los datos de FlujoTarea,
     * y devuelve un objeto ArrayList con los resultados obtenidos
     * @param codigoTarea
     * @param codigoFuncion
     * @return
     */
    public ArrayList spGetFlujoT(long codigoTarea, long codigoFuncion) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_GET_FLUJO_T");
        // Registrar los parámetros de entrada y salida
        consultaProcedimiento.registerStoredProcedureParameter("IN_CODIGO_FUNCION", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_CODIGO_TAREA", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_FLUJO_T", void.class, ParameterMode.REF_CURSOR);
        // Asignar valor de entrada
        consultaProcedimiento.setParameter("IN_CODIGO_TAREA", codigoTarea);
        consultaProcedimiento.setParameter("IN_CODIGO_FUNCION", codigoFuncion);
        // Ejecutar procedimiento
        consultaProcedimiento.execute();
        // Obtener los valores de salida
        String glosa = (String) consultaProcedimiento.getOutputParameterValue("OUT_GLOSA");
        int estado = (int) consultaProcedimiento.getOutputParameterValue("OUT_ESTADO");
        List<?> flujo = obtener((ResultSet) consultaProcedimiento.getOutputParameterValue("OUT_FLUJO_T"));
        // Encapsular los los resultados
        ArrayList respuesta = new ArrayList<>();
        respuesta.add(glosa);
        respuesta.add(estado);
        respuesta.addAll(flujo);
        return respuesta;
    }

    /**
     * Ejecuta el procedimiento almacenado SP_DEL_FLUJO_T,
     * para eliminar un registro de FlujoTarea,
     * y retorna un objeto ArrayList con los resultados obtenidos
     * @param indice
     * @param codigoTarea
     * @param codigoFuncion
     * @return
     */
    public ArrayList spDelFlujoT(byte indice, Long codigoTarea, Long codigoFuncion) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_DEL_FLUJO_T");
        // Registrar los parámetros de entrada y salida
        consultaProcedimiento.registerStoredProcedureParameter("IN_INDICE", byte.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_CODIGO_TAREA", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_CODIGO_FUNCION", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        // Asignar valores de entrada
        consultaProcedimiento.setParameter("IN_INDICE", indice);
        consultaProcedimiento.setParameter("IN_CODIGO_TAREA", codigoTarea);
        consultaProcedimiento.setParameter("IN_CODIGO_FUNCION", codigoFuncion);
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
     * Ejecuta el procedimiento almacenado SP_REG_FLUJO_T,
     * para ingresar o actualizar un registro de FlujoTarea,
     * y retorna un un objeto ArrayList con los resultados obtenidos
     *
     * @param indice
     * @param codigoTarea
     * @param codigoFuncion
     * @return
     */
    public ArrayList spRegFlujoT(byte indice, Long codigoTarea, Long codigoFuncion) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_REG_FLUJO_T");
        // Registrar los parámetros de entrada y salida
        consultaProcedimiento.registerStoredProcedureParameter("IN_INDICE", byte.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_CODIGO_TAREA", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_CODIGO_FUNCION", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_INDEX_SALIDA", byte.class, ParameterMode.OUT);
        // Asignar valores de entrada
        consultaProcedimiento.setParameter("IN_INDICE", indice);
        consultaProcedimiento.setParameter("IN_CODIGO_TAREA", codigoTarea);
        consultaProcedimiento.setParameter("IN_CODIGO_FUNCION", codigoFuncion);
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
