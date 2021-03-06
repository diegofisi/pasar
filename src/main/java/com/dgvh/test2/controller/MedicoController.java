package com.dgvh.test2.controller;

import com.dgvh.test2.dto.MedicoDTO;
import com.dgvh.test2.exception.ModeloNotFoundException;
import com.dgvh.test2.model.Medico;
import com.dgvh.test2.service.IMedicoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private IMedicoService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<MedicoDTO>> listar() throws Exception{
        List<MedicoDTO> lista = service.listar().stream().map(p -> mapper.map(p, MedicoDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicoDTO> listarPorId(@PathVariable("id") Integer id) throws Exception{
        MedicoDTO dtoResponse;
        Medico obj = service.listarPorId(id);
        if(obj == null){
            throw new ModeloNotFoundException("ID NO ENCONTRADO" + id);
        }else{
            dtoResponse = mapper.map(obj, MedicoDTO.class);
        }
        return new ResponseEntity<>(dtoResponse,HttpStatus.OK);
    }

    /* @PostMapping
    public ResponseEntity<MedicoDTO> registrar(@Valid @RequestBody MedicoDTO dtoRequest) throws Exception{
        Medico p = mapper.map(dtoRequest, Medico.class);
        Medico obj = service.registrar(p);
        MedicoDTO dtoResponse = mapper.map(obj, MedicoDTO.class);
        return new ResponseEntity<>(dtoResponse, HttpStatus.CREATED);
    } */

    @PostMapping
    public ResponseEntity<Void> registrar(@Valid @RequestBody MedicoDTO dtoRequest) throws Exception {
        Medico p = mapper.map(dtoRequest, Medico.class);
        Medico obj = service.registrar(p);
        MedicoDTO dtoResponse = mapper.map(obj, MedicoDTO.class);
        //localhost:8080/Medicos/9
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dtoResponse.getIdMedico()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<MedicoDTO> modificar(@Valid @RequestBody MedicoDTO dtoRequest) throws Exception{
        Medico pac = service.listarPorId(dtoRequest.getIdMedico());
        if(pac ==null){
            throw new ModeloNotFoundException("ID NO ENCONTRADO" + dtoRequest.getIdMedico());
        }
        Medico p = mapper.map(dtoRequest, Medico.class);
        Medico obj = service.modificar(p);
        MedicoDTO dtoResponse = mapper.map(obj, MedicoDTO.class);
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable("id") Integer id) throws Exception{
        service.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<MedicoDTO> listarHateoas (@PathVariable("id") Integer id) throws Exception{
        Medico obj = service.listarPorId(id);
        if(obj == null){
            throw new ModeloNotFoundException("ID NO ENCONTRADO" + id);
        }
        MedicoDTO dto = mapper.map(obj, MedicoDTO.class);

        EntityModel<MedicoDTO> recurso = EntityModel.of(dto);
        //localhost:8080/Medicos/1
        WebMvcLinkBuilder link1 = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).listarPorId(id));
        recurso.add(link1.withRel("medico-link"));
        return recurso;
    }
}
