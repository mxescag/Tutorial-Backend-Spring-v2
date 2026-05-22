package com.ccsw.tutorial.category.model;

import jakarta.persistence.*;

/**
 * @author Max Escrivá
 */

/* -- ATENCIÓN!! Al ser Entity, no interactuamos con ella en controller y service!!! --
* -- Para eso, ya tenemos DTOs. -- */


@Entity /* Sin esto, no podemos hacer queries */
@Table(name = "category") /* Le indica a JPA el nombre y schema de la tabla que representa esta clase. */
public class Category {

    @Id /* Id indica a JPA que esta propiedad es la que mapea una PK */
    @GeneratedValue(strategy = GenerationType.IDENTITY) /* La PK se genera de forma autoincremental. */
    @Column(name = "id", nullable = false) /* Le indica a JPA que esta propiedad mapea una columna de la tabla, y el nombre de ésta */
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;


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
