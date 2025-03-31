package com.br.fiap.quod.model.documentos;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Arrays;

@Document(collation = "documento")
public class Documento {

    @Id
    private Long idImagem;
    private byte[] imagemDocumento;
    private LocalDateTime dataCaptura;
    private Boolean validacao;

    public Documento(){}

    public Documento(byte[] imagemDocumento, LocalDateTime dataCaptura, Boolean validacao) {
        this.imagemDocumento = imagemDocumento;
        this.dataCaptura = dataCaptura;
        this.validacao = validacao;
    }

    public Long getIdImagem() {
        return idImagem;
    }

    public void setIdImagem(Long idImagem) {
        this.idImagem = idImagem;
    }

    public byte[] getImagemDocumento() {
        return imagemDocumento;
    }

    public void setImagemDocumento(byte[] imagemDocumento) {
        this.imagemDocumento = imagemDocumento;
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


}
