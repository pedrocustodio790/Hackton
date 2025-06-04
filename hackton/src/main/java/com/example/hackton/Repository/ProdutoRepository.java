package com.example.hackton.Repository;

import com.example.hackton.Entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    @Query("SELECT DISTINCT p.fabricante FROM Produto p WHERE p.fabricante IS NOT NULL")
    List<String> findDistinctFabricante();
}
}
