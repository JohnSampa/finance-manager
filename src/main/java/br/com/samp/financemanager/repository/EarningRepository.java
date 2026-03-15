package br.com.samp.financemanager.repository;

import br.com.samp.financemanager.model.Earning;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EarningRepository extends JpaRepository<Earning, Long> {
}
