package com.virtus.common.interceptor;

import com.virtus.common.utils.CorrelationUtils;
import com.virtus.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Slf4j
public class LoginRequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        traceRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        tranceResponse(request, response);
        return response;
    }

    private void tranceResponse(HttpRequest request, ClientHttpResponse response) {
        StringBuilder inputStringBuilder = new StringBuilder();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), StandardCharsets.UTF_8))) {
            String line = bufferedReader.readLine();
            while (line != null){
                inputStringBuilder.append(line).append('\n');
                line = bufferedReader.readLine();
            }
            log.info("Request-Id: {}, Time: {}, Status code: {}, Status text: {}, Headers: {}, Response body: {}",
                    request.getHeaders().get(CorrelationUtils.CORRELATION_ID_HEADER_NAME),
                    DateUtils.formatDateTimePtBr(LocalDateTime.now()),
                    response.getStatusCode(),
                    response.getStatusText(),
                    response.getHeaders(),
                    inputStringBuilder.toString()
            );
        } catch (IOException e) {
            log.info("Request-Id: {}, Error: {}", request.getHeaders().get(CorrelationUtils.CORRELATION_ID_HEADER_NAME), e.getMessage());
        }
    }

    private void traceRequest(HttpRequest request, byte[] body) {

    }
}
