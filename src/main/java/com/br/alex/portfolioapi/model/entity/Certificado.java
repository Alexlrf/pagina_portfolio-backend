package com.br.alex.portfolioapi.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.ReadOnlyProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
public class Certificado implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 2, max = 150)
    @NotBlank
    @Column(nullable = false, unique = true)
    private String nome;

    @Column(name = "tecnologia", nullable = false)
    @NotBlank
    private String tecnologia;

    @Column(name = "assunto", nullable = false)
    @NotBlank
    private String assunto;

    @Column(name = "resumo_conteudo", nullable = false)
    @NotBlank
    @JsonProperty(value = "resumo_conteudo")
    private String resumoConteudo;

    @Column(name = "url_arquivo", nullable = false, unique = true)
    @JsonProperty(value = "url_arquivo")
    @ReadOnlyProperty
    private String urlArquivo;

    @Column(name = "url_logo", nullable = false)
    @NotBlank
    @JsonProperty(value = "url_logo")
    private String urlLogo;

    @Column(name = "data_conclusao", nullable = false)
    @NotNull
    @JsonProperty(value = "data_conclusao")
    private LocalDate dataConclusao;

    @Column(name = "emissor_doc", nullable = false)
    @NotBlank
    @JsonProperty(value = "emissor_doc")
    private String emissorDoc;
}
