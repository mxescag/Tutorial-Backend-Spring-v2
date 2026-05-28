package com.ccsw.tutorial.loan.repository;

import com.ccsw.tutorial.loan.model.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Max Escrivá
 */
public interface LoanRepository extends CrudRepository<Loan, Long>, JpaSpecificationExecutor<Loan> // Sin esto, no ejecuta las specif
{
    /**
     * Método para recuperar un listado paginado de {@link Loan}
     * @param pageable
     * @return {@link Page} de {@link Loan}
     */
    Page<Loan> findAll(Pageable pageable);

    /**
     * Método de validación: el juego está prestado en un rango de fechas
     * @param gameId PK de la entidad. ID del juego por el que queremos filtrar.
     * @param startDate Fecha de inicio del nuevo préstamo.
     * @param endDate Fecha de fin del nuevo préstamo.
     * @param excludeId Excluímos. ID del juego por el que queremos filtrar.
     * @return {@link List} de juegos que se solapan.
     */
    @Query("""
        SELECT l FROM Loan l
        WHERE l.game.id = :gameId
          AND l.id <> :excludeId
          AND l.startDate <= :endDate
          AND l.endDate   >= :startDate
    """)
    List<Loan> findOverlappingByGame(
            @Param("gameId")    Long gameId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("excludeId") Long excludeId
    );

    /**
     * Método de validación: el juego está prestado en un rango de fechas
     * @param clientId PK de la entidad. ID del cliente por el que queremos filtrar.
     * @param startDate Fecha de inicio del nuevo préstamo.
     * @param endDate Fecha de fin del nuevo préstamo.
     * @param excludeId Excluímos. ID del cliente por el que queremos filtrar.
     * @return {@link List} de clientes que se solapan.
     */
    @Query("""
        SELECT l FROM Loan l
        WHERE l.client.id = :clientId
          AND l.id <> :excludeId
          AND l.startDate <= :endDate
          AND l.endDate   >= :startDate
    """)
    List<Loan> findOverlappingByClient(
            @Param("clientId")  Long clientId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate")   LocalDate endDate,
            @Param("excludeId") Long excludeId
    );
}
