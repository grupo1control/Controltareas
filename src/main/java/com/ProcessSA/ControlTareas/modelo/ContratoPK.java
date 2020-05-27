package com.ProcessSA.ControlTareas.modelo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Clase que representa la clave primaria compuesta de la tabla CONTRATO
 */
@Data
@Embeddable
public class ContratoPK implements Serializable {

    @ManyToOne(optional = false)
    @JoinColumn(name = "rut_EMPRESA", referencedColumnName = "rut")
    private Empresa fkEmpresa;

    @ManyToOne(optional = false)
    @JoinColumn(name = "rut_PERSONA", referencedColumnName = "rut")
    private Persona fkPersona;

}
