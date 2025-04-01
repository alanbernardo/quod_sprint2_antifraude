package com.br.fiap.quod.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MetadadosDto(
        @NotNull @JsonProperty("latitude") Double latitude,
        @NotNull @JsonProperty("longitude") Double longitude,
        @NotBlank @JsonProperty("ipOrigem") String ipOrigem) {

        @Override
        public String toString() {
                return "MetadadosDto{" +
                        "latitude=" + latitude +
                        ", longitude=" + longitude +
                        ", ipOrigem='" + ipOrigem + '\'' +
                        '}';
        }

        public MetadadosDto test(){
                if (latitude == null){
                        System.out.println("latitude null");
                }
                if (longitude == null){
                        System.out.println("longitude null");
                }
                if (ipOrigem == null){
                        System.out.println("ip null");
                }
                return null;
        }
}
