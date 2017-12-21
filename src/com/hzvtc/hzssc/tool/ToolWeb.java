package com.hzvtc.hzssc.tool;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;

/**
 * WEB工具类
 * @author 董华健 2012-9-3 下午7:39:43
 */
public abstract class ToolWeb {

	private static final Log log = Log.getLog(ToolWeb.class);

	/**
	 * 获取客户端IP地址
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 获取上下文URL全路径
	 * 
	 * @param request
	 * @return
	 */
	public static String getContextPath(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		sb.append(request.getScheme()).append("://").append(request.getServerName());
		int port = request.getServerPort();
		if(port != 80 ){
			sb.append(":").append(port);
		}
		sb.append(request.getContextPath());
		String path = sb.toString();
		sb = null;
		return path;
	}

	/**
	 * 获取完整请求路径(含内容路径及请求参数)
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestURIWithParam(HttpServletRequest request) {
		return request.getRequestURI() + (request.getQueryString() == null ? "" : "?" + request.getQueryString());
	}


	/**
	 * 获取ParameterMap
	 * @param request
	 * @return
	 */
	public static Map<String, String> getParamMap(HttpServletRequest request){
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<String> enume = request.getParameterNames();
		while (enume.hasMoreElements()) {
			String name = enume.nextElement();
			map.put(name, request.getParameter(name));
		}
		return map;
	}

	
	
	/**
	 * 获取cookie的值
	 * 
	 * @param request
	 * @param name
	 *            cookie的名称
	 * @return
	 */
	public static String getCookieValueByName(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = ToolWeb.readCookieMap(request);
		// 判断cookie集合中是否有我们像要的cookie对象 如果有返回它的值
		if (cookieMap.containsKey(name)) {
			Cookie cookie = cookieMap.get(name);
			return cookie.getValue();
		} else {
			return null;
		}
	}

	/**
	 * 获得cookie
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static Cookie getCookieByName(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = ToolWeb.readCookieMap(request);
		// 判断cookie集合中是否有我们像要的cookie对象 如果有返回它的值
		if (cookieMap.containsKey(name)) {
			Cookie cookie = cookieMap.get(name);
			return cookie;
		} else {
			return null;
		}
	}

	/**
	 * 获得所有cookie
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		// 从request范围中得到cookie数组 然后遍历放入map集合中
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (int i = 0; i < cookies.length; i++) {
				cookieMap.put(cookies[i].getName(), cookies[i]);
			}
		}
		return cookieMap;
	}

	
}
