package com.generation.appCarona.service;
import org.springframework.stereotype.Service;

@Service
public class ViagemService {

	    public double calcularTempoViagem(double distanciaKm, double velocidadeMediaKmH) {
	        if (velocidadeMediaKmH <= 0) {
	            throw new IllegalArgumentException("A velocidade média deve ser maior que zero.");
	        }
	        return distanciaKm / velocidadeMediaKmH;
	    }
	}

