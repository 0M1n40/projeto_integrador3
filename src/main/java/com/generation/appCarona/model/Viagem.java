package com.generation.appCarona.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Viagem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O atributo origem é obrigatório")
	@Size(min = 5, max = 200, message = "O origem tem que ser maior que 5 e menor que 200 caracteres")
	private String origem;
	
	@NotBlank(message = "O atributo destino é obrigatório")
	@Size(min = 5, max = 200, message = "O destino tem que ser maior que 5 e menor que 200 caracteres")
	private String destino;
	
	@NotNull(message = "O atributo vagas é obrigatório")
	private int vagas;
	
	@NotNull(message = "O atributo distância é obrigatório")
	private double distancia;
	
	@ManyToOne
	@JsonIgnoreProperties("viagem")
	private Veiculo veiculo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public int getVagas() {
		return vagas;
	}

	public void setVagas(int vagas) {
		this.vagas = vagas;
	}

	public double getDistancia() {
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}
	
}
