package com.team1.tm.bantutani.app.configuration;

import com.team1.tm.bantutani.app.dto.response.StringResponse;
import io.jsonwebtoken.JwtException;
import org.hibernate.JDBCException;
import org.hibernate.QueryTimeoutException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.ServletException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionHandlerConfig {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public StringResponse handleValidationError(MethodArgumentNotValidException e) {
        return new StringResponse.Builder().status("error").message(e.getMessage()).build();
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public StringResponse handleValidationNullError(NullPointerException e) {
        return new StringResponse.Builder().status("error").message(e.getMessage()).build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public StringResponse handleValidationIllegalError(IllegalArgumentException e) {
        return new StringResponse.Builder().status("error").message(e.getMessage()).build();
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public StringResponse handleValidationIOError(IOException e) {
        return new StringResponse.Builder().status("error").message(e.getMessage()).build();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public StringResponse handleValidationRuntimeError(RuntimeException e) {
        return new StringResponse.Builder().status("error").message(e.getMessage()).build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @ResponseBody
    public StringResponse handleValidationNoElementError(NoSuchElementException e) {
        return new StringResponse.Builder().status("error").message(e.getMessage()).build();
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public StringResponse handleValidationNotFoundError(UsernameNotFoundException e) {
        return new StringResponse.Builder().status("error").message(e.getMessage()).build();
    }

    @ExceptionHandler({JwtException.class, org.springframework.security.oauth2.jwt.JwtException.class})
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    @ResponseBody
    public StringResponse handleValidationTokenError(JwtException e) {
        return new StringResponse.Builder().status("error").message(e.getMessage()).build();
    }

    @ExceptionHandler(ServletException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public StringResponse handleValidationServletError(ServletException e) {
        return new StringResponse.Builder().status("error").message(e.getMessage()).build();
    }

    @ExceptionHandler(NoSuchAlgorithmException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public StringResponse handleValidationTokenAuthError(NoSuchAlgorithmException e) {
        return new StringResponse.Builder().status("error").message(e.getMessage()).build();
    }

    @ExceptionHandler({AuthenticationException.class, javax.security.sasl.AuthenticationException.class})
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    @ResponseBody
    public StringResponse handleValidationAuthError(Exception e) {
        return new StringResponse.Builder().status("error").message(e.getMessage()).build();
    }

    @ExceptionHandler(ParseException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public StringResponse handleValidationParseError(ParseException e) {
        return new StringResponse.Builder().status("error").message(e.getMessage()).build();
    }

    @ExceptionHandler({DataIntegrityViolationException.class, DataAccessException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public StringResponse handleValidationDataError(Exception e) {
        return new StringResponse.Builder().status("error").message(e.getMessage()).build();
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public StringResponse handleValidationDataSQLError(SQLIntegrityConstraintViolationException e) {
        return new StringResponse.Builder().status("error").message(e.getMessage()).build();
    }

    @ExceptionHandler(DataException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public StringResponse handleValidationDataDBError(JDBCException e) {
        return new StringResponse.Builder().status("error").message(e.getMessage()).build();
    }

    @ExceptionHandler({ConstraintViolationException.class, javax.validation.ConstraintViolationException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public StringResponse handleValidationDataConstraintError(JDBCException e) {
        return new StringResponse.Builder().status("error").message(e.getMessage()).build();
    }

    @ExceptionHandler(JDBCConnectionException.class)
    @ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public StringResponse handleValidationDataConnectionError(JDBCException e) {
        return new StringResponse.Builder().status("error").message(e.getMessage()).build();
    }

    @ExceptionHandler({QueryTimeoutException.class, org.springframework.dao.QueryTimeoutException.class})
    @ResponseStatus(code = HttpStatus.REQUEST_TIMEOUT)
    @ResponseBody
    public StringResponse handleValidationDataTimeoutError(JDBCException e) {
        return new StringResponse.Builder().status("error").message(e.getMessage()).build();
    }

    @ExceptionHandler(JDBCException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public StringResponse handleValidationDataError(JDBCException e) {
        return new StringResponse.Builder().status("error").message(e.getMessage()).build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public StringResponse handleValidationInternalError(Exception e) {
        return new StringResponse.Builder().status("error").message(e.getMessage()).build();
    }
}
