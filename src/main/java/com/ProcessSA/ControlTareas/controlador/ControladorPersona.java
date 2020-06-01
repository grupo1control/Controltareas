package com.ProcessSA.ControlTareas.controlador;

import com.ProcessSA.ControlTareas.servicio.ServicioPersona;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

/**
 * Clase que representa el servicio REST de Persona
 */
@RestController
@RequestMapping("/api/persona")
@Api(tags = "Persona")
public class ControladorPersona {

    private final ServicioPersona servicio;

    public ControladorPersona(ServicioPersona servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/lista")
    @ApiOperation(value = "Obtener lista de todas las Personas", notes = "Servicio para listar a todas las Personas")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Personas encontradas correctamente"),
            @ApiResponse(code = 404, message = "Personas no encontradas")
    })
    public ResponseEntity<?> obtenerListaPersonas() {
        return ResponseEntity.ok(this.servicio.obtenerPersonas());
    }

    @PostMapping("/ingresar")
    @ApiOperation(value = "Ingresar Persona", notes = "Servicio para ingresar una nueva Persona")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Persona creada correctamente"),
            @ApiResponse(code = 400, message = "Solicitud inválida")
    })
    public ResponseEntity<?> ingresarPersona(String rut, String nombre, String apellido, Date natalicio) {
        return new ResponseEntity<>(this.servicio.registroPersona(rut, nombre, apellido, natalicio), HttpStatus.CREATED);
    }

    @PutMapping("/actualizar/{rut}")
    @ApiOperation(value = "Actualizar Persona", notes = "Servicio para actualizar una Persona")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Persona actualizada correctamente"),
            @ApiResponse(code = 400, message = "Solicitud inválida")
    })
    public ResponseEntity<?> actualizarPersona(@PathVariable("rut") String rut, String nombre, String apellido, Date natalicio) {
        return new ResponseEntity<>(this.servicio.registroPersona(rut, nombre, apellido, natalicio), HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{rut}")
    @ApiOperation(value = "Eliminar Persona", notes = "Servicio para eliminar una Persona")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Persona eliminada correctamente"),
            @ApiResponse(code = 404, message = "Persona no encontrada")
    })
    public ResponseEntity<?> eliminarPersona(@PathVariable("rut") String rut) {
        return new ResponseEntity<>(this.servicio.eliminarPersona(rut), HttpStatus.OK);
    }

    @GetMapping("/{rut}")
    @ApiOperation(value = "Obtener una Persona", notes = "Servicio para obtener datos de una Persona")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Persona encontrada correctamente"),
            @ApiResponse(code = 404, message = "Persona no encontradas")
    })
    public ResponseEntity<?> obtenerPersona(@PathVariable("rut") String rut) {
        return ResponseEntity.ok(this.servicio.obtenerPersona(rut));
    }

}
