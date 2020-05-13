package com.iAnalyze.AuthMicroService.security;

import com.iAnalyze.AuthMicroService.jwt.JwtAuthFilter;
import com.iAnalyze.AuthMicroService.service.ApplicationUserService;
import com.iAnalyze.AuthMicroService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private final Environment environment;
    private ApplicationUserService userDetailService;


    public ApplicationSecurityConfig(ApplicationUserService userDetailService, Environment environment) {
        this.userDetailService = userDetailService;
        this.environment = environment;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtAuthFilter(authenticationManager()))
                .authorizeRequests()
                .antMatchers("/**", "index", "/css/*", "/js/*")
                .permitAll()
                .anyRequest()
                .authenticated();

//        http.headers().frameOptions().disable();

//                .and()
//                .httpBasic();
    }

//    private JwtAuthFilter getAuthenticationFilter() throws Exception {
//        //authenticationFilter.setAuthenticationManager(authenticationManager());
////        authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
//        return new JwtAuthFilter(authenticationManager(), environment, userService);
//    }
}
