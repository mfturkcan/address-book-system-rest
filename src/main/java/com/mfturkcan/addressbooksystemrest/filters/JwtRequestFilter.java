package com.mfturkcan.addressbooksystemrest.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.mfturkcan.addressbooksystemrest.models.BookUserDetail;
import com.mfturkcan.addressbooksystemrest.services.BookUserDetailService;
import com.mfturkcan.addressbooksystemrest.utils.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private BookUserDetailService userDetailsService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private JwtUtils jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        System.out.println(request.getRequestURI());
        if(request.getRequestURI().equals("/api/auth/user")){
            System.out.println("matches");
            chain.doFilter(request, response);
            return;
        }
        try{
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                username = jwtUtil.extractUsername(jwt);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);


                    if (jwtUtil.validateToken(jwt, userDetails)) {

                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken
                                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }

                chain.doFilter(request, response);
                return;
            }
        catch(ExpiredJwtException e){

            var map = new HashMap<String, String>();
            map.put("message", "JWT expired");
            map.put("status", HttpStatus.UNAUTHORIZED.toString());

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            mapper.writeValue(response.getWriter(), map);

            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        catch (Exception e){
            var map = new HashMap<String, String>();
            map.put("message", e.getMessage());
            map.put("status", HttpStatus.BAD_REQUEST.toString());

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            mapper.writeValue(response.getWriter(), map);

            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}