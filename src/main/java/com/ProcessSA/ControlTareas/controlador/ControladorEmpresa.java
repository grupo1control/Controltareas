package com.ProcessSA.ControlTareas.controlador;

import com.ProcessSA.ControlTareas.servicio.ServicioEmpresa;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

/**
 * Clase que representa el servicio REST de Empresa
 */
@RestController
@RequestMapping("/api/empresa")
@Api(tags = "Empresa")
public class ControladorEmpresa {

    private final ServicioEmpresa servicio;

    public ControladorEmpresa(ServicioEmpresa servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/lista")
    @ApiOperation(value = "Obtener lista de todas las Empresas", notes = "Servicio para listar a todas las Empresas")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Empresas encontradas correctamente"),
            @ApiResponse(code = 404, message = "Empresas no encontradas")
    })
    public ResponseEntity<?> obtenerListaEmpresas() { return ResponseEntity.ok(this.servicio.obtenerEmpresas()); }

    @PostMapping("/ingresar")
    @ApiOperation(value = "Ingresar Empresa", notes = "Servicio para ingresar una nueva Empresa")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Empresa creada correctamente"),
            @ApiResponse(code = 400, message = "Solicitud inválida")
    })
    public ResponseEntity<?> ingresarEmpresa(String rut, String nombre, String razonSocial, String giroComercial) {
        return new ResponseEntity<>(this.servicio.registroEmpresa(rut, nombre, razonSocial, giroComercial), HttpStatus.CREATED);
    }

    @PutMapping("/actualizar/{rut}")
    @ApiOperation(value = "Actualizar Empresa", notes = "Servicio para actualizar una Empresa")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Empresa actualizada correctamente"),
            @ApiResponse(code = 400, message = "Solicitud inválida")
    })
    public ResponseEntity<?> actualizarEmpresa(@PathVariable("rut") String rut, String nombre, String razonSocial, String giroComercial) {
        return new ResponseEntity<>(this.servicio.registroEmpresa(rut, nombre, razonSocial, giroComercial), HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{rut}")
    @ApiOperation(value = "Eliminar Empresa", notes = "Servicio para eliminar una Empresa")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Empresa eliminada correctamente"),
            @ApiResponse(code = 404, message = "Empresa no encontrada")
    })
    public ResponseEntity<?> eliminarEmpresa(@PathVariable("rut") String rut) {
        return new ResponseEntity<>(this.servicio.eliminarEmpresa(rut), HttpStatus.OK);
    }

    @GetMapping("/{rut}")
    @ApiOperation(value = "Obtener una Empresa", notes = "Servicio para obtener datos de una Empresa")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Empresa encontrada correctamente"),
            @ApiResponse(code = 404, message = "Empresa no encontradas")
    })
    public ResponseEntity<?> obtenerEmpresa(@PathVariable("rut") String rut) {
        return ResponseEntity.ok(this.servicio.obtenerEmpresa(rut));
    }

}
