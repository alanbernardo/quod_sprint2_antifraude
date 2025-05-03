package com.br.fiap.quod.model.documentos;

import com.br.fiap.quod.model.dados.Metadados;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Arrays;

@Document(collection = "documento")
public class Documento {

    @Id
    private Long idImagem;
    private byte[] imagemDocumento;
    private LocalDateTime dataCaptura;
    private Boolean validacao;
    private Metadados metadados;
    private String cpf;
    private String rg;
    private String nome;
    private String textoExtraido;

    public Documento() {}

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

    public Metadados getMetadados() {
        return metadados;
    }

    public void setMetadados(Metadados metadados) {
        this.metadados = metadados;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTextoExtraido() {
        return textoExtraido;
    }

    public void setTextoExtraido(String textoExtraido) {
        this.textoExtraido = textoExtraido;
    }


}
