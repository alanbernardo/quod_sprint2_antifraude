package com.br.fiap.quod.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BiometriaDigitalDTO(

        @NotNull Long idDigital,
        @NotBlank String templateDigital) {

}
