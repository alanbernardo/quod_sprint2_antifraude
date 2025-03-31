package com.br.fiap.quod.controller;

import com.br.fiap.quod.service.BiometriaFacialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping("/api/biometriaFacial")
public class BiometriaFacialController {

    @Autowired
    private BiometriaFacialService facialService;

    @PostMapping(value = "/validar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> validacaoFacial(@RequestParam("file") MultipartFile file) {
        try {
            // Converte MultipartFile para BufferedImage (pode lançar IOException ou IllegalArgumentException)
            BufferedImage imagem = facialService.converterParaBufferedImage(file);

            // Validações
            boolean ehDeepfake = facialService.detectarDeepfake(imagem);
            boolean temMascara = facialService.detectarMascara(imagem);
            boolean ehFotoDeFoto = facialService.verificarContrasteBaixo(imagem);

            if (ehDeepfake) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Imagem inválida: Possível deepfake detectado!");
            }
            if (temMascara) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Imagem inválida: Possível uso de máscara detectado!");
            }
            if (ehFotoDeFoto) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Imagem inválida: Parece ser uma foto de uma foto!");
            }

            return ResponseEntity.ok("Imagem válida para biometria facial.");

        } catch (IllegalArgumentException e) {
            // Captura erros de arquivo inválido (ex.: não é uma imagem)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IOException e) {
            // Captura erros de leitura do arquivo
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar a imagem.");
        }
    }
}