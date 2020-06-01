package com.ProcessSA.ControlTareas.controlador;

import com.ProcessSA.ControlTareas.servicio.ServicioDisennador;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Clase que representa el servicio REST de Diseñador
 */
@RestController
@RequestMapping("/api/disennador")
@Api(tags = "Disennador")

public class ControladorDisennador {

    private final ServicioDisennador servicio;

    public ControladorDisennador(ServicioDisennador servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/lista")
    @ApiOperation(value = "Obtener lista de todos los Diseñadores", notes = "Servicio para listar todos los Diseñadores")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Diseñadores encontrados correctamente"),
            @ApiResponse(code = 404, message = "Diseñadores no encontrados")
    })
    public ResponseEntity<?> obtenerListaDisennadores() {
        return ResponseEntity.ok(this.servicio.obtenerDisennadores());
    }

    @PostMapping("/ingresar")
    @ApiOperation(value = "Ingresar Diseñador", notes = "Servicio para ingresar una nuevo Diseñador")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Diseñador creado correctamente"),
            @ApiResponse(code = 400, message = "Solicitud inválida")
    })
    public ResponseEntity<?> ingresarDisennador(long codigo) {
        return new ResponseEntity<>(this.servicio.registroDisennador(codigo), HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "Obtener un Diseñador", notes = "Servicio para obtener datos de un Diseñador")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Diseñador encontrado correctamente"),
            @ApiResponse(code = 404, message = "Diseñador no encontrada")
    })
    public ResponseEntity<?> obtenerDisennador(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.servicio.obtenerDisennador(id));
    }

    @DeleteMapping("/eliminar/{id}")
    @ApiOperation(value = "Eliminar Diseñador", notes = "Servicio para eliminar un Diseñador")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Diseñador eliminado correctamente"),
            @ApiResponse(code = 404, message = "Diseñador no encontrado")
    })
    public ResponseEntity<?> eliminarDisennador(@PathVariable("id") long id) {
        return new ResponseEntity<>(this.servicio.eliminarDisennador(id), HttpStatus.OK);
    }

}
