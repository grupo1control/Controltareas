package com.ProcessSA.ControlTareas.modelo;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Clase que representa a la tabla PLAZO
 */
@Entity
@Table(
        name = "PLAZO",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {
                        "contador",
                        "codigo_TAREA"
                })
)
@Data
public class Plazo implements Serializable {

    /**
     * Cantidad de veces que se a asignado  o modificado un plazo a una tarea
     */
    @Basic
    @Column(name = "contador", nullable = false)
    private Byte contador;

    @Basic
    @Column(name = "fecha")
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "codigo_TAREA", referencedColumnName = "codigo")
    @Id
    private Tarea tareaFk;

    @Basic
    @Column(name = "creado", nullable = false)
    private Date creado;

    @Basic
    @Column(name = "modificado")
    private Date modificado;

}
