package br.com.samp.financemanager.repository;

import br.com.samp.financemanager.model.Account;
import br.com.samp.financemanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByHolder(User holder);

    Optional<Account> findByHolderAndId(User holder, Long id);

    void deleteByHolderAndId(User holder, Long id);
}
