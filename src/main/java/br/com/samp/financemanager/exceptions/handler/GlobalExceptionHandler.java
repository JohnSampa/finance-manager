package br.com.samp.financemanager.exceptions.handler;


import br.com.samp.financemanager.exceptions.BusinessException;
import br.com.samp.financemanager.exceptions.DataBaseException;
import br.com.samp.financemanager.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFoundException(ResourceNotFoundException e) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setDetail(e.getMessage());
        problem.setProperty("timestamp", Instant.now());

        return problem;
    }

    @ExceptionHandler(DataBaseException.class)
    public ProblemDetail handleDataBaseException(DataBaseException e) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problem.setDetail(e.getMessage());
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }

    @ExceptionHandler(BusinessException.class)
    public ProblemDetail handleBusinessException(BusinessException e) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setDetail(e.getMessage());
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }
}
