package com.example.hackton.Controller;

import com.example.hackton.Entity.Produto;
import com.example.hackton.Repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    // Operações CRUD básicas ========================================

    @PostMapping
    public Produto criarProduto(@RequestBody Produto produto) {
        return produtoRepository.save(produto);
    }

    @GetMapping
    public List<Produto> listarTodosProdutos() {
        return produtoRepository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody Produto produtoAtualizado) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.setNome(produtoAtualizado.getNome());
                    produto.setTextoDescritivo(produtoAtualizado.getTextoDescritivo());
                    produto.setCor(produtoAtualizado.getCor());
                    produto.setFabricante(produtoAtualizado.getFabricante());
                    produto.setPreco(produtoAtualizado.getPreco());
                    produto.setQuantidade(produtoAtualizado.getQuantidade());
                    produto.setImagens(produtoAtualizado.getImagens());
                    Produto atualizado = produtoRepository.save(produto);
                    return ResponseEntity.ok(atualizado);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Operações de Estoque ========================================

    @GetMapping("/{id}/estoque")
    public ResponseEntity<Integer> consultarEstoque(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .map(produto -> ResponseEntity.ok(produto.getQuantidade()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/estoque")
    public ResponseEntity<Produto> adicionarEstoque(
            @PathVariable Long id,
            @RequestParam int quantidade) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.setQuantidade(produto.getQuantidade() + quantidade);
                    Produto atualizado = produtoRepository.save(produto);
                    return ResponseEntity.ok(atualizado);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/estoque")
    public ResponseEntity<Produto> definirEstoque(
            @PathVariable Long id,
            @RequestParam int novaQuantidade) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.setQuantidade(novaQuantidade);
                    Produto atualizado = produtoRepository.save(produto);
                    return ResponseEntity.ok(atualizado);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}/estoque")
    public ResponseEntity<Object> zerarEstoque(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.setQuantidade(0);
                    produtoRepository.save(produto);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Operações de Marca ========================================

    @GetMapping("/marcas")
    public List<String> listarMarcas() {
        return produtoRepository.findDistinctFabricante();
    }

    @PostMapping("/marcas")
    public ResponseEntity<Produto> definirMarca(
            @RequestParam Long id,
            @RequestParam String novaMarca) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.setFabricante(novaMarca);
                    Produto atualizado = produtoRepository.save(produto);
                    return ResponseEntity.ok(atualizado);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/marcas")
    public ResponseEntity<Object> removerMarca(@RequestParam Long id) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.setFabricante(null);
                    produtoRepository.save(produto);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Operações de Descrição ========================================

    @GetMapping("/{id}/descricao")
    public ResponseEntity<String> consultarDescricao(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .map(produto -> ResponseEntity.ok(produto.getTextoDescritivo()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/descricao")
    public ResponseEntity<Produto> atualizarDescricao(
            @PathVariable Long id,
            @RequestBody String novaDescricao) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.setTextoDescritivo(novaDescricao);
                    Produto atualizado = produtoRepository.save(produto);
                    return ResponseEntity.ok(atualizado);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}/descricao")
    public ResponseEntity<Object> removerDescricao(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.setTextoDescritivo(null);
                    produtoRepository.save(produto);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Operações de Imagens ========================================

    @GetMapping("/{id}/imagens")
    public ResponseEntity<List<String>> listarImagens(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .map(produto -> ResponseEntity.ok(produto.getImagens()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/imagens")
    public ResponseEntity<Produto> adicionarImagem(
            @PathVariable Long id,
            @RequestBody String novaImagemUrl) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.getImagens().add(novaImagemUrl);
                    Produto atualizado = produtoRepository.save(produto);
                    return ResponseEntity.ok(atualizado);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}/imagens")
    public ResponseEntity<Object> removerImagem(
            @PathVariable Long id,
            @RequestParam String imagemUrl) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.getImagens().remove(imagemUrl);
                    produtoRepository.save(produto);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}