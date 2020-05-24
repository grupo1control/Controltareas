package com.ProcessSA.ControlTareas.modelo;

import lombok.Data;
import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

/**
 * Clase que representa a la tabla PROYECTO
 */
@Entity
@Table(name = "PROYECTO")
@Data
public class Proyecto {

    @Id
    @Column(name = "codigo")
    private Long codigo;

    @Basic
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Basic
    @Column(name = "estado")
    private String estado;

    @OneToMany(mappedBy = "proyectoFk", cascade = CascadeType.ALL)
    private Collection<Proceso> procesos;

    @ManyToOne
    @JoinColumn(name = "rut_EMPRESA", referencedColumnName = "rut")
    private Empresa empresaFk;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_ADMINISTRADOR", referencedColumnName = "id")
    private Administrador administradorFk;

    @Basic
    @Column(name = "creado", nullable = false)
    private Date creado;

    @Basic
    @Column(name = "modificado")
    private Date modificado;

}
