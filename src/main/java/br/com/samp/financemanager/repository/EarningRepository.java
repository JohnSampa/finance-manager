package br.com.samp.financemanager.repository;

import br.com.samp.financemanager.model.Earning;
import br.com.samp.financemanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EarningRepository extends JpaRepository<Earning, Long> {
    List<Earning> findByUser(User user);

    Optional<Earning> findByUserAndId(User user,Long id);
}
