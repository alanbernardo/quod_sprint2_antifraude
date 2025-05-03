package com.br.fiap.quod.service;

import com.br.fiap.quod.repository.BiometriaDigitalRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BiometriaDigitalService {

    private BiometriaDigitalRepository digitalRepositry;

    public Map<String, String> validarTemplate(String templateDigital) {
        Map<String, String> resultado = new HashMap<>();

        if (templateDigital == null || templateDigital.isEmpty()) {
            resultado.put("status", "fraude");
            resultado.put("motivo", "Template vazio ou inexistente");
            return resultado;
        }

        byte[] decodedBytes;
        try {
            decodedBytes = Base64.getDecoder().decode(templateDigital);
        } catch (IllegalArgumentException e) {
            resultado.put("status", "fraude");
            resultado.put("motivo", "Template não está em Base64 válido");
            return resultado;
        }

        // Validação de tamanho mínimo e máximo (simulando padrão real de templates biométricos)
        if (decodedBytes.length < 100 || decodedBytes.length > 10000) {
            resultado.put("status", "fraude");
            resultado.put("motivo", "Template fora do padrão de tamanho esperado (100 a 10000 bytes)");
            return resultado;
        }

        // Verificação de entropia: conteúdo muito repetitivo é considerado suspeito
        Set<Byte> unicos = new HashSet<>();
        for (byte b : decodedBytes) {
            unicos.add(b);
        }

        double taxaUnicos = (double) unicos.size() / decodedBytes.length;

        if (taxaUnicos < 0.1) {
            resultado.put("status", "fraude");
            resultado.put("motivo", "Template com padrão repetitivo suspeito");
            return resultado;
        }

        // Simulação adicional: verificar se o conteúdo parece um template biométrico (simples)
        if (decodedBytes[0] == 0 || decodedBytes[1] == 0) {
            resultado.put("status", "fraude");
            resultado.put("motivo", "Template contém dados iniciais inconsistentes");
            return resultado;
        }

        resultado.put("status", "valida");
        resultado.put("motivo", "Template biométrico dentro do padrão esperado");
        return resultado;
    }
}
