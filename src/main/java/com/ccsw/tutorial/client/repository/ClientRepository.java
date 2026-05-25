package com.ccsw.tutorial.client.repository;

import com.ccsw.tutorial.client.model.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {
    boolean findByName(String name);
}
