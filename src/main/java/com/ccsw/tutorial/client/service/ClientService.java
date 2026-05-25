package com.ccsw.tutorial.client.service;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;

import java.util.List;

/**
 * @author Max Escrivá
 */

/* -- LÓGICA DE NEGOCIO QUE SE PODRÁ REALIZAR -- */
public interface ClientService {

    /**
     * Recupera un {@link Client} a partir de su ID.
     * @param id id del cliente que queremos recuperar.
     * @return {@link Client}
     */
    Client get(Long id);

    /**
     * Método que retorna una lista de todos los clientes.
     * @return {@link List} de {@link Client}
     */
    List<Client> findAll();

    /**
     * Método para crear o actualizar un cliente.
     * @param id id del cliente a guardar o modificar.
     * @param dto datos de la entidad cliente.
     * @throws Exception Si el nombre del cliente ya está guardado anteriormente.
     */
    void save (Long id, ClientDto dto) throws Exception;

    /**
     * Método para eliminar un cliente, especificado por su ID.
     * @param id id del cliente a eliminar.
     * @throws Exception Si el ID del cliente no existe en la BD
     */
    void delete(Long id) throws Exception;

}
