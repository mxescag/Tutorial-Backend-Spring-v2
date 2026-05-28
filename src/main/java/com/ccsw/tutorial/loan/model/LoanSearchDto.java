package com.ccsw.tutorial.loan.model;

import com.ccsw.tutorial.common.pagination.PageableRequest;

import java.time.LocalDate;

/**
 * @author Max Escrivá
 */
public class LoanSearchDto {
    /* -- ATRIBUTOS -- */
    private PageableRequest pageable;
    private Long gameId;
    private Long clientId;
    private LocalDate date;

    /* -- GETTERS Y SETTERS -- */
    public PageableRequest getPageable() {
        return pageable;
    }

    public void setPageable(PageableRequest pageable) {
        this.pageable = pageable;
    }

    /* Añadimos campos de filtro, si no, siempre devolverá resultado sin filtrar. */

    public Long getGameId() { return gameId; }
    public void setGameId(Long gameId) { this.gameId = gameId; }

    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}
