package com.br.fiap.quod.model.biometriafacial;

import com.br.fiap.quod.model.dados.Metadados;
import com.br.fiap.quod.model.dispositivos.Dispositivo;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Document(collation = "biometriaFacial")
public class BiometriaFacial {

    @Id
    private Long idImagem;
    private byte[] imagemFacial;
    private LocalDateTime dataCaptura;
    private Boolean validacao;
    private Metadados metadados;
    private Dispositivo dispositivo;

    public BiometriaFacial (){}

    public BiometriaFacial(Dispositivo dispositivo, Metadados metadados, Boolean validacao, LocalDateTime dataCaptura, byte[] imagemFacial, Long idImagem) {
        this.dispositivo = dispositivo;
        this.metadados = metadados;
        this.validacao = validacao;
        this.dataCaptura = dataCaptura;
        this.imagemFacial = imagemFacial;
        this.idImagem = idImagem;
    }

    public Long getIdImagem() {
        return idImagem;
    }

    public void setIdImagem(Long idImagem) {
        this.idImagem = idImagem;
    }

    public byte[] getImagemFacial() {
        return imagemFacial;
    }

    public void setImagemFacial(byte[] imagemFacial) {
        this.imagemFacial = imagemFacial;
    }

    public LocalDateTime getDataCaptura() {
        return dataCaptura;
    }

    public Dispositivo getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }

    public void setDataCaptura(LocalDateTime dataCaptura) {
        this.dataCaptura = dataCaptura;
    }

    public Boolean getValidacao() {
        return validacao;
    }

    public void setValidacao(Boolean validacao) {
        this.validacao = validacao;
    }

    public Metadados getMetadados() {
        return metadados;
    }

    public void setMetadados(Metadados metadados) {
        this.metadados = metadados;
    }

}
