package com.ccsw.tutorial.game.service;

import com.ccsw.tutorial.author.service.AuthorService;
import com.ccsw.tutorial.category.service.CategoryService;
import com.ccsw.tutorial.game.model.Game;
import com.ccsw.tutorial.game.model.GameDto;
import com.ccsw.tutorial.game.repository.GameRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

        return (List<Game>) this.gameRepository.findAll();
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