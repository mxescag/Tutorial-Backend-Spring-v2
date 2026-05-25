package com.ccsw.tutorial.loan.model;


import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.game.model.GameDto;

import java.util.Date;

/**
 * @author Max Escrivá
 */
public class LoanDto {

    /* -- ATRIBUTOS -- */

    private Long id;
    private ClientDto clientDto;
    private GameDto gameDto;
    private Date startDate;
    private Date endDate;


    /* -- GETTERS Y SETTERS -- */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientDto getClientDto() {
        return clientDto;
    }

    public void setClientDto(ClientDto clientDto) {
        this.clientDto = clientDto;
    }

    public GameDto getGameDto() {
        return gameDto;
    }

    public void setGameDto(GameDto gameDto) {
        this.gameDto = gameDto;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
