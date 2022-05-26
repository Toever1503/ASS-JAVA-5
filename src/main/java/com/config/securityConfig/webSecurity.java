package com.config.securityConfig;

import com.config.jwtConfig.jwtFilter.JwtFilterAuthentication;
import com.config.jwtConfig.jwtUtil.JwtTokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.*;

@Configuration
@EnableWebSecurity(debug = false)
@EnableGlobalMethodSecurity(prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class webSecurity extends WebSecurityConfigurerAdapter {

    private RequestMatcher PRIVATE_URLS = new OrRequestMatcher(
            new AntPathRequestMatcher("/admin/**"),
            new AntPathRequestMatcher("/api/**"),
            new AntPathRequestMatcher("/edit_profile"),
            new AntPathRequestMatcher("/change_password"),
            new AntPathRequestMatcher("/order/**")
    );

    private RequestMatcher PUBLIC_URLS = new NegatedRequestMatcher(PRIVATE_URLS);
//            new OrRequestMatcher(
//            new AntPathRequestMatcher("/"),
//            new AntPathRequestMatcher("/product**"),
//            new AntPathRequestMatcher("/category**"),
//            new AntPathRequestMatcher("/contact"),
//            new AntPathRequestMatcher("/static/**"),
//            new AntPathRequestMatcher("/search"),
//            new AntPathRequestMatcher("/favicon.ico"),
//            new AntPathRequestMatcher("/login"),
//            new AntPathRequestMatcher("/logout"),
//            new AntPathRequestMatcher("/register"),
//
//    );


    private final JwtFilterAuthentication jwtFilterAuthentication;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    public webSecurity(UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.jwtFilterAuthentication = new JwtFilterAuthentication(userDetailsService, jwtTokenUtil);
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PUBLIC_URLS);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO Auto-generated method stub
        // we don't need session
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().requestMatchers(PRIVATE_URLS).authenticated();
        http.authorizeRequests().anyRequest().authenticated();

        http.logout().disable().formLogin().disable();

        http.addFilterBefore(jwtFilterAuthentication, UsernamePasswordAuthenticationFilter.class);
    }

}
