package com.br.fiap.quod.dto;

import jakarta.validation.constraints.NotBlank;

public record DocumentoDto(

         String nome,
         String cpf,
         String rg,
         String textoExtraido) {
}
