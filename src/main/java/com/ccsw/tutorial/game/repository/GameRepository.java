package com.ccsw.tutorial.game.repository;

import com.ccsw.tutorial.game.model.Game;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Max Escrivá
 */
public interface GameRepository extends CrudRepository<Game, Long>, JpaSpecificationExecutor<Game> // Ejecuta los filtros
{
    /* Añadiendo el EntityGraph y los atributos que queremos que se incluyan, podemos hacer una única consulta
    * y que haga las subconsultas mediante JOIN,
    * evitando así multiples queries y mejorando el rendimiento. */
    @Override
    @EntityGraph(attributePaths = {"category", "author"})
    List<Game> findAll(Specification<Game> spec);
}