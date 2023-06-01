package com.br.alex.portfolioapi.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public class CertificadoInput {

    @Size(min = 2, max = 150)
    @NotBlank
    private String nome;

    @NotBlank
    private String tecnologia;

    @NotBlank
    private String assunto;

    @NotBlank
    @JsonProperty(value = "resumo_conteudo")
    private String resumoConteudo;

    @NotBlank
    @JsonProperty(value = "url_logo")
    private String urlLogo;

    @NotNull
    @JsonProperty(value = "data_conclusao")
    private LocalDate dataConclusao;

    @NotBlank
    @JsonProperty(value = "emissor_doc")
    private String emissorDoc;
}
