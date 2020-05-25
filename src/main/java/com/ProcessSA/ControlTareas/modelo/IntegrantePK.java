package com.ProcessSA.ControlTareas.modelo;

import lombok.Data;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Clase que representa la clave primaria compuesta de tabla INTEGRANTE
 */
@Embeddable
@Data
public class IntegrantePK implements Serializable {

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_USUARIO", referencedColumnName = "id")
    private Usuario fkUsuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "codigo_UI", referencedColumnName = "codigo")
    private UnidadInterna fkUnidadInterna;

}
