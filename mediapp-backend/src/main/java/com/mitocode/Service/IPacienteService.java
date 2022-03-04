package com.mitocode.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mitocode.model.Paciente;

public interface IPacienteService extends ICRUD<Paciente, Integer>{
	
	Page<Paciente> listarPageable(Pageable page);


}
