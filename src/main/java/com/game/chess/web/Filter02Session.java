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
//import com.game.chess.utils.SessionUtil;
//import com.game.common.constant.GameConstant;
//import com.game.common.constant.MessageConstant;
//import com.game.common.resp.Resp;
//import com.game.common.utils.PropertiesUtil;
//
//
///**
// * Servlet Filter implementation class UserFilter
// * 
// */
//
///**
// * 
// * @Description Session过滤器，用于判断是否登录
// *
// * @author YingJH
// * @Date 2018年3月9日
// * @version v1.1
// */
//@WebFilter(urlPatterns={ "/*"})
//public class Filter02Session implements Filter {
//
//	protected Logger logger = LogManager.getLogger();
//
//	private Set<String> excludeURL = new HashSet<String>();
//
//	/**
//	 * Default constructor.
//	 */
//	public Filter02Session() {
//	}
//
//	/**
//	 * @see Filter#destroy()
//	 */
//	public void destroy() {
//	}
//
//	/**
//	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
//	 */
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//		HttpServletRequest httpRequest = (HttpServletRequest) request;
//		HttpServletResponse httpResponse = (HttpServletResponse) response;
//		
//		if (!isExculde(httpRequest) && !SessionUtil.isLogined(httpRequest, httpResponse)) {// 用户未登录
//			Resp resp = Resp.error(MessageConstant.SESSION_EXPIRE, PropertiesUtil.getMessage(MessageConstant.SESSION_EXPIRE));
//			String json = JSON.toJSONString(resp);
//			OutputStream out = response.getOutputStream();  
//	        out.write(json.getBytes(GameConstant.GAME_ENCODE));
//	        return;
//		}
//		chain.doFilter(request, response);
//	}
//
//	private boolean isExculde(HttpServletRequest httpRequest) {
//		String uri = httpRequest.getRequestURI().replaceFirst(httpRequest.getContextPath(), "");
//		return excludeURL.contains(uri.trim());
//	}
//
//	/**
//	 * @see Filter#init(FilterConfig)
//	 */
//
//	public void init(FilterConfig fConfig) throws ServletException {
//		InputStreamReader is = null;
//		try {
//			Properties pro = new Properties();
//			ClassLoader cl = Thread.currentThread().getContextClassLoader();
//			is = new InputStreamReader(cl.getResourceAsStream("properties/Filter02Session.properties"), "UTF-8");
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
//			if (key.toString().trim().startsWith("session.excludeURL.")) {
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
