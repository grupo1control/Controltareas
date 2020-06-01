package com.ProcessSA.ControlTareas.controlador;

import com.ProcessSA.ControlTareas.servicio.ServicioFuncionario;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Clase que representa el servicio REST de Funcionario
 */
@RestController
@RequestMapping("/api/funcionario")
@Api(tags = "Funcionario")
public class ControladorFuncionario {

    private final ServicioFuncionario servicio;

    public ControladorFuncionario(ServicioFuncionario servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/lista")
    @ApiOperation(value = "Obtener lista de todos los Funcionarios", notes = "Servicio para listar todos los Funcionarios")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Funcionarios encontrados correctamente"),
            @ApiResponse(code = 404, message = "Funcionarios no encontrados")
    })
    public ResponseEntity<?> obtenerListaFuncionarios() { return ResponseEntity.ok(this.servicio.obtenerFuncionarios()); }

    @PostMapping("/ingresar")
    @ApiOperation(value = "Ingresar Funcionario", notes = "Servicio para ingresar un nuevo Funcionario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Funcionario creado correctamente"),
            @ApiResponse(code = 400, message = "Solicitud inválida")
    })
    public ResponseEntity<?> ingresarFuncionario(Long codigo) {
        return new ResponseEntity<>(this.servicio.registroFuncionario(codigo), HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "Obtener un Funcionario", notes = "Servicio para obtener datos de un Funcionario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Funcionario encontrado correctamente"),
            @ApiResponse(code = 404, message = "Funcionario no encontrada")
    })
    public ResponseEntity<?> obtenerFuncionario(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.servicio.obtenerFuncionario(id));
    }

    @DeleteMapping("/eliminar/{id}")
    @ApiOperation(value = "Eliminar Funcionario", notes = "Servicio para eliminar un Funcionario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Funcionario eliminado correctamente"),
            @ApiResponse(code = 404, message = "Funcionario no encontrado")
    })
    public ResponseEntity<?> eliminarFuncionario(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.servicio.eliminarFuncionario(id), HttpStatus.OK);
    }

}
