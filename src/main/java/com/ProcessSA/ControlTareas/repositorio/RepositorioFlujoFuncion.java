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
 *   public interface RepositorioFlujoFuncion implements JpaRepository<[Clase], [tipoDatoID]>(){ }
 */
/**
 * Clase para definir las operaciones de BD relacionadas con FLUJO_FUNCION
 */
@Repository
public class RepositorioFlujoFuncion {
    private final EntityManager gestorDeEntidad;

    public RepositorioFlujoFuncion(EntityManager gestorDeEntidad) {
        this.gestorDeEntidad = gestorDeEntidad;
    }

    /**
     * Ejecuta el procedimiento almacenado SP_GET_FLUJOS_F,
     * para obetener una lista de todos los registros de FlujoFuncion,
     * y devuelve un objeto ArrayList con los resultados obtenidos
     *
     * @return
     */
    public ArrayList spGetFlujosF() {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_GET_FLUJOS_F");
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
     * retonando una lista de objetos de FlujoFuncion
     *
     * @param rs
     * @return
     */
    public ArrayList obtener(ResultSet rs) {
        ArrayList lista = new ArrayList<>();
        FlujoFuncion entidad;
        FlujoFuncionPK pkEntidad;
        try {
            while (rs.next()) {

                entidad = new FlujoFuncion();
                pkEntidad = new FlujoFuncionPK();

                // Por validar
                pkEntidad.setIndice(rs.getByte("indice"));
                pkEntidad.setFkFuncion(
                        (Funcion) new RepositorioFuncion(this.gestorDeEntidad)
                                .spGetFuncion(
                                        rs.getLong("codigo_FUNCION")
                                ).get(2)
                );


                pkEntidad.setFkProceso(
                        (Proceso) new RepositorioProceso(this.gestorDeEntidad)
                                .spGetProceso(
                                        rs.getLong("codigo_PROCESO")
                                ).get(2)
                );

                entidad.setPkFlujoFuncion(pkEntidad);
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
     * Ejecuta el procedimiento almacenado SP_GET_FLUJO_F,
     * para obtener los datos de FlujoFuncion,
     * y devuelve un objeto ArrayList con los resultados obtenidos
     * @param codigoProceso
     * @param codigoFuncion
     * @return
     */
    public ArrayList spGetFlujoF(long codigoFuncion, long codigoProceso) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_GET_FLUJO_F");
        // Registrar los parámetros de entrada y salida
        consultaProcedimiento.registerStoredProcedureParameter("IN_CODIGO_FUNCION", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_CODIGO_PROCESO", Long.class, ParameterMode.IN);
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
