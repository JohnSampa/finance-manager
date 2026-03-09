package br.com.samp.financemanager.services;

import br.com.samp.financemanager.dto.ViaCepAddressDTO;
import br.com.samp.financemanager.dto.mapstruct.ViaCepAddressMapper;
import br.com.samp.financemanager.exceptions.BusinessException;
import br.com.samp.financemanager.integration.ViaCepClient;
import br.com.samp.financemanager.model.Address;
import br.com.samp.financemanager.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private ViaCepClient viaCepClient;

    @Autowired
    private ViaCepAddressMapper viaCepAddressMapper;

    @Autowired
    private AddressRepository addressRepository;

    public Address saveAddress(String cep, String number) {

        ViaCepAddressDTO response = viaCepClient.getViaCepAddress(cep);

        if (response == null) throw new BusinessException("Invalid CEP");

        Address address = viaCepAddressMapper.toEntity(response);

        address.setNumber(number);

        return addressRepository.save(address);
    }
}
