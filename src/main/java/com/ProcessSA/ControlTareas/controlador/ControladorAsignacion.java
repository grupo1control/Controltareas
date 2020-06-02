package com.ProcessSA.ControlTareas.controlador;

import com.ProcessSA.ControlTareas.servicio.ServicioAsignacion;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Clase que representa el servicio REST de Asignacion
 */
@RestController
@RequestMapping("/api/asignacion")
@Api(tags = "Asignacion")
public class ControladorAsignacion {

    private final ServicioAsignacion servicio;

    public ControladorAsignacion(ServicioAsignacion servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/lista")
    @ApiOperation(value = "Obtener lista de todas las asignaciones", notes = "Servicio para listar todas las Asignaciones")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Asignaciones encontradas correctamente"),
            @ApiResponse(code = 404, message = "Asignaciones no encontradas")
    })
    public ResponseEntity<?> obtenerListaAsignaciones() {
        return ResponseEntity.ok(this.servicio.obtenerAsignaciones());
    }

    @PostMapping("/ingresar")
    @ApiOperation(value = "Ingresar Asigacion", notes = "Servicio para ingresar una nueva Asignacion")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Asignacion creada correctamente"),
            @ApiResponse(code = 400, message = "Solicitud inválida")
    })
    public ResponseEntity<?> ingresarAsignacion(Long codigoTarea, Long codigoUi, Long idUsuario, String rol, String estado, String nota) {
        return new ResponseEntity<>(this.servicio.registroAsignacion(codigoTarea, codigoUi, idUsuario, rol, estado, nota), HttpStatus.CREATED);
    }


    @GetMapping("/{usuario}-{UI}-{tarea}")
    @ApiOperation(value = "Obtener un Asignacion", notes = "Servicio para obtener datos de una Asignacion")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Asignacion encontrada correctamente"),
            @ApiResponse(code = 404, message = "Asignacion no encontrada")
    })
    public ResponseEntity<?> obtenerAsignacion(
            @PathVariable("usuario") Long idUsuario,
            @PathVariable("UI") Long codigoUI,
            @PathVariable("tarea") Long codigoTarea
    ) {
        return ResponseEntity.ok(this.servicio.obtenerAsignacion(idUsuario, codigoUI, codigoTarea));
    }

    @DeleteMapping("/eliminar/{usuario}-{UI}-{tarea}")
    @ApiOperation(value = "Eliminar Administrador", notes = "Servicio para eliminar un Administrador")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Administrador eliminado correctamente"),
            @ApiResponse(code = 404, message = "Administrador no encontrado")
    })
    public ResponseEntity<?> eliminarAsignacion(
            @PathVariable("usuario") Long idUsuario,
            @PathVariable("UI") Long codigoUI,
            @PathVariable("tarea") Long codigoTarea
    ) {
        return new ResponseEntity<>(this.servicio.eliminarAsignacion(idUsuario, codigoUI, codigoTarea), HttpStatus.OK);
    }

    @PutMapping("/actualizar/{usuario}-{UI}-{tarea}")
    @ApiOperation(value = "Actualizar Asignacion", notes = "Servicio para actualizar una Asignacion")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Asignación actualizada correctamente"),
            @ApiResponse(code = 400, message = "Solicitud inválida")
    })
    public ResponseEntity<?> actualizarAsignacion(
            @PathVariable("usuario") Long idUsuario,
            @PathVariable("UI") Long codigoUI,
            @PathVariable("tarea") Long codigoTarea,
            String rol, String estado, String nota
    ) {
        return new ResponseEntity<>(this.servicio.registroAsignacion(codigoTarea, codigoUI, idUsuario, rol, estado, nota), HttpStatus.OK);
    }

}
