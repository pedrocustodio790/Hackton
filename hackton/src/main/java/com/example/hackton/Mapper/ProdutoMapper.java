package com.example.hackton.Mapper;


import com.example.hackton.DTO.ProdutoRequestDTO;
import com.example.hackton.DTO.ProdutoResponseDTO;
import com.example.hackton.Entity.Produto;

public class ProdutoMapper {

    public static Produto toEntity(ProdutoRequestDTO dto) {
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setTextoDescritivo(dto.getTextoDescritivo());
        produto.setCor(dto.getCor());
        produto.setFabricante(dto.getFabricante());
        produto.setPreco(dto.getPreco());
        produto.setQuantidade(dto.getQuantidade());
        produto.setImagens(dto.getImagens());
        return produto;
    }

    public static ProdutoResponseDTO toDTO(Produto produto) {
        ProdutoResponseDTO dto = new ProdutoResponseDTO();
        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setTextoDescritivo(produto.getTextoDescritivo());
        dto.setCor(produto.getCor());
        dto.setFabricante(produto.getFabricante());
        dto.setPreco(produto.getPreco());
        dto.setQuantidade(produto.getQuantidade());
        dto.setImagens(produto.getImagens());
        return dto;
    }
}