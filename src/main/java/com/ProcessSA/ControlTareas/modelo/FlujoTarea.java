package com.ProcessSA.ControlTareas.modelo;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "FLUJO_TAREA")
@Data
public class FlujoTarea implements Serializable {

    @EmbeddedId
    private FlujoTareaPK pkFlujoTarea;

    @Basic
    @Column(name = "creado", nullable = false)
    private Date creado;

    @Basic
    @Column(name = "modificado")
    private Date modificado;
}
