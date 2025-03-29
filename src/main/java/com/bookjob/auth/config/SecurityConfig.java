package com.bookjob.auth.config;

import com.bookjob.auth.filter.JwtAuthFilter;
import com.bookjob.auth.filter.JwtLoginFilter;
import com.bookjob.auth.service.MemberDetailsService;
import com.bookjob.auth.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final MemberDetailsService memberDetailsService;

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(memberProvider());
    }

    @Bean
    public AuthenticationProvider memberProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(passwordEncoder);
        provider.setUserDetailsService(memberDetailsService);
        return provider;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:8080"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setMaxAge(60L);
        configuration.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 필터링에서 제외할 요청들
        final RequestMatcher ignoredRequests = new OrRequestMatcher(
                List.of(new AntPathRequestMatcher("/api/v1/members/signup", HttpMethod.POST.name())
                ));

        // 요청별 권한 관리
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(ignoredRequests).permitAll()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // 정적 리소스 허용
                .requestMatchers(HttpMethod.GET, "/ping", "/error", "/actuator/health").permitAll() // 헬스 체크 허용
                .anyRequest().permitAll() // 이후 수정 필요. 개발 위해 permitAll 해둔 상황
        );

        // CSRF 비활성화 및 CORS 설정
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));

        // 세션 비활성화 (JWT 사용)
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 로그인 폼 비활성화
        http.formLogin(AbstractHttpConfigurer::disable);

        http.addFilterBefore(new JwtLoginFilter(jwtProvider, authenticationManager(), "/api/v1/auth/login"), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new JwtAuthFilter(memberDetailsService, jwtProvider), JwtLoginFilter.class);

        return http.build();
    }
}
