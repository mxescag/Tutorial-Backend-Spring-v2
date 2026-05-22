package com.ccsw.tutorial.common.pagination;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO de paginación: traduce los parámetros que envía el cliente en el body de una petición HTTP al objeto {@link Pageable} que entiende
 * Spring Data JPA.
 * Visión:
 * Cliente (JSON) -> PageableRequest -> Pageable -> Spring Data JPA
 */
public class PageableRequest implements Serializable {

    /* -- ATRIBUTOS -- */
    private static final long serialVersionUID = 1L;

    private int pageNumber; // Qué página quiero (por defecto, 0 es la primera)

    private int pageSize; // Cuántos elementos quiero por página

    private List<SortRequest> sort; // Criterios de ordenación

    /* -- CONSTRUCTORES -- */
    public PageableRequest() {

        sort = new ArrayList<>();
    }

    public PageableRequest(int pageNumber, int pageSize) {

        this();
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public PageableRequest(int pageNumber, int pageSize, List<SortRequest> sort) {

        this();
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sort = sort;
    }

    /* -- GETTERS Y SETTERS -- */
    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<SortRequest> getSort() {
        return sort;
    }

    public void setSort(List<SortRequest> sort) {
        this.sort = sort;
    }

    /* -- MÉTODO CLAVE -- */

    /**
     * Convierte los datos del DTO al objeto {@link Pageable} que Spring Data entiende.
     */
    @JsonIgnore // Jackson no lo serializa como campo JSON
    public Pageable getPageable() {

        return PageRequest.of( // 3. Crea el Pageable completo
                this.pageNumber,
                this.pageSize,
                Sort.by( // 2. Lo agrupa en un Sort
                        sort.stream().map(e -> new Sort.Order(e.getDirection(), e.getProperty()))
                                .collect(Collectors.toList()))); // 1. Convierte cada SortRequest en un Sort.order de Spring
    }

    /**
     * Clase interna, DTO auxilia que representa un criterio de ordenación
     */
    public static class SortRequest implements Serializable {

        /* -- ATRIBUTOS -- */
        private static final long serialVersionUID = 1L;

        private String property; // Nombre del campo: "name", "id"...

        private Sort.Direction direction; // ASC(endente) o DESC(endente)

        /* -- GETTERS Y SETTERS -- */
        protected String getProperty() {
            return property;
        }

        protected void setProperty(String property) {
            this.property = property;
        }

        protected Sort.Direction getDirection() {
            return direction;
        }

        protected void setDirection(Sort.Direction direction) {
            this.direction = direction;
        }
    }

}