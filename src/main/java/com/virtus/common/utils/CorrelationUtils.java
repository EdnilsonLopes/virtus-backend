package com.virtus.common.utils;

import org.slf4j.MDC;

public class CorrelationUtils {

    private static final String CORRELATION_ID_LOG_VARIABLE_NAME = "correlationId";
    public static final String CORRELATION_ID_HEADER_NAME = "X-Correlation-Id";

    public static void putCorrelationId(String correlationId) {
        MDC.put(CORRELATION_ID_LOG_VARIABLE_NAME, correlationId);
    }

    public static String getCorrelationId() {
        return MDC.get(CORRELATION_ID_LOG_VARIABLE_NAME);
    }
}
