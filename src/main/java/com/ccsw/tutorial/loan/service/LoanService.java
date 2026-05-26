package com.ccsw.tutorial.loan.service;

import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Max Escrivá
 */
public interface LoanService {

    /**
     * Recupera un listado de préstamos.
     * @return {@link List} de {@link Loan}
     */
    List<Loan> findAll();

    /**
     * Retorna un préstamo por su ID
     * @param id PK de la entidad préstamo
     * @return {@link Loan}
     */
    Loan get(Long id);

    /**
     * Recupera un listado paginado de préstamos.
     * @param dto dto de búsqueda
     * @return {@link Page} de {@link Loan}
     */
    Page<Loan> findPage(LoanSearchDto dto);


    /**
     * Método para crear o actualizar un préstamo
     * @param id PK de la entidad
     * @param dto datos de la entidad
     * @throws Exception Lanzará si la fecha de fin es anterior a la de inicio.<br>Si la duración del préstamo es mayor a 14 días. <br>Si el juego está prestado a dos clientes distintos el mismo día.<br>Si el cliente ya tiene 2 juegos prestados en el mismo día.
     */
    void save(Long id, LoanDto dto) throws Exception;

    /**
     * Método para eliminar un préstamo
     * @param id PK de la entidad. ID del préstamo.
     * @throws Exception Lanzará si el ID no existe.
     */
    void delete(Long id) throws Exception;
}
