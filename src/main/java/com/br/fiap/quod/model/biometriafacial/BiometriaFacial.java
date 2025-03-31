package com.br.fiap.quod.model.biometriafacial;

import com.br.fiap.quod.model.dados.Metadados;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Document(collation = "biometriaFacial")//define o nome da colecao no db
public class BiometriaFacial {

    @Id
    private Long idImagem;
    private byte[] imagemFacial;
    private LocalDateTime dataCaptura;
    private Boolean validacao;
    private Metadados metadados;

    public BiometriaFacial (){}

    public BiometriaFacial(byte[] imagemFacial, LocalDateTime dataCaptura, Boolean validacao, Metadados metadados) {
        this.imagemFacial = imagemFacial;
        this.dataCaptura = dataCaptura;
        this.validacao = validacao;
        this.metadados = metadados;
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
