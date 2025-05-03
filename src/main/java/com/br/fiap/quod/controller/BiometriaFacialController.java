package com.br.fiap.quod.controller;

import com.br.fiap.quod.dto.MetadadosDto;
import com.br.fiap.quod.service.BiometriaFacialService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping("/api/biometria")
public class BiometriaFacialController {

    @Autowired
    private BiometriaFacialService facialService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping(value = "/facial", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> validarFacial(@RequestPart("file") MultipartFile file,
                                                @RequestPart(value = "metadados", required = false) String metadados) {
        try {
            // Se for enviado metadados, pode usar no futuro (atualmente é ignorado)
            if (metadados != null && !metadados.isEmpty()) {
                MetadadosDto metadadosDto = objectMapper.readValue(metadados, MetadadosDto.class);
            }

            BufferedImage imagem = facialService.converterParaBufferedImage(file);

            String resultadoValidacao = validarImagem(imagem);
            if (resultadoValidacao != null) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(resultadoValidacao);
            }

            return ResponseEntity.ok("Imagem válida para biometria facial.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar a imagem.");
        }
    }

    private String validarImagem(BufferedImage imagem) {
        if (facialService.detectarDeepfake(imagem)) {
            return "Imagem inválida: Possível deepfake detectado!";
        }
        if (facialService.detectarMascara(imagem)) {
            return "Imagem inválida: Possível uso de máscara detectado!";
        }
        if (facialService.verificarContrasteBaixo(imagem)) {
            return "Imagem inválida: Parece ser uma foto de uma foto!";
        }
        return null;
    }
}



