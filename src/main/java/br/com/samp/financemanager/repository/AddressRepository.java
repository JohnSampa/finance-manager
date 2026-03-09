package br.com.samp.financemanager.repository;

import br.com.samp.financemanager.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
