package com.example.taskmanager.config; // Adaptez le package selon votre projet

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults()) // Active la configuration CORS ci-dessous
                .csrf(csrf -> csrf.disable()) // À adapter selon le besoin (ex: désactivé pour API stateless JWT)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**", "/public/**").permitAll() // Vos routes publiques
                        .anyRequest().authenticated()
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 1. Origines autorisées : Netlify, Localhost et Mobile/Capacitor
        configuration.setAllowedOriginPatterns(Arrays.asList(
                "https://*.netlify.app",               // Tous vos sous-domaines Netlify
                "https://mon-app-taskmanager.netlify.app", // L'URL exacte de votre site Netlify
                "http://localhost:5173",               // Vite React en local
                "http://localhost:5432",
                "http://localhost",                    // Capacitor sur Android
                "capacitor://localhost"               // Capacitor schéma natif
        ));

        // 2. Méthodes HTTP autorisées
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));

        // 3. En-têtes (Headers) autorisés
        configuration.setAllowedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "X-Requested-With",
                "Accept",
                "Origin",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers"
        ));

        // 4. Autoriser l'envoi de cookies ou d'en-têtes d'authentification si nécessaire
        configuration.setAllowCredentials(true);

        // 5. Exposer les en-têtes nécessaires (ex: tokens JWT)
        configuration.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Appliquer à toutes les routes
        return source;
    }
}