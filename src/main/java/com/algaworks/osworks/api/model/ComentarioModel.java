package com.algaworks.osworks.api.model;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class ComentarioModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String descricao;
	private OffsetDateTime dataEnvio;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public OffsetDateTime getDataEnvio() {
		return dataEnvio;
	}
	public void setDataEnvio(OffsetDateTime dataEnvio) {
		this.dataEnvio = dataEnvio;
	}
	
	
}
