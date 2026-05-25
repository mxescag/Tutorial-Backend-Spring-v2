package com.ccsw.tutorial.client.model;

public class ClientDto {
    /* -- ATRIBUTOS -- */

    private Long id;
    private String name;

    /* -- GETTERS Y SETTERS -- */

    /**
     * @return id of client
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
     * @return name of client
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
