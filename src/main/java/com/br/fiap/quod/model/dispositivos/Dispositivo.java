package com.br.fiap.quod.model.dispositivos;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "dispositivos")
public class Dispositivo {

    @Id
    private Long ip;
    private String fabricante;
    private String modelo;
    private String sistemaOperacional;

    public Dispositivo(){}

    public Dispositivo(String fabricante, String modelo, String sistemaOperacional) {
        this.fabricante = fabricante;
        this.modelo = modelo;
        this.sistemaOperacional = sistemaOperacional;
    }

    public Long getIp() {
        return ip;
    }

    public void setIp(Long ip) {
        this.ip = ip;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getSistemaOperacional() {
        return sistemaOperacional;
    }

    public void setSistemaOperacional(String sistemaOperacional) {
        this.sistemaOperacional = sistemaOperacional;
    }

    @Override
    public String toString() {
        return "Dispositivo{" +
                "fabricante='" + fabricante + '\'' +
                ", modelo='" + modelo + '\'' +
                ", sistemaOperacional='" + sistemaOperacional + '\'' +
                '}';
    }
}
