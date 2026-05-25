package com.ccsw.tutorial.loan.repository;

import com.ccsw.tutorial.loan.model.Loan;
import org.springframework.data.repository.CrudRepository;

public interface LoanRepository extends CrudRepository<Loan, Long> {
}
