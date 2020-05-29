package com.ProcessSA.ControlTareas.repositorio;

import com.ProcessSA.ControlTareas.modelo.Administrador;
import com.ProcessSA.ControlTareas.modelo.Empresa;
import com.ProcessSA.ControlTareas.modelo.Proyecto;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/*
 * Convensión Java Spring:
 *   public interface RepositorioProyecto implements JpaRepository<[Clase], [tipoDatoID]>(){ }
 */

/**
 * Clase para definir las operaciones de BD relacionadas con PROYECTO
 */
@Repository
public class RepositorioProyecto {
    private final EntityManager gestorDeEntidad;

    public RepositorioProyecto(EntityManager gestorDeEntidad) {
        this.gestorDeEntidad = gestorDeEntidad;
    }

    /**
     * Ejecuta el procedimiento almacenado SP_GET_PROYECTOS,
     * para obetener una lista de todos los registros de proyecto,
     * y devuelve un objeto ArrayList con los resultados obtenidos
     *
     * @return
     */
    public ArrayList spGetProyectos() {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_GET_PROYECTOS");
        // Registrar los parámetros de salida
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_PLAZOS", void.class, ParameterMode.REF_CURSOR);
        // Ejecutar procedimiento
        consultaProcedimiento.execute();
        // Obtener los valores de salida
        String glosa = (String) consultaProcedimiento.getOutputParameterValue("OUT_GLOSA");
        int estado = (int) consultaProcedimiento.getOutputParameterValue("OUT_ESTADO");
        List<?> proyectos = obtener((ResultSet) consultaProcedimiento.getOutputParameterValue("OUT_PROYECTOS"));
        // Encapsular los resultados
        ArrayList respuesta = new ArrayList<>();
        respuesta.add(glosa);
        respuesta.add(estado);
        proyectos.forEach(proyecto -> respuesta.add(proyecto));
        return respuesta;
    }

    /**
     * Recorre el ResultSet obtenido de un Cursor
     * proyecto de la ejecución de un Procedimiento Almacenado
     * retonando una lista de objetos de proyectos
     *
     * @param rs
     * @return
     */
    public ArrayList obtener(ResultSet rs) {
        ArrayList lista = new ArrayList<>();
        Proyecto entidad;

        try {
            while (rs.next()) {

                entidad = new Proyecto();

                entidad.setCodigo(rs.getLong("codigo"));
                entidad.setNombre(rs.getString("nombre"));
                entidad.setEstado(rs.getString("estado"));

                entidad.setFkEmpresa(
                        (Empresa) new RepositorioEmpresa(this.gestorDeEntidad)
                                .spGetEmpresa(
                                        rs.getString("rut_EMPRESA")
                                ).get(2)
                );


                entidad.setFkAdministrador(
                        (Administrador) new RepositorioAdministrador(this.gestorDeEntidad)
                                .spGetAdministrador(
                                        rs.getLong("id_ADMINISTRADOR")
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
     * Ejecuta el procedimiento almacenado SP_GET_PROYECTO,
     * para obtener los datos de un Proyecto,
     * y devuelve un objeto ArrayList con los resultados obtenidos
     * @param codigo
     * @return
     */
    public ArrayList spGetProyecto(long codigo) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_GET_PROYECTO");
        // Registrar los parámetros de entrada y salida
        consultaProcedimiento.registerStoredProcedureParameter("IN_CODIGO", Long.class, ParameterMode.IN);
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
     * Ejecuta el procedimiento almacenado SP_DEL_PROYECTO,
     * para eliminar un registro de Proyecto,
     * y retorna un objeto ArrayList con los resultados obtenidos
     * @param codigo
     * @return
     */
    public ArrayList spDelProyecto(long codigo) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_DEL_PROYECTO");
        // Registrar los parámetros de entrada y salida
        consultaProcedimiento.registerStoredProcedureParameter("IN_CODIGO", Long.class, ParameterMode.IN);
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
     * Ejecuta el procedimiento almacenado SP_REG_PROYECTO,
     * para ingresar o actualizar un registro de Proyecto,
     * y retorna un un objeto ArrayList con los resultados obtenidos
     *
     * @param codigo
     * @param nombre
     * @param inputEstado
     * @param rut_empresa
     * @param id_administrador
     * @return
     */
    public ArrayList spRegProyecto(long codigo, String nombre, String inputEstado, String rut_empresa, int id_administrador ) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_REG_PROYECTO");
        // Registrar los parámetros de entrada y salida
        consultaProcedimiento.registerStoredProcedureParameter("IN_CODIGO", long.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_NOMBRE", String.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_ESTADO", String.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_RUT_EMPRESA", String.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_ID_ADMINISTRADOR", int.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_COD_SALIDA", Long.class, ParameterMode.OUT);
        // Asignar valores de entrada
        consultaProcedimiento.setParameter("IN_CODIGO", codigo);
        consultaProcedimiento.setParameter("IN_NOMBRE", nombre);
        consultaProcedimiento.setParameter("IN_ESTADO", inputEstado);
        consultaProcedimiento.setParameter("IN_RUT_EMPRESA", rut_empresa);
        consultaProcedimiento.setParameter("IN_ID_ADMINISTRADOR", id_administrador);
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
