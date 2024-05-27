package com.example.Nubida.JWT;

import com.example.Nubida.CustomUser.CustomUserDetails;
import com.example.Nubida.Entity.Traveler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;


    public JWTFilter(JWTUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if(authorization==null||!authorization.startsWith("Bearer ")){
            System.out.println("token null");
            filterChain.doFilter(request,response);
            return;
        }

        String token = authorization.split(" ")[1];
        System.out.println(token);

        if(jwtUtil.isExpired(token)){
            System.out.println("token expired");
            filterChain.doFilter(request,response);
            return;
        }

        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

//        System.out.println(username);
//        System.out.println(role);

        Traveler traveler = new Traveler();
        traveler.setUsername(username);
        traveler.setRole(role);
        traveler.setNickname("tempNickname");
        traveler.setPassword("temppassword");

//        System.out.println("hihi");
//        System.out.println(traveler.getUsername());

        CustomUserDetails customUserDetails = new CustomUserDetails(traveler);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails,customUserDetails.getPassword(),customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request,response);
    }
}
