package com.generation.appCarona.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.appCarona.model.Viagem;
import com.generation.appCarona.repository.VeiculoRepository;
import com.generation.appCarona.repository.ViagemRepository;
import com.generation.appCarona.service.ViagemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/viagens")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class ViagemController {

	
	@Autowired
	private ViagemRepository viagemRepository;
	
	@Autowired
	private VeiculoRepository veiculoRepository;
	
	
	@GetMapping
	public ResponseEntity<List<Viagem>> getAll(){
		return ResponseEntity.ok(viagemRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Viagem> getById(@PathVariable Long id){
		return viagemRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	
	@GetMapping("/destino/{destino}")
	public ResponseEntity<List<Viagem>> getByTitulo(@PathVariable String destino){
		return ResponseEntity.ok(viagemRepository.
				findAllByDestinoContainingIgnoreCase(destino));
	}

	@PostMapping
	public ResponseEntity<Viagem> post(@Valid @RequestBody Viagem viagem){
		if (veiculoRepository.existsById(viagem.getVeiculo().getId()))
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(viagemRepository.save(viagem));
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Veículo não existe!",null);
	}
	
	@PutMapping
	public ResponseEntity<Viagem> put(@Valid @RequestBody Viagem viagem){
		if (viagemRepository.existsById(viagem.getId())) {
			
			if (veiculoRepository.existsById(viagem.getVeiculo().getId()))
				return ResponseEntity.status(HttpStatus.OK)
						.body(viagemRepository.save(viagem));
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Veículo não existe", null);
			
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	// calculo horas e distancia
	
	@RestController
	@RequestMapping("/carona")
	public class CaronaController {

	    @Autowired
	    private ViagemService viagemService;

	    @GetMapping("/calcular-tempo")
	    public double calcularTempo(
	            @RequestParam double distancia,
	            @RequestParam double velocidade) {
	        return viagemService.calcularTempoViagem(distancia, velocidade);
	    }
	}
	
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}") 
	public void delete(@PathVariable Long id) { 
		
		Optional<Viagem> viagem = viagemRepository.findById(id);
		
		if(viagem.isEmpty()) 
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		viagemRepository.deleteById(id);
	}
}
