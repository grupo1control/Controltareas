package com.ProcessSA.ControlTareas.controlador;

import com.ProcessSA.ControlTareas.servicio.ServicioFlujoFuncion;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Clase que respresenta el servicio REST de FlujoFuncion
 */
@RestController
@RequestMapping("/api/flujo-funcion")
@Api(tags = "Flujo Funcion")

public class ControladorFlujoFuncion {
    private final ServicioFlujoFuncion servicio;

    public ControladorFlujoFuncion(ServicioFlujoFuncion servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/lista")
    @ApiOperation(value = "Obtener lista de todo FlujoFuncion", notes = "Servicio para listar a todos los registros de FlujoFuncion")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Flujos encontrados correctamente"),
            @ApiResponse(code = 404, message = "Flujos no encontrados")
    })
    public ResponseEntity<?> obtenerListaFlujoFuncion() { return ResponseEntity.ok(this.servicio.obtenerFlujosFunciones()); }

    @PostMapping("/ingresar")
    @ApiOperation(value = "Ingresar FlujoFuncion", notes = "Servicio para ingresar un nuevo FlujoFuncion")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "FlujoFuncion creado correctamente"),
            @ApiResponse(code = 400, message = "Solicitud inválida")
    })
    public ResponseEntity<?> ingresarFlujoFuncion(byte indice, Long codigoProceso, Long codigoFuncion) {
        return new ResponseEntity<>(this.servicio.registroFlujoFuncion(indice, codigoProceso, codigoFuncion), HttpStatus.CREATED);
    }

    @PutMapping("/actualizar/{codigoProceso}-{indice}-{codigoFuncion}")
    @ApiOperation(value = "Actualizar FlujoFuncion", notes = "Servicio para actualizar un FlujoFuncion")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "FlujoFuncion actualizado correctamente"),
            @ApiResponse(code = 400, message = "Solicitud inválida")
    })
    public ResponseEntity<?> actualizarFlujoFuncion(
            @PathVariable("indice") byte indice,
            @PathVariable("codigoProceso") Long codigoProceso,
            @PathVariable("codigoFuncion") Long codigoFuncion
    ) {
        return new ResponseEntity<>(this.servicio.registroFlujoFuncion(indice, codigoProceso, codigoFuncion), HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{codigoProceso}-{indice}-{codigoFuncion}")
    @ApiOperation(value = "Eliminar FlujoFuncion", notes = "Servicio para eliminar un FlujoFuncion")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Funcion eliminada correctamente"),
            @ApiResponse(code = 404, message = "Funcion no encontrada")
    })
    public ResponseEntity<?> eliminarFlujoFuncion(
            @PathVariable("indice") byte indice,
            @PathVariable("codigoProceso") Long codigoProceso,
            @PathVariable("codigoFuncion") Long codigoFuncion
    ) {
        return new ResponseEntity<>(this.servicio.eliminarFlujoFuncion(indice, codigoProceso, codigoFuncion), HttpStatus.OK);
    }

    @GetMapping("/{codigoProceso}-{codigoFuncion}")
    @ApiOperation(value = "Obtener registros de FlujoFuncion", notes = "Servicio para obtener datos de registro de FlujoFuncion")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "FlujoFuncion encontrado correctamente"),
            @ApiResponse(code = 404, message = "FlujoFuncion no encontrado")
    })
    public ResponseEntity<?> obtenerFlujoFuncion(
            @PathVariable("codigoProceso") Long codigoProceso,
            @PathVariable("codigoFuncion") Long codigoFuncion
    ) {
        return ResponseEntity.ok(this.servicio.obtenerFlujoFuncion(codigoProceso, codigoFuncion));
    }
}
