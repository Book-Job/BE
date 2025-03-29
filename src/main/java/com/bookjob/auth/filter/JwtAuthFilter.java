package com.bookjob.auth.filter;

import com.bookjob.auth.dto.MemberDetailsDto;
import com.bookjob.auth.service.MemberDetailsService;
import com.bookjob.auth.utils.JwtProvider;
import com.bookjob.common.exception.UnAuthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final MemberDetailsService memberDetailsService;
    private final JwtProvider jwtProvider;
    private final RequestMatcher ignoreRequestMatcher;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (ignoreRequestMatcher.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (token != null && token.startsWith("Bearer ")) {
            try {
                processTokenAuth(token);
            } catch (ExpiredJwtException e) {
                handleExpiredToken(request);
            } catch (Exception e) {
                handleInvalidToken();
            }
        }

        filterChain.doFilter(request, response);
    }

    private void processTokenAuth(String token) {
        Jws<Claims> claims = jwtProvider.parseToken(token);
        String loginId = claims.getPayload().getSubject();

        MemberDetailsDto memberDetails = (MemberDetailsDto) memberDetailsService.loadUserByUsername(loginId);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                memberDetails,
                null,
                memberDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void handleExpiredToken(HttpServletRequest request) {
        String newAccessToken = (String) request.getAttribute("newAccessToken");

        if (newAccessToken != null) {
            processTokenAuth(newAccessToken);
        } else {
            SecurityContextHolder.clearContext();
            throw UnAuthorizedException.securityUnauthorized();
        }
    }

    private void handleInvalidToken() {
        SecurityContextHolder.clearContext();
        throw UnAuthorizedException.securityUnauthorized();
    }
}
