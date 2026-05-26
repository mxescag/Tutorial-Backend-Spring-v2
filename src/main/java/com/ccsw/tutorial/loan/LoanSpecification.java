package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.game.model.Game;
import com.ccsw.tutorial.loan.model.Loan;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

/**
 * @author Max Escrivá
 */

/* Esta clase contendrá la construcción de la consulta según los criterios proporcionados.
Se creará una clase Specification por Entity que queramos consultar. */

public class LoanSpecification implements Specification<Loan> {

    private static final long serialVersionUID = 1L;

    private final SearchCriteria criteria;

    public LoanSpecification(SearchCriteria criteria) {

        this.criteria = criteria;
    }

    /* Spring llama a este método para construir el WHERE de la consulta.
     *  - Root: la entidad Loan. Da acceso a sus campos.
     *  - Query: la consulta completa.
     *  - Builder: construye condiciones (like, equal, greater than... ) */
    @Override
    public Predicate toPredicate(Root<Loan> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        /* Este if comprueba que el operador es doble punto y no está vacío.
         * El doble punto ( " : " ) significa "contiene/igual" */
        if (criteria.getOperation().equalsIgnoreCase(":") && criteria.getValue() != null) {

            Path<String> path = getPath(root); // Obtiene la ruta a la entidad.

            if (path.getJavaType() == String.class) { // Si la ruta es una clase String
                return builder.like(path, "%" + criteria.getValue() + "%"); // Creará una condición entre % (un like) (WHERE game_title LIKE %valorant%)
            } else {
                return builder.equal(path, criteria.getValue()); // Si no es String, pondrá un = (por ejemplo: category_id = 2 )
            }

        }
        return null;
    }

    /**
     * Permite navegar por campos anidados usando el punto como separador.
     * @param root entidad raíz donde queremos consultar.
     * @return expression
     */
    private Path<String> getPath(Root<Loan> root) {

        String key = criteria.getKey(); // Aquí tenemos el campo anidado que queremos separar. Eg: "game.title"

        String[] split = key.split("[.]", 0); // En un array, guardamos por separado la entidad y su atributo. Eg: ["game", "title"]

        Path<String> expression = root.get(split[0]); // Aquí guardará como raíz la primera posición del array. Eg: ["game"]
        for (int i = 1; i < split.length; i++) {
            expression = expression.get(split[i]); // Recorremos el resto del array y guardamos el atributo ("title") en la variable "expression". Eg: ["title"]
        }

        return expression;
    }

    /**
     * Devuelve un filtro aplicable sobre la entidad {@link Loan}. Filtramos según juego.
     * @param gameId PK de la entidad. ID del juego prestado.
     * @return {@link Specification}
     */
    public static Specification<Loan> hasGame(Long gameId){
        return ((root, query, criteriaBuilder) ->
        {
            if (gameId == null) { // Si es null, Spring ignora el filtro.
                return null;
            } else {
                return criteriaBuilder.equal(root.get("game").get("id"), gameId);
                /* SQL: WHERE game_id = :gameId (el juego que le pasamos) */
            }
        });
    }

    /**
     * Filtro aplicable sobre la entidad {@link Loan}. Filtramos según Cliente
     * @param clientId PK de la entidad. ID del cliente.
     * @return {@link Specification}
     */
    public static Specification<Loan> hasClient(Long clientId){
        return (root, query, criteriaBuilder) ->
        {
            if (clientId == null) {
                return null; // Ignoramos el filtro y mostramos todo
            } else {
                return criteriaBuilder.equal(root.get("client").get("id"), clientId);
                /* SQL: WHERE client_id = :clientId (el cliente que le pasamos) */
            }
        };
    }

    /**
     * Filtro, comprueba si la fecha dada está entre la fecha de inicio y la de final del préstamo
     * @param date fecha que queremos comprobar
     * @return {@link Specification}
     */
    public static Specification<Loan> containsDate(LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            if (date == null) {
                return null; // Se ignora el filtro
            } else {
                return criteriaBuilder.and( // Une ambos predicados con un AND
                        criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), date), // 1r predicado
                        criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), date) // 2do predicado
                ); /* SQL: WHERE date >= startDate AND date <= endDate */
            }
        };
    }
}