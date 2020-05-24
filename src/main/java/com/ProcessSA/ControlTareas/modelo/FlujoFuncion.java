package com.ProcessSA.ControlTareas.modelo;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Clase que respresenta a la tabla FLUJO_FUNCION
 */
@Entity
@Table(name = "FLUJO_FUNCION")
@Data
public class FlujoFuncion implements Serializable {

    @Id
    @Column(name = "indice")
    private Byte indice;

    @ManyToOne
    @JoinColumn(name = "codigo_PROCESO", referencedColumnName = "codigo")
    @Id
    private Proceso procesoFk;

    @ManyToOne
    @JoinColumn(name = "codigo_FUNCION", referencedColumnName = "codigo")
    @Id
    private Funcion funcionFk;

    @Basic
    @Column(name = "creado", nullable = false)
    private Date creado;

    @Basic
    @Column(name = "modificado")
    private Date modificado;

}
