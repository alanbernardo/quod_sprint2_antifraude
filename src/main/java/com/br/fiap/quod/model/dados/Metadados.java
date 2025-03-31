package com.br.fiap.quod.model.dados;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "metadados")
public class Metadados {

    @Id
    private Long idMtDados;
    private Double latitude;
    private Double logitude;
    private String ipOrigem;

    public Metadados(){}

    public Metadados(Double latitude, Double logitude, String ipOrigem) {
        this.latitude = latitude;
        this.logitude = logitude;
        this.ipOrigem = ipOrigem;
    }

    public Long getIdMtDados() {
        return idMtDados;
    }

    public void setIdMtDados(Long idMtDados) {
        this.idMtDados = idMtDados;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLogitude() {
        return logitude;
    }

    public void setLogitude(Double logitude) {
        this.logitude = logitude;
    }

    public String getIpOrigem() {
        return ipOrigem;
    }

    public void setIpOrigem(String ipOrigem) {
        this.ipOrigem = ipOrigem;
    }

    @Override
    public String toString() {
        return "Metadados{" +
                "latitude=" + latitude +
                ", logitude=" + logitude +
                ", ipOrigem='" + ipOrigem + '\'' +
                '}';
    }
}
