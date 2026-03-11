package br.com.samp.financemanager.dto.request;

public record AccountRequest(
        String bankName,
        Double balance
) {

}
