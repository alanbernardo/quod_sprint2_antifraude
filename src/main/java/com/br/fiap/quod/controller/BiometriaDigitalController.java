package com.br.fiap.quod.controller;

import com.br.fiap.quod.dto.BiometriaDigitalDTO;
import com.br.fiap.quod.service.BiometriaDigitalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/biometria/digital") // ✅ Corrigido: endpoint usado no Postman
public class BiometriaDigitalController {

    @Autowired
    private BiometriaDigitalService biometriaService;

    @PostMapping
    public ResponseEntity<Map<String, String>> validarTemplate(@RequestBody @Valid BiometriaDigitalDTO dto) {
        Map<String, String> resultado = biometriaService.validarTemplate(dto.templateDigital());
        return ResponseEntity.ok(resultado);
    }
}
