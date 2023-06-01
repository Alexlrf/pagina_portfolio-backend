package com.br.alex.portfolioapi.exceptions.handler;

import com.br.alex.portfolioapi.exceptions.RegraNegocioException;
import com.br.alex.portfolioapi.model.dto.ResponseErroDto;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<ResponseErroDto.PropriedadeRequest> erros = new ArrayList<>();

        for (ObjectError erro : ex.getBindingResult().getAllErrors()) {
            String nome = ((FieldError) erro).getField();
            String mensagem = messageSource.getMessage(erro, LocaleContextHolder.getLocale());
            erros.add(new ResponseErroDto.PropriedadeRequest(nome, mensagem));
        }

        ResponseErroDto bodyErroResponse = new ResponseErroDto();
        bodyErroResponse.setStatus(status.value());
        bodyErroResponse.setDataHora(OffsetDateTime.now());
        bodyErroResponse.setTitulo("Uma ou mais propriedades possuem valor(es) inválido(s)");
        bodyErroResponse.setPropriedades(erros);

        return handleExceptionInternal(ex, bodyErroResponse, headers, status, request);
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<Object> handleRegraNegocioException(Exception ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseErroDto bodyErroResponse = new ResponseErroDto();
        bodyErroResponse.setStatus(httpStatus.value());
        bodyErroResponse.setDataHora(OffsetDateTime.now());
        bodyErroResponse.setTitulo(ex.getMessage());
        return handleExceptionInternal(ex, bodyErroResponse, new HttpHeaders(), httpStatus, request);
    }
}
