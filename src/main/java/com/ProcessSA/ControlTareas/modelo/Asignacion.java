package com.ProcessSA.ControlTareas.modelo;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Clase que representa a la tabla ASIGNACION
 */
@Entity
@Table(name = "ASIGNACION")
@Data
public class Asignacion implements Serializable {

    /**
     * Funcíón que cumple el integrante en la tarea asignada
     */
    @Basic
    @Column(name = "rol")
    private String rol;

    /**
     * Estado actual de la tarea asignada
     */
    @Basic
    @Column(name = "estado", nullable = false)
    private String estado;

    /**
     * Justificación de rechazo u
     * observación importante en la tarea
     */
    @Basic
    @Column(name = "nota")
    private String nota;

    @EmbeddedId
    private AsignacionPK pkAsignacion;

    @Basic
    @Column(name = "creada", nullable = false)
    private Date creada;

    @Basic
    @Column(name = "modificada")
    private Date modificada;
}
