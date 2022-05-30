package com.team1.tm.bantutani.app.configuration;

import com.team1.tm.bantutani.app.configuration.security.MainUserService;
import com.team1.tm.bantutani.app.configuration.security.token.TokenFilter;
import com.team1.tm.bantutani.app.configuration.security.token.TokenManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Security extends WebSecurityConfigurerAdapter {
    private MainUserService mainUserService;
    private TokenManager tokenManager;
    Security(MainUserService mainUserService, TokenManager tokenManager) {
        this.mainUserService = mainUserService;
        this.tokenManager = tokenManager;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(mainUserService).passwordEncoder(new SCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/public/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                cors().disable().
                csrf().disable().
                requiresChannel().anyRequest().
                requiresInsecure().
                and().
                authorizeRequests().antMatchers("/actuator","/v3/**","/swagger-ui/**").hasAuthority("ADMIN")
                .antMatchers("/user/**","/plants/**","/media/**","/commodity/**","/news/**").permitAll().
                anyRequest().authenticated();
        http.addFilterBefore(new TokenFilter.Builder().addTokenManager(tokenManager).
                addPath(Arrays.asList("/public/**")).build(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
