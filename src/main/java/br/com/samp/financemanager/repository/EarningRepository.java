package br.com.samp.financemanager.repository;

import br.com.samp.financemanager.model.Earning;
import br.com.samp.financemanager.model.User;
import br.com.samp.financemanager.model.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EarningRepository extends JpaRepository<Earning, Long> {
    List<Earning> findByUser(User user);

    Optional<Earning> findByUserAndUuid(User user, UUID id);

    @Query("""
        SELECT e FROM Earning e
        WHERE(e.user = :user)
        AND (:categoryId IS NULL OR EXISTS(
            SELECT c FROM e.categories c WHERE c.id = :categoryId
        ))
        AND (:date IS NULL OR e.date = :date)
        AND (:status IS NULL OR e.status = :status)
    """)
    List<Earning> findWithFilters(
            @Param("user") User user,
            @Param("categoryId") Long id,
            @Param("date") LocalDate date,
            @Param("status") TransactionStatus status
    );

    List<Earning> findByUserAndCategoriesUuid(User user, UUID categoryId);
}
