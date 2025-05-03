package com.br.fiap.quod.model.notificacao;

import com.br.fiap.quod.model.dados.Metadados;
import com.br.fiap.quod.model.dispositivos.Dispositivo;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "notificacaoFraude")
public class NotificacaoFraude {

    @Id
    private String transacaoId;
    private String tipoBiometria;
    private String tipoFraude;
    private LocalDateTime dataCaptura;
    private Dispositivo dispositivo;
    private List<String> canalNotificacao;
    private String notificacao;
    private Metadados metadados;

    public NotificacaoFraude() {}

    public NotificacaoFraude(String transacaoId, String tipoBiometria, String tipoFraude,
                             LocalDateTime dataCaptura, Dispositivo dispositivo,
                             List<String> canalNotificacao, String notificacao, Metadados metadados) {
        this.transacaoId = transacaoId;
        this.tipoBiometria = tipoBiometria;
        this.tipoFraude = tipoFraude;
        this.dataCaptura = dataCaptura;
        this.dispositivo = dispositivo;
        this.canalNotificacao = canalNotificacao;
        this.notificacao = notificacao;
        this.metadados = metadados;
    }

    public String getTransacaoId() {
        return transacaoId;
    }

    public void setTransacaoId(String transacaoId) {
        this.transacaoId = transacaoId;
    }

    public String getTipoBiometria() {
        return tipoBiometria;
    }

    public void setTipoBiometria(String tipoBiometria) {
        this.tipoBiometria = tipoBiometria;
    }

    public String getTipoFraude() {
        return tipoFraude;
    }

    public void setTipoFraude(String tipoFraude) {
        this.tipoFraude = tipoFraude;
    }

    public LocalDateTime getDataCaptura() {
        return dataCaptura;
    }

    public void setDataCaptura(LocalDateTime dataCaptura) {
        this.dataCaptura = dataCaptura;
    }

    public Dispositivo getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }

    public List<String> getCanalNotificacao() {
        return canalNotificacao;
    }

    public void setCanalNotificacao(List<String> canalNotificacao) {
        this.canalNotificacao = canalNotificacao;
    }

    public String getNotificacao() {
        return notificacao;
    }

    public void setNotificacao(String notificacao) {
        this.notificacao = notificacao;
    }

    public Metadados getMetadados() {
        return metadados;
    }

    public void setMetadados(Metadados metadados) {
        this.metadados = metadados;
    }

    @Override
    public String toString() {
        return "NotificacaoFraude{" +
                "transacaoId='" + transacaoId + '\'' +
                ", tipoBiometria='" + tipoBiometria + '\'' +
                ", tipoFraude='" + tipoFraude + '\'' +
                ", dataCaptura=" + dataCaptura +
                ", dispositivo=" + dispositivo +
                ", canalNotificacao=" + canalNotificacao +
                ", notificacao='" + notificacao + '\'' +
                ", metadados=" + metadados +
                '}';
    }
}