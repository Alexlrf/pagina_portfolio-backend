package com.br.alex.portfolioapi.service;

import com.br.alex.portfolioapi.model.entity.Login;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class TokenService {

    @Value("${jwt.expiracao}")
    private String expiracao;

    @Value("${jwt.secret}")
    private String secret;

    public String gerarToken(Authentication authentication) {
        Login usuarioLogado = (Login) authentication.getPrincipal();
        Date dataAtual = new Date();
        Date expiracaoToken = new Date(dataAtual.getTime() + Long.parseLong(expiracao));

        return Jwts.builder()
                .setIssuer("Api Back-end site_portfólio")
                .setSubject(usuarioLogado.getIdLogin().toString())
                .setIssuedAt(dataAtual)
                .setExpiration(expiracaoToken)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean isTokenValido(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(this.secret)
                    .parseClaimsJws(token);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public Long getIdUsuario(String token) {
        Claims body = Jwts.parser()
                .setSigningKey(this.secret)
                .parseClaimsJws(token).getBody();
        return Long.parseLong(body.getSubject());
    }
}
