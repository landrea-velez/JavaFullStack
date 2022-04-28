package com.mitocode.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mitocode.Service.ISignosService;
import com.mitocode.model.Paciente;
import com.mitocode.model.Signos;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.ISignosRepo;

@Service
public class SignosServiceImpl  extends CRUDImpl<Signos, Integer> implements ISignosService {
	
	@Autowired
	private ISignosRepo repo;

	@Override
	protected IGenericRepo<Signos, Integer> getRepo() {
		return repo;
	}
	
	@Override
	public Page<Signos> listarPageable(Pageable page) {
		return repo.findAll(page);
	}

}
