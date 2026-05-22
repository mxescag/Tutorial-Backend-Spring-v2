package com.ccsw.tutorial.author.controller;

import com.ccsw.tutorial.author.model.Author;
import com.ccsw.tutorial.author.model.AuthorDto;
import com.ccsw.tutorial.author.model.AuthorSearchDto;
import com.ccsw.tutorial.author.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

@Tag(name = "Author", description = "API of Author")
@RequestMapping(value = "/author") /* Define la ruta base del controlador. Todos los endpoints heredan este prefijo. */
@RestController
@CrossOrigin(origins = "*")
public class AuthorController {

    /* Inyecciones */

    @Autowired
    AuthorService authorService;
    @Autowired
    ModelMapper mapper;

    /**
     * Método para recuperar un listado paginado de {@link Author}
     *
     * @param dto dto de búsqueda
     * @return {@link Page} de {@link AuthorDto}
     */
    @Operation(summary = "Find Page", description = "Method that return a page of Authors")
    @PostMapping(path = "") /* Recuerda que al cliente no le deben llegar modelos entidades sino DTOs. */
    public Page<AuthorDto> findPage(@RequestBody AuthorSearchDto dto) {

        Page<Author> page = this.authorService.findPage(dto);
        /* El servicio consulta la base de datos y devuelve un Page<Author>*/

        return new PageImpl<>( // Reconstruye el objeto de la Page pero con AuthorDto en vez de Author
                page.getContent(). // Obtiene la lista de Author de esta página
                        stream() // Abre un stream para procesarlos 1 a 1
                        .map(e -> mapper.map(e, AuthorDto.class)) // Convierte cada Author a AuthorDto usando ModelMapper
                        .collect(Collectors.toList()), // Recoge el resultado en una List<AuthorDto>
                page.getPageable(), // cómo será la paginación
                page.getTotalElements());  // total de elementos
    }

    /**
     * Método para crear o actualizar un {@link Author}
     *
     * @param id PK de la entidad
     * @param dto datos de la entidad
     */
    @Operation(summary = "Save or Update", description = "Method that saves or updates a Author")
    @RequestMapping(path = { "", "/{id}" }, method = RequestMethod.PUT)
    public void save(@PathVariable(name = "id", required = false) Long id, @RequestBody AuthorDto dto) {

        this.authorService.save(id, dto);
    }

    /**
     * Método para eliminar un
     *
     * @param id PK de la entidad
     */
    @Operation(summary = "Delete", description = "Method that deletes a Author")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) throws Exception {

        this.authorService.delete(id);
    }
}
