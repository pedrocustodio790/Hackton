package com.example.hackton.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String textoDescritivo;
    private String cor;
    private String fabricante;
    private BigDecimal preco;
    private Integer quantidade;

    @ElementCollection
    @CollectionTable(name = "produto_imagens", joinColumns = @JoinColumn(name = "produto_id"))
    @Column(name = "url_imagem")
    private List<String> imagens;  // Novo campo obrigatório
}