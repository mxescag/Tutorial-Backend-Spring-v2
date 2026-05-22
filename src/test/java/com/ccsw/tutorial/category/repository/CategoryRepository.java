package com.ccsw.tutorial.category.repository;

import com.ccsw.tutorial.category.model.Category;
import org.springframework.data.repository.CrudRepository;


/**
 * @author Max Escrivá
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
