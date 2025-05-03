package com.br.fiap.quod.service;

import com.br.fiap.quod.model.dados.Metadados;
import net.sourceforge.tess4j.TesseractException;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DocumentoServiceTest {

    private DocumentoService documentoService = new DocumentoService();

    @Test
    void deveFalharSeArquivoForVazio() {
        MockMultipartFile file = new MockMultipartFile("arquivo", "", "image/jpeg", new byte[0]);
        Metadados metadados = new Metadados();

        Exception exception = assertThrows(IOException.class, () -> {
            documentoService.validarDocumento(file, metadados);
        });

        assertTrue(exception.getMessage().contains("vazio"));
    }
}
