package com.br.fiap.quod.controller;

import com.br.fiap.quod.dto.DocumentoDto;
import com.br.fiap.quod.model.dados.Metadados;
import com.br.fiap.quod.model.documentos.Documento;
import com.br.fiap.quod.repository.DocumentoRepository;
import com.br.fiap.quod.service.DocumentoService;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/documento")
public class DocumentoController {

    @Autowired
    private DocumentoService documentoService;

    @Autowired
    private DocumentoRepository documentoRepository;

    @PostMapping("/validar")
    public ResponseEntity<DocumentoDto> validarDocumento(
            @RequestParam("file") MultipartFile file,
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude,
            @RequestParam("ip") String ipOrigem
    ) throws IOException, TesseractException {
        Metadados metadados = new Metadados(latitude, longitude, ipOrigem);
        DocumentoDto response = documentoService.validarDocumento(file, metadados);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/validados")
    public ResponseEntity<List<Documento>> listarValidados() {
        return ResponseEntity.ok(documentoRepository.findByValidacaoTrue());
    }
}

