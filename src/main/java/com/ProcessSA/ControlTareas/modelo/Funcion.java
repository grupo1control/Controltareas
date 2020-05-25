package com.ProcessSA.ControlTareas.modelo;

import lombok.Data;
import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

/**
 * Clase que representa a la tabla FUNCION
 */
@Entity
@Table(name = "FUNCION")
@Data
public class Funcion {

    @Id
    @Column(name = "codigo")
    private Long codigo;

    @Basic
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Basic
    @Column(name = "descripcion")
    private String descripcion;

    @Basic
    @Column(name = "estado")
    private String estado;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "codigo_FUNCION")
    private Collection<FlujoFuncion> flujosFuncion;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "codigo_FUNCION")
    private Collection<FlujoTarea> flujosTarea;

    @Basic
    @Column(name = "creada", nullable = false)
    private Date creada;

    @Basic
    @Column(name = "modificada")
    private Date modificada;

}
