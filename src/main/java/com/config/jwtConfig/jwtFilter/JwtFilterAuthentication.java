package com.config.jwtConfig.jwtFilter;

import com.config.jwtConfig.jwtUtil.JwtTokenUtil;
import com.model.ResponseData;
import io.jsonwebtoken.ExpiredJwtException;
import liquibase.pro.packaged.co;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

public class JwtFilterAuthentication extends OncePerRequestFilter implements Serializable {
    private static final long serialVersionUID = 1L;

    private final UserDetailsService userService;

    private final JwtTokenUtil jwtTokenUtil;

    public JwtFilterAuthentication(UserDetailsService userService, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    // check authorize header ìf not present throw 401 else set securityContext
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("jwt filter load");

        String userLoginToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; ++i) {
                if (cookies[i].getName().equals("user_login")) {
                    userLoginToken = cookies[i].getValue();
                    break;
                }
            }
        }

        System.out.printf("token: " + userLoginToken);

        String username = null;
        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        // only the Token
        if (userLoginToken != null && !userLoginToken.isEmpty()) {
            try {
                username = jwtTokenUtil.getUsernameFromToken(userLoginToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }

            if (username != null) {
                // Once we get the token validate it.
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = this.userService.loadUserByUsername(username);

                    // if token is valid configure Spring Security to manually set
                    // authentication
                    if (jwtTokenUtil.validateToken(userLoginToken, userDetails)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken
                                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        // After setting the Authentication in the context, we specify
                        // that the current user is authenticated. So it passes the
                        // Spring Security Configurations successfully.
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                        System.out.println("filter roles: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
                        filterChain.doFilter(request, response);
                    } else {
                        invalidToken(response);
                    }
                } else {
                    invalidToken(response);
                }
            } else {
                invalidToken(response);
            }
        } else {
            logger.warn("JWT does not begin with Bearer String");

            ResponseData res = new ResponseData("error", "miss authorization header");
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().println(res.toJson());
        }
    }

    // reuse respone for 401 request
    public void invalidToken(HttpServletResponse response) throws IOException {
        ResponseData res = new ResponseData("error", "auth token invalid or mailware");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().println(res.toJson());
    }

//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        if (this.authenticateUrl.matches(request))
//            return false;
//        else return true;
//    }
}
