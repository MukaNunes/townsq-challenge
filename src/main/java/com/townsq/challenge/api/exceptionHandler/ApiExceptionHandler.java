package com.townsq.challenge.api.exceptionHandler;

import com.townsq.challenge.domain.exceptions.InvalidTokenUserDataException;
import com.townsq.challenge.domain.exceptions.OrderNotFoundException;
import com.townsq.challenge.domain.exceptions.PaymentAlreadyProcessedException;
import com.townsq.challenge.domain.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String GENERIC_ERROR_MESSAGE = "Unexpected internal error";

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
                                                                      HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity.status(status).headers(headers).build();
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<?> handleOrderNotFoundException(OrderNotFoundException ex,
                                                          WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(status, detail)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleNotFoundEntity(UserNotFoundException ex,
                                                  WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(status, detail)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(InvalidTokenUserDataException.class)
    public ResponseEntity<?> handleInvalidUserJwt(InvalidTokenUserDataException ex,
                                                  WebRequest request) {

        HttpStatus status = HttpStatus.FORBIDDEN;
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(status, detail)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(PaymentAlreadyProcessedException.class)
    public ResponseEntity<?> handlePaymentAlreadyProcessedException(PaymentAlreadyProcessedException ex,
                                                                    WebRequest request) {

        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(status, detail)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<?> handleinvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException ex) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(status, detail)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, null);
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatusCode status, WebRequest request) {

        if (body == null) {
            body = Problem.builder()
                    .timestamp(OffsetDateTime.now())
                    .title(HttpStatus.valueOf(status.value()).getReasonPhrase())
                    .status(status.value())
                    .userMessage(GENERIC_ERROR_MESSAGE)
                    .build();
        } else if (body instanceof String) {
            body = Problem.builder()
                    .timestamp(OffsetDateTime.now())
                    .title((String) body)
                    .status(status.value())
                    .userMessage(GENERIC_ERROR_MESSAGE)
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatusCode status, String detail) {

        return Problem.builder()
                .timestamp(OffsetDateTime.now())
                .status(status.value());
    }

}
