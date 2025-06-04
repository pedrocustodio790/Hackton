
package com.example.hackton.Service;

import com.example.hackton.Entity.Produto;
import com.example.hackton.Repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    // Operações CRUD básicas ========================================

    @Transactional
    public Produto criarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public List<Produto> listarTodosProdutos() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscarProdutoPorId(Long id) {
        return produtoRepository.findById(id);
    }

    @Transactional
    public Optional<Produto> atualizarProduto(Long id, Produto produtoAtualizado) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.setNome(produtoAtualizado.getNome());
                    produto.setTextoDescritivo(produtoAtualizado.getTextoDescritivo());
                    produto.setCor(produtoAtualizado.getCor());
                    produto.setFabricante(produtoAtualizado.getFabricante());
                    produto.setPreco(produtoAtualizado.getPreco());
                    produto.setQuantidade(produtoAtualizado.getQuantidade());
                    produto.setImagens(produtoAtualizado.getImagens());
                    return produtoRepository.save(produto);
                });
    }

    @Transactional
    public boolean deletarProduto(Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Operações de Estoque ========================================

    public Optional<Integer> consultarEstoque(Long id) {
        return produtoRepository.findById(id)
                .map(Produto::getQuantidade);
    }

    @Transactional
    public Optional<Produto> adicionarEstoque(Long id, int quantidade) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    int novoEstoque = produto.getQuantidade() + quantidade;
                    produto.setQuantidade(novoEstoque);
                    return produtoRepository.save(produto);
                });
    }

    @Transactional
    public Optional<Produto> definirEstoque(Long id, int novaQuantidade) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.setQuantidade(novaQuantidade);
                    return produtoRepository.save(produto);
                });
    }

    @Transactional
    public Optional<Produto> zerarEstoque(Long id) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.setQuantidade(0);
                    return produtoRepository.save(produto);
                });
    }

    // Operações de Marca ========================================

    public List<String> listarMarcasDistintas() {
        return produtoRepository.findDistinctFabricante();
    }

    @Transactional
    public Optional<Produto> definirMarca(Long id, String novaMarca) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.setFabricante(novaMarca);
                    return produtoRepository.save(produto);
                });
    }

    @Transactional
    public Optional<Produto> removerMarca(Long id) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.setFabricante(null);
                    return produtoRepository.save(produto);
                });
    }

    // Operações de Descrição ========================================

    public Optional<String> consultarDescricao(Long id) {
        return produtoRepository.findById(id)
                .map(Produto::getTextoDescritivo);
    }

    @Transactional
    public Optional<Produto> atualizarDescricao(Long id, String novaDescricao) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.setTextoDescritivo(novaDescricao);
                    return produtoRepository.save(produto);
                });
    }

    @Transactional
    public Optional<Produto> removerDescricao(Long id) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.setTextoDescritivo(null);
                    return produtoRepository.save(produto);
                });
    }

    // Operações de Imagens ========================================

    public Optional<List<String>> listarImagens(Long id) {
        return produtoRepository.findById(id)
                .map(Produto::getImagens);
    }

    @Transactional
    public Optional<Produto> adicionarImagem(Long id, String novaImagemUrl) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.getImagens().add(novaImagemUrl);
                    return produtoRepository.save(produto);
                });
    }

    @Transactional
    public Optional<Produto> removerImagem(Long id, String imagemUrl) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.getImagens().remove(imagemUrl);
                    return produtoRepository.save(produto);
                });
    }
}