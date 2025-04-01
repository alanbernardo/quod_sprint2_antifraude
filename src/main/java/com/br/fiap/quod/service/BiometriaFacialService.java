package com.br.fiap.quod.service;

import com.br.fiap.quod.model.biometriafacial.BiometriaFacial;
import com.br.fiap.quod.repository.BiometriaFacialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class BiometriaFacialService {

    @Autowired
    private BiometriaFacialRepository biometriaRepository;

    public BiometriaFacial salvarImagem(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Arquivo não pode ser nulo ou vazio.");
        }

        BiometriaFacial biometria = new BiometriaFacial();
        biometria.setImagemFacial(file.getBytes());
        biometria.setDataCaptura(LocalDateTime.now());
        return biometriaRepository.save(biometria);
    }

    public BufferedImage converterParaBufferedImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Arquivo não pode ser nulo ou vazio.");
        }
        BufferedImage image = ImageIO.read(file.getInputStream());
        if (image == null) {
            throw new IllegalArgumentException("Arquivo não é uma imagem válida.");
        }
        return image;
    }

    public boolean detectarDeepfake(BufferedImage imagem) {
        int[][] pontos = {
                {imagem.getWidth() / 2, imagem.getHeight() / 3},
                {imagem.getWidth() / 3, imagem.getHeight() / 2},
                {2 * imagem.getWidth() / 3, imagem.getHeight() / 2},
                {imagem.getWidth() / 2, 2 * imagem.getHeight() / 3},
                {imagem.getWidth() / 2, 4 * imagem.getHeight() / 5}
        };

        int pontosSuspeitos = 0;
        for (int[] ponto : pontos) {
            int x = ponto[0], y = ponto[1];
            if (x >= 0 && x < imagem.getWidth() && y >= 0 && y < imagem.getHeight()) {
                Color cor = new Color(imagem.getRGB(x, y));
                double luminancia = (0.299 * cor.getRed() + 0.587 * cor.getGreen() + 0.114 * cor.getBlue());
                if (luminancia > 220 || luminancia < 30) {
                    pontosSuspeitos++;
                }
            }
        }
        return pontosSuspeitos >= 3;
    }

    public boolean detectarMascara(BufferedImage imagem) {
        int xInicio = imagem.getWidth() / 4;
        int yInicio = 2 * imagem.getHeight() / 3;
        int largura = imagem.getWidth() / 2;
        int altura = imagem.getHeight() / 3;

        double variancia = calcularVarianciaRegiao(imagem, xInicio, yInicio, largura, altura);
        return variancia < 25;
    }

    public BufferedImage converterParaCinza(BufferedImage imagem) {
        BufferedImage imagemCinza = new BufferedImage(imagem.getWidth(), imagem.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = imagemCinza.getGraphics();
        g.drawImage(imagem, 0, 0, null);
        g.dispose();
        return imagemCinza;
    }

    public boolean verificarContrasteBaixo(BufferedImage imagem) {
        BufferedImage cinza = converterParaCinza(imagem);
        int diferencaTotal = 0, amostras = 0;

        for (int x = 10; x < cinza.getWidth() - 10; x += 5) {
            for (int y = 10; y < cinza.getHeight() - 10; y += 5) {
                int centro = new Color(cinza.getRGB(x, y)).getRed();
                int vizinho = new Color(cinza.getRGB(x + 5, y + 5)).getRed();
                diferencaTotal += Math.abs(centro - vizinho);
                amostras++;
            }
        }

        double contrasteMedio = (double) diferencaTotal / amostras;
        return contrasteMedio < 10;
    }

    private double calcularVarianciaRegiao(BufferedImage img, int x, int y, int largura, int altura) {
        double soma = 0, somaQuadrados = 0;
        int pixels = 0;

        for (int dy = 0; dy < altura; dy++) {
            for (int dx = 0; dx < largura; dx++) {
                if (x + dx < img.getWidth() && y + dy < img.getHeight()) {
                    Color cor = new Color(img.getRGB(x + dx, y + dy));
                    double luminancia = 0.299 * cor.getRed() + 0.587 * cor.getGreen() + 0.114 * cor.getBlue();
                    soma += luminancia;
                    somaQuadrados += luminancia * luminancia;
                    pixels++;
                }
            }
        }

        double media = soma / pixels;
        return (somaQuadrados / pixels) - (media * media);
    }
}
