package com.ProcessSA.ControlTareas.modelo;

import lombok.Data;
import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

/**
 * Clase que respresenta a la tabla UNIDAD_INTERNA
 */
@Entity
@Table(name = "UNIDAD_INTERNA")
@Data
public class UnidadInterna {

    @Id
    @Column(name = "codigo")
    private Long codigo;

    @Basic
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Basic
    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "unidadInternaFk")
    private Collection<Integrante> integrantes;

    @OneToMany(mappedBy = "unidadInternaFk")
    private Collection<Proceso> procesos;

    @Basic
    @Column(name = "creada", nullable = false)
    private Date creada;

    @Basic
    @Column(name = "modificada")
    private Date modificada;


}
