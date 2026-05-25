package com.ccsw.tutorial.game;

import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.game.model.Game;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author Max Escrivá
 */

/* Esta clase contendrá la construcción de la consulta según los criterios proporcionados.
Se creará una clase Specification por Entity que queramos consultar. */

public class GameSpecification implements Specification<Game> {

    private static final long serialVersionUID = 1L;

    private final SearchCriteria criteria;

    public GameSpecification(SearchCriteria criteria) {

        this.criteria = criteria;
    }

    /* Spring llama a este método para construir el WHERE de la consulta.
    *  - Root: la entidad Game. Da acceso a sus campos.
    *  - Query: la consulta completa.
    *  - Builder: construye condiciones (like, equal, greater than... ) */
    @Override
    public Predicate toPredicate(Root<Game> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
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
    private Path<String> getPath(Root<Game> root) {

        String key = criteria.getKey(); // Aquí tenemos el campo anidado que queremos separar. Eg: "game.title"

        String[] split = key.split("[.]", 0); // En un array, guardamos por separado la entidad y su atributo. Eg: ["game", "title"]

        Path<String> expression = root.get(split[0]); // Aquí guardará como raíz la primera posición del array. Eg: ["game"]
        for (int i = 1; i < split.length; i++) {
            expression = expression.get(split[i]); // Recorremos el resto del array y guardamos el atributo ("title") en la variable "expression". Eg: ["title"]
        }

        return expression;
    }

}