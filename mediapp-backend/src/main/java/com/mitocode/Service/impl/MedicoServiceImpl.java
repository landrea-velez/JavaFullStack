package com.mitocode.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitocode.Service.IMedicoService;
import com.mitocode.model.Medico;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.IMedicoRepo;

@Service
public class MedicoServiceImpl extends CRUDImpl<Medico, Integer> implements IMedicoService {

	@Autowired
	private IMedicoRepo repo;

	@Override
	protected IGenericRepo<Medico, Integer> getRepo() {
		return repo;
	}

}