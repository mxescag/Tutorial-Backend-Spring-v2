package com.ccsw.tutorial.loan.model;

import com.ccsw.tutorial.common.pagination.PageableRequest;

/**
 * @author Max Escrivá
 */
public class LoanSearchDto {
    /* -- ATRIBUTOS -- */
    private PageableRequest pageable;

    /* -- GETTERS Y SETTERS -- */
    public PageableRequest getPageable() {
        return pageable;
    }

    public void setPageable(PageableRequest pageable) {
        this.pageable = pageable;
    }
}
