package com.rocket.rocketponto.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Extrai o cabeçalho "Authorization" da requisição
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // Verifica se o cabeçalho contém um token JWT
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7); // Remove o prefixo "Bearer " para obter o token
            username = jwtUtil.extractUsername(jwt); // Extrai o nome de usuário do token
        }

        // Se o nome de usuário foi extraído e não há autenticação no contexto atual
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Carrega os detalhes do usuário com base no nome de usuário
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // Valida o token JWT
            if (jwtUtil.validateToken(jwt, username)) {

                // Cria um objeto de autenticação
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                // Define os detalhes da autenticação (como o endereço IP, por exemplo)
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Configura a autenticação no contexto do Spring Security
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // Continua o processamento da requisição
        chain.doFilter(request, response);
    }
}