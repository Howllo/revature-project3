package net.revature.project3.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.annotation.PostConstruct;
import net.revature.project3.security.JwtAuthenticationFilter;
import net.revature.project3.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${argon2.saltLength}")
    private int saltLength;

    @Value("${argon2.hashLength}")
    private int hashLength;

    @Value("${argon2.parallelism}")
    private int parallelism;

    @Value("${argon2.memory}")
    private int memory;

    @Value("${argon2.iterations}")
    private int iterations;

    private static final String[] WHITELIST = {"/api/v1/auth/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**"};

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void validateConfig() {
        if (memory <= 0 || iterations <= 0 || saltLength <= 0 || hashLength <= 0 || parallelism <= 0) {
            throw new IllegalArgumentException("All values must be a positive value.");
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder(saltLength, hashLength, parallelism, memory, iterations);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:5173",
                "https://devature.dev",
                "https://www.devature.dev"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        http.csrf(AbstractHttpConfigurer::disable);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/api/v1/user/username/**").permitAll()
                .requestMatchers("/api/v1/user/getSearchDto/**").permitAll()
                .requestMatchers("/api/v1/user/{id}/follow/{user}").permitAll()
                .requestMatchers("/api/v1/user/{id}/followers/**").permitAll()
                .requestMatchers("/api/v1/auth/verify-captcha").permitAll()
                .requestMatchers("/api/v1/auth/verify-token").permitAll()
                .requestMatchers("/api/v1/user/check/username/{username}").permitAll()
                .requestMatchers("/api/v1/user/check/email").permitAll()
                .requestMatchers("/api/v1/user/**").hasRole("USER")
                .requestMatchers("/api/v1/search/**").permitAll()
                .anyRequest().authenticated()
        );
        return http.build();
    }

    @Bean
    public OpenAPI customApi(){
        return new OpenAPI()
            .components(new Components()
                    .addSecuritySchemes("bearerAuth", new SecurityScheme()
                            .name("bearerAuth")
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                    )
            )
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
            .info(new Info()
                    .title("Rev Pro Ecommerce")
                    .version("v1")
                    .license(new License().name("Apache 2.0").url("http://springdoc.org"))
            );

    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            var optionalUser = userService.findByEmail(email);
            if (optionalUser.isEmpty()) {
                throw new UsernameNotFoundException("User not found");
            }
            var user = optionalUser.get();

            List<GrantedAuthority> authorities = new ArrayList<>();
            String roleWithPrefix = user.getRole().startsWith("ROLE_") ?
                    user.getRole() : "ROLE_" + user.getRole().toUpperCase();
            authorities.add(new SimpleGrantedAuthority(roleWithPrefix));

            return new User(user.getName(),
                    user.getPassword(),
                    authorities);
        };
    }
}