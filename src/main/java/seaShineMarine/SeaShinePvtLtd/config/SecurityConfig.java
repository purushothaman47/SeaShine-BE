package seaShineMarine.SeaShinePvtLtd.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // Allow Spring Boot internal error dispatch
                        .requestMatchers("/error")
                        .permitAll()

                        // Admin login/authentication endpoints
                        .requestMatchers("/api/v1/auth/**")
                        .permitAll()

                        // Public website GET endpoints
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/services", "/api/v1/services/**",
                                "/api/v1/careers", "/api/v1/careers/**",
                                "/api/v1/content", "/api/v1/content/**",
                                "/api/v1/gallery", "/api/v1/gallery/**"
                        ).permitAll()

                        // Public website POST endpoints
                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/contact",
                                "/api/v1/job-applications"
                        ).permitAll()

                        // All remaining endpoints require admin JWT
                        .anyRequest()
                        .authenticated()
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    /**
     * Allows Angular applications running on localhost
     * to communicate with the backend during development.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(
                List.of("http://localhost:*")
        );

        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "OPTIONS"
                )
        );

        configuration.setAllowedHeaders(
                List.of("*")
        );

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }
}