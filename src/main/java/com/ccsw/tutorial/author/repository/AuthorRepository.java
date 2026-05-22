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

}