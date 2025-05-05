package com.bookjob.auth.filter;

import com.bookjob.auth.dto.LoginResponse;
import com.bookjob.auth.dto.MemberDetailsDto;
import com.bookjob.auth.dto.MemberPasswordLoginRequest;
import com.bookjob.auth.utils.JwtProvider;
import com.bookjob.common.dto.CommonResponse;
import com.bookjob.common.utils.ServletResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtProvider jwtProvider;

    public JwtLoginFilter(JwtProvider jwtProvider, AuthenticationManager authenticationManager, String loginUrl) {
        super(authenticationManager);
        this.jwtProvider = jwtProvider;
        setFilterProcessesUrl(loginUrl);
        setPostOnly(true);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            MemberPasswordLoginRequest requestDto = new ObjectMapper().readValue(request.getInputStream(), MemberPasswordLoginRequest.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.loginId(),
                            requestDto.password()
                    )
            );
        } catch (IOException e) {
            throw new AuthenticationServiceException("로그인 시도 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        MemberDetailsDto userDetails = (MemberDetailsDto) authResult.getPrincipal();
        LoginResponse loginResponse = new LoginResponse(userDetails.member().getNickname(), userDetails.getUsername());

        String token = jwtProvider.createToken(loginResponse);

        response.addHeader(HttpHeaders.AUTHORIZATION, token);

        CommonResponse<?> successResponse = CommonResponse.success(loginResponse);
        ServletResponseUtil.servletResponse(response, successResponse, HttpStatus.OK);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        CommonResponse<?> errorResponse = CommonResponse.failure("인증 과정 중 오류 발생");

        ServletResponseUtil.servletResponse(response, errorResponse, HttpStatus.UNAUTHORIZED);
    }
}
