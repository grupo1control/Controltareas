package com.ProcessSA.ControlTareas.controlador;


import com.ProcessSA.ControlTareas.servicio.ServicioFlujoTarea;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Clase que respresenta el servicio REST de FlujoTarea
 */
@RestController
@RequestMapping("/api/flujo-tarea")
@Api(tags = "Flujo Tarea")
public class ControladorFlujoTarea {

    private final ServicioFlujoTarea servicio;

    public ControladorFlujoTarea(ServicioFlujoTarea servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/lista")
    @ApiOperation(value = "Obtener lista de todo de FlujoTarea", notes = "Servicio para listar a todas los registros de FlujoTarea")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Flujos encontradas correctamente"),
            @ApiResponse(code = 404, message = "Flujos no encontradas")
    })
    public ResponseEntity<?> obtenerListaFlujoTarea() {
        return ResponseEntity.ok(this.servicio.obtenerFlujosTareas());
    }

    @PostMapping("/ingresar")
    @ApiOperation(value = "Ingresar FlujoTarea", notes = "Servicio para ingresar un nuevo FlujoTarea")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "FlujoTarea creado correctamente"),
            @ApiResponse(code = 400, message = "Solicitud inválida")
    })
    public ResponseEntity<?> ingresarFlujoTarea(Byte indice, Long codigoTarea, Long codigoFuncion) {
        return new ResponseEntity<>(this.servicio.registroFlujoTarea(indice, codigoTarea, codigoFuncion), HttpStatus.CREATED);
    }

    @PutMapping("/actualizar/{codigoTarea}-{indice}-{codigoFuncion}")
    @ApiOperation(value = "Actualizar FlujoTarea", notes = "Servicio para actualizar un FlujoTarea")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "FlujoTarea actualizado correctamente"),
            @ApiResponse(code = 400, message = "Solicitud inválida")
    })
    public ResponseEntity<?> actualizarFlujoTarea(
            @PathVariable("indice") Byte indice,
            @PathVariable("codigoTarea") Long codigoTarea,
            @PathVariable("codigoFuncion") Long codigoFuncion
    ) {
        return new ResponseEntity<>(this.servicio.registroFlujoTarea(indice, codigoTarea, codigoFuncion), HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{codigoTarea}-{codigoFuncion}")
    @ApiOperation(value = "Eliminar FlujoTarea", notes = "Servicio para eliminar un FlujoTarea")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "FlujoTarea eliminado correctamente"),
            @ApiResponse(code = 404, message = "FlujoTarea no encontrado")
    })
    public ResponseEntity<?> eliminarFlujoTarea(
            @PathVariable("codigoTarea") Long codigoTarea,
            @PathVariable("codigoFuncion") Long codigoFuncion
    ) {
        return new ResponseEntity<>(this.servicio.eliminarFlujoTarea(codigoTarea, codigoFuncion), HttpStatus.OK);
    }

    @GetMapping("/{codigoTarea}-0-{codigoFuncion}")
    @ApiOperation(value = "Obtener registros de FlujoTarea", notes = "Servicio para obtener datos de registro de FlujoTarea")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "FlujoTarea encontrado correctamente"),
            @ApiResponse(code = 404, message = "FlujoTarea no encontrado")
    })
    public ResponseEntity<?> obtenerFlujoTarea(
            @PathVariable("codigoTarea") Long codigoTarea,
            @PathVariable("codigoFuncion") Long codigoFuncion
    ) {
        return ResponseEntity.ok(this.servicio.obtenerFlujoTarea(codigoTarea, codigoFuncion));
    }

}
