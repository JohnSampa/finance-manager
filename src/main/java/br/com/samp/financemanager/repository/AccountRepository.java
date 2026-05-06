package br.com.samp.financemanager.repository;

import br.com.samp.financemanager.model.Account;
import br.com.samp.financemanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByHolder(User holder);

    Optional<Account> findByHolderAndUuid(User holder, UUID uuid);
}
