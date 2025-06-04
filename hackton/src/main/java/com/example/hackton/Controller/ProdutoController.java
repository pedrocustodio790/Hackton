package com.example.hackton.Controller;

import com.example.hackton.DTO.*;
import com.example.hackton.Entity.Produto;
import com.example.hackton.Mapper.ProdutoMapper;
import com.example.hackton.Service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    @Autowired
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    // ======= CRUD =======

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criarProduto(@RequestBody ProdutoRequestDTO dto) {
        Produto produto = produtoService.criarProduto(ProdutoMapper.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(ProdutoMapper.toDTO(produto));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listarTodos() {
        List<ProdutoResponseDTO> produtos = produtoService.listarTodosProdutos().stream()
                .map(ProdutoMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Long id) {
        return produtoService.buscarProdutoPorId(id)
                .map(produto -> ResponseEntity.ok(ProdutoMapper.toDTO(produto)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(
            @PathVariable Long id,
            @RequestBody ProdutoRequestDTO dto) {

        return produtoService.atualizarProduto(id, ProdutoMapper.toEntity(dto))
                .map(produto -> ResponseEntity.ok(ProdutoMapper.toDTO(produto)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        if (produtoService.deletarProduto(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // ======= ESTOQUE =======

    @GetMapping("/{id}/estoque")
    public ResponseEntity<EstoqueDTO> consultarEstoque(@PathVariable Long id) {
        return produtoService.consultarEstoque(id)
                .map(quantidade -> {
                    EstoqueDTO dto = new EstoqueDTO();
                    dto.setQuantidade(quantidade);
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/estoque/adicionar")
    public ResponseEntity<ProdutoResponseDTO> adicionarEstoque(
            @PathVariable Long id,
            @RequestBody EstoqueDTO dto) {

        return produtoService.adicionarEstoque(id, dto.getQuantidade())
                .map(produto -> ResponseEntity.ok(ProdutoMapper.toDTO(produto)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/estoque")
    public ResponseEntity<ProdutoResponseDTO> definirEstoque(
            @PathVariable Long id,
            @RequestBody EstoqueDTO dto) {

        return produtoService.definirEstoque(id, dto.getQuantidade())
                .map(produto -> ResponseEntity.ok(ProdutoMapper.toDTO(produto)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}/estoque")
    public ResponseEntity<Object> zerarEstoque(@PathVariable Long id) {
        return produtoService.zerarEstoque(id)
                .map(p -> ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
    }

    // ======= MARCAS =======

    @GetMapping("/marcas")
    public ResponseEntity<MarcasResponseDTO> listarMarcas() {
        MarcasResponseDTO response = new MarcasResponseDTO();
        response.setMarcas(produtoService.listarMarcasDistintas());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/marca")
    public ResponseEntity<ProdutoResponseDTO> atualizarMarca(
            @PathVariable Long id,
            @RequestBody MarcaDTO dto) {

        return produtoService.definirMarca(id, dto.getFabricante())
                .map(produto -> ResponseEntity.ok(ProdutoMapper.toDTO(produto)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}/marca")
    public ResponseEntity<Object> removerMarca(@PathVariable Long id) {
        return produtoService.removerMarca(id)
                .map(p -> ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
    }

    // ======= DESCRIÇÃO =======

    @GetMapping("/{id}/descricao")
    public ResponseEntity<DescricaoDTO> consultarDescricao(@PathVariable Long id) {
        return produtoService.consultarDescricao(id)
                .map(descricao -> {
                    DescricaoDTO dto = new DescricaoDTO();
                    dto.setTextoDescritivo(descricao);
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/descricao")
    public ResponseEntity<ProdutoResponseDTO> atualizarDescricao(
            @PathVariable Long id,
            @RequestBody DescricaoDTO dto) {

        return produtoService.atualizarDescricao(id, dto.getTextoDescritivo())
                .map(produto -> ResponseEntity.ok(ProdutoMapper.toDTO(produto)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}/descricao")
    public ResponseEntity<Object> removerDescricao(@PathVariable Long id) {
        return produtoService.removerDescricao(id)
                .map(p -> ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
    }

    // ======= IMAGENS =======

    @GetMapping("/{id}/imagens")
    public ResponseEntity<List<ImagemDTO>> listarImagens(@PathVariable Long id) {
        return produtoService.listarImagens(id)
                .map(imagens -> imagens.stream()
                        .map(url -> {
                            ImagemDTO dto = new ImagemDTO();
                            dto.setUrl(url);
                            return dto;
                        })
                        .collect(Collectors.toList()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/imagens")
    public ResponseEntity<ProdutoResponseDTO> adicionarImagem(
            @PathVariable Long id,
            @RequestBody ImagemDTO dto) {

        return produtoService.adicionarImagem(id, dto.getUrl())
                .map(produto -> ResponseEntity.ok(ProdutoMapper.toDTO(produto)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}/imagens")
    public ResponseEntity<Object> removerImagem(
            @PathVariable Long id,
            @RequestParam String url) {

        return produtoService.removerImagem(id, url)
                .map(p -> ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
    }
}
