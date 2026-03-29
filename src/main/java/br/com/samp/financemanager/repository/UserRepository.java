package br.com.samp.financemanager.repository;

import br.com.samp.financemanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByLogin(String email);
}
