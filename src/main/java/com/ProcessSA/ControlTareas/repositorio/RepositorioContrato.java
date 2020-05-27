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
 *   public interface RepositorioContrato implements JpaRepository<[Clase], [tipoDatoID]>(){ }
 */

/**
 * Clase para definir las operaciones de BD relacionadas con CONTRATO
 */
@Repository
public class RepositorioContrato {

    private final EntityManager gestorDeEntidad;

    public RepositorioContrato(EntityManager gestorDeEntidad) { this.gestorDeEntidad = gestorDeEntidad; }

    /**
     * Ejecuta el procedimiento almacenado SP_GET_CONTRATOS,
     * para obetener una lista de todos los registros de Contrato,
     * y devuelve un objeto ArrayList con los resultados obtenidos
     *
     * @return
     */
    public ArrayList spGetContratos() {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_GET_CONTRATOS");
        // Registrar los parámetros de salida
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_CONTRATOS", void.class, ParameterMode.REF_CURSOR);
        // Ejecutar procedimiento
        consultaProcedimiento.execute();
        // Obtener los valores de salida
        String glosa = (String) consultaProcedimiento.getOutputParameterValue("OUT_GLOSA");
        int estado = (int) consultaProcedimiento.getOutputParameterValue("OUT_ESTADO");
        List<?> contratos = obtener((ResultSet) consultaProcedimiento.getOutputParameterValue("OUT_CONTRATOS"));
        // Encapsular los resultados
        ArrayList respuesta = new ArrayList<>();
        respuesta.add(glosa);
        respuesta.add(estado);
        contratos.forEach(contrato -> respuesta.add(contrato));
        return respuesta;
    }

    /**
     * Recorre el ResultSet obtenido de un Cursor
     * producto de la ejecución de un Procedimiento Almacenado
     * retonando una lista de objetos de Contratos
     *
     * @param rs
     * @return
     */
    public ArrayList obtener(ResultSet rs) {
        ArrayList lista = new ArrayList<>();
        Contrato entidad;
        ContratoPK pkEntidad;
        try {
            while (rs.next()) {

                entidad = new Contrato();
                pkEntidad = new ContratoPK();

                entidad.setSalario(rs.getInt("salario"));
                entidad.setCargo(rs.getString("cargo"));
                entidad.setFuncion(rs.getString("funcion"));

                pkEntidad.setFkEmpresa(
                        (Empresa) new RepositorioEmpresa(this.gestorDeEntidad)
                                .spGetEmpresa(
                                        rs.getString("rut_EMPRESA")
                                ).get(2)
                );


                pkEntidad.setFkPersona(
                        (Persona) new RepositorioPersona(this.gestorDeEntidad)
                                .spGetPersona(
                                        rs.getString("rut_PERSONA")
                                ).get(2)
                );

                entidad.setPkContrato(pkEntidad);
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
     * Ejecuta el procedimiento almacenado SP_GET_CONTRATO,
     * para obtener datos de Contrato de una Persona o Empresa,
     * y devuelve un objeto ArrayList con los resultados obtenidos
     * @param rut
     * @return
     */
    public ArrayList spGetContrato(String rut) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_GET_CONTRATO");
        // Registrar los parámetros de entrada y salida
        consultaProcedimiento.registerStoredProcedureParameter("IN_RUT", String.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_CONTRATO", void.class, ParameterMode.REF_CURSOR);
        // Asignar valor de entrada
        consultaProcedimiento.setParameter("IN_RUT", rut);
        // Ejecutar procedimiento
        consultaProcedimiento.execute();
        // Obtener los valores de salida
        String glosa = (String) consultaProcedimiento.getOutputParameterValue("OUT_GLOSA");
        int estado = (int) consultaProcedimiento.getOutputParameterValue("OUT_ESTADO");
        List<?> contratos = obtener((ResultSet) consultaProcedimiento.getOutputParameterValue("OUT_CONTRATO"));
        // Encapsular los los resultados
        ArrayList respuesta = new ArrayList<>();
        respuesta.add(glosa);
        respuesta.add(estado);
        respuesta.addAll(contratos);
        return respuesta;
    }

    /**
     * Ejecuta el procedimiento almacenado SP_DEL_CONTRATO,
     * para eliminar un registro de Contrato,
     * y retorna un objeto ArrayList con los resultados obtenidos
     * @param rutPersona
     * @param rutEmpresa
     * @return
     */
    public ArrayList spDelContrato(String rutPersona, String rutEmpresa) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_DEL_CONTRATO");
        // Registrar los parámetros de entrada y salida
        consultaProcedimiento.registerStoredProcedureParameter("IN_rut_PERSONA", String.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_rut_EMPRESA", String.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        // Asignar valores de entrada
        consultaProcedimiento.setParameter("IN_rut_PERSONA", rutPersona);
        consultaProcedimiento.setParameter("IN_rut_EMPRESA", rutEmpresa);
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
     * Ejecuta el procedimiento almacenado SP_REG_CONTRATO,
     * para ingresar o actualizar un registro de Contrato,
     * y retorna un un objeto ArrayList con los resultados obtenidos
     * @param rutPersona
     * @param rutEmpresa
     * @param salario
     * @param cargo
     * @param funcion
     * @return
     */
    public ArrayList spRegContrato(String rutPersona, String rutEmpresa, int salario, String cargo, String funcion) {
        StoredProcedureQuery consultaProcedimiento = gestorDeEntidad.createStoredProcedureQuery("SP_REG_CONTRATO");
        // Registrar los parámetros de entrada y salida
        consultaProcedimiento.registerStoredProcedureParameter("IN_RUT_EMPRESA", String.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_RUT_PERSONA", String.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_SALARIO", int.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_CARGO", String.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("IN_FUNCION", String.class, ParameterMode.IN);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_GLOSA", String.class, ParameterMode.OUT);
        consultaProcedimiento.registerStoredProcedureParameter("OUT_ESTADO", int.class, ParameterMode.OUT);
        // Asignar valores de entrada
        consultaProcedimiento.setParameter("IN_RUT_EMPRESA", rutEmpresa);
        consultaProcedimiento.setParameter("IN_RUT_PERSONA", rutPersona);
        consultaProcedimiento.setParameter("IN_SALARIO", salario);
        consultaProcedimiento.setParameter("IN_CARGO", cargo);
        consultaProcedimiento.setParameter("IN_FUNCION", funcion);
        // Ejecutar procedimiento
        consultaProcedimiento.execute();
        // Obtener valores de salida
        String glosa = (String) consultaProcedimiento.getOutputParameterValue("OUT_GLOSA");
        int estado = (int) consultaProcedimiento.getOutputParameterValue("OUT_ESTADO");
        // Encapsular resultados
        ArrayList respuesta = new ArrayList<>();
        respuesta.add(glosa);
        respuesta.add(estado);
        return respuesta;
    }

}
