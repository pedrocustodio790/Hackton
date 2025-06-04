package com.example.hackton.DTO;

import lombok.Data;

@Data
public class ClienteRequestDTO {
    private String nome;
    private String email;
    private String endereco;
    private String senha;
}