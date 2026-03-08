package br.com.samp.financemanager.repository;

import br.com.samp.financemanager.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByHolderId(Long userId);

    Optional<Account> findByHolderIdAndId(Long userId, Long id);

    void deleteByHolderIdAndId(Long userId, Long id);
}
