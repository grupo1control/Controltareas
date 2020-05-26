package com.ProcessSA.ControlTareas.modelo;

import lombok.Data;
import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

/**
 * Clase que representa a la tabla PERSONA
 */
@Entity
@Table(name = "PERSONA")
@Data
public class Persona {
    @Id
    @Column(name = "rut")
    private String rut;

    @Basic
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Basic
    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Basic
    @Column(name = "natalicio", nullable = false)
    private Date natalicio;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "rut_PERSONA")
    private Collection<Contrato> contratos;

    @OneToMany(mappedBy = "fkPersona", cascade = CascadeType.ALL)
    private Collection<Usuario> usuarios;

    @Basic
    @Column(name = "creada", nullable = false)
    private Date creada;

    @Basic
    @Column(name = "modificada")
    private Date modificada;
}
