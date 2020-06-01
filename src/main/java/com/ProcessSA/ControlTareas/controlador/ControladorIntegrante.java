package com.ProcessSA.ControlTareas.controlador;

import com.ProcessSA.ControlTareas.servicio.ServicioIntegrante;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Clase que representa el servicio REST de Integrante
 */
@RestController
@RequestMapping("/api/integrante")
@Api(tags = "Integrante")
public class ControladorIntegrante {
    private final ServicioIntegrante servicio;

    public ControladorIntegrante(ServicioIntegrante servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/lista")
    @ApiOperation(value = "Obtener lista de todos los Integrantes", notes = "Servicio para listar todos los Integrantes")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Integrantes encontrados correctamente"),
            @ApiResponse(code = 404, message = "Integrantes no encontrados")
    })
    public ResponseEntity<?> obtenerListaIntegrantes() {
        return ResponseEntity.ok(this.servicio.obtenerIntegrantes());
    }

    @PostMapping("/ingresar")
    @ApiOperation(value = "Ingresar Integrante", notes = "Servicio para ingresar un nuevo Integrante")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Integrante creado correctamente"),
            @ApiResponse(code = 400, message = "Solicitud inválida")
    })
    public ResponseEntity<?> ingresarIntegrante(Long codigoUi, Long idUsuario) {
        return new ResponseEntity<>(this.servicio.registroIntegrante(codigoUi, idUsuario), HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "Obtener un Integrante", notes = "Servicio para obtener datos de un Integrante")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Integrante encontrado correctamente"),
            @ApiResponse(code = 404, message = "Integrante no encontrada")
    })
    public ResponseEntity<?> obtenerIntegrante(@PathVariable("id") Long codigoUi, Long idUsuario) {
        return ResponseEntity.ok(this.servicio.obtenerIntegrante(codigoUi, idUsuario));
    }

    @DeleteMapping("/eliminar/{id}")
    @ApiOperation(value = "Eliminar Integrante", notes = "Servicio para eliminar un Integrante")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Integrante eliminado correctamente"),
            @ApiResponse(code = 404, message = "Integrante no encontrado")
    })
    public ResponseEntity<?> eliminarIntegrante(@PathVariable("id") Long codigoUi, Long idUsuario) {
        return new ResponseEntity<>(this.servicio.eliminarIntegrante(codigoUi, idUsuario), HttpStatus.OK);
    }
}
