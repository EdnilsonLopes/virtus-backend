package com.virtus.common.interceptor;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.virtus.common.utils.CorrelationUtils;

@Component
public class CorrelationIdInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String correlationId = getCorrelationIdFromHeader(request);
        response.setHeader(CorrelationUtils.CORRELATION_ID_HEADER_NAME, correlationId);
        CorrelationUtils.putCorrelationId(correlationId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.clear();
    }

    private String getCorrelationIdFromHeader(final HttpServletRequest request){
        String correlationId = request.getHeader(CorrelationUtils.CORRELATION_ID_HEADER_NAME);
        if(!StringUtils.hasText(correlationId)){
            correlationId = generateUniqueCorrelationId();
        }
        return correlationId;
    }

    private String generateUniqueCorrelationId() {
        return UUID.randomUUID().toString();
    }
}
