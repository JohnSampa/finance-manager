package br.com.samp.financemanager.repository;

import br.com.samp.financemanager.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByUuid(UUID id);

    List<Category> findAllByUuidIn(List<UUID> uuids);
}
