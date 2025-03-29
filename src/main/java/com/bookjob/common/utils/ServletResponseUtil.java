package com.bookjob.common.utils;

import com.bookjob.common.dto.CommonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ServletResponseUtil {
    public static void servletResponse(HttpServletResponse response, CommonResponse<?> responseDto, HttpStatus status) throws IOException {
        String jsonResponse = new ObjectMapper().writeValueAsString(responseDto);

        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        response.getWriter().write(jsonResponse);
    }
}
