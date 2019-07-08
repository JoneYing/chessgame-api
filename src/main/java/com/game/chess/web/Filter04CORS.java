package com.game.chess.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @Description CORS解决跨域请求
 *
 * @author YingJH
 * @Date 2018年3月9日
 * @version v1.1
 */
@WebFilter(urlPatterns={ "/*"})
public class Filter04CORS implements Filter {

	protected Logger logger = LogManager.getLogger();

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		logger.debug("======================>doFilter..................");
		httpResponse.setHeader("Access-Control-Allow-Origin", httpRequest.getHeader("Origin"));
		httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		httpResponse.setHeader("Access-Control-Max-Age", "3600");
		httpResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
		httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
		chain.doFilter(request, response);
	}


	public Filter04CORS() {}

	public void destroy() {}
	
	public void init(FilterConfig fConfig) throws ServletException {}

}
