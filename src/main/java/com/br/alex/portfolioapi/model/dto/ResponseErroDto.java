package com.br.alex.portfolioapi.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ResponseErroDto {

    private int status;
    private String titulo;
    @JsonProperty(value = "data_hora")
    private OffsetDateTime dataHora;
    private List<PropriedadeRequest> propriedades;
    @Getter
    @AllArgsConstructor
    public static class PropriedadeRequest {
        private String nome;
        private String mensagem;
    }
}

