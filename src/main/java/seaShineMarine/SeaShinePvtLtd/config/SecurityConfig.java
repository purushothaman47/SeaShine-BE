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
                        // Let Spring Boot's internal error dispatch through. Without
                        // this, any exception thrown from an authenticated endpoint
                        // (e.g. a 404 from downloadResume) gets re-dispatched to
                        // /error internally, JwtAuthenticationFilter skips that
                        // internal dispatch by default (OncePerRequestFilter's
                        // shouldNotFilterErrorDispatch() defaults to true), and
                        // AuthorizationFilter then denies the now-anonymous
                        // re-dispatch with 403 — masking the real status code.
                        .requestMatchers("/error")
                        .permitAll()

                        // Admin login stays open so the admin panel can authenticate.
                        .requestMatchers("/api/v1/auth/**")
                        .permitAll()

                        // Public website: read-only browsing of services / careers / content.
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/services", "/api/v1/services/**",
                                "/api/v1/careers", "/api/v1/careers/**",
                                "/api/v1/content", "/api/v1/content/**"
                        ).permitAll()

                        // Public website: contact form + job application submissions.
                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/contact",
                                "/api/v1/job-applications"
                        ).permitAll()

                        // Everything else (create/update/delete, viewing applications &
                        // messages, content management writes, etc.) requires the admin JWT.
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
     * Allows the Angular apps (served from a different origin during development,
     * e.g. http://localhost:4200) to call this API. Adjust the allowed origins
     * before deploying to production.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("http://localhost:*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}