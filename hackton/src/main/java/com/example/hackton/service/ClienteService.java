package com.technova.service;


import com.example.hackton.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // Criar novo cliente
    public Cliente criar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // Login (simplesmente verifica se existe um cliente com o email informado)
    public Optional<Cliente> login(String email) {
        return clienteRepository.findAll().stream()
                .filter(cliente -> cliente.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    // Atualizar cliente existente
    public Optional<Cliente> atualizar(Long id, Cliente clienteAtualizado) {
        return clienteRepository.findById(id).map(cliente -> {
            cliente.setNome(clienteAtualizado.getNome());
            cliente.setEmail(clienteAtualizado.getEmail());
            cliente.setEndereco(clienteAtualizado.getEndereco());
            return clienteRepository.save(cliente);
        });
    }

    // Listar todos os clientes
    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    // Deletar cliente por ID
    public boolean delete(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
