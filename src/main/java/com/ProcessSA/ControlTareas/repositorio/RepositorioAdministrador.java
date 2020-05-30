package com.ProcessSA.ControlTareas.repositorio;


import com.ProcessSA.ControlTareas.modelo.Administrador;
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
 *   public interface RepositorioAdministrador implements JpaRepository<[Clase], [tipoDatoID]>(){ }
 */

/**
 * Clase para definir las operaciones de BD relacionadas con ADMINISTRADOR
 */
@Repository
public class RepositorioAdministrador {

    private final EntityManager gestorDeEntidad;

    public RepositorioAdministrador(EntityManager gestorDeEntidad) {
        this.gestorDeEntidad = gestorDeEntidad;
    }

    /**
     * Ejecuta el procedimiento almacenado SP_GET_ADMINISTRADORES,
     * para obetener una lista de todos los registros de administradores,
     * y devuelve un objeto ArrayList con los resultados obtenidos
     *
     * @return
     */
    public ArrayList spGetAdministradores() {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_GET_ADMINISTRADORES");
        // Registrar los parámetros de salida
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ADMINISTRADORES", void.class, ParameterMode.REF_CURSOR);
        // Ejecutar procedimiento
        consultaProcedimiento.execute();
        // Obtener los valores de salida
        String glosa = (String) consultaProcedimiento.getOutputParameterValue("OUT_GLOSA");
        int estado = (int) consultaProcedimiento.getOutputParameterValue("OUT_ESTADO");
        List<?> administradores = obtener((ResultSet) consultaProcedimiento.getOutputParameterValue("OUT_ADMINISTRADORES"));
        // Encapsular los resultados
        ArrayList respuesta = new ArrayList<>();
        respuesta.add(glosa);
        respuesta.add(estado);
        administradores.forEach(administrador -> respuesta.add(administrador));
        return respuesta;
    }

    /**
     * Recorre el ResultSet obtenido de un Cursor
     * administrador de la ejecución de un Procedimiento Almacenado
     * retonando una lista de objetos de administrador
     *
     * @param rs
     * @return
     */
    public ArrayList obtener(ResultSet rs) {
        ArrayList lista = new ArrayList<>();
        Administrador entidad;

        try {
            while (rs.next()) {
                entidad = new Administrador();
                entidad.setUsuarioFk(
                        (Usuario) new RepositorioUsuario(this.gestorDeEntidad)
                                .spGetUsuario(
                                        rs.getLong("id")
                                ).get(2)
                );
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
     * Ejecuta el procedimiento almacenado SP_GET_ADMINISTRADOR,
     * para obtener los datos de un Administrador,
     * y devuelve un objeto ArrayList con los resultados obtenidos
     * @param id
     * @return
     */
    public ArrayList spGetAdministrador(Long id) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_GET_ADMINISTRADOR");
        // Registrar los parámetros de entrada y salida
        consultaProcedimiento.registerStoredProcedureParameter("IN_ID", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ADMINISTRADOR", void.class, ParameterMode.REF_CURSOR);
        // Asignar valores de entrada
        consultaProcedimiento.setParameter("IN_ID", id);
        // Ejecutarprocedimiento
        consultaProcedimiento.execute();
        // Obtener valores de salida
        String glosa = (String) consultaProcedimiento.getOutputParameterValue("OUT_GLOSA");
        int estado = (int) consultaProcedimiento.getOutputParameterValue("OUT_ESTADO");
        List<?> administrador = this.obtener((ResultSet) consultaProcedimiento.getOutputParameterValue("OUT_ADMINISTRADOR"));
        // Encapsular resultado
        ArrayList respuesta = new ArrayList<>();
        respuesta.add(glosa);
        respuesta.add(estado);
        respuesta.addAll(administrador);
        return respuesta;
    }

    /**
     * Ejecuta el procedimiento almacenado SP_REG_ADMINISTRADOR,
     * para ingresar o actualizar un registro de Administrador,
     * y retorna un un objeto ArrayList con los resultados obtenidos
     *
     * @param id
     * @return
     */
    public ArrayList spRegAdministrador(long id) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_REG_ADMINISTRADOR");
        // Registrar los parámetros de entrada y salida
        consultaProcedimiento.registerStoredProcedureParameter("IN_ID", long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ID_SALIDA", Long.class, ParameterMode.OUT);
        // Asignar valores de entrada
        consultaProcedimiento.setParameter("IN_ID", id);
        // Ejecutar procedimiento
        consultaProcedimiento.execute();
        // Obtener valores de salida
        String glosa = (String) consultaProcedimiento.getOutputParameterValue("OUT_GLOSA");
        int estado = (int) consultaProcedimiento.getOutputParameterValue("OUT_ESTADO");
        Long codigoSalida = (Long) consultaProcedimiento.getOutputParameterValue("OUT_ID_SALIDA");
        // Encapsular resultados
        ArrayList respuesta = new ArrayList<>();
        respuesta.add(glosa);
        respuesta.add(estado);
        respuesta.add(codigoSalida);
        return respuesta;
    }

    /**
     * Ejecuta el procedimiento almacenado SP_DEL_ADMINISTRADOR,
     * para eliminar un registro de Administrador,
     * y retorna un objeto ArrayList con los resultados obtenidos
     * @param id
     * @return
     */
    public ArrayList spDelAdministrador(long id) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_DEL_ADMINISTRADOR");
        // Registrar los parámetros de entrada y salida
        consultaProcedimiento.registerStoredProcedureParameter("IN_ID", long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        // Asignar valores de entrada
        consultaProcedimiento.setParameter("IN_ID",id);
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
