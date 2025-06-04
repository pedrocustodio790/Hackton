package com.example.hackton.controller;



import com.example.hackton.DTO.ClienteLoginDTO;
import com.example.hackton.DTO.ClienteRequestDTO;
import com.example.hackton.DTO.ClienteResponseDTO;
import com.example.hackton.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> criar(@RequestBody ClienteRequestDTO dto) {
        return ResponseEntity.ok(clienteService.criar(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<ClienteResponseDTO> login(@RequestBody ClienteLoginDTO loginDto) {
        return clienteService.login(loginDto.getEmail(), loginDto.getSenha())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizar(@PathVariable Long id, @RequestBody ClienteRequestDTO dto) {
        return clienteService.atualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listar() {
        return ResponseEntity.ok(clienteService.listar());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return clienteService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}