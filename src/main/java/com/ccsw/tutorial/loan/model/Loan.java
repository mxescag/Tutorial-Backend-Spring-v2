package com.ccsw.tutorial.loan.model;


import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.game.model.Game;
import jakarta.persistence.*;

import java.util.Date;

/**
 * @author Max Escrivá
 */

@Entity
@Table(name = "loan")
public class Loan {
    /* -- ATRIBUTOS -- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Column(name = "startDate", nullable = false)
    private Date startDate;

    @Column(name = "endDate",  nullable = false)
    private Date endDate;


    /* -- GETTERS Y SETTERS -- */

    /**
     * Retorna el ID del préstamo.
      * @return {@link #id} del préstamo
     */
    public Long getId() {
        return id;
    }

    /**
     * Asigna un nuevo valor a ID
     * @param id nuevo valor de {@link #getId()}
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retorna el cliente en el préstamo.
     * @return {@link #client}
     */
    public Client getClient() {
        return client;
    }

    /**
     * Asigna un nuevo valor al cliente
     * @param client nuevo valor de {@link #getClient()}
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Devuelve el juego prestado.
     * @return {@link #game}
     */
    public Game getGame() {
        return game;
    }

    /**
     * Asigna un nuevo valor al juego
     * @param game nuevo valor de {@link #getGame()}
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Devuelve la fecha de inicio del préstamo.
     * @return {@link #startDate}
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Asigna nuevo valor a la fecha de inicio del préstamo.
     * @param startDate nuevo valor de {@link #getStartDate()}
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Devuelve la fecha de final de préstamo.
     * @return {@link #endDate}
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Asigna un nuevo valor a la fecha de final de préstamo.
     * @param endDate nuevo valor de {@link #getEndDate()}
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
