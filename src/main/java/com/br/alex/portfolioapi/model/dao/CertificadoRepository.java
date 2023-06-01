package com.br.alex.portfolioapi.model.dao;

import com.br.alex.portfolioapi.model.entity.Certificado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificadoRepository extends JpaRepository<Certificado, Integer> {

    Certificado findByNome(String nome);
    Certificado findByUrlArquivo(String nome);
}
