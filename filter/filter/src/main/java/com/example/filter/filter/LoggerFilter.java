package com.example.filter.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
//@Component
public class LoggerFilter implements Filter { //필터는 요청과 응답의 통로

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        //진입전
        log.info(">>>>>>>>>>진입");

        var req=new ContentCachingRequestWrapper((HttpServletRequest) servletRequest);
        var res=new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);

        // ContnetCashing--- 사용 이유
        // http를 통해서 input을 읽어오면 뒤에서 request 클래스에 매핑을 하지 못한다.
        // 따라서 이 경우를 위한 전용 클래스를 통해 캐시에 저장한다
        filterChain.doFilter(req, res);

        var reqJson=new String(req.getContentAsByteArray());
        log.info("req : {}", reqJson);

        var resJson=new String(res.getContentAsByteArray());
        log.info("res: {}", resJson);

        log.info("<<<<<<<<<<리턴");
        //진입후
        //

        res.copyBodyToResponse(); // 이전에 캐시에서 읽으면 응답할 body가 비기 때문에 덮어씌우는 과정 필요
    }
}
