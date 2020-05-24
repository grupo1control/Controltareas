package com.ProcessSA.ControlTareas.repositorio;

import com.ProcessSA.ControlTareas.modelo.Persona;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/*
* Convensión JavaSpring:
* public interface RepositorioPersona<Persona, String>(){}
* */
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
        List<?> personas = resultado((ResultSet) consultaProcedimiento.getOutputParameterValue("OUT_PERSONAS"));
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
     *
     * @param rs
     * @return
     */
    public ArrayList resultado(ResultSet rs) {
        ArrayList lista = new ArrayList<>();
        Persona entidad;
        try {
            while (rs.next()) {
                entidad = new Persona();
                entidad.setRut(rs.getString("rut"));
                entidad.setNombre(rs.getString("nombre"));
                entidad.setApellido(rs.getString("apellido"));
                entidad.setNatalicio(rs.getTime("natalicio"));
                entidad.setCreado(rs.getTime("creado"));
                entidad.setModificado(rs.getTime("modificado"));
                lista.add(entidad);
                //System.out.println(entidad);
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        //System.out.println(lista);
        return lista;
    }
}
