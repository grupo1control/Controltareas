package com.ProcessSA.ControlTareas.controlador;

import com.ProcessSA.ControlTareas.servicio.ServicioPersona;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
}
