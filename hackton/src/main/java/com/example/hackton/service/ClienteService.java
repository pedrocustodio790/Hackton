package com.example.hackton.service;




import com.example.hackton.DTO.ClienteRequestDTO;
import com.example.hackton.DTO.ClienteResponseDTO;
import com.example.hackton.entity.Cliente;
import com.example.hackton.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public ClienteResponseDTO criar(ClienteRequestDTO dto) {
        Cliente cliente = toEntity(dto);
        Cliente salvo = clienteRepository.save(cliente);
        return toResponseDTO(salvo);
    }

    public Optional<ClienteResponseDTO> login(String email, String senha) {
        return clienteRepository.findAll().stream()
                .filter(c -> c.getEmail().equalsIgnoreCase(email) && c.getSenha().equals(senha))
                .findFirst()
                .map(this::toResponseDTO);
    }

    public Optional<ClienteResponseDTO> atualizar(Long id, ClienteRequestDTO dto) {
        return clienteRepository.findById(id).map(cliente -> {
            cliente.setNome(dto.getNome());
            cliente.setEmail(dto.getEmail());
            cliente.setEndereco(dto.getEndereco());
            cliente.setSenha(dto.getSenha());
            Cliente atualizado = clienteRepository.save(cliente);
            return toResponseDTO(atualizado);
        });
    }

    public List<ClienteResponseDTO> listar() {
        return clienteRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public boolean delete(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private Cliente toEntity(ClienteRequestDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setEndereco(dto.getEndereco());
        cliente.setSenha(dto.getSenha());
        return cliente;
    }

    private ClienteResponseDTO toResponseDTO(Cliente cliente) {
        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setEmail(cliente.getEmail());
        dto.setEndereco(cliente.getEndereco());
        return dto;
    }
}
