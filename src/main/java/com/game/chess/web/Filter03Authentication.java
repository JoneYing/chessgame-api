//package com.game.chess.web;
//
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.util.HashSet;
//import java.util.Properties;
//import java.util.Set;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import com.alibaba.fastjson.JSON;
//import com.game.common.constant.GameConstant;
//import com.game.common.constant.MessageConstant;
//import com.game.common.resp.Resp;
//import com.game.common.utils.PropertiesUtil;
//
///**
// * 
// * @Description 权限过滤器，用于判断用户权限
// *
// * @author YingJH
// * @Date 2018年3月9日
// * @version v1.1
// */
//@WebFilter(urlPatterns={ "/*"})
//public class Filter03Authentication implements Filter {
//
//	protected Logger logger = LogManager.getLogger();
//	
//	private Set<String> excludeURL = new HashSet<String>();
//	
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//		HttpServletResponse httpResponse = (HttpServletResponse) response;
//		HttpServletRequest httpRequest = (HttpServletRequest) request;
//		logger.debug("Filter03Authentication======================>doFilter..................");
//		
//		Boolean comparisonResult = Boolean.TRUE;
//		
//		//判断url是否需要验证权限
//		if(isExculde(httpRequest)){
//			comparisonResult = Boolean.TRUE;
//		}else{
////			//获取请求地址
////			String requestURI = httpRequest.getRequestURI();
////			//获取用户Session
////			UserSession userSession = SessionUtil.getLogined(httpRequest, httpResponse);
//			//TODO 比对权限
//		} 
//		if(comparisonResult) {
//			chain.doFilter(httpRequest, httpResponse);
//		}else{
//			Resp resp = Resp.error(MessageConstant.AUTHENTICATION_EXPIRE, PropertiesUtil.getMessage(MessageConstant.AUTHENTICATION_EXPIRE));
//			String json = JSON.toJSONString(resp);
//			OutputStream out = response.getOutputStream();  
//			out.write(json.getBytes(GameConstant.GAME_ENCODE));
//			return;
//		}
//		logger.debug("Filter03Authentication======================>doFilter..................comparisonResult:"+comparisonResult);
//	}
//
//	private boolean isExculde(HttpServletRequest httpRequest) {
//		String uri = httpRequest.getRequestURI().replaceFirst(httpRequest.getContextPath(), "");
//		return excludeURL.contains(uri.trim());
//	}
//
//	public Filter03Authentication() {}
//
//	public void destroy() {}
//	
//	public void init(FilterConfig fConfig) throws ServletException {
//		InputStreamReader is = null;
//		try {
//			Properties pro = new Properties();
//			ClassLoader cl = Thread.currentThread().getContextClassLoader();
//			is = new InputStreamReader(cl.getResourceAsStream("properties/Filter03Authentication.properties"), "UTF-8");
//			pro.load(is);
//			this.initExcludeURL(pro);
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		} finally {
//			if (is != null) {
//				try {
//					is.close();
//				} catch (IOException e) {
//				}
//			}
//		}
//	}
//
//	private void initExcludeURL(Properties pro) {
//		Set<Object> keys = pro.keySet();
//		for (Object key : keys) {
//			if (key.toString().trim().startsWith("authentication.excludeURL.")) {
//				String[] vals = pro.getProperty(key.toString(), "").split(",");
//				for (String val : vals) {
//					val = val.trim();
//					if (!"".equals(val)) {
//						this.excludeURL.add(val);
//					}
//				}
//			}
//		}
//	}
//
//}
