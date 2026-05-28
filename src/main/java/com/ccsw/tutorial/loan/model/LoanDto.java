package com.ccsw.tutorial.loan.model;


import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.game.model.GameDto;

import java.time.LocalDate;


/**
 * @author Max Escrivá
 */
public class LoanDto {
    /* -- ATRIBUTOS -- */

    private Long id;
    private ClientDto client; /* Recuerda que para que funcione el Mapper, deben llamarse igual en Loan y LoanDto */
    private GameDto game;  /* Recuerda que para que funcione el Mapper, deben llamarse igual en Loan y LoanDto */
    private LocalDate startDate;
    private LocalDate endDate;


    /* -- GETTERS Y SETTERS -- */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientDto getClient() {
        return client;
    }

    public void setClient(ClientDto client) {
        this.client = client;
    }

    public GameDto getGame() {
        return game;
    }

    public void setGame(GameDto game) {
        this.game = game;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
