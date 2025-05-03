package com.br.fiap.quod.service;

import com.br.fiap.quod.dto.DocumentoDto;
import com.br.fiap.quod.model.dados.Metadados;
import com.br.fiap.quod.model.documentos.Documento;
import com.br.fiap.quod.repository.DocumentoRepository;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DocumentoService {

    @Autowired
    private DocumentoRepository documentoRepository;

    public DocumentoDto validarDocumento(MultipartFile file, Metadados metadados) throws IOException, TesseractException {
        if (file == null || file.isEmpty()) {
            throw new IOException("Arquivo de documento está vazio ou não foi enviado.");
        }

        if (file.getSize() > 5 * 1024 * 1024) {
            throw new IOException("O arquivo excede o tamanho máximo permitido (5MB).");
        }

        String contentType = file.getContentType();
        String extension;

        if (contentType != null) {
            switch (contentType) {
                case "image/jpeg":
                    extension = ".jpg";
                    break;
                case "image/png":
                    extension = ".png";
                    break;
                case "image/tiff":
                    extension = ".tiff";
                    break;
                default:
                    throw new IOException("Tipo de arquivo não suportado: " + contentType);
            }
        } else {
            throw new IOException("Não foi possível determinar o tipo do arquivo.");
        }

        File tempFile = File.createTempFile("documento", extension);
        file.transferTo(tempFile);

        // Instanciando e configurando o Tesseract para Windows
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR");// Certifique-se de que este é o diretório correto
        tesseract.setLanguage("por"); // Requer o arquivo por.traineddata na pasta tessdata

        String textoExtraido = tesseract.doOCR(tempFile);

        String cpf = extrairCpf(textoExtraido);
        String rg = extrairRg(textoExtraido);
        String nome = extrairNome(textoExtraido);

        Documento doc = new Documento();
        doc.setImagemDocumento(file.getBytes());
        doc.setMetadados(metadados);
        doc.setDataCaptura(LocalDateTime.now());
        doc.setValidacao(cpf != null && rg != null && nome != null);
        doc.setTextoExtraido(textoExtraido);
        doc.setCpf(cpf);
        doc.setRg(rg);
        doc.setNome(nome);

        documentoRepository.save(doc);

        return new DocumentoDto(nome, cpf, rg, textoExtraido);
    }

    private String extrairCpf(String texto) {
        Matcher matcher = Pattern.compile("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}").matcher(texto);
        return matcher.find() ? matcher.group() : null;
    }

    private String extrairRg(String texto) {
        Matcher matcher = Pattern.compile("\\d{2}\\.\\d{3}\\.\\d{3}-[0-9Xx]").matcher(texto);
        return matcher.find() ? matcher.group() : null;
    }

    private String extrairNome(String texto) {
        Matcher matcher = Pattern.compile("(?i)nome\\s*[:\\-]?\\s*([A-Z\\s]+)").matcher(texto);
        return matcher.find() ? matcher.group(1).trim() : null;
    }
}

