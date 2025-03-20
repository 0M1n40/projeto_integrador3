package com.generation.appCarona.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.appCarona.model.Viagem;



public interface ViagemRepository extends JpaRepository<Viagem,Long>{
	
	public List<Viagem> 
	findAllByDestinoContainingIgnoreCase(@Param("Destino") String destino);

}
