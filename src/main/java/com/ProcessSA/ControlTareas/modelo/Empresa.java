package com.ProcessSA.ControlTareas.modelo;

import lombok.Data;
import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;


/**
 * Clase que representa la tabla EMPRESA
 */
@Entity
@Table(name = "EMPRESA")
@Data
public class Empresa {

    @Id
    @Column(name = "RUT")
    private String rut;

    @Basic
    @Column(name = "razon_social")
    private String razonSocial;

    @Basic
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Basic
    @Column(name = "giro_comercial")
    private String giroComercial;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "rut_Empresa")
    private Collection<Contrato> contratos;

    @OneToMany(mappedBy = "fkEmpresa")
    private Collection<Proyecto> proyectos;

    @Basic
    @Column(name = "creada", nullable = false)
    private Date creada;

    @Basic
    @Column(name = "modificada")
    private Date modificada;

}
