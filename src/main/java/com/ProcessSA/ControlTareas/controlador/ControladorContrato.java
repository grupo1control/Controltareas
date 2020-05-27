package com.ProcessSA.ControlTareas.controlador;


import com.ProcessSA.ControlTareas.servicio.ServicioContrato;
import com.ProcessSA.ControlTareas.servicio.ServicioFlujoTarea;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Clase que respresenta el servicio REST de Contrato
 */
@RestController
@RequestMapping("/api/contrato")
@Api(tags = "Contrato")
public class ControladorContrato {

    private final ServicioContrato servicio;

    public ControladorContrato(ServicioContrato servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/lista")
    @ApiOperation(value = "Obtener lista de todo de Contrato", notes = "Servicio para listar todos los registros de Contrato")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Contratos encontradas correctamente"),
            @ApiResponse(code = 404, message = "Contratos no encontradas")
    })
    public ResponseEntity<?> obtenerListaContrato() { return ResponseEntity.ok(this.servicio.obtenerContratos()); }

    @PostMapping("/ingresar")
    @ApiOperation(value = "Ingresar Contrato", notes = "Servicio para ingresar un nuevo Contrato")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Contrato creado correctamente"),
            @ApiResponse(code = 400, message = "Solicitud inválida")
    })
    public ResponseEntity<?> ingresarContrato(String rutPersona, String rutEmpresa, int salario, String cargo, String funcion) {
        return new ResponseEntity<>(this.servicio.registroContrato(rutPersona, rutEmpresa, salario, cargo, funcion), HttpStatus.CREATED);
    }

    @PutMapping("/actualizar/{rutPersona}-{rutEmpresa}")
    @ApiOperation(value = "Actualizar Contrato", notes = "Servicio para actualizar un Contrato")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Contrato actualizado correctamente"),
            @ApiResponse(code = 400, message = "Solicitud inválida")
    })
    public ResponseEntity<?> actualizarContrato(
            @PathVariable("rutPersona")String rutPersona,
            @PathVariable("rutEmpresa")String rutEmpresa,
            int salario, String cargo, String funcion
    ) {
        return new ResponseEntity<>(this.servicio.registroContrato(rutPersona, rutEmpresa, salario, cargo, funcion), HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{rutPersona}-{rutEmpresa}")
    @ApiOperation(value = "Eliminar Contrato", notes = "Servicio para eliminar un Contrato")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Funcion eliminada correctamente"),
            @ApiResponse(code = 404, message = "Funcion no encontrada")
    })
    public ResponseEntity<?> eliminarContrato(
            @PathVariable("rutPersona")String rutPersona,
            @PathVariable("rutEmpresa")String rutEmpresa
    ) {
        return new ResponseEntity<>(this.servicio.eliminarContrato(rutPersona, rutEmpresa), HttpStatus.OK);
    }

    @GetMapping("/{rut}")
    @ApiOperation(value = "Obtener registros de Contrato", notes = "Servicio para obtener datos de registro de Contrato")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Contrato encontrado correctamente"),
            @ApiResponse(code = 404, message = "Contrato no encontrado")
    })
    public ResponseEntity<?> obtenerContrato(
            @PathVariable("rut")String rut
    ) {
        return ResponseEntity.ok(this.servicio.obtenerContrato(rut));
    }

}
