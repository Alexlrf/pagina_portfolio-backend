package com.br.alex.portfolioapi.security;

import com.br.alex.portfolioapi.model.dao.LoginRepository;
import com.br.alex.portfolioapi.model.entity.Login;
import com.br.alex.portfolioapi.service.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AutenticacaoTokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final LoginRepository loginRepository;

    public AutenticacaoTokenFilter(TokenService tokenService, LoginRepository loginRepository) {
        this.tokenService = tokenService;
        this.loginRepository = loginRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = recuperarToken(request);
        boolean tokenValido = tokenService.isTokenValido(token);
        if (tokenValido){
            autenticarUsuario(token);
        }
        filterChain.doFilter(request, response);
    }

    private void autenticarUsuario(String token) {
        Long idUsuario = tokenService.getIdUsuario(token);
        Login usuarioLogado = loginRepository.findById(idUsuario).get();

        UsernamePasswordAuthenticationToken autenticacao =
                new UsernamePasswordAuthenticationToken(usuarioLogado, null, usuarioLogado.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(autenticacao);
    }

    private String recuperarToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")){
            return null;
        }
        return token.substring(7, token.length());
    }
}
