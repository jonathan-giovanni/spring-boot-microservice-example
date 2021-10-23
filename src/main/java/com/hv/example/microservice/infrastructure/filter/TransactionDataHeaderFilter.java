package com.hv.example.microservice.infrastructure.filter;

import com.hv.example.microservice.infrastructure.logging.LogCategory;
import com.hv.example.microservice.infrastructure.logging.TransactionUtil;
import com.hv.example.microservice.infrastructure.logging.WSLog;
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

    private final WSLog wsLog;

    public TransactionDataHeaderFilter(WSLog wsLog) {
        this.wsLog = wsLog;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        ContentCachingRequestWrapper wrappedRequest     = new ContentCachingRequestWrapper(((HttpServletRequest) request));

        TransactionUtil.begin(wrappedRequest);
        TransactionUtil.timerStart();

        if(wrappedRequest.getRequestURI().contains("actuator")){
            TransactionUtil.setLogSkipped(true);
        }

        wsLog.info(LogCategory.FILTER,"REQUEST: "+request.getRequestURI());

        HttpServletResponse wrappedResponse = new HttpServletResponseWrapper(response) {
            @Override
            public void setStatus(int sc) {
                super.setStatus(sc);

                wsLog.info(LogCategory.FILTER,"HTTP STATUS: "+sc);

                TransactionUtil.timerStop();
                TransactionUtil.clear(this);
            }
        };

        chain.doFilter(request, wrappedResponse);
    }
}
