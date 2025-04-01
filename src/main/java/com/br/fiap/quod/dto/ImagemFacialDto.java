package com.br.fiap.quod.dto;

import com.br.fiap.quod.model.dados.Metadados;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public record ImagemFacialDto(

        @NotNull MultipartFile imagemFacial,
        @NotNull @Valid MetadadosDto metadados) {
}
