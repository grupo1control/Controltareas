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
public class Contrato{

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

    @EmbeddedId
    private ContratoPK pkContrato;

    @Basic
    @Column(name = "creado", nullable = false)
    private Date creado;

    @Basic
    @Column(name = "modificado")
    private Date modificado;

}
