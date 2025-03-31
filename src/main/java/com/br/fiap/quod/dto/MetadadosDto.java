package com.br.fiap.quod.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MetadadosDto(

        //@NotNull
        Double latitude,

       // @NotNull
        Double logitude,

      //  @NotBlank
        String ipOrigem) {
}
