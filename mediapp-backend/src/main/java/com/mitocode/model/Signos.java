package com.mitocode.model;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "signos")
public class Signos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idsignos;

	@ManyToOne
	@JoinColumn(name = "id_paciente", nullable = false, foreignKey = @ForeignKey(name = "FK_consulta_paciente"))
	private Paciente paciente; 
	
	@Column(name = "fecha", nullable = false)
	private LocalDateTime fecha;
	
	@Column(name = "temperatura", length = 3, nullable = false)
	private String temperatura;
	
	@Column(name = "pulso", length = 4, nullable = false)
	private String pulso;
	
	@Column(name = "ritmo", length = 4, nullable = false)
	private String ritmo;

	public Integer getIdsignos() {
		return idsignos;
	}

	public void setIdsignos(Integer idsignos) {
		this.idsignos = idsignos;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public String getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(String temperatura) {
		this.temperatura = temperatura;
	}

	public String getPulso() {
		return pulso;
	}

	public void setPulso(String pulso) {
		this.pulso = pulso;
	}

	public String getRitmo() {
		return ritmo;
	}

	public void setRitmo(String ritmo) {
		this.ritmo = ritmo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idsignos);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Signos other = (Signos) obj;
		return Objects.equals(idsignos, other.idsignos);
	}
	
	
}
