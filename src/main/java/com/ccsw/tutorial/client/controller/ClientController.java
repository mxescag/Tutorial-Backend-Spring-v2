package com.ccsw.tutorial.client.controller;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.client.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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

    @Operation(summary = "Find", description = "Method that returns a List of Client")
    @GetMapping(path = "")
    public List<ClientDto> findAll(){
        // Llama a la capa servicios
        List<Client> clients = this.clientService.findAll();

        // Como ésta retorna una List de Client, tenemos que mapearla a ClientDto
        return clients.stream().map(e -> mapper.map(e, ClientDto.class)).collect(Collectors.toList());

    }

}
