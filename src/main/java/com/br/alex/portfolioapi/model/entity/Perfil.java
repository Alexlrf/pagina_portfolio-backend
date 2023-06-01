package com.br.alex.portfolioapi.model.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Data
public class Perfil implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_perfil")
    private Long idPerfil;

    @Column(name = "nome_perfil")
    private String nomePerfil;

    @Override
    public String getAuthority() {
        return nomePerfil;
    }
}

