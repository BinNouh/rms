package com.waseel.rms.security;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // Enable method-level security
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
//                .requestMatchers("/api/admin/**").hasRole("admin") // New Line: Admin-only endpoints
//                .requestMatchers("/api/recruiter/**").hasRole("recruiter-main") // New Line: Recruiter-only endpoints
//                .requestMatchers("/api/form/**").hasRole("applicant-main") // New Line: Applicant-only endpoints
                .requestMatchers("api/form/**")
                .permitAll() // Allow any request to be accessed
                .anyRequest()
                .authenticated(); // Modified Line: Any other request just needs authentication

        http
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthConverter);

        http
                .sessionManagement()
                .sessionCreationPolicy(STATELESS);

        return http.build();
    }
}


//        @Value("${spring.security.oauth2.resourceserver.opaquetoken.introspection-uri}")
//        private String introspectionUri;
//        @Value("${keycloak.resource}")
//        private String clientId;
//        @Value("${keycloak.credentials.secret}")
//        private String clientSecret;


//        @Bean
//        public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
//
//            httpSecurity.cors()
//                    .disable()
//                    .csrf()
//                    .disable()
//                    .authorizeHttpRequests()
//                    .requestMatchers("/api/applicant/**")
//                    .authenticated()
//                    .anyRequest()
//                    .permitAll()
//                    .and()
//                    .exceptionHandling()
//                    .and()
//                    .sessionManagement()
//                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
////            httpSecurity.oauth2ResourceServer()
////                    .opaqueToken();
////                    .introspectionUri(introspectionUri)
////                    .introspectionClientCredentials(clientId, clientSecret);
//            return httpSecurity.build();
//        }
//    }
