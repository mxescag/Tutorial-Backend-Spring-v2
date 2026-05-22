package com.ccsw.tutorial.category.model;


/**
 * @author Max Escrivá
 */
public class CategoryDto {
    // Atributos

    private Long id;
    private String name;

    // Getters y setters

    /**
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id new value of {@link #getId()}
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name new value of {@link #getName()}
     */
    public void setName(String name) {
        this.name = name;
    }
}
