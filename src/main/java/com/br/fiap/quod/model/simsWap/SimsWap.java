package com.br.fiap.quod.model.simsWap;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collation = "simsWap")
public class SimsWap {

    @Id
    private String transacaoId;
    private String operadora;
    private LocalDateTime dataTroca;
    private String status;

    public SimsWap(){}

    public SimsWap(String transacaoId, String operadora, LocalDateTime dataTroca, String status) {
        this.transacaoId = transacaoId;
        this.operadora = operadora;
        this.dataTroca = dataTroca;
        this.status = status;
    }

    public String getTransacaoId() {
        return transacaoId;
    }

    public void setTransacaoId(String transacaoId) {
        this.transacaoId = transacaoId;
    }

    public String getOperadora() {
        return operadora;
    }

    public void setOperadora(String operadora) {
        this.operadora = operadora;
    }

    public LocalDateTime getDataTroca() {
        return dataTroca;
    }

    public void setDataTroca(LocalDateTime dataTroca) {
        this.dataTroca = dataTroca;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String
    toString() {
        return "SimsWap{" +
                "transacaoId='" + transacaoId + '\'' +
                ", operadora='" + operadora + '\'' +
                ", dataTroca=" + dataTroca +
                ", status='" + status + '\'' +
                '}';
    }
}
