package com.ccsw.tutorial.category.controller;


import com.ccsw.tutorial.category.model.Category;
import com.ccsw.tutorial.category.model.CategoryDto;
import com.ccsw.tutorial.category.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Max Escrivá
 */

@Tag(name="Category", description = "API of Category") /* Sirve para documentar la API */
@RequestMapping(value = "/category") /* Define la ruta base del controlador. Todos los endpoints heredan este prefijo. */
@RestController /* Indica que cada método devuelve datos directamente en el cuerpo de respuesta HTTP, serialización automática*/
@CrossOrigin(origins = "*")

/* -- Esta clase sirve para definir las rutas de las operaciones --
* -- Interactuaremos con el patrón fachada, es decir, la interfaz de servicio, que a la vez tendrá una implementación de ese servicio -- */


public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    ModelMapper mapper;

    /**
     * Método para recuperar todas las categorías
     * @return {@link List} de {@link CategoryDto}
     */
    @Operation(summary = "Find", description = "Method that return a list of Category")
    @GetMapping(path = "")
    public List<CategoryDto> findAll(){
        List<Category> categories = this.categoryService.findAll();

        return categories.stream().map(e -> mapper.map(e, CategoryDto.class)).collect(Collectors.toList());
    }

    /**
     * Método para crear o actualizar una categoria
     *
     * @param id PK de la entidad
     * @param dto datos de la entidad
     */
    @Operation(summary = "Save or Update", description = "Method that saves or updates a Category")
    @RequestMapping(path = { "", "/{id}" }, method = RequestMethod.PUT)
    /* PathVariable recoge el id de la URL; RequestBody recoge el cuerpo JSON de la petición y lo convierte automáticamente a CategoryDto.
    * Esto contiene los datos que el cliente quiere guardar, por ejemplo: el nombre. */
    public void save(@PathVariable(name = "id", required = false) Long id,
                     @RequestBody CategoryDto dto) {

        this.categoryService.save(id,dto);

    }

    /**
     * Método para borrar una categoria
     *
     * @param id PK de la entidad
     */
    @Operation(summary = "Delete", description = "Method that deletes a Category")
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable("id") Long id) throws Exception {

        this.categoryService.delete(id);
    }
}
