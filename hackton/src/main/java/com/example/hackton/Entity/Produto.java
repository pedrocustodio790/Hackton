package com.example.hackton.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String textoDescritivo;

    private String cor;
    private String fabricante;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(nullable = false)
    private Integer quantidade = 0;

    @ElementCollection
    @CollectionTable(name = "produto_imagens", joinColumns = @JoinColumn(name = "produto_id"))
    @Column(name = "url_imagem")
    private List<String> imagens = new ArrayList<>();

    // Método auxiliar para adicionar imagem
    public void adicionarImagem(String url) {
        this.imagens.add(url);
    }

    // Método auxiliar para remover imagem
    public void removerImagem(String url) {
        this.imagens.remove(url);
    }
}