package com.ProcessSA.ControlTareas.controlador;

import com.ProcessSA.ControlTareas.servicio.ServicioTarea;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Clase que representa el servicio REST de Tarea
 */
@RestController
@RequestMapping("/api/tarea")
@Api(tags = "Tarea")
public class ControladorTarea {

    private final ServicioTarea servicio;

    public ControladorTarea(ServicioTarea servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/lista")
    @ApiOperation(value = "Obtener lista de todas las Tareas", notes = "Servicio para listar a todas las Tareas")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Tareas encontradas correctamente"),
            @ApiResponse(code = 404, message = "Tareas no encontradas")
    })
    public ResponseEntity<?> obtenerListaTareas() { return ResponseEntity.ok(this.servicio.obtenerTareas()); }

    @PostMapping("/ingresar")
    @ApiOperation(value = "Ingresar Tarea", notes = "Servicio para ingresar una nueva Tarea")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Tarea creada correctamente"),
            @ApiResponse(code = 400, message = "Solicitud inválida")
    })
    public ResponseEntity<?> ingresarTarea(Long codigo, String nombre, String descripcion, String estado) {
        return new ResponseEntity<>(this.servicio.registroTarea(codigo, nombre, descripcion, estado), HttpStatus.CREATED);
    }

    @PutMapping("/actualizar/{codigo}")
    @ApiOperation(value = "Actualizar Tarea", notes = "Servicio para actualizar una Tarea")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Tarea actualizada correctamente"),
            @ApiResponse(code = 400, message = "Solicitud inválida")
    })
    public ResponseEntity<?> actualizarTarea(@PathVariable("codigo") Long codigo, String nombre, String descripcion, String estado) {
        return new ResponseEntity<>(this.servicio.registroTarea(codigo, nombre, descripcion, estado), HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{codigo}")
    @ApiOperation(value = "Eliminar Tarea", notes = "Servicio para eliminar una Tarea")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Tarea eliminada correctamente"),
            @ApiResponse(code = 404, message = "Tarea no encontrada")
    })
    public ResponseEntity<?> eliminarTarea(@PathVariable("codigo") Long codigo) {
        return new ResponseEntity<>(this.servicio.eliminarTarea(codigo), HttpStatus.OK);
    }

    @GetMapping("/{codigo}")
    @ApiOperation(value = "Obtener una Tarea", notes = "Servicio para obtener datos de una Tarea")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Tarea encontrada correctamente"),
            @ApiResponse(code = 404, message = "Tarea no encontrada")
    })
    public ResponseEntity<?> obtenerTarea(@PathVariable("codigo") Long codigo) {
        return ResponseEntity.ok(this.servicio.obtenerTarea(codigo));
    }

}
