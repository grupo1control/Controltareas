package com.ProcessSA.ControlTareas.controlador;


import com.ProcessSA.ControlTareas.servicio.ServicioUnidadInterna;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Clase que representa el servicio REST de Unidad Interna
 */
@RestController
@RequestMapping("/api/UnidadInterna")
@Api(tags = "UnidadInterna")
public class ControladorUnidadInterna {

    private final ServicioUnidadInterna servicio;

    public ControladorUnidadInterna(ServicioUnidadInterna servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/lista")
    @ApiOperation(value = "Obtener lista de todas las Unidades Internas", notes = "Servicio para listar a todas las Unidades Internas")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Unidades encontradas correctamente"),
            @ApiResponse(code = 404, message = "Unidades no encontradas")
    })
    public ResponseEntity<?> obtenerListaUnidadesInternas() {
        return ResponseEntity.ok(this.servicio.obtenerUnidadesInternas());
    }

    @PostMapping("/ingresar")
    @ApiOperation(value = "Ingresar Unidad Interna", notes = "Servicio para ingresar una nueva Unidad Interna")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Unidad creada correctamente"),
            @ApiResponse(code = 400, message = "Solicitud inválida")
    })
    public ResponseEntity<?> ingresarUnidadInterna(Long codigo, String nombre, String descripcion) {
        return new ResponseEntity<>(this.servicio.registroUnidadInterna(codigo, nombre, descripcion), HttpStatus.CREATED);
    }

    @PutMapping("/actualizar/{codigo}")
    @ApiOperation(value = "Actualizar Unidad Interna", notes = "Servicio para actualizar una Unidad Interna")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Unidad Interna actualizada correctamente"),
            @ApiResponse(code = 400, message = "Solicitud inválida")
    })
    public ResponseEntity<?> actualizarUnidadInterna(@PathVariable("codigo") Long codigo, String nombre, String descripcion) {
        return new ResponseEntity<>(this.servicio.registroUnidadInterna(codigo, nombre, descripcion), HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{codigo}")
    @ApiOperation(value = "Eliminar Unidad Interna", notes = "Servicio para eliminar una Unidad Interna")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Unidad Interna eliminada correctamente"),
            @ApiResponse(code = 404, message = "Unidad Interna no encontrada")
    })
    public ResponseEntity<?> eliminarUnidadInterna(@PathVariable("codigo") Long codigo) {
        return new ResponseEntity<>(this.servicio.eliminarUnidadInterna(codigo), HttpStatus.OK);
    }

    @GetMapping("/{codigo}")
    @ApiOperation(value = "Obtener una Unidad Interna", notes = "Servicio para obtener datos de una Unidad Interna")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Unidad Interna encontrada correctamente"),
            @ApiResponse(code = 404, message = "Unidad Interna no encontrada")
    })
    public ResponseEntity<?> obtenerUnidadInterna(@PathVariable("codigo") Long codigo) {
        return ResponseEntity.ok(this.servicio.obtenerUnidadInterna(codigo));
    }
}
