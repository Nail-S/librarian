package com.skillstest.librarian.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.skillstest.librarian.data.security.Roles.READER;
import static com.skillstest.librarian.data.security.Roles.WRITER;
import static com.skillstest.librarian.data.security.Roles.EDITOR;
import static com.skillstest.librarian.data.security.Roles.ADMIN;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    @Qualifier("libraryUserDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;
    @Bean
    PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationProvider authProvider () {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider ());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/books/**").hasAnyRole(READER.getTitle(), WRITER.getTitle(), EDITOR.getTitle(), ADMIN.getTitle())
                .antMatchers(HttpMethod.POST, "/books").hasAnyRole(WRITER.getTitle(), EDITOR.getTitle(), ADMIN.getTitle())
                .antMatchers(HttpMethod.POST, "/books/all").hasAnyRole(WRITER.getTitle(), EDITOR.getTitle(), ADMIN.getTitle())
                .antMatchers(HttpMethod.POST, "/books/**").hasAnyRole(EDITOR.getTitle(), ADMIN.getTitle())
                .antMatchers(HttpMethod.PUT, "/books/**").hasAnyRole(EDITOR.getTitle(), ADMIN.getTitle())
                .antMatchers(HttpMethod.DELETE, "/books/**").hasRole(ADMIN.getTitle())
                .antMatchers(HttpMethod.GET, "/authors/**").hasAnyRole(READER.getTitle(), WRITER.getTitle(), EDITOR.getTitle(), ADMIN.getTitle())
                .antMatchers(HttpMethod.POST, "/authors").hasAnyRole(WRITER.getTitle(), EDITOR.getTitle(), ADMIN.getTitle())
                .antMatchers(HttpMethod.POST, "/authors/all").hasAnyRole(WRITER.getTitle(), EDITOR.getTitle(), ADMIN.getTitle())
                .antMatchers(HttpMethod.POST, "/authors/**").hasAnyRole(EDITOR.getTitle(), ADMIN.getTitle())
                .antMatchers(HttpMethod.PUT, "/authors/**").hasAnyRole(EDITOR.getTitle(), ADMIN.getTitle())
                .antMatchers(HttpMethod.DELETE, "/authors/**").hasRole(ADMIN.getTitle())
                .antMatchers(HttpMethod.GET, "/users").hasRole(ADMIN.getTitle())
                .antMatchers(HttpMethod.GET, "/users/**").hasRole(ADMIN.getTitle())
                .antMatchers(HttpMethod.POST, "/users").hasRole(ADMIN.getTitle())
                .antMatchers(HttpMethod.PUT, "/users").hasRole(ADMIN.getTitle())
                .antMatchers(HttpMethod.DELETE, "/users").hasRole(ADMIN.getTitle())
                .and()
                .csrf().disable()
                .formLogin().disable();
    }
}
