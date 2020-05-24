package com.ProcessSA.ControlTareas.modelo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Collection;

/**
 * Clase que representa la tabla ADMINISTRADOR
 */
@Entity
@Table(name = "ADMINISTRADOR")
@Data
public class Administrador implements Serializable {

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    @Id
    private Usuario usuarioFk;

    @OneToMany(mappedBy = "administradorFk", cascade = CascadeType.ALL)
    private Collection<Proyecto> proyectos;

    @Basic
    @Column(name = "creado", nullable = false)
    private Date creado;

}
