package com.ProcessSA.ControlTareas.controlador;

import com.ProcessSA.ControlTareas.servicio.ServicioProceso;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Clase que representa el servicio REST de Proceso
 */
@RestController
@RequestMapping("/api/proceso")
@Api(tags = "Proceso")
public class ControladorProceso {
    private final ServicioProceso servicio;

    public ControladorProceso(ServicioProceso servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/lista")
    @ApiOperation(value = "Obtener lista de todos los Procesos", notes = "Servicio para listar todos los Procesos")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Procesos encontrados correctamente"),
            @ApiResponse(code = 404, message = "Procesos no encontrados")
    })
    public ResponseEntity<?> obtenerListaProcesos() {
        return ResponseEntity.ok(this.servicio.obtenerProcesos());
    }

    @PostMapping("/ingresar")
    @ApiOperation(value = "Ingresar Proceso", notes = "Servicio para ingresar una nuevo Proceso")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Proceso creado correctamente"),
            @ApiResponse(code = 400, message = "Solicitud inválida")
    })
    public ResponseEntity<?> ingresarProceso(Long codigo, Byte indice, String nombre, String descripcion, String input_estado, Long codigo_ui, Long id_disennador, Long codigo_proyecto) {
        return new ResponseEntity<>(this.servicio.registroProceso(codigo, indice, nombre, descripcion, input_estado, codigo_ui, id_disennador, codigo_proyecto), HttpStatus.CREATED);
    }

    @PutMapping("/actualizar/{codigo}")
    @ApiOperation(value = "Actualizar Proceso", notes = "Servicio para actualizar un Proceso")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Proceso actualizado correctamente"),
            @ApiResponse(code = 400, message = "Solicitud inválida")
    })
    public ResponseEntity<?> actualizarProceso(@PathVariable("codigo") Long codigo, Byte indice, String nombre, String descripcion, String input_estado, Long codigo_ui, Long id_disennador, Long codigo_proyecto) {
        return new ResponseEntity<>(this.servicio.registroProceso(codigo, indice, nombre, descripcion, input_estado, codigo_ui, id_disennador, codigo_proyecto), HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{codigo}")
    @ApiOperation(value = "Eliminar Proceso", notes = "Servicio para eliminar una Proceso")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Proceso eliminado correctamente"),
            @ApiResponse(code = 404, message = "Proceso no encontrado")
    })
    public ResponseEntity<?> eliminarProceso(@PathVariable("codigo") Long codigo) {
        return new ResponseEntity<>(this.servicio.eliminarProceso(codigo), HttpStatus.OK);
    }

    @GetMapping("/{codigo}")
    @ApiOperation(value = "Obtener un Proceso", notes = "Servicio para obtener datos de un Proceso")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Proceso encontrado correctamente"),
            @ApiResponse(code = 404, message = "Proceso no encontrado")
    })
    public ResponseEntity<?> obtenerProceso(@PathVariable("codigo") Long codigo) {
        return ResponseEntity.ok(this.servicio.obtenerProceso(codigo));
    }
}
