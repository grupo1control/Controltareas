package com.ProcessSA.ControlTareas.controlador;

import com.ProcessSA.ControlTareas.servicio.ServicioUsuario;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Clase que representa el servicio REST de Usuario
 */
@RestController
@RequestMapping("/api/usuario")
@Api(tags = "Usuario")
public class ControladorUsuario {
    private final ServicioUsuario servicio;

    public ControladorUsuario(ServicioUsuario servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/lista")
    @ApiOperation(value = "Obtener lista de todos los Usuarios", notes = "Servicio para listar todos los Usuarios")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Usuarios encontrados correctamente"),
            @ApiResponse(code = 404, message = "Usuarios no encontrados")
    })
    public ResponseEntity<?> obtenerListaUsuarios() {
        return ResponseEntity.ok(this.servicio.obtenerUsuarios());
    }

    @PostMapping("/ingresar")
    @ApiOperation(value = "Ingresar Usuario", notes = "Servicio para ingresar un nuevo Usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Usuario creado correctamente"),
            @ApiResponse(code = 400, message = "Solicitud inválida")
    })
    public ResponseEntity<?> ingresarUsuarioo(String rut, String nombre, String correo, String clave) {
        return new ResponseEntity<>(this.servicio.registroUsuario(rut, nombre, correo, clave), HttpStatus.CREATED);
    }

    @PutMapping("/actualizar/{rut}")
    @ApiOperation(value = "Actualizar Usuario", notes = "Servicio para actualizar un Usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Usuario actualizado correctamente"),
            @ApiResponse(code = 400, message = "Solicitud inválida")
    })
    public ResponseEntity<?> actualizarUsuario(@PathVariable("rut") String rut, String nombre, String correo, String clave) {
        return new ResponseEntity<>(this.servicio.registroUsuario(rut, nombre, correo, clave), HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    @ApiOperation(value = "Eliminar Usuario", notes = "Servicio para eliminar un Usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Usuario eliminado correctamente"),
            @ApiResponse(code = 404, message = "Usuario no encontrado")
    })
    public ResponseEntity<?> eliminarUsuario(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.servicio.eliminarUsuario(id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Obtener un Usuario", notes = "Servicio para obtener datos de un Usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Usuario encontrado correctamente"),
            @ApiResponse(code = 404, message = "Usuario no encontrado")
    })
    public ResponseEntity<?> obtenerUsuario(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.servicio.obtenerUsuario(id));
    }
}
