package com.br.fiap.quod.model.biometriaDigital;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "biometriaDigital")
public class BiometriaDigital {

    private Long idDigital;
    private String digitalTamplate;

    public BiometriaDigital(String digitalTamplate) {
        this.digitalTamplate = digitalTamplate;
    }

    public String getDigitalTamplate() {
        return digitalTamplate;
    }

    public void setDigitalTamplate(String digitalTamplate) {
        this.digitalTamplate = digitalTamplate;
    }

    public Long getIdDigital() {
        return idDigital;
    }

    public void setIdDigital(Long idDigital) {
        this.idDigital = idDigital;
    }
}
