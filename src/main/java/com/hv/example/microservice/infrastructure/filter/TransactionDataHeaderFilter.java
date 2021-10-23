package com.hv.example.microservice.infrastructure.filter;

import com.hv.example.microservice.infrastructure.logging.TransactionUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

@Component
public class TransactionDataHeaderFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        ContentCachingRequestWrapper wrappedRequest     = new ContentCachingRequestWrapper(((HttpServletRequest) request));

        TransactionUtil.begin(wrappedRequest);

        TransactionUtil.timerStart();

        HttpServletResponse wrappedResponse = new HttpServletResponseWrapper(response) {
            @Override
            public void setStatus(int sc) {
                super.setStatus(sc);

                TransactionUtil.timerStop();
                TransactionUtil.clear(this);
            }
        };

        chain.doFilter(request, wrappedResponse);
    }
}
