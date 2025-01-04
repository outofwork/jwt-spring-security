package org.outofwork.config;

import org.outofwork.jwtsecurity.JwtAccessDeniedHandler;
import org.outofwork.jwtsecurity.JwtAuthenticationEntryPoint;
import org.outofwork.jwtsecurity.JwtRequestFilter;
import org.outofwork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

/**
 *
 * @author outofwork
 * @version 1.0
 * @since Jan 2025
 */

@Configuration
@EnableWebSecurity
@Import({
        CorsConfig.class,
        JwtConfig.class
})
public class WebSecurityConfig {

    @Autowired
    private CorsConfigurationSource corsConfigurationSource;  // Autowire CORS config

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;  // Autowire JWT entry point

    @Autowired
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;  // Autowire JWT access denied handler

    @Autowired
    private JwtRequestFilter jwtRequestFilter;  // Autowire JWT request filter

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/**").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/h2-console/**");
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public UserRepository userRepository() {
        return new UserRepository();
    }
}
