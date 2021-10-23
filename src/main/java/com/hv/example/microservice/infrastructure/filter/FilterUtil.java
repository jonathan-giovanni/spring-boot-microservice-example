package com.hv.example.microservice.infrastructure.filter;

import com.hv.example.microservice.infrastructure.logging.TransactionUtil;
import io.micrometer.core.instrument.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class FilterUtil {

    private static final String T_REQUEST	    = "REQUEST";
    private static final String T_RESPONSE      = "RESPONSE";

    private static final String ERROR_REQ       = "ERROR GET CONTENT REQUEST : {0}";
    private static final String ERROR_RES       = "ERROR GET CONTENT RESPONSE: {0}";
    private static final String HEALTH_CHECK_URI = "actuator/health";

    private Map<String, String> headers = new HashMap<>();

    private final int maxPayloadLength = 1000;

    private String getContentAsString(byte[] buf, String charsetName) {
        if (buf == null || buf.length == 0) return "";
        int length = Math.min(buf.length, this.maxPayloadLength);
        try {
            return new String(buf, 0, length, charsetName);
        } catch (UnsupportedEncodingException ex) {
            return "Unsupported Encoding";
        }
    }

    public IOContent getContentRequest(ContentCachingRequestWrapper request) {
        IOContent requestContent = new IOContent(T_REQUEST);
        try {
            //cleaning headers
            headers.clear();
            requestContent.setMethod(request.getMethod());
            //set request
            requestContent.setUri(request.getRequestURI());
            if(requestContent.getUri().contains(HEALTH_CHECK_URI)){
                TransactionUtil.setLogSkipped(true);
            }
            //collecting headers
            Collections.list(request.getHeaderNames()).forEach(resHeader->headers.put(resHeader, request.getHeader(resHeader)));
            requestContent.setHeaders(headers);
            //encoding type
            requestContent.setContentEncoding(requestContent.getContentEncoding());
            //body
            String requestBody = getContentAsString(request.getContentAsByteArray(),request.getCharacterEncoding());
            requestContent.setBody(JsonUtils.prettyPrint(requestBody));
        } catch (Exception e) {
            log.error(ERROR_REQ,e);
        }
        return requestContent;
    }

    public IOContent getContentResponse(ContentCachingResponseWrapper response) {
        IOContent responseContent = new IOContent(T_RESPONSE);
        try {
            headers.clear();
            //http status
            responseContent.setStatusCode(response.getStatus());
            //headers
            response.getHeaderNames().forEach(resHeader->headers.put(resHeader, response.getHeader(resHeader)));
            responseContent.setHeaders(headers);
            //encoding type
            responseContent.setContentEncoding(responseContent.getContentEncoding());
            //body
            String responseBody = getContentAsString(response.getContentAsByteArray(),response.getCharacterEncoding());
            responseContent.setBody(JsonUtils.prettyPrint(responseBody));

            response.copyBodyToResponse();
        } catch (Exception e) {
            log.error(ERROR_RES,e);
        }
        return responseContent;
    }

}
