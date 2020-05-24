package com.ProcessSA.ControlTareas.modelo;

import lombok.Data;
import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

/**
 * Clase que representa a la tabla PROCESO
 */
@Entity
@Table(name = "PROCESO")
@Data
public class Proceso {

    @Id
    @Column(name = "codigo")
    private Long codigo;

    @Basic
    @Column(name = "indice")
    private Byte indice;

    @Basic
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Basic
    @Column(name = "descripcion")
    private String descripcion;

    @Basic
    @Column(name = "estado")
    private String estado;

    @OneToMany(mappedBy = "procesoFk", cascade = CascadeType.ALL)
    private Collection<FlujoFuncion> flujosFuncion;

    @ManyToOne
    @JoinColumn(name = "codigo_UI", referencedColumnName = "codigo")
    private UnidadInterna unidadInternaFk;

    @ManyToOne
    @JoinColumn(name = "id_DISENNADOR", referencedColumnName = "id")
    private Disennador disennadorFk;

    @ManyToOne(optional = false)
    @JoinColumn(name = "codigo_PROYECTO", referencedColumnName = "codigo")
    private Proyecto proyectoFk;

    @Basic
    @Column(name = "creado", nullable = false)
    private Date creado;

    @Basic
    @Column(name = "modificado")
    private Date modificado;

}
