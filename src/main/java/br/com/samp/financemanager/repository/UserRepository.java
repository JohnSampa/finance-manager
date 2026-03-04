package br.com.samp.financemanager.repository;

import br.com.samp.financemanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
