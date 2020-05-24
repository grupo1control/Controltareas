package com.ProcessSA.ControlTareas.modelo;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "FLUJO_TAREA")
@Data
public class FlujoTarea implements Serializable {

    @Id
    @Column(name = "indice")
    private Byte indice;

    @ManyToOne
    @JoinColumn(name = "codigo_FUNCION", referencedColumnName = "codigo")
    @Id
    private Funcion funcionFk;

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
