package br.com.samp.financemanager.repository;

import br.com.samp.financemanager.model.Expense;
import br.com.samp.financemanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUser(User user);

    Optional<Expense> findByUserAndId(User user, Long id);
}
