package com.ProcessSA.ControlTareas.controlador;

import com.ProcessSA.ControlTareas.servicio.ServicioAdministrador;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

/**
 * Clase que representa el servicio REST de Administrador
 */
@RestController
@RequestMapping("/api/administrador")
@Api(tags = "Administrador")
public class ControladorAdministrador {
    
    private final ServicioAdministrador servicio;

    public ControladorAdministrador(ServicioAdministrador servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/lista")
    @ApiOperation(value = "Obtener lista de todos los Administradores", notes = "Servicio para listar todos los Administradores")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Administradores encontrados correctamente"),
            @ApiResponse(code = 404, message = "Administradores no encontrados")
    })
    public ResponseEntity<?> obtenerListaAdministradores() { return ResponseEntity.ok(this.servicio.obtenerAdministradores()); }

    @PostMapping("/ingresar")
    @ApiOperation(value = "Ingresar Administrador", notes = "Servicio para ingresar una nuevo Administrador")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Administrador creado correctamente"),
            @ApiResponse(code = 400, message = "Solicitud inválida")
    })
    public ResponseEntity<?> ingresarAdministrador(long codigo) {
        return new ResponseEntity<>(this.servicio.registroAdministrador(codigo), HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "Obtener un Administrador", notes = "Servicio para obtener datos de un Administrador")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Administrador encontrado correctamente"),
            @ApiResponse(code = 404, message = "Administrador no encontrada")
    })
    public ResponseEntity<?> obtenerAdministrador(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.servicio.obtenerAdministrador(id));
    }

    @DeleteMapping("/eliminar/{id}")
    @ApiOperation(value = "Eliminar Administrador", notes = "Servicio para eliminar un Administrador")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Administrador eliminado correctamente"),
            @ApiResponse(code = 404, message = "Administrador no encontrado")
    })
    public ResponseEntity<?> eliminarAdministrador(@PathVariable("id") long id) {
        return new ResponseEntity<>(this.servicio.eliminarAdministrador(id), HttpStatus.OK);
    }

}
