package br.com.samp.financemanager.dto;

public record AccountRequest(
        String bankName,
        Double balance
) {

}
