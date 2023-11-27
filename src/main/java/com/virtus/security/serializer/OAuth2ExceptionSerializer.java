package com.virtus.security.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.virtus.exception.OAuth2Exception;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2ExceptionSerializer extends StdSerializer<OAuth2Exception> {


    protected OAuth2ExceptionSerializer() {
        super(OAuth2Exception.class);
    }

    @Override
    public void serialize(OAuth2Exception value, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {
        value.getAuthErrorCode();
        jgen.writeStartObject();
        jgen.writeObjectField("code", value.getAuthErrorCode());
        jgen.writeStringField("status", "Unauthorized");
        jgen.writeStringField("message", "Unauthorized");
        jgen.writeStringField("description", "Unauthorized");
        jgen.writeStringField("correlationId", "Unauthorized");
        jgen.writeEndObject();
    }

}
