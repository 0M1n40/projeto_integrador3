package com.generation.appCarona.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.generation.appCarona.model.Categoria;

@Repository
public  interface CategoriaRepository extends JpaRepository<Categoria, Long>{
	List<Categoria> findAllByNomeContainingIgnoreCase(String nome);
	
}
