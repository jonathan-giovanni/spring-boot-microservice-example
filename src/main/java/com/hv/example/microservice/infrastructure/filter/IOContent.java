package com.hv.example.microservice.infrastructure.filter;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter @Setter @NoArgsConstructor
@ToString
public class IOContent {

    private String type;
    private String method;
    private String uri;
    private Integer statusCode;
    private Map<String, String> headers;
    private String body;
    private String contentEncoding;

    public IOContent(String type) {
        this.type = type;
    }
}
