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
public class Integrante implements Serializable {

    @OneToMany(mappedBy = "integranteFk", cascade = CascadeType.ALL)
    private Collection<Asignacion> asignaciones;

    @ManyToOne
    @JoinColumn(name = "id_USUARIO", referencedColumnName = "id")
    @Id
    private Usuario usuarioFk;

    @ManyToOne
    @JoinColumn(name = "codigo_UI", referencedColumnName = "codigo")
    @Id
    private UnidadInterna unidadInternaFk;

    @Basic
    @Column(name = "creado", nullable = false)
    private Date creado;

    @Basic
    @Column(name = "modificado")
    private Date modificado;

}
