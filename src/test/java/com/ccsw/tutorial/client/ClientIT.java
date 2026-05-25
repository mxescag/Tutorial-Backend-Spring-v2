package com.ccsw.tutorial.client;


import com.ccsw.tutorial.category.model.CategoryDto;
import com.ccsw.tutorial.client.model.ClientDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests de integración
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // Puerto aleatorio cada vez.
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // Tests son transaccionales, no modificarán la BD al terminar.
public class ClientIT {

    /* Constantes para tests */
    public static final String LOCALHOST = "http://localhost:";
    public static final String SERVICE_PATH = "/client";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    /* Esto se declara fuera del test, a nivel de clase para indicar a Jackson que el JSON debe convertirse a List<ClientDto> */
    ParameterizedTypeReference<List<ClientDto>> responseType = new ParameterizedTypeReference<List<ClientDto>>(){};

    @Test
    public void findAllShouldReturnAllClients(){
        ResponseEntity<List<ClientDto>> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH, // URL
                HttpMethod.GET, // método HTTP que usamos
                null, // no body no headers
                responseType // deserializado
        );

        /* verifica las respuestas */
        assertNotNull(response); // ¿Existe respuesta? OK
        assertNotNull(response.getBody());
        assertEquals(4, response.getBody().size()); // ¿Devuelve el número de clientes que esperamos? OK
    }
}
