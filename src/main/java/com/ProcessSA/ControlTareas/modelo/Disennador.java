package com.ProcessSA.ControlTareas.modelo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Collection;

/**
 * Clase que representa la tabla DISENNADOR
 */
@Entity
@Table(name = "DISENNADOR")
@Data
public class Disennador implements Serializable {

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    @Id
    private Usuario usuarioFk;

    @OneToMany(mappedBy = "disennadorFk")
    private Collection<Proceso> procesos;

    @Basic
    @Column(name = "creado", nullable = false)
    private Date creado;

}
