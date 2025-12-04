package com.tpp.example.config;

import com.tpp.example.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/home", "/login", "/register",
                                "/css/**", "/js/**", "/images/**", "/error/**").permitAll()
                        .requestMatchers(
                                "/vehicles/add", "/vehicles/edit/**", "/vehicles/delete/**",
                                "/customers/add", "/customers/edit/**", "/customers/delete/**",
                                "/sales/add", "/sales/edit/**", "/sales/delete/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/vehicles").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/customers").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/sales").hasRole("ADMIN")

                        .requestMatchers(
                                "/vehicles", "/sales", "/customers"
                        ).hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                )
                .exceptionHandling(e -> e
                        .accessDeniedHandler((req, res, ex) -> res.sendError(403))
                );

        return http.build();
    }
}