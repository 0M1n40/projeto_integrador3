package com.generation.appCarona.service;

import org.springframework.stereotype.Service;

@Service
public class ViagemService {

    private static final double VELOCIDADE_PADRAO = 60.0; // Velocidade padrão em km/h

    public String calcularTempo(double distancia) {
        if (distancia <= 0) {
            return "Distância inválida. Informe um valor maior que zero.";
        }

        double tempoHoras = distancia / VELOCIDADE_PADRAO;
        int horas = (int) tempoHoras;
        int minutos = (int) ((tempoHoras - horas) * 60);

        return String.format("%d horas e %d minutos", horas, minutos);
    }
}
