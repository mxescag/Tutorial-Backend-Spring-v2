package com.ccsw.tutorial.category;


import com.ccsw.tutorial.category.model.Category;
import com.ccsw.tutorial.category.model.CategoryDto;
import com.ccsw.tutorial.category.repository.CategoryRepository;
import com.ccsw.tutorial.category.service.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Tests unitarios
 */

/*Esto indica a Spring que no debe inicializar el contexto, ya que se trata de test estáticos que no lo requieren*/
@ExtendWith(MockitoExtension.class)
public class CategoryTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    /**
     * Debe probar la lógica de la operación de negocio de consulta de Categoría.
     * Debe devolver todas las categorías.
     */
    @Test
    public void findAllShouldReturnAllCategories() {
        List<Category> list = new ArrayList<>(); // Crea una lista vacía
        list.add(mock(Category.class)); // Genera un objeto falso de Category

        when(categoryRepository.findAll()).thenReturn(list); // Cuando alguien llame al repository, devuelve lista falsa en vez de la real

        /* Ejecutará el método bajo prueba. Como el repositorio está mockeado, devolverá la lista de arriba. */
        List<Category> categories = categoryService.findAll();

        /* Verificación de los resultados */
        assertNotNull(categories); // ¿La lista devuelta no es null? Entonces OK
        assertEquals(1, categories.size()); // ¿La lista devuelta es exactamente 1? Entonces OK
    }


    public static final String CATEGORY_NAME = "CAT1";

    /**
     * Método que comprueba que una categoría sin ID se guarda como nueva, y con el nombre indicado.
     */
    @Test
    public void saveNotExistsCategoryIdShouldInsert() {

        CategoryDto categoryDto = new CategoryDto(); // Construimos objeto CategoryDto
        categoryDto.setName(CATEGORY_NAME); // Le damos el nombre de arriba, pero no ID

        /* Herramienta que captura el objeto exacto que se le pasa al repository cuando se llame al save(), para poder inspeccionarlo después */
        ArgumentCaptor<Category> category = ArgumentCaptor.forClass(Category.class);

        categoryService.save(null, categoryDto); // Invocamos la operación save con ID nulo, se guardará como nuevo.

        verify(categoryRepository).save(category.capture()); // Verifica que el save() fue llamado exactamente 1 vez y captura el objeto que se pasó como argumento

        assertEquals(CATEGORY_NAME, category.getValue().getName()); // obtiene el objeto capturado y comprueba que el nombre que llegó al repositorio es correcto
    }


    public static final Long EXISTS_CATEGORY_ID = 1L;

    @Test
    public void saveExistsCategoryIdShouldUpdate() {

        CategoryDto categoryDto = new CategoryDto(); // Creamos objeto vacío
        categoryDto.setName(CATEGORY_NAME); // Le ponemos el nombre de arriba

        Category category = mock(Category.class); // Creamos una entidad falsa

        /* Cuando el repositorio busque con el ID, que ese ID sea el mock de arriba */
        when(categoryRepository.findById(EXISTS_CATEGORY_ID)).thenReturn(Optional.of(category));

        categoryService.save(EXISTS_CATEGORY_ID, categoryDto); // Invocamos la operación con el save(), al existir el ID, no creará sino modificará

        verify(categoryRepository).save(category); // Verificamos que se llama al save()
    }

    @Test
    public void deleteExistsCategoryIdShouldDelete() throws Exception {

        Category category = mock(Category.class);
        when(categoryRepository.findById(EXISTS_CATEGORY_ID)).thenReturn(Optional.of(category));

        categoryService.delete(EXISTS_CATEGORY_ID);

        verify(categoryRepository).deleteById(EXISTS_CATEGORY_ID);
    }
}
