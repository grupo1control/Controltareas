package com.ProcessSA.ControlTareas.modelo;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

/**
 * Clase que respresenta la clave primaria compuesta de la tabla FLUJO_TAREA
 */
@Data
@Embeddable
public class FlujoTareaPK implements Serializable {

    @Basic
    @Column(name = "indice", nullable = false)
    private Byte indice;

    @ManyToOne(optional = false)
    @JoinColumn(name = "codigo_FUNCION", referencedColumnName = "codigo")
    private Funcion fkFuncion;

    @ManyToOne(optional = false)
    @JoinColumn(name = "codigo_TAREA", referencedColumnName = "codigo")
    private Tarea fkTarea;

}
