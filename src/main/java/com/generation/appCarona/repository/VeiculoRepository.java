package com.generation.appCarona.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.appCarona.model.Veiculo;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long>{
	List<Veiculo> findByVeiculoContainingIgnoreCase(String tipoVeiculo);
	
	
}
