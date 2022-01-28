package com.mitocode.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.mitocode.Service.IPacienteService;
import com.mitocode.model.Paciente;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

	@Autowired
	private IPacienteService service;

	@GetMapping
	// @RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<List<Paciente>> listar() throws Exception {
		List<Paciente> lista = service.listar();
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Paciente> listarPorId(@PathVariable("id") Integer id) throws Exception {
		Paciente paciente = service.listarPorId(id);
		return new ResponseEntity<>(paciente, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Void> registrar(@RequestBody Paciente p) throws Exception {
		Paciente paciente = service.registrar(p);
		//localhost:8080/pacientes/5
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(paciente.getIdPaciente()).toUri();
		return  ResponseEntity.created(location).build();
	}

	@PutMapping
	public ResponseEntity<Paciente> modificar(@RequestBody Paciente p) throws Exception {
		Paciente paciente = service.modificar(p);
		return new ResponseEntity<>(paciente, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
		service.eliminar(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
