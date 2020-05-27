package com.ProcessSA.ControlTareas.modelo;

import lombok.Data;
import javax.persistence.*;
import java.sql.Date;

/**
 * Clase que representa a la tabla PLAZO
 */
@Entity
@Table(name = "PLAZO")
@Data
public class Plazo {

    @EmbeddedId
    private PlazoPK pkPlazo;

    @Basic
    @Column(name = "fecha")
    private Date fecha;

    @Basic
    @Column(name = "creado", nullable = false)
    private Date creado;

    @Basic
    @Column(name = "modificado")
    private Date modificado;

}
