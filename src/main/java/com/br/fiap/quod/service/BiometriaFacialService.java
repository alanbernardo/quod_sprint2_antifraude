package com.br.fiap.quod.service;

import com.br.fiap.quod.model.biometriafacial.BiometriaFacial;
import com.br.fiap.quod.repository.BiometriaFacialRepository;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BiometriaFacialService {

    @Autowired
    private BiometriaFacialRepository BiometriaRepository;

    public BiometriaFacial salvarImagem(MultipartFile file) throws IOException {
        BiometriaFacial biometria = new BiometriaFacial();
        biometria.setImagemFacial(file.getBytes()); // converte em byte
        biometria.setDataCaptura(LocalDateTime.now());
        return BiometriaRepository.save(biometria);
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
        // Analisa apenas 5 pontos estratégicos do rosto
        int[][] pontos = {
                {imagem.getWidth()/2, imagem.getHeight()/3},  // testa
                {imagem.getWidth()/3, imagem.getHeight()/2},  // olho esquerdo
                {2*imagem.getWidth()/3, imagem.getHeight()/2}, // olho direito
                {imagem.getWidth()/2, 2*imagem.getHeight()/3}, // nariz
                {imagem.getWidth()/2, 4*imagem.getHeight()/5}  // boca
        };

        int pontosSuspeitos = 0;

        for (int[] ponto : pontos) {
            int x = ponto[0];
            int y = ponto[1];

            if (x >= 0 && x < imagem.getWidth() && y >= 0 && y < imagem.getHeight()) {
                Color cor = new Color(imagem.getRGB(x, y));
                double luminancia = (0.299 * cor.getRed() + 0.587 * cor.getGreen() + 0.114 * cor.getBlue());

                // Verifica padrões anormais de cor
                if (luminancia > 220 || luminancia < 30) {
                    pontosSuspeitos++;
                }
            }
        }

        return pontosSuspeitos >= 3; // Se 3+ pontos forem suspeitos
    }

    public boolean detectarMascara(BufferedImage imagem) {
        // Analisa a região do queixo (onde máscaras físicas são mais visíveis)
        int xInicio = imagem.getWidth()/4;
        int yInicio = 2*imagem.getHeight()/3;
        int largura = imagem.getWidth()/2;
        int altura = imagem.getHeight()/3;

        double variancia = calcularVarianciaRegiao(imagem, xInicio, yInicio, largura, altura);

        return variancia < 25; // Limiar mais generoso
    }


    public BufferedImage converterParaCinza(BufferedImage imagem) {
        BufferedImage imagemCinza = new BufferedImage(imagem.getWidth(), imagem.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = imagemCinza.getGraphics();
        g.drawImage(imagem, 0, 0, null);
        g.dispose();
        return imagemCinza;
    }

    public boolean verificarContrasteBaixo(BufferedImage imagem) {
        // Converte para escala de cinza
        BufferedImage cinza = new BufferedImage(imagem.getWidth(), imagem.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = cinza.getGraphics();
        g.drawImage(imagem, 0, 0, null);
        g.dispose();

        // Analisa apenas as bordas da imagem
        int diferencaTotal = 0;
        int amostras = 0;

        for (int x = 10; x < cinza.getWidth()-10; x += 5) {
            for (int y = 10; y < cinza.getHeight()-10; y += 5) {
                int centro = new Color(cinza.getRGB(x, y)).getRed();
                int vizinho = new Color(cinza.getRGB(x+5, y+5)).getRed();
                diferencaTotal += Math.abs(centro - vizinho);
                amostras++;
            }
        }

        double contrasteMedio = diferencaTotal / (double)amostras;
        return contrasteMedio < 10; // Limiar ajustado
    }

    private double calcularMediaRegiao(BufferedImage img, int x, int y, int tamanho) {
        long soma = 0;
        int pixels = 0;

        for (int dy = 0; dy < tamanho; dy++) {
            for (int dx = 0; dx < tamanho; dx++) {
                if (x+dx < img.getWidth() && y+dy < img.getHeight()) {
                    Color cor = new Color(img.getRGB(x+dx, y+dy));
                    soma += cor.getRed() + cor.getGreen() + cor.getBlue();
                    pixels++;
                }
            }
        }

        return pixels > 0 ? (double)soma/(pixels*3) : 0;
    }

    private double calcularVarianciaRegiao(BufferedImage img, int x, int y, int tamanho, double media) {
        double somaQuadrados = 0;
        int pixels = 0;

        for (int dy = 0; dy < tamanho; dy++) {
            for (int dx = 0; dx < tamanho; dx++) {
                if (x+dx < img.getWidth() && y+dy < img.getHeight()) {
                    Color cor = new Color(img.getRGB(x+dx, y+dy));
                    double luminancia = 0.299*cor.getRed() + 0.587*cor.getGreen() + 0.114*cor.getBlue();
                    somaQuadrados += Math.pow(luminancia - media, 2);
                    pixels++;
                }
            }
        }

        return pixels > 0 ? somaQuadrados/pixels : 0;
    }
}


/*public boolean detectarDeepfake(BufferedImage imagem) {
        long soma = 0;
        int contador = 0;
        int maxX = Math.min(imagem.getWidth(), 20); // Aumentei para 20x20 pixels para análise mais precisa
        int maxY = Math.min(imagem.getHeight(), 20);

        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                int rgb = imagem.getRGB(x, y);

                int r = (rgb >> 16) & 0xff;
                int g = (rgb >> 8) & 0xff;
                int b = rgb & 0xff;
                soma += (r + g + b);
                contador++;
            }
        }
        double media = (double) soma / contador;
        return media > 240; // Ajustei o limiar para 220 (era 200) testando com 240
    }

    public boolean detectarMascara(BufferedImage imagem) {
        // aqui define a região central da ft para analisar
        int centroX = imagem.getWidth() / 2;
        int centroY = imagem.getHeight() / 2;
        int area = 30; // Aumentei a área para 30 pixels (era 20)

        List<Integer> valoresLuminancia = new ArrayList<>();

        // Percorre uma área central de 60x60 pixels (de centro - area até centro + area)
        for (int y = centroY - area; y < centroY + area; y++) {
            for (int x = centroX - area; x < centroX + area; x++) {
                // Verifica se está dentro dos limites da imagem
                if (x >= 0 && x < imagem.getWidth() && y >= 0 && y < imagem.getHeight()) {
                    int rgb = imagem.getRGB(x, y);
                    int r = (rgb >> 16) & 0xff;
                    int g = (rgb >> 8) & 0xff;
                    int b = rgb & 0xff;
                    int luminancia = (int) (0.299 * r + 0.587 * g + 0.114 * b); // Fórmula de luminância mais precisa
                    valoresLuminancia.add(luminancia);
                }
            }
        }

        // Calcula a média dos valores de luminância
        double soma = 0;
        for (Integer valor : valoresLuminancia) {
            soma += valor;
        }
        double media = soma / valoresLuminancia.size();

        // Calcula a variância
        double somaVariancia = 0;
        for (Integer valor : valoresLuminancia) {
            somaVariancia += Math.pow(valor - media, 2);
        }
        double variancia = somaVariancia / valoresLuminancia.size();

        // Ajustei o limiar para 30 (era 50)
        return variancia < 20; // variancia<30=mascara variancia>30=n tm mascara testando com 20
    }

    public BufferedImage converterParaCinza(BufferedImage imagem) {
        BufferedImage imagemCinza = new BufferedImage(imagem.getWidth(), imagem.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = imagemCinza.getGraphics();
        g.drawImage(imagem, 0, 0, null);
        g.dispose();
        return imagemCinza;
    }

    public boolean verificarContrasteBaixo(BufferedImage imagem) {
        BufferedImage imagemCinza = converterParaCinza(imagem);
        int largura = imagemCinza.getWidth();
        int altura = imagemCinza.getHeight();
        long somaDiferenca = 0;
        int contador = 0;

        // Analisa apenas uma amostra da imagem (a cada 50 pixels)
        int passo = Math.max(largura, altura) / 50;

        for (int x = 0; x < largura; x += passo) {
            for (int y = 0; y < altura; y += passo) {
                if (x > 0 && y > 0) { // Evita bordas
                    int corAtual = new Color(imagemCinza.getRGB(x, y)).getRed();
                    int corVizinho = new Color(imagemCinza.getRGB(x - 1, y - 1)).getRed();
                    somaDiferenca += Math.abs(corAtual - corVizinho);
                    contador++;
                }
            }
        }

        if (contador == 0) return false;

        double mediaContraste = (double) somaDiferenca / contador;
        return mediaContraste < 20; // Ajustei para 15 (era 10) testando 20
    }*/