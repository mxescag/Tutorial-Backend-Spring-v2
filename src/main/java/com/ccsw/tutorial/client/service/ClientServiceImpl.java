package com.ccsw.tutorial.client.service;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.client.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    /* Inyectamos el repositorio porque vamos a trabajar con la DB */
    @Autowired
    ClientRepository clientRepository;


    /**
     * {@inheritDoc}
     */
    @Override
    public Client get(Long id) {
        return this.clientRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Client> findAll() {
        return (List<Client>) this.clientRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, ClientDto dto) throws Exception {

        Client client;
        if (id != null) {
            client = this.get(id);
        } else {
            client = null;
        }

        if (client == null) {
            if (this.clientRepository.existsByName(dto.getName())){ // Si en la BD se encuentra el nombre, significa que ya está registrado.
                throw new Exception("El cliente ya está registrado.");
            }
            // Si el cliente no existe antes (el ID es nulo)
            client = new Client(); // Creamos un objeto Client vacío y nuevo.
        } else {
            // Si el ID se ha encontrado
            client = this.get(id); // Actualizamos

        }

        assert client != null; // Nos aseguramos que client no está vacío
        client.setName(dto.getName()); // Asignamos el nombre recibido en el body

        this.clientRepository.save(client); // Guardamos el objeto en la base de datos
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {

        if (this.get(id) == null) {
            throw new Exception("El cliente con ese ID no existe.");
        }

        this.clientRepository.deleteById(id);
    }
}
