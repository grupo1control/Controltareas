package com.ProcessSA.ControlTareas.modelo;

import lombok.Data;
import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

/**
 * Clase que representa la tabla USUARIO
 */
@Entity
@Table(name = "USUARIO")
@Data
public class Usuario {

    @Id
    @Column(name = "id")
    private Long id;

    /**
     * Nombre perzonalizado del Usuario
     */
    @Basic
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Basic
    @Column(name = "correo", nullable = false)
    private String correo;

    @Basic
    @Column(name = "clave", nullable = false)
    private String clave;

    @Basic
    @Column(name = "estado", nullable = false)
    private boolean estado;

    @OneToOne(mappedBy = "usuarioFk", cascade = CascadeType.ALL)
    private Administrador administrador;
    @OneToOne(mappedBy = "usuarioFk", cascade = CascadeType.ALL)
    private Disennador disennador;
    @OneToOne(mappedBy = "usuarioFk", cascade = CascadeType.ALL)
    private Funcionario funcionario;

    @OneToMany(mappedBy = "usuarioFk", cascade = CascadeType.ALL)
    private Collection<Integrante> integrantes;

    @ManyToOne(optional = false)
    @JoinColumn(name = "rut_PERSONA", referencedColumnName = "rut")
    private Persona personaFk;

    @Basic
    @Column(name = "creado", nullable = false)
    private Date creado;

    @Basic
    @Column(name = "modificado")
    private Date modificado;

}
