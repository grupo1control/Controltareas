package com.ProcessSA.ControlTareas.controlador;

import com.ProcessSA.ControlTareas.servicio.ServicioFuncion;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Clase que representa el servicio REST de Funcion
 */
@RestController
@RequestMapping("/api/funcion")
@Api(tags = "Funcion")
public class ControladorFuncion {

    private final ServicioFuncion servicio;

    public ControladorFuncion(ServicioFuncion servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/lista")
    @ApiOperation(value = "Obtener lista de todas las Funciones", notes = "Servicio para listar a todas las Funciones")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Funciones encontradas correctamente"),
            @ApiResponse(code = 404, message = "Funciones no encontradas")
    })
    public ResponseEntity<?> obtenerListaFunciones() { return ResponseEntity.ok(this.servicio.obtenerFunciones()); }

    @PostMapping("/ingresar")
    @ApiOperation(value = "Ingresar Funcion", notes = "Servicio para ingresar una nueva Funcion")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Funcion creada correctamente"),
            @ApiResponse(code = 400, message = "Solicitud inválida")
    })
    public ResponseEntity<?> ingresarFuncion(Long codigo, String nombre, String descripcion, String estado) {
        return new ResponseEntity<>(this.servicio.registroFuncion(codigo, nombre, descripcion, estado), HttpStatus.CREATED);
    }

    @PutMapping("/actualizar/{codigo}")
    @ApiOperation(value = "Actualizar Funcion", notes = "Servicio para actualizar una Funcion")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Funcion actualizada correctamente"),
            @ApiResponse(code = 400, message = "Solicitud inválida")
    })
    public ResponseEntity<?> actualizarFuncion(@PathVariable("codigo") Long codigo, String nombre, String descripcion, String estado) {
        return new ResponseEntity<>(this.servicio.registroFuncion(codigo, nombre, descripcion, estado), HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{codigo}")
    @ApiOperation(value = "Eliminar Funcion", notes = "Servicio para eliminar una Funcion")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Funcion eliminada correctamente"),
            @ApiResponse(code = 404, message = "Funcion no encontrada")
    })
    public ResponseEntity<?> eliminarFuncion(@PathVariable("codigo") Long codigo) {
        return new ResponseEntity<>(this.servicio.eliminarFuncion(codigo), HttpStatus.OK);
    }

    @GetMapping("/{codigo}")
    @ApiOperation(value = "Obtener una Funcion", notes = "Servicio para obtener datos de una Funcion")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Funcion encontrada correctamente"),
            @ApiResponse(code = 404, message = "Funcion no encontrada")
    })
    public ResponseEntity<?> obtenerFuncion(@PathVariable("codigo") Long codigo) {
        return ResponseEntity.ok(this.servicio.obtenerFuncion(codigo));
    }


}
