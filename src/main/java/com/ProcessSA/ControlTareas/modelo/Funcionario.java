package com.ProcessSA.ControlTareas.modelo;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Clase que representa la tabla FUNCIONARIO
 */
@Entity
@Table(name = "FUNCIONARIO")
@Data
public class Funcionario implements Serializable {

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    @Id
    private Usuario usuarioFk;

    @Basic
    @Column(name = "creado", nullable = false)
    private Date creado;

}
