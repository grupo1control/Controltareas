package com.ProcessSA.ControlTareas.repositorio;


import com.ProcessSA.ControlTareas.modelo.Administrador;
import com.ProcessSA.ControlTareas.modelo.Usuario;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
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
        // Asignar valores de entrada
        consultaProcedimiento.setParameter("IN_CODIGO", id);
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
     * Ejecuta el procedimiento almacenado SP_REG_ADMINISTRADOR,
     * para ingresar o actualizar un registro de Administrador,
     * y retorna un un objeto ArrayList con los resultados obtenidos
     *
     * @param id
     * @param creado
     * @return
     */
    public ArrayList spRegAdministrador(long id, Date creado ) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_REG_ADMINISTRADOR");
        // Registrar los parámetros de entrada y salida
        consultaProcedimiento.registerStoredProcedureParameter("IN_ID", long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_CREADO", String.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_COD_SALIDA", Long.class, ParameterMode.OUT);
        // Asignar valores de entrada
        consultaProcedimiento.setParameter("IN_ID", id);
        consultaProcedimiento.setParameter("IN_CREADO", creado);
        // Ejecutar procedimiento
        consultaProcedimiento.execute();
        // Obtener valores de salida
        String glosa = (String) consultaProcedimiento.getOutputParameterValue("OUT_GLOSA");
        int estado = (int) consultaProcedimiento.getOutputParameterValue("OUT_ESTADO");
        Long codigoSalida = (Long) consultaProcedimiento.getOutputParameterValue("OUT_COD_SALIDA");
        // Encapsular resultados
        ArrayList respuesta = new ArrayList<>();
        respuesta.add(glosa);
        respuesta.add(estado);
        respuesta.add(codigoSalida);
        return respuesta;
    }
}
