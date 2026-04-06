package br.com.samp.financemanager.exceptions.handler;


import br.com.samp.financemanager.exceptions.AccountDisableException;
import br.com.samp.financemanager.exceptions.AccountLockedException;
import br.com.samp.financemanager.exceptions.BusinessException;
import br.com.samp.financemanager.exceptions.DataBaseException;
import br.com.samp.financemanager.exceptions.InvalidCredentialsException;
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

    @ExceptionHandler(InvalidCredentialsException.class)
    public ProblemDetail handleInvalidCredentialsException(InvalidCredentialsException e) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        problem.setDetail(e.getMessage());
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }

    @ExceptionHandler(AccountLockedException.class)
    public ProblemDetail handleAccountLockedException(AccountLockedException e) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.TOO_MANY_REQUESTS);
        problem.setDetail(e.getMessage());
        problem.setProperty("timestamp", Instant.now());
        problem.setProperty("retry_after_second",e.getRetryAfterSeconds());
        return problem;
    }

    @ExceptionHandler(AccountDisableException.class)
    public ProblemDetail handleAccountDisableException(AccountDisableException e) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        problem.setDetail(e.getMessage());
        problem.setProperty("timestamp", Instant.now());
        return problem;

    }
}
