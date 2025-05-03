package com.br.fiap.quod.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TesteController {

    @GetMapping("/api/hello")
    public String hello() {
        return "API funcionando!";
    }
}
