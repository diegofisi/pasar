package com.dgvh.test2.controller;

import com.dgvh.test2.dto.PacienteDTO;
import com.dgvh.test2.exception.ModeloNotFoundException;
import com.dgvh.test2.model.Paciente;
import com.dgvh.test2.service.IPacienteService;
import com.sun.net.httpserver.HttpsServer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.w3c.dom.Entity;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private IPacienteService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<PacienteDTO>> listar() throws Exception{
        List<PacienteDTO> lista = service.listar().stream().map(p -> mapper.map(p, PacienteDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> listarPorId(@PathVariable("id") Integer id) throws Exception{
        PacienteDTO dtoResponse;
        Paciente obj = service.listarPorId(id);
        if(obj == null){
            throw new ModeloNotFoundException("ID NO ENCONTRADO" + id);
        }else{
            dtoResponse = mapper.map(obj, PacienteDTO.class);
        }
        return new ResponseEntity<>(dtoResponse,HttpStatus.OK);
    }

    /* @PostMapping
    public ResponseEntity<PacienteDTO> registrar(@Valid @RequestBody PacienteDTO dtoRequest) throws Exception{
        Paciente p = mapper.map(dtoRequest, Paciente.class);
        Paciente obj = service.registrar(p);
        PacienteDTO dtoResponse = mapper.map(obj, PacienteDTO.class);
        return new ResponseEntity<>(dtoResponse, HttpStatus.CREATED);
    } */

    @PostMapping
    public ResponseEntity<Void> registrar(@Valid @RequestBody PacienteDTO dtoRequest) throws Exception {
        Paciente p = mapper.map(dtoRequest, Paciente.class);
        Paciente obj = service.registrar(p);
        PacienteDTO dtoResponse = mapper.map(obj, PacienteDTO.class);
        //localhost:8080/pacientes/9
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dtoResponse.getIdPaciente()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<PacienteDTO> modificar(@Valid @RequestBody PacienteDTO dtoRequest) throws Exception{
        Paciente pac = service.listarPorId(dtoRequest.getIdPaciente());
        if(pac ==null){
            throw new ModeloNotFoundException("ID NO ENCONTRADO" + dtoRequest.getIdPaciente());
        }
        Paciente p = mapper.map(dtoRequest, Paciente.class);
        Paciente obj = service.modificar(p);
        PacienteDTO dtoResponse = mapper.map(obj, PacienteDTO.class);
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable("id") Integer id) throws Exception{
        service.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<PacienteDTO> listarHateoas (@PathVariable("id") Integer id) throws Exception{
        Paciente obj = service.listarPorId(id);
        if(obj == null){
            throw new ModeloNotFoundException("ID NO ENCONTRADO" + id);
        }
        PacienteDTO dto = mapper.map(obj, PacienteDTO.class);

        EntityModel<PacienteDTO> recurso = EntityModel.of(dto);
        //localhost:8080/pacientes/1
        WebMvcLinkBuilder link1 = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).listarPorId(id));
        recurso.add(link1.withRel("paciente-link"));
        return recurso;
    }
}
