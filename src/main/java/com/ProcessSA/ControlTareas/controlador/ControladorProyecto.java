package com.ProcessSA.ControlTareas.controlador;


import com.ProcessSA.ControlTareas.servicio.ServicioProyecto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Clase que representa el servicio REST de Proyecto
 */
@RestController
@RequestMapping("/api/proyecto")
@Api(tags = "Proyecto")
public class ControladorProyecto {

    private final ServicioProyecto servicio;

    public ControladorProyecto(ServicioProyecto servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/lista")
    @ApiOperation(value = "Obtener lista de todos los Proyectos", notes = "Servicio para listar todos los Proyectos")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Proyectos encontrados correctamente"),
            @ApiResponse(code = 404, message = "Proyectos no encontrados")
    })
    public ResponseEntity<?> obtenerListaProyectos() {
        return ResponseEntity.ok(this.servicio.obtenerProyectos());
    }

    @PostMapping("/ingresar")
    @ApiOperation(value = "Ingresar Proyecto", notes = "Servicio para ingresar una nuevo Proyecto")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Proyecto creado correctamente"),
            @ApiResponse(code = 400, message = "Solicitud inválida")
    })
    public ResponseEntity<?> ingresarProyecto(Long codigo, String nombre, String inputEstado, String rut_empresa, Long id_administrador) {
        return new ResponseEntity<>(this.servicio.registroProyecto(codigo, nombre, inputEstado,rut_empresa,id_administrador), HttpStatus.CREATED);
    }

    @PutMapping("/actualizar/{codigo}")
    @ApiOperation(value = "Actualizar Proyecto", notes = "Servicio para actualizar un Proyecto")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Proyecto actualizado correctamente"),
            @ApiResponse(code = 400, message = "Solicitud inválida")
    })
    public ResponseEntity<?> actualizarProyecto(@PathVariable("codigo") Long codigo, String nombre, String inputEstado, String rut_empresa, Long id_administrador) {
        return new ResponseEntity<>(this.servicio.registroProyecto(codigo, nombre, inputEstado,rut_empresa,id_administrador), HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{codigo}")
    @ApiOperation(value = "Eliminar Proyecto", notes = "Servicio para eliminar una Proyecto")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Proyecto eliminado correctamente"),
            @ApiResponse(code = 404, message = "Proyecto no encontrado")
    })
    public ResponseEntity<?> eliminarProyecto(@PathVariable("codigo") Long codigo) {
        return new ResponseEntity<>(this.servicio.eliminarProyecto(codigo), HttpStatus.OK);
    }

    @GetMapping("/{codigo}")
    @ApiOperation(value = "Obtener un Proyecto", notes = "Servicio para obtener datos de un Proyecto")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Proyecto encontrado correctamente"),
            @ApiResponse(code = 404, message = "Proyecto no encontrado")
    })
    public ResponseEntity<?> obtenerProyecto(@PathVariable("codigo") Long codigo) {
        return ResponseEntity.ok(this.servicio.obtenerProyecto(codigo));
    }

}
