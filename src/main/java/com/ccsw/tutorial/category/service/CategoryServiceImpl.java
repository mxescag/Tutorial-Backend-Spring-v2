package com.ccsw.tutorial.category.service;

import com.ccsw.tutorial.category.model.Category;
import com.ccsw.tutorial.category.model.CategoryDto;
import com.ccsw.tutorial.category.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    CategoryRepository categoryRepository;

    /**
     * {@inheritDoc}
     */
    public List<Category> findAll(){
        return (List<Category>) this.categoryRepository.findAll();
    }


    /* Este método se utilizará en GameServiceImpl para saber la ID de Category */
    /**
     * {@inheritDoc}
     */
    @Override
    public Category get(Long id) {

        return this.categoryRepository.findById(id).orElse(null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, CategoryDto dto) {
        Category category = this.categoryRepository.findById(id).orElse(null);
        if (category == null) {
            // Si el ID es nulo, creamos
            category = new Category(); // Crea un objeto Category nuevo
        } else {
            // Si el ID ya existe
            category = this.get(id); // Actualizamos
        }

        assert category != null; // Nos aseguramos que category no está vacío
        category.setName(dto.getName()); // Asigna el nombre recibido del body

        this.categoryRepository.save(category); // y guarda el objeto en la base de datos
    }

    /**
     {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {
        if(this.get(id) == null) {
            throw new Exception("Not exists");
        }

        this.categoryRepository.deleteById(id);
    }

}
