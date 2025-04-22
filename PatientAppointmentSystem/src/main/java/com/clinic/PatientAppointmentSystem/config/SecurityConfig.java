package com.clinic.PatientAppointmentSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Disable for testing; re-enable with CSRF token later
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/index.html", "/book-appointment.html", "/css/**", "/js/**", "/api/public/**").permitAll()
                        .requestMatchers("/admin/login.html").permitAll() // Allow login page
                        .requestMatchers("/admin/dashboard.html").authenticated() // Require auth for dashboard
                        .requestMatchers("/admin/**", "/api/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/admin/login.html")
                        .loginProcessingUrl("/perform_login")
                        .defaultSuccessUrl("/admin/dashboard.html", true)
                        .failureUrl("/admin/login.html?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/perform_logout")
                        .logoutSuccessUrl("/index.html?logout=true")
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        var admin = User.withUsername("admin")
                .password("{noop}admin123")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }
}