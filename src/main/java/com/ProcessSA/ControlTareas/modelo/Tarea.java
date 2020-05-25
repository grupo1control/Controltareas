package com.ProcessSA.ControlTareas.modelo;

import lombok.Data;
import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

/**
 * Clase que representa a la tabla TAREA
 */
@Entity
@Table(name = "TAREA")
@Data
public class Tarea {

    @Id
    @Column(name = "codigo")
    private Long codigo;

    @Basic
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Basic
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Basic
    @Column(name = "estado", nullable = false)
    private String estado;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "codigo_TAREA")
    private Collection<Asignacion> asignaciones;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "codigo_TAREA")
    private Collection<FlujoTarea> flujosTarea;

    @OneToMany(mappedBy = "tareaFk", cascade = CascadeType.ALL)
    private Collection<Plazo> plazos;

    @Basic
    @Column(name = "creada", nullable = false)
    private Date creada;

    @Basic
    @Column(name = "modificada")
    private Date modificada;

}
