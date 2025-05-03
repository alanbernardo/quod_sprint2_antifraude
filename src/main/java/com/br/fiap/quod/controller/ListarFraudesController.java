package com.br.fiap.quod.controller;

import com.br.fiap.quod.model.notificacao.NotificacaoFraude;
import com.br.fiap.quod.repository.NotificacaoFraudeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fraudes")
public class ListarFraudesController {

    @Autowired
    private NotificacaoFraudeRepository repository;

    @GetMapping("/listar")
    public ResponseEntity<List<NotificacaoFraude>> listarTodasFraudes() {
        List<NotificacaoFraude> fraudes = repository.findAll();
        return ResponseEntity.ok(fraudes);
    }
}
