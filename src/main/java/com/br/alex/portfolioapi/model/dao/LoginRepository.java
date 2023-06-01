package com.br.alex.portfolioapi.model.dao;

import com.br.alex.portfolioapi.model.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {

    Optional<Login> findByLogin(String login);
}
