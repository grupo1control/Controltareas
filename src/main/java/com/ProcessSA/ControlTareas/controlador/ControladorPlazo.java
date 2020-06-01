package com.ProcessSA.ControlTareas.controlador;

import com.ProcessSA.ControlTareas.servicio.ServicioPlazo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

/**
 * Clase que representa el servicio REST de Plazo
 */
@RestController
@RequestMapping("/api/plazo")
@Api(tags = "Plazo")
public class ControladorPlazo {

    private final ServicioPlazo servicio;

    public ControladorPlazo(ServicioPlazo servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/lista")
    @ApiOperation(value = "Obtener lista de todos los Plazos", notes = "Servicio para listar a todos los Plazos")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Plazos encontrados correctamente"),
            @ApiResponse(code = 404, message = "Plazos no encontradas")
    })
    public ResponseEntity<?> obtenerListaPlazos() { return ResponseEntity.ok(this.servicio.obtenerPlazos()); }

    @PostMapping("/ingresar")
    @ApiOperation(value = "Ingresar Plazo", notes = "Servicio para ingresar un nuevo Plazo")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Plazo creado correctamente"),
            @ApiResponse(code = 400, message = "Solicitud inválida")
    })
    public ResponseEntity<?> ingresarPlazo(Date fecha, Long codigoTarea) {
        return new ResponseEntity<>(this.servicio.registroPlazo(fecha, codigoTarea), HttpStatus.CREATED);
    }

    @DeleteMapping("/eliminar/{codigoTarea}-{contador}")
    @ApiOperation(value = "Eliminar Plazo", notes = "Servicio para eliminar un Plazo")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Plazo eliminado correctamente"),
            @ApiResponse(code = 404, message = "Plazo no encontrado")
    })
    public ResponseEntity<?> eliminarPlazo(
            @PathVariable("codigoTarea") Long codigoTarea,
            @PathVariable("contador") byte contador
            ) {
        return new ResponseEntity<>(this.servicio.eliminarPlazo(codigoTarea, contador), HttpStatus.OK);
    }

    @GetMapping("/{codigoTarea}")
    @ApiOperation(value = "Obtener un Plazo", notes = "Servicio para obtener datos de un Plazo")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Plazo encontrada correctamente"),
            @ApiResponse(code = 404, message = "Plazo no encontrado")
    })
    public ResponseEntity<?> obtenerPlazo(@PathVariable("codigoTarea") Long codigoTarea) {
        return ResponseEntity.ok(this.servicio.obtenerPlazo(codigoTarea));
    }

}
