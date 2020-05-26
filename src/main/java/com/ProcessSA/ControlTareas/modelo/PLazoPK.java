package com.ProcessSA.ControlTareas.modelo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Clase que respresenta la clave primaria compuesta de la tabla PLAZO
 */
@Data
@Embeddable
public class PLazoPK implements Serializable {

    /**
     * Cantidad de veces que se a asignado  o modificado un plazo a una tarea
     */
    @Basic
    @Column(name = "contador", nullable = false)
    private Byte contador;

    @ManyToOne(optional = false)
    @JoinColumn(name = "codigo_TAREA", referencedColumnName = "codigo")
    private Tarea fkTarea;

}
