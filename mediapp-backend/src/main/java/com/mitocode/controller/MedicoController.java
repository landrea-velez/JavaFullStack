package com.mitocode.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.mitocode.Service.IMedicoService;
import com.mitocode.dto.PacienteDTO;
import com.mitocode.exception.ModeloNotFoundException;
import com.mitocode.model.Medico;;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

	@Autowired
	private IMedicoService service;

	@Autowired
	private ModelMapper mapper;

	@GetMapping
	// @RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<List<PacienteDTO>> listar() throws Exception {
		List<PacienteDTO> lista = service.listar().stream().map(p -> mapper.map(p, PacienteDTO.class))
				.collect(Collectors.toList());

		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PacienteDTO> listarPorId(@PathVariable("id") Integer id) throws Exception {
		Medico medico = service.listarPorId(id);
		if (medico == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO" + id);
		}

		PacienteDTO dto = mapper.map(medico, PacienteDTO.class);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Void> registrar(@Valid @RequestBody PacienteDTO dto) throws Exception {
		Medico p = mapper.map(dto, Medico.class);
		Medico medico = service.registrar(p);
		// localhost:8080/pacientes/5
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(medico.getIdMedico()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping
	public ResponseEntity<PacienteDTO> modificar(@Valid @RequestBody PacienteDTO dto) throws Exception {
		Medico medico = service.listarPorId(dto.getIdPaciente());

		if (medico == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO" + dto.getIdPaciente());
		}

		Medico p = mapper.map(dto, Medico.class);
		Medico pac = service.modificar(p);
		PacienteDTO dtoResponse = mapper.map(pac, PacienteDTO.class);

		return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
		Medico medico = service.listarPorId(id);

		if (medico == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO" + id);
		}
		service.eliminar(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/hateoas/{id}")
	public EntityModel<PacienteDTO> listarHateoas(@PathVariable("id") Integer id) throws Exception {
		Medico medico = service.listarPorId(id);

		if (medico == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO" + id);
		}

		PacienteDTO dto = mapper.map(medico, PacienteDTO.class);

		EntityModel<PacienteDTO> recurso = EntityModel.of(dto);

		WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).listarPorId(id));
		WebMvcLinkBuilder link2 = linkTo(methodOn(MedicoController.class).listarPorId(id));

		recurso.add(link1.withRel("paciente-info"));
		recurso.add(link2.withRel("medico-info"));

		return recurso;
	}

}
