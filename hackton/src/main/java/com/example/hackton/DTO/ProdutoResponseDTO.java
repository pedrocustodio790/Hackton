package com.example.hackton.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class ProdutoResponseDTO {
    private Long id;
    private String nome;
    private String textoDescritivo;
    private String cor;
    private String fabricante;
    private BigDecimal preco;
    private Integer quantidade;
    private List<String> imagens;
}