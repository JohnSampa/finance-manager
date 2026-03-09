package br.com.samp.financemanager.dto;

public record ViaCepAddressDTO(
        String cep,
        String logradouro,
        String bairro,
        String localidade,
        String uf
        ) {
}
