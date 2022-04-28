package com.mitocode.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mitocode.Service.ISignosService;
import com.mitocode.dto.PacienteDTO;
import com.mitocode.dto.SignosDTO;
import com.mitocode.exception.ModeloNotFoundException;
import com.mitocode.model.Signos;

@RestController
@RequestMapping("/signos")
public class SignosController {
	
	@Autowired
	private ISignosService service;
	
	@Autowired
	private ModelMapper mapper;
	
	@GetMapping
	//@RequestMapping(value = "/" , method = RequestMethod.GET)
	public ResponseEntity<List<SignosDTO>> listar() throws Exception {
		List<SignosDTO> lista = service.listar().stream().map(p -> mapper.map(p, SignosDTO.class)).collect(Collectors.toList());
		
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<SignosDTO> listarPorId(@PathVariable("id") Integer id) throws Exception {
		Signos obj = service.listarPorId(id);
		
		if(obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
						
		SignosDTO dto = mapper.map(obj, SignosDTO.class);
		
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	
	@PostMapping
	public ResponseEntity<Void> registrar(@Valid @RequestBody SignosDTO dto) throws Exception {
		Signos p = mapper.map(dto, Signos.class);
		Signos obj = service.registrar(p);
		
		//localhost:8080/pacientes/5
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdsignos()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	public ResponseEntity<SignosDTO> modificar(@Valid @RequestBody SignosDTO dto) throws Exception {
		Signos obj = service.listarPorId(dto.getIdSignos());
		
		if(obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + dto.getIdSignos());
		}
		
		Signos p = mapper.map(dto, Signos.class);		
		Signos pac = service.modificar(p);
		SignosDTO dtoResponse = mapper.map(pac, SignosDTO.class);
		return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
		Signos obj = service.listarPorId(id);
		
		if(obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		service.eliminar(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	//@ResponseStatus(HttpStatus.NOT_FOUND)
		@GetMapping("/hateoas/{id}")
		public EntityModel<SignosDTO> listarHateoas(@PathVariable("id") Integer id) throws Exception{
			Signos obj = service.listarPorId(id);
			
			if(obj == null) {
				throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
			}
			
			SignosDTO dto = mapper.map(obj, SignosDTO.class);
			
			EntityModel<SignosDTO> recurso = EntityModel.of(dto);
			//localhost:8080/pacientes/1
			WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).listarPorId(id));		
			recurso.add(link1.withRel("signos-info"));		
			return recurso;

		}
		
		@GetMapping("/pageable")
		public ResponseEntity<Page<SignosDTO>> listarPageable(Pageable page) throws Exception{
			Page<SignosDTO> signos = service.listarPageable(page).map(p -> mapper.map(p, SignosDTO.class));
			
			return new ResponseEntity<>(signos, HttpStatus.OK);
		}

}
