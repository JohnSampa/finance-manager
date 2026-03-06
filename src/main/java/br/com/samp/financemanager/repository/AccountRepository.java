package br.com.samp.financemanager.repository;

import br.com.samp.financemanager.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
