package com.ccsw.tutorial.common.criteria;



/**
 * Clase simple tipo DTO/Value Object que representa un único criterio de filtrado.
 * Es como una forma de encapsular una condición de búsqueda.
 */
public class SearchCriteria {

    /* -- ATRIBUTOS -- */
    private String key; // Nombre del atributo o columna a filtrar, eg. "title", "age", "id"...
    private String operation; // Tipo de comparación, eg. "like", "<", ">"...
    private Object value; // Valor contra el que comparar (es Object para admitir cualquier tipo)

    /* -- CONSTRUCTOR -- */
    public SearchCriteria(String key, String operation, Object value) {

        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    /* -- GETTERS Y SETTERS -- */
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}