package com.ccsw.tutorial.client.controller;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.client.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Max Escrivá
 */
@Tag(name = "Client", description = "API of Client")
@RestController
@RequestMapping("/client") /* Define la ruta base del controlador. Todos los endpoints heredan este prefijo. */
@CrossOrigin(origins = "*")
public class ClientController {

    /* Inyecciones para hacer funcional */
    @Autowired
    ModelMapper mapper;

    @Autowired
    ClientService clientService;

    /* -- OPERACIONES -- */

    /**
     * Método para recuperar todos los clientes.
     * @return {@link List} de {@link ClientDto}
     */
    @Operation(summary = "Find", description = "Method that returns a List of Client")
    @GetMapping(path = "")
    public List<ClientDto> findAll(){
        // Llama a la capa servicios
        List<Client> clients = this.clientService.findAll();

        // Como ésta retorna una List de Client, tenemos que mapearla a ClientDto
        return clients.stream().map(e -> mapper.map(e, ClientDto.class)).collect(Collectors.toList());

    }

    /**
     * Método para crear o modificar un cliente
     * @param id PK de la entidad. ID del cliente
     * @param dto Datos del cliente en el body
     * @throws Exception Lanzará esto si el nombre del cliente ya está guardado anteriormente
     */
    @Operation(summary = "Save or Update", description = "Method that saves or updates a Client")
    @RequestMapping(path = {"", "/{id"}, method = RequestMethod.PUT)
    public void save(@PathVariable(name = "id", required = false) Long id,
                     @RequestBody ClientDto dto) throws Exception {
        this.clientService.save(id, dto);
    }

    /**
     * Método para borrar un cliente
     * @param id PK de la entidad. ID del cliente
     * @throws Exception Lanzará esto si no encuentra el ID para borrar.
     */
    @Operation(summary = "Delete", description = "Method that deletes a Client")
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) throws Exception {
        this.clientService.delete(id);
    }
}
