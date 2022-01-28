package com.mitocode.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "consulta_examen")
@IdClass(ConsultaExamenPK.class)
public class ConsultaExamen {

	@Id
	private Consulta idConsulta;

	@Id
	private Examen idExamen;

	public Consulta getIdConsulta() {
		return idConsulta;
	}

	public void setIdConsulta(Consulta idConsulta) {
		this.idConsulta = idConsulta;
	}

	public Examen getIdExamen() {
		return idExamen;
	}

	public void setIdExamen(Examen idExamen) {
		this.idExamen = idExamen;
	}

}
