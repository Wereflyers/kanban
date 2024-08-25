package ru.kanban.main.web.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kanban.main.dto.error.ErrorResponse;
import ru.kanban.main.exception.AccessDenialException;
import ru.kanban.main.exception.DuplicateException;
import ru.kanban.main.exception.EntityNotFoundException;
import ru.kanban.main.exception.WrongConditionException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The type Error handler.
 */
@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Handle duplicate exception error response.
     *
     * @param e the e
     * @return the error response
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDuplicateException(final DuplicateException e) {
        e.printStackTrace();
        log.error(Arrays.toString(e.getStackTrace()));
        return ErrorResponse.builder()
                .message(e.getMessage())
                .error(e.getClass().getSimpleName())
                .status(HttpStatus.CONFLICT.toString())
                .timestamp(LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter))
                .build();
    }

    /**
     * Handle wrong condition exception error response.
     *
     * @param e the e
     * @return the error response
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleWrongConditionException(final WrongConditionException e) {
        e.printStackTrace();
        log.error(String.valueOf(e));
        return ErrorResponse.builder()
                .message(e.getMessage())
                .error(e.getClass().getSimpleName())
                .status(HttpStatus.BAD_REQUEST.toString())
                .timestamp(LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter))
                .build();
    }

    /**
     * Handle user not found exception error response.
     *
     * @param e the e
     * @return the error response
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFoundException(final EntityNotFoundException e) {
        e.printStackTrace();
        log.error(String.valueOf(e));
        return ErrorResponse.builder()
                .message(e.getMessage())
                .error(e.getClass().getSimpleName())
                .status(HttpStatus.NOT_FOUND.toString())
                .timestamp(LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter))
                .build();
    }

    /**
     * Handle null pointer exception error response.
     *
     * @param e the e
     * @return the error response
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNullPointerException(final NullPointerException e) {
        e.printStackTrace();
        log.error(String.valueOf(e));
        return ErrorResponse.builder()
                .message(e.getMessage())
                .error(e.getClass().getSimpleName())
                .status(HttpStatus.NOT_FOUND.toString())
                .timestamp(LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter))
                .build();
    }

    /**
     * Handle validation errors list.
     *
     * @param e the e
     * @return the list
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ErrorResponse> handleValidationErrors(final MethodArgumentNotValidException e) {
        e.printStackTrace();
        List<ErrorResponse> details = new ArrayList<>();
        for (ObjectError error : e.getBindingResult().getAllErrors()) {
            details.add(ErrorResponse.builder()
                    .message(error.getDefaultMessage())
                    .error(error.getClass().getSimpleName())
                    .status(HttpStatus.BAD_REQUEST.toString())
                    .timestamp(LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter))
                    .build());
        }
        log.error(String.valueOf(e));
        return details;
    }

    /**
     * Handle access denial exception error response.
     *
     * @param e the e
     * @return the error response
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleAccessDenialException(final AccessDenialException e) {
        e.printStackTrace();
        log.error(String.valueOf(e));
        return ErrorResponse.builder()
                .message(e.getMessage())
                .error(e.getClass().getSimpleName())
                .status(HttpStatus.CONFLICT.toString())
                .timestamp(LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter))
                .build();
    }

    /**
     * Handle sql exception error response.
     *
     * @param e the e
     * @return the error response
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleSqlException(final SQLException e) {
        e.printStackTrace();
        log.error(String.valueOf(e));
        return ErrorResponse.builder()
                .message(e.getMessage())
                .error(e.getClass().getSimpleName())
                .status(HttpStatus.BAD_REQUEST.toString())
                .timestamp(LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter))
                .build();
    }
}
