package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.common.pagination.PageableRequest;
import com.ccsw.tutorial.config.ResponsePage;
import com.ccsw.tutorial.game.model.GameDto;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LoanIT {

    public static final String LOCALHOST    = "http://localhost:";
    public static final String SERVICE_PATH = "/loan";

    private static final int  TOTAL_LOANS        = 4;
    private static final int  PAGE_SIZE          = 3;
    private static final Long DELETE_LOAN_ID     = 1L;
    private static final Long EXISTING_GAME_ID   = 2L;  // game del préstamo 1 (client 1, 03/01-07/01)
    private static final Long EXISTING_CLIENT_ID = 1L;  // client con préstamos 1 y 3
    private static final Long OTHER_CLIENT_ID    = 2L;
    private static final Long GAME_FREE_ID       = 6L;  // juego sin préstamos en data.sql

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    ParameterizedTypeReference<ResponsePage<LoanDto>> responseTypePage =
            new ParameterizedTypeReference<ResponsePage<LoanDto>>() {};

    ParameterizedTypeReference<List<LoanDto>> responseTypeList =
            new ParameterizedTypeReference<List<LoanDto>>() {};


    @Test
    public void findAllShouldReturnAllLoans() {
        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH,
                HttpMethod.GET,
                null,
                responseTypeList
        );

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(TOTAL_LOANS, response.getBody().size());
    }


    @Test
    public void findFirstPageShouldReturnFirstResults() {
        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST,
                new HttpEntity<>(searchDto),
                responseTypePage
        );

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());
        assertEquals(PAGE_SIZE, response.getBody().getContent().size());
    }

    @Test
    public void findSecondPageShouldReturnRemainingResults() {
        int remainingElements = TOTAL_LOANS - PAGE_SIZE;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(1, PAGE_SIZE));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST,
                new HttpEntity<>(searchDto),
                responseTypePage
        );

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());
        assertEquals(remainingElements, response.getBody().getContent().size());
    }


    @Test
    public void findPageFilteredByGameShouldReturnOnlyThatGameLoans() {
        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, TOTAL_LOANS));
        searchDto.setGameId(EXISTING_GAME_ID);

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST,
                new HttpEntity<>(searchDto),
                responseTypePage
        );

        assertNotNull(response);
        assertNotNull(response.getBody());
        response.getBody().getContent().forEach(loan ->
                assertEquals(EXISTING_GAME_ID, loan.getGame().getId())
        );
    }

    @Test
    public void findPageFilteredByClientShouldReturnOnlyThatClientLoans() {
        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, TOTAL_LOANS));
        searchDto.setClientId(EXISTING_CLIENT_ID);

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST,
                new HttpEntity<>(searchDto),
                responseTypePage
        );

        assertNotNull(response);
        assertNotNull(response.getBody());
        response.getBody().getContent().forEach(loan ->
                assertEquals(EXISTING_CLIENT_ID, loan.getClient().getId())
        );
    }

    @Test
    public void findPageFilteredByDateShouldReturnOnlyActiveLoans() {
        LocalDate searchDate = LocalDate.of(2026, 1, 5);

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, TOTAL_LOANS));
        searchDto.setDate(searchDate);

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST,
                new HttpEntity<>(searchDto),
                responseTypePage
        );

        assertNotNull(response);
        assertNotNull(response.getBody());
        response.getBody().getContent().forEach(loan -> {
            assertFalse(loan.getStartDate().isAfter(searchDate));
            assertFalse(loan.getEndDate().isBefore(searchDate));
        });
    }


    @Test
    public void saveWithoutIdShouldCreateNewLoan() {
        long newTotalLoans = TOTAL_LOANS + 1;

        GameDto gameDto = new GameDto();
        gameDto.setId(GAME_FREE_ID);
        ClientDto clientDto = new ClientDto();
        clientDto.setId(OTHER_CLIENT_ID);

        LoanDto dto = new LoanDto();
        dto.setGame(gameDto);
        dto.setClient(clientDto);
        dto.setStartDate(LocalDate.of(2026, 3, 1));
        dto.setEndDate(LocalDate.of(2026, 3, 7));

        restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH,
                HttpMethod.PUT,
                new HttpEntity<>(dto),
                Void.class
        );

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, (int) newTotalLoans));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST,
                new HttpEntity<>(searchDto),
                responseTypePage
        );

        assertNotNull(response.getBody());
        assertEquals(newTotalLoans, response.getBody().getTotalElements());
    }


    @Test
    public void saveWithEndDateBeforeStartDateShouldThrowException() {

        LoanDto dto = new LoanDto();
        GameDto gameDto = new GameDto();
        gameDto.setId(GAME_FREE_ID);
        ClientDto clientDto = new ClientDto();
        clientDto.setId(OTHER_CLIENT_ID);

        dto.setGame(gameDto);
        dto.setClient(clientDto);
        dto.setStartDate(LocalDate.of(2026, 3, 10));
        dto.setEndDate(LocalDate.of(2026, 3, 5));  // fin < inicio

        ResponseEntity<?> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH,
                HttpMethod.PUT,
                new HttpEntity<>(dto),
                Void.class
        );

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void saveWithMoreThan14DaysShouldThrowException() {

        LoanDto dto = new LoanDto();

        GameDto gameDto = new GameDto();
        gameDto.setId(GAME_FREE_ID);
        ClientDto clientDto = new ClientDto();
        clientDto.setId(OTHER_CLIENT_ID);


        dto.setGame(gameDto);
        dto.setClient(clientDto);
        dto.setStartDate(LocalDate.of(2026, 3, 1));
        dto.setEndDate(LocalDate.of(2026, 3, 20));  // 19 días

        ResponseEntity<?> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH,
                HttpMethod.PUT,
                new HttpEntity<>(dto),
                Void.class
        );

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void saveWithGameAlreadyLoanedShouldThrowException() {

        LoanDto dto = new LoanDto();

        GameDto gameDto = new GameDto();
        gameDto.setId(EXISTING_GAME_ID);
        ClientDto clientDto = new ClientDto();
        clientDto.setId(OTHER_CLIENT_ID);

        dto.setGame(gameDto);
        dto.setClient(clientDto);
        dto.setStartDate(LocalDate.of(2026, 1, 5));  // se solapa
        dto.setEndDate(LocalDate.of(2026, 1, 10));

        ResponseEntity<?> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH,
                HttpMethod.PUT,
                new HttpEntity<>(dto),
                Void.class
        );

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void saveWithClientHaving2ActiveLoansShouldThrowException() {

        LoanDto dto = new LoanDto();

        GameDto gameDto = new GameDto();
        gameDto.setId(GAME_FREE_ID);
        ClientDto clientDto = new ClientDto();
        clientDto.setId(EXISTING_CLIENT_ID); // Este cliente ya tiene 2 juegos prestados

        dto.setGame(gameDto);
        dto.setClient(clientDto);
        dto.setStartDate(LocalDate.of(2026, 1, 5));  // se solapa con ambos
        dto.setEndDate(LocalDate.of(2026, 1, 8));

        ResponseEntity<?> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH,
                HttpMethod.PUT,
                new HttpEntity<>(dto),
                Void.class
        );

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }


    @Test
    public void deleteWithExistingIdShouldDeleteLoan() {
        long newTotalLoans = TOTAL_LOANS - 1;

        restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH + "/" + DELETE_LOAN_ID,
                HttpMethod.DELETE,
                null,
                Void.class
        );

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, TOTAL_LOANS));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST,
                new HttpEntity<>(searchDto),
                responseTypePage
        );

        assertNotNull(response.getBody());
        assertEquals(newTotalLoans, response.getBody().getTotalElements());
    }

    @Test
    public void deleteWithNotExistingIdShouldThrowException() {
        long nonExistingId = TOTAL_LOANS + 100;

        ResponseEntity<?> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH + "/" + nonExistingId,
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}