package com.ccsw.tutorial.loan.service;

import com.ccsw.tutorial.client.service.ClientService;
import com.ccsw.tutorial.game.service.GameService;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;
import com.ccsw.tutorial.loan.repository.LoanRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {
    @Autowired
    LoanRepository loanRepository;

    @Autowired
    GameService gameService;

    @Autowired
    ClientService clientService;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Loan> findAll() {
        return (List<Loan>) this.loanRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Loan get(Long id) {
        return this.loanRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Loan> findPage(LoanSearchDto dto) {
        return this.loanRepository.findAll(dto.getPageable().getPageable());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, LoanDto dto) throws Exception {

        /* Validación de fechas */
        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new Exception("La fecha de fin no puede ser anterior a la de inicio.");
        }

        /* Período máximo de 14 días */
        long days = ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate()); // Días entre la fecha de inicio y la fecha de final
        if (days > 14) {
            throw new Exception("No se puede realizar un préstamo de más de 14 días.");
        }

        /* Juego no puede ser prestado a otro cliente en ese rango de fechas */
        List<Loan> gameConflicts = loanRepository.findOverlappingByGame(
                dto.getGameDto().getId(), dto.getStartDate(), dto.getEndDate(), dto.getGameDto().getId()); // Este es para excluirlo y que no salga en la lista

        if (!gameConflicts.isEmpty()) { // Si hay algo en la lista
            throw new Exception("El juego ya está prestado en ese período.");
        }

        /* El cliente no puede tener más de 2 préstamos activos en ese rango. */
        List<Loan> clientLoans = loanRepository.findOverlappingByClient(
                dto.getClientDto().getId(), dto.getStartDate(), dto.getEndDate(), dto.getClientDto().getId());

        if (!clientLoans.isEmpty()) {
            throw new Exception("El cliente ya tiene 2 préstamos activos.");
        }

        /* Si nada de lo de arriba se da... */

        Loan loan = null; // Creamos un objeto Loan sin inicializar.
        if (dto.getId() !=null) {
            this.loanRepository.findById(dto.getId()).orElseThrow(); // Si el ID dado no es nulo, lo buscará en la BD
        } else {
            loan = new Loan(); // Si el ID no existe antes, creamos un nuevo préstamo
        }

        /* Rellenamos los datos del préstamo */

        assert loan != null;
        loan.setGame(gameService.get(dto.getGameDto().getId()));
        loan.setClient(clientService.get(dto.getClientDto().getId()));
        loan.setStartDate(dto.getStartDate());
        loan.setEndDate(dto.getEndDate());

        /* Lo guardamos en la BD */
        this.loanRepository.save(loan);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {

        if (this.get(id) == null) {
            throw new Exception("No existe préstamo con este ID.");
        }

        this.loanRepository.deleteById(id);
    }
}
