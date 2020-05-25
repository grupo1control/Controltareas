package com.ProcessSA.ControlTareas.modelo;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

/**
 * Clase que respresenta la clave primaria compuesta de la tabla FLUJO_FUNCION
 */
@Data
@Embeddable
public class FlujoFuncionPK implements Serializable {

    @Basic
    @Column(name = "indice", nullable = false)
    private Byte indice;

    @ManyToOne(optional = false)
    @JoinColumn(name = "codigo_PROCESO", referencedColumnName = "codigo")
    private Proceso fkProceso;

    @ManyToOne(optional = false)
    @JoinColumn(name = "codigo_FUNCION", referencedColumnName = "codigo")
    private Funcion fkFuncion;

}
