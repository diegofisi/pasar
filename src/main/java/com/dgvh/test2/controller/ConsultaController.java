package com.dgvh.test2.controller;

import com.dgvh.test2.dto.ConsultaDTO;
import com.dgvh.test2.dto.ConsultaListaExamenDTO;
import com.dgvh.test2.exception.ModeloNotFoundException;
import com.dgvh.test2.model.Consulta;
import com.dgvh.test2.model.Examen;
import com.dgvh.test2.service.IConsultaService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
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
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private IConsultaService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<ConsultaDTO>> listar() throws Exception{
        List<ConsultaDTO> lista = service.listar().stream().map(p -> mapper.map(p, ConsultaDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaDTO> listarPorId(@PathVariable("id") Integer id) throws Exception{
        ConsultaDTO dtoResponse;
        Consulta obj = service.listarPorId(id);
        if(obj == null){
            throw new ModeloNotFoundException("ID NO ENCONTRADO" + id);
        }else{
            dtoResponse = mapper.map(obj, ConsultaDTO.class);
        }
        return new ResponseEntity<>(dtoResponse,HttpStatus.OK);
    }

    /* @PostMapping
    public ResponseEntity<ConsultaDTO> registrar(@Valid @RequestBody ConsultaDTO dtoRequest) throws Exception{
        Consulta p = mapper.map(dtoRequest, Consulta.class);
        Consulta obj = service.registrar(p);
        ConsultaDTO dtoResponse = mapper.map(obj, ConsultaDTO.class);
        return new ResponseEntity<>(dtoResponse, HttpStatus.CREATED);
    } */

    @PostMapping
    public ResponseEntity<Void> registrar(@Valid @RequestBody ConsultaListaExamenDTO dtoRequest) throws Exception {

        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Consulta c = mapper.map(dtoRequest, Consulta.class);
        //Consulta c = mapper.map(dtoRequest.getConsulta(), Consulta.class);
        List<Examen> examenes = mapper.map(dtoRequest.getLstExamen(), new TypeToken<List<Examen>>() {}.getType());

        Consulta obj = service.registrarTransaccional(c, examenes);
        ConsultaDTO dtoResponse = mapper.map(obj, ConsultaDTO.class);
        //localhost:8080/Consultas/9
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dtoResponse.getIdConsulta()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<ConsultaDTO> modificar(@Valid @RequestBody ConsultaDTO dtoRequest) throws Exception{
        Consulta pac = service.listarPorId(dtoRequest.getIdConsulta());
        if(pac ==null){
            throw new ModeloNotFoundException("ID NO ENCONTRADO" + dtoRequest.getIdConsulta());
        }
        Consulta p = mapper.map(dtoRequest, Consulta.class);
        Consulta obj = service.modificar(p);
        ConsultaDTO dtoResponse = mapper.map(obj, ConsultaDTO.class);
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable("id") Integer id) throws Exception{
        service.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<ConsultaDTO> listarHateoas (@PathVariable("id") Integer id) throws Exception{
        Consulta obj = service.listarPorId(id);
        if(obj == null){
            throw new ModeloNotFoundException("ID NO ENCONTRADO" + id);
        }
        ConsultaDTO dto = mapper.map(obj, ConsultaDTO.class);

        EntityModel<ConsultaDTO> recurso = EntityModel.of(dto);
        //localhost:8080/Consultas/1
        WebMvcLinkBuilder link1 = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).listarPorId(id));
        recurso.add(link1.withRel("consulta-link"));
        return recurso;
    }
}
