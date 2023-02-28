package com.example.demo.filter;

import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.net.URI;
import java.time.Duration;
import java.util.concurrent.TimeoutException;

@Component
public class ContextFilter implements WebFilter {

    private static Logger log = LoggerFactory.getLogger(ContextFilter.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();
        log.debug("request uri:{}",uri);
        HttpHeaders headers = request.getHeaders();
        String imei = StringUtil.isNullOrEmpty(headers.getFirst("imei"))?"test":headers.getFirst("imei");
        Duration timeoutDuration = Duration.ofMillis(10000L);
        long now = System.nanoTime();
        return chain
                .filter(exchange)
                .timeout(timeoutDuration)
                .onErrorResume(TimeoutException.class,(final Throwable unused)-> getTimeoutFallback(exchange,now))
                .contextWrite(Context.of("imei",imei));
    }

    private Mono<Void> getTimeoutFallback(final ServerWebExchange exchange,final long startTime){
        ServerHttpResponse response = exchange.getResponse();
        final boolean responseStatusCodeAlreadyCommitted = !response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        final HttpRequest request = exchange.getRequest();
        String uri = request.getURI().toString();
        String methodValue = request.getMethodValue();
        String responseStatusMessage = responseStatusCodeAlreadyCommitted?String.format("Response status code was already committed :'%s'.", response.getStatusCode()):String.format("Set response status code to '%s'",HttpStatus.REQUEST_TIMEOUT);
        long duration = Duration.ofNanos(System.nanoTime() - startTime).toMillis();
        log.error(String.format("Response timeout after %s Milliseconds for %s request with uri '%s'.%s",duration,methodValue,uri,responseStatusMessage));
        return Mono.empty();
    }
}
