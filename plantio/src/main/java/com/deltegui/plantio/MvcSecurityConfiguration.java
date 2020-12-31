package com.deltegui.plantio;

import com.deltegui.plantio.users.application.TokenProvider;
import com.deltegui.plantio.users.implementation.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class MvcSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final TokenProvider tokenProvider;

    public MvcSecurityConfiguration(Environment environment) {
        this.tokenProvider = new JwtTokenProvider(environment);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .addFilterAfter(new JwtAuthorizationFilter(createTokenProvider()), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                    .antMatchers("/api/user/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/", "/*.html", "/css/**", "/js/**", "/*.png", "/*.ttf", "/*.jpg", "/*.css");
    }

    @Bean
    public TokenProvider createTokenProvider() {
        return this.tokenProvider;
    }
}
