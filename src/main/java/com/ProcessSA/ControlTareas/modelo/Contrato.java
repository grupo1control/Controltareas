package com.ProcessSA.ControlTareas.modelo;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Clase que representa la tabla CONTRATO
 */
@Entity
@Table(name = "CONTRATO")
@Data
public class Contrato implements Serializable {

    @Basic
    @Column(name = "salario")
    private int salario;

    /**
     * Título del cargo en la empresa
     */
    @Basic
    @Column(name = "CARGO")
    private String cargo;

    /**
     * Descripción de las funciones en la empresa
     */
    @Basic
    @Column(name = "funcion")
    private String funcion;

    @ManyToOne
    @JoinColumn(name = "RUT_EMPRESA", referencedColumnName = "RUT")
    @Id
    private Empresa empresaFk;

    @ManyToOne
    @JoinColumn(name = "RUT_PERSONA", referencedColumnName = "RUT", nullable = false)
    @Id
    private Persona personaFk;

    @Basic
    @Column(name = "creado", nullable = false)
    private Date creado;

    @Basic
    @Column(name = "modificado")
    private Date modificado;

}
