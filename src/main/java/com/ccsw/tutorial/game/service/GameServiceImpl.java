package com.ccsw.tutorial.game.service;

import com.ccsw.tutorial.author.service.AuthorService;
import com.ccsw.tutorial.category.service.CategoryService;
import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.game.GameSpecification;
import com.ccsw.tutorial.game.model.Game;
import com.ccsw.tutorial.game.model.GameDto;
import com.ccsw.tutorial.game.repository.GameRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * @author ccsw
 *
 */
@Service
@Transactional
public class GameServiceImpl implements GameService {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    AuthorService authorService; // Hemos creado un método para saber el ID de Author y usarlo aquí

    @Autowired
    CategoryService categoryService; // Hemos creado un método para saber el ID de Category y usarlo aquí

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Game> find(String title, Long idCategory) {

        GameSpecification titleSpec = new GameSpecification(new SearchCriteria("title", ":", title));
        GameSpecification categorySpec = new GameSpecification(new SearchCriteria("category.id", ":", idCategory));

        /* Esto crea una consulta SQL juntando las dos de arriba, algo así como:
        * "SELECT * from game
        * WHERE title LIKE %title(var)%
        * AND category.id = idCategory(var) */
        Specification<Game> specification = titleSpec.and(categorySpec);

        return this.gameRepository.findAll(specification); // Devolverá los resultados filtrados por las condiciones de arriba.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, GameDto dto) {

        Game game;

        if (id == null) {
            game = new Game();
        } else {
            game = this.gameRepository.findById(id).orElse(null);
        }

        BeanUtils.copyProperties(dto, game, "id", "author", "category"); // Ignoramos Author y Category aquí para añadirlos con métodos especiales más tarde...

        game.setAuthor(this.authorService.get(dto.getAuthor().getId()));
        game.setCategory(this.categoryService.get(dto.getCategory().getId()));

        this.gameRepository.save(game);
    }

}