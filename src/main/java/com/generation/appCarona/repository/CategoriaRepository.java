package com.generation.appCarona.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.generation.appCarona.model.Veiculo;

@Repository
public  interface CategoriaRepository extends JpaRepository<Veiculo, Long>{
	List<Veiculo> findAllByNomeContainingIgnoreCase(String nome);
	
}
