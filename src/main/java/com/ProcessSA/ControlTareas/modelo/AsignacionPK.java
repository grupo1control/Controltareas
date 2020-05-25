package com.ProcessSA.ControlTareas.modelo;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

/**
 * Clase que representa la clave primaria compuesta de tabla ASIGNACION
 */
@Data
@Embeddable
public class AsignacionPK implements Serializable {

    @ManyToOne(optional = false)
    @JoinColumn(name = "codigo_TAREA", referencedColumnName = "codigo")
    private Tarea fkTarea;

    @ManyToOne(optional = false)
    @JoinColumns({
            @JoinColumn(name = "codigo_UI", referencedColumnName = "codigo_UI"),
            @JoinColumn(name = "id_USUARIO", referencedColumnName = "id_USUARIO")
    })
    private Integrante fkIntegrante;

}
