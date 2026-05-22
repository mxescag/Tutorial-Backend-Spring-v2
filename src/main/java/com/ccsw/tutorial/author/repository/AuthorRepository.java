package com.ccsw.tutorial.author.repository;

import com.ccsw.tutorial.author.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Max Escrivá
 *
 */
public interface AuthorRepository extends CrudRepository<Author, Long> {
    /**
     * Método para recuperar un listado paginado de {@link Author}
     *
     * @param pageable pageable
     * @return {@link Page} de {@link Author}
     */
    Page<Author> findAll(Pageable pageable); // Este método lo añadimos a mano porque CrudRepo no lo tiene, cosa que no pasaría en JPA, creo.

}