package com.gam.api.config.json;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;


@Component
public class CustomObjectMapper extends ObjectMapper {

    public CustomObjectMapper() {
        getSerializerProvider().setNullValueSerializer(new NullToEmptyStringSerializer());
    }
}