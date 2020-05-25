package com.ProcessSA.ControlTareas.modelo;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Collection;

/**
 * Clase que respresenta la tabla INTEGRANTE
 */
@Entity
@Table(name = "INTEGRANTE")
@Data
public class Integrante {

    @EmbeddedId
    private IntegrantePK pkIntegrante;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "id_USUARIO"),
            @JoinColumn(name = "codigo_UI")
    })
    private Collection<Asignacion> asignaciones;

    @Basic
    @Column(name = "creado", nullable = false)
    private Date creado;

    @Basic
    @Column(name = "modificado")
    private Date modificado;





}
