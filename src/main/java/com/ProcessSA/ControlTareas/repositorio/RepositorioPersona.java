package com.ProcessSA.ControlTareas.repositorio;

import com.ProcessSA.ControlTareas.modelo.Persona;
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
*
*   public interface RepositorioPersona<Persona, String>(){ }
*/

/**
 * Clase para definir las operaciones de BD relacionadas con PERSONA
 */
@Repository
public class RepositorioPersona {

    private final EntityManager gestorDeEntidad;

    public RepositorioPersona(EntityManager gestorDeEntidad) {
        this.gestorDeEntidad = gestorDeEntidad;
    }

    /**
     * Ejecuta el procedimiento almacenado SP_GET_PERSONAS,
     * para obetener una lista de todas las Personas,
     * y devuelve un objeto ArrayList con los resultados obtenidos
     *
     * @return
     */
    public ArrayList spGetPersonas() {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_GET_PERSONAS");
        // Registrar los parámetros de salida
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_PERSONAS", void.class, ParameterMode.REF_CURSOR);
        // Ejecutar procedimiento
        consultaProcedimiento.execute();
        // Obtener los valores de salida
        String glosa = (String) consultaProcedimiento.getOutputParameterValue("OUT_GLOSA");
        int estado = (int) consultaProcedimiento.getOutputParameterValue("OUT_ESTADO");
        List<?> personas = obtener((ResultSet) consultaProcedimiento.getOutputParameterValue("OUT_PERSONAS"));
        // Encapsular los resultados
        ArrayList respuesta = new ArrayList<>();
        respuesta.add(glosa);
        respuesta.add(estado);
        personas.forEach(persona -> respuesta.add(persona));
        return respuesta;
    }

    /**
     * Prepara el RUT para ser ingresado en la base de datos
     * (sin puntos ni guion)
     *
     * @param rut
     * @return
     */
    public String limpiar(String rut) {
        return rut.replace(".", "").replace("-", "");
    }

    /**
     * Recorre el ResultSet obtenido de un Cursor
     * producto de la ejecución de un Procedimiento Almacenado
     * retonando una lista de objetos de Persona
     *
     * @param rs
     * @return
     */
    public ArrayList obtener(ResultSet rs) {
        ArrayList lista = new ArrayList<>();
        Persona entidad;
        try {
            while (rs.next()) {
                entidad = new Persona();
                entidad.setRut(rs.getString("rut"));
                entidad.setNombre(rs.getString("nombre"));
                entidad.setApellido(rs.getString("apellido"));
                entidad.setNatalicio(rs.getDate("natalicio"));
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
     * Ejecuta el procedimiento almacenado SP_GET_PERSONA,
     * para obtener los datos de una Persona,
     * y devuelve un objeto ArrayList con los resultados obtenidos
     *
     * @return
     */
    public ArrayList spGetPersona(String rut) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_GET_PERSONA");
        // Registrar los parámetros de entrada y salida
        consultaProcedimiento.registerStoredProcedureParameter("IN_RUT", String.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_PERSONA", void.class, ParameterMode.REF_CURSOR);
        // Asignar valor de entrada
        consultaProcedimiento.setParameter("IN_RUT", limpiar(rut));
        // Ejecutar procedimiento
        consultaProcedimiento.execute();
        // Obtener los valores de salida
        String glosa = (String) consultaProcedimiento.getOutputParameterValue("OUT_GLOSA");
        int estado = (int) consultaProcedimiento.getOutputParameterValue("OUT_ESTADO");
        Object persona = obtener((ResultSet) consultaProcedimiento.getOutputParameterValue("OUT_PERSONA")).get(0);
        // Encapsular los los resultados
        ArrayList respuesta = new ArrayList<>();
        respuesta.add(glosa);
        respuesta.add(estado);
        respuesta.add(persona);
        return respuesta;
    }

    /**
     * Ejecuta el procedimiento almacenado SP_DEL_PERSONA,
     * para eliminar un registro de Persona,
     * y retorna un objeto ArrayList con los resultados obtenidos
     * @param rut
     * @return
     */
    public ArrayList spDelPersona(String rut) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_DEL_PERSONA");
        // Registrar los parámetros de entrada y salida
        consultaProcedimiento.registerStoredProcedureParameter("IN_RUT", String.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        // Asignar valores de entrada
        consultaProcedimiento.setParameter("IN_RUT", limpiar(rut));
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
     * Ejecuta el procedimiento almacenado SP_REG_PERSONA,
     * para ingresar o actualizar un registro de Persona,
     * y retorna un un objeto ArrayList con los resultados obtenidos
     *
     * @param rut
     * @param nombre
     * @param apellido
     * @param natalicio
     * @return
     */
    public ArrayList spRegPersona(String rut, String nombre, String apellido, Date natalicio) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_REG_PERSONA");
        // Registrar los parámetros de entrada y salida
        consultaProcedimiento.registerStoredProcedureParameter("IN_RUT", String.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_NOMBRE", String.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_APELLIDO", String.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_NATALICIO", Date.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ID_SALIDA", String.class, ParameterMode.OUT);
        // Asignar valores de entrada
        consultaProcedimiento.setParameter("IN_RUT", limpiar(rut));
        consultaProcedimiento.setParameter("IN_NOMBRE", nombre);
        consultaProcedimiento.setParameter("IN_APELLIDO", apellido);
        consultaProcedimiento.setParameter("IN_NATALICIO", natalicio);
        // Ejecutar procedimiento
        consultaProcedimiento.execute();
        // Obtener valores de salida
        String glosa = (String) consultaProcedimiento.getOutputParameterValue("OUT_GLOSA");
        int estado = (int) consultaProcedimiento.getOutputParameterValue("OUT_ESTADO");
        String rutSalida = (String) consultaProcedimiento.getOutputParameterValue("OUT_ID_SALIDA");
        // Encapsular resultados
        ArrayList respuesta = new ArrayList<>();
        respuesta.add(glosa);
        respuesta.add(estado);
        respuesta.add(rutSalida);
        return respuesta;
    }

}
