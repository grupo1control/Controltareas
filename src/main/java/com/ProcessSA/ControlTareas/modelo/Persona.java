package com.ProcessSA.ControlTareas.modelo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PERSONA")
@Data
public class Persona {
    @Id
    @Column(name = "rut")
    private String rut;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "apellido")
    private String apellido;
    @Basic
    @Column(name = "natalicio")
    private Date natalicio;
    @Basic
    @Column(name = "creado")
    private Date creado;
    @Basic
    @Column(name = "modificado")
    private Date modificado;
}
