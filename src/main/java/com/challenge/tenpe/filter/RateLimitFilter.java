package com.challenge.tenpe.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RateLimitFilter implements Filter {

	private static final int MAX_REQUESTS_PER_MINUTE = 3;
	private final ConcurrentHashMap<String, Integer> requestCounts = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<String, Long> requestTimes = new ConcurrentHashMap<>();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String clientIp = httpRequest.getRemoteAddr();
		String uri = httpRequest.getRequestURI();
		if (!uri.contains("swagger") && !uri.contains("api-docs")) {
			long currentTime = System.currentTimeMillis();
			requestTimes.putIfAbsent(clientIp, currentTime);
			requestCounts.putIfAbsent(clientIp, 0);

			long timeSinceFirstRequest = currentTime - requestTimes.get(clientIp);
			if (timeSinceFirstRequest > TimeUnit.MINUTES.toMillis(1)) {
				requestCounts.put(clientIp, 0);
				requestTimes.put(clientIp, currentTime);
			}

			int requestCount = requestCounts.get(clientIp);
			if (requestCount >= MAX_REQUESTS_PER_MINUTE) {

				httpResponse.setStatus(429); // 429 Too Many Requests

				httpResponse.getWriter().write("Too many requests - please try again later.");
				log.info("Too many requests - please try again later.");
				return;
			}

			requestCounts.put(clientIp, requestCount + 1);
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}
}
