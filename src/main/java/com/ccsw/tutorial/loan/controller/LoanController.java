package com.ccsw.tutorial.loan.controller;

import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;
import com.ccsw.tutorial.loan.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Loan", description = "API of Loan")
@RestController
@RequestMapping("/loan") /* Ruta base del controlador */
@CrossOrigin(origins = "*")
public class LoanController {
    /* Inyecciones */

    @Autowired
    LoanService loanService;
    @Autowired
    ModelMapper mapper;

    /**
     * Recupera un listado normal de présamos. {@link Loan}
     * @return {@link List} de {@link LoanDto}
     */
    /* Implementamos listado normal de préstamos */
    @Operation(summary = "Find", description = "Method that returns a List of Loan")
    @GetMapping(path = "")
    public List<LoanDto> findAll() {
        List<Loan> loans = this.loanService.findAll();
        return loans.stream().map(e -> mapper.map(e, LoanDto.class)).collect(Collectors.toList());
    }

    /**
     * Recupera un listado paginado de préstamos
     * @param dto datos de la entidad
     * @return {@link Page} de {@link LoanDto}
     */
    /* Implementamos listado paginado*/
    @Operation(summary = "Find Page", description = "Method that returns a page of Loan")
    @PostMapping(path = "")
    public Page<LoanDto> findPage(@RequestBody LoanSearchDto dto) {

        Page<Loan> page = this.loanService.findPage(dto);

        return new PageImpl<>( // Reconstruye el objeto de la Page pero con LoanDto en vez de Loan
                page.getContent(). // Obtiene la lista de Loan de esta página
                        stream() // Abre un stream para procesarlos 1 a 1
                        .map(e -> mapper.map(e, LoanDto.class)) // Convierte cada Loan a LoanDto usando ModelMapper
                        .collect(Collectors.toList()), // Recoge el resultado en una List<LoanDto>
                page.getPageable(), // cómo será la paginación
                page.getTotalElements());  // total de elementos
    }

    /**
     * Método para crear o actualizar un {@link Loan}
     * @param id PK de la entidad.
     * @param dto datos de la entidad
     * @throws Exception Lanzará distintas excepciones según qué error
     */
    @Operation(summary = "Save or Update", description = "Method that saves or updates a Loan")
    @RequestMapping(path = {"", "/{id}"}, method = RequestMethod.PUT)
    public void save (@PathVariable(name = "id", required = false) Long id, @RequestBody LoanDto dto) throws Exception {

        this.loanService.save(id, dto);
    }

    /**
     * Método para eliminar un {@link Loan}
     * @param id PK de la entidad
     * @throws Exception Lanzará excepción si el ID no corresponde a ningún préstamo existente.
     */
    @Operation(summary = "Delete", description = "Method that deletes a Loan")
    @DeleteMapping(path = "/{id}")
    public void delete (@PathVariable Long id) throws Exception {

        this.loanService.delete(id);
    }
}
