package com.ProcessSA.ControlTareas.repositorio;

import com.ProcessSA.ControlTareas.modelo.Persona;
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
 *   public interface RepositorioUsuario implements JpaRepository<[Clase], [tipoDatoID]>(){ }
 */

/**
 * Clase para definir las operaciones de BD relacionadas con Usuario
 */
@Repository
public class RepositorioUsuario {

    private final EntityManager gestorDeEntidad;

    public RepositorioUsuario(EntityManager gestorDeEntidad) {
        this.gestorDeEntidad = gestorDeEntidad;
    }

    /**
     * Ejecuta el procedimiento almacenado SP_GET_USUARIO,
     * para obetener una lista de todos los registros de usuarios,
     * y devuelve un objeto ArrayList con los resultados obtenidos
     *
     * @return
     */
    public ArrayList spGetUsuarios() {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_GET_USUARIOS");
        // Registrar los parámetros de salida
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_USUARIOS", void.class, ParameterMode.REF_CURSOR);
        // Ejecutar procedimiento
        consultaProcedimiento.execute();
        // Obtener los valores de salida
        String glosa = (String) consultaProcedimiento.getOutputParameterValue("OUT_GLOSA");
        int estado = (int) consultaProcedimiento.getOutputParameterValue("OUT_ESTADO");
        List<?> usuarios = obtener((ResultSet) consultaProcedimiento.getOutputParameterValue("OUT_USUARIOS"));
        // Encapsular los resultados
        ArrayList respuesta = new ArrayList<>();
        respuesta.add(glosa);
        respuesta.add(estado);
        usuarios.forEach(usuario -> respuesta.add(usuario));
        return respuesta;
    }

    /**
     * Recorre el ResultSet obtenido de un Cursor
     * administrador de la ejecución de un Procedimiento Almacenado
     * retonando una lista de objetos de usuario
     *
     * @param rs
     * @return
     */
    public ArrayList obtener(ResultSet rs) {

        ArrayList lista = new ArrayList<>();
        Usuario entidad;

        try {
            while (rs.next()) {

                entidad = new Usuario();
                entidad.setId(rs.getLong("id"));
                entidad.setNombre(rs.getString("nombre"));
                entidad.setCorreo(rs.getString("correo"));
                entidad.setFkPersona(
                        (Persona) new RepositorioPersona(this.gestorDeEntidad)
                                .spGetPersona(
                                        rs.getString("rut_PERSONA")
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
     * Ejecuta el procedimiento almacenado SP_GET_USUARIO,
     * para obtener los datos de un Usuario,
     * y devuelve un objeto ArrayList con los resultados obtenidos
     *
     * @param id
     * @return
     */
    public ArrayList spGetUsuario(Long id) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_GET_USUARIO");
        // Registrar los parámetros de entrada y salida
        consultaProcedimiento.registerStoredProcedureParameter("IN_ID", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_USUARIO", void.class, ParameterMode.REF_CURSOR);
        // Asignar valores de entrada
        consultaProcedimiento.setParameter("IN_ID", id);
        // Ejecutarprocedimiento
        consultaProcedimiento.execute();
        // Obtener valores de salida
        String glosa = (String) consultaProcedimiento.getOutputParameterValue("OUT_GLOSA");
        int estado = (int) consultaProcedimiento.getOutputParameterValue("OUT_ESTADO");
        List<?> usuario = obtener((ResultSet) consultaProcedimiento.getOutputParameterValue("OUT_USUARIO"));
        // Encapsular resultado
        ArrayList respuesta = new ArrayList<>();
        respuesta.add(glosa);
        respuesta.add(estado);
        respuesta.addAll(usuario);
        return respuesta;
    }

    /**
     * Ejecuta el procedimiento almacenado SP_REG_USUARIO,
     * para ingresar o actualizar un registro de Usuario,
     * y retorna un un objeto ArrayList con los resultados obtenidos
     *
     * @param rut
     * @param nombre
     * @param correo
     * @param clave
     * @return
     */
    public ArrayList spRegUsuario(String rut, String nombre, String correo, String clave) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_REG_USUARIO");
        // Registrar los parámetros de entrada y salida
        consultaProcedimiento.registerStoredProcedureParameter("IN_RUT", String.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_NOMBRE", String.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_CORREO", String.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_CLAVE", String.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ID_SALIDA", Long.class, ParameterMode.OUT);
        // Asignar valores de entrada
        consultaProcedimiento.setParameter("IN_RUT", rut);
        consultaProcedimiento.setParameter("IN_NOMBRE", nombre);
        consultaProcedimiento.setParameter("IN_CORREO", correo);
        consultaProcedimiento.setParameter("IN_CLAVE", clave);
        // Ejecutar procedimiento
        consultaProcedimiento.execute();
        // Obtener valores de salida
        String glosa = (String) consultaProcedimiento.getOutputParameterValue("OUT_GLOSA");
        int estado = (int) consultaProcedimiento.getOutputParameterValue("OUT_ESTADO");
        Object codigoSalida = consultaProcedimiento.getOutputParameterValue("OUT_ID_SALIDA");
        // Encapsular resultados
        ArrayList respuesta = new ArrayList<>();
        respuesta.add(glosa);
        respuesta.add(estado);
        respuesta.add(codigoSalida);
        return respuesta;
    }

    /**
     * Ejecuta el procedimiento almacenado SP_DEL_USUARIO,
     * para eliminar un registro de Usuario,
     * y retorna un objeto ArrayList con los resultados obtenidos
     *
     * @param id
     * @return
     */
    public ArrayList spDelUsuario(Long id) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_DEL_USUARIO");
        // Registrar los parámetros de entrada y salida
        consultaProcedimiento.registerStoredProcedureParameter("IN_ID", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        // Asignar valores de entrada
        consultaProcedimiento.setParameter("IN_ID", id);
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
