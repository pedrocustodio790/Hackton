package com.example.hackton.DTO;

import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;
import java.util.List;
@Data
public class ProdutoRequestDTO {
    private String nome;
    private String textoDescritivo;
    private String cor;
    private String fabricante;
    private BigDecimal preco;
    private Integer quantidade;
    private List<String> imagens;
}