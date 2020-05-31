package com.ProcessSA.ControlTareas.repositorio;

import com.ProcessSA.ControlTareas.modelo.Disennador;
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
 *   public interface RepositorioDisennador implements JpaRepository<[Clase], [tipoDatoID]>(){ }
 */

/**
 * Clase para definir las operaciones de BD relacionadas con DISENNADOR
 */
@Repository
public class RepositorioDisennador {
    private final EntityManager gestorDeEntidad;

    public RepositorioDisennador(EntityManager gestorDeEntidad) {
        this.gestorDeEntidad = gestorDeEntidad;
    }

    /**
     * Ejecuta el procedimiento almacenado SP_GET_DISENNADORES,
     * para obetener una lista de todos los registros de Diseñadores,
     * y devuelve un objeto ArrayList con los resultados obtenidos
     *
     * @return
     */
    public ArrayList spGetDisennadores() {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_GET_DISENNADORES");
        // Registrar los parámetros de salida
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_DISENNADORES", void.class, ParameterMode.REF_CURSOR);
        // Ejecutar procedimiento
        consultaProcedimiento.execute();
        // Obtener los valores de salida
        String glosa = (String) consultaProcedimiento.getOutputParameterValue("OUT_GLOSA");
        int estado = (int) consultaProcedimiento.getOutputParameterValue("OUT_ESTADO");
        List<?> disennadores = obtener((ResultSet) consultaProcedimiento.getOutputParameterValue("OUT_DISENNADORES"));
        // Encapsular los resultados
        ArrayList respuesta = new ArrayList<>();
        respuesta.add(glosa);
        respuesta.add(estado);
        disennadores.forEach(disennador -> respuesta.add(disennador));
        return respuesta;
    }

    /**
     * Recorre el ResultSet obtenido de un Cursor
     * Diseñador de la ejecución de un Procedimiento Almacenado
     * retonando una lista de objetos de diseñador
     *
     * @param rs
     * @return
     */
    public ArrayList obtener(ResultSet rs) {
        ArrayList lista = new ArrayList<>();
        Disennador entidad;

        try {
            while (rs.next()) {
                entidad = new Disennador();
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
     * Ejecuta el procedimiento almacenado SP_GET_DISENNADOR,
     * para obtener los datos de un Diseñador,
     * y devuelve un objeto ArrayList con los resultados obtenidos
     * @param id
     * @return
     */
    public ArrayList spGetDisennador(Long id) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_GET_DISENNADOR");
        // Registrar los parámetros de entrada y salida
        consultaProcedimiento.registerStoredProcedureParameter("IN_ID", Long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_DISENNADOR", void.class, ParameterMode.REF_CURSOR);
        // Asignar valores de entrada
        consultaProcedimiento.setParameter("IN_ID", id);
        // Ejecutarprocedimiento
        consultaProcedimiento.execute();
        // Obtener valores de salida
        String glosa = (String) consultaProcedimiento.getOutputParameterValue("OUT_GLOSA");
        int estado = (int) consultaProcedimiento.getOutputParameterValue("OUT_ESTADO");
        List<?> disennador = this.obtener((ResultSet) consultaProcedimiento.getOutputParameterValue("OUT_DISENNADOR"));
        // Encapsular resultado
        ArrayList respuesta = new ArrayList<>();
        respuesta.add(glosa);
        respuesta.add(estado);
        respuesta.addAll(disennador);
        return respuesta;
    }

    /**
     * Ejecuta el procedimiento almacenado SP_REG_DISENNADOR,
     * para ingresar o actualizar un registro de Diseñador,
     * y retorna un un objeto ArrayList con los resultados obtenidos
     *
     * @param id
     * @return
     */
    public ArrayList spRegDisennador(long id) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_REG_DISENNADOR");
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
     * Ejecuta el procedimiento almacenado SP_DEL_DISENNADOR,
     * para eliminar un registro de Diseñador,
     * y retorna un objeto ArrayList con los resultados obtenidos
     * @param id
     * @return
     */
    public ArrayList spDelDisennador(long id) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_DEL_DISENNADOR");
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
