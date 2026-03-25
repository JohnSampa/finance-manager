package br.com.samp.financemanager.services;

import br.com.samp.financemanager.dto.mapstruct.EarningMapper;
import br.com.samp.financemanager.dto.response.EarningResponse;
import br.com.samp.financemanager.exceptions.ResourceNotFoundException;
import br.com.samp.financemanager.model.Earning;
import br.com.samp.financemanager.repository.EarningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EarningService {

    @Autowired
    private EarningRepository earningRepository;

    @Autowired
    private EarningMapper earningMapper;

    public EarningResponse findById(Long id) {
        Earning earning = earningRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Earning not found with id: " + id));

        return earningMapper.toResponse(earning);
    }

    public List<EarningResponse> findAll() {
        List<Earning> earnings = earningRepository.findAll();

        return  earningMapper.toResponseList(earnings);
    }
}
