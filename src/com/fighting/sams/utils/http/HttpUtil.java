package com.fighting.sams.utils.http;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义访问对象工具类
 * 
 * 获取对象的IP地址等信息
 * 
 * @author X-rapido
 *
 */
public class HttpUtil {

	/**
	 * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址, 参考文章：
	 * http://developer.51cto.com/art/201111/305181.htm
	 * 
	 * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
	 * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
	 * 
	 * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
	 * 192.168.1.100
	 * 
	 * 用户真实IP为： 192.168.1.110
	 * 
	 * @param request
	 * @return
	 */

	/**
	 * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public final static String getIpAddress(HttpServletRequest request) throws IOException {
		// 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址

		String ip = request.getHeader("X-Forwarded-For");
		System.out.println("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=" + ip);
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
				System.out.println("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
				System.out.println("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
				System.out.println("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
				System.out.println("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
				System.out.println("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);
			}
		} else if (ip.length() > 15) {
			String[] ips = ip.split(",");
			for (int index = 0; index < ips.length; index++) {
				String strIp = (String) ips[index];
				if (!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}
		return ip;
	}
	/*
	 * public static String getIpAddress(HttpServletRequest request) { String ip
	 * = request.getHeader("x-forwarded-for"); if (ip == null || ip.length() ==
	 * 0 || "unknown".equalsIgnoreCase(ip)) { ip =
	 * request.getHeader("Proxy-Client-IP"); } if (ip == null || ip.length() ==
	 * 0 || "unknown".equalsIgnoreCase(ip)) { ip =
	 * request.getHeader("WL-Proxy-Client-IP"); } if (ip == null || ip.length()
	 * == 0 || "unknown".equalsIgnoreCase(ip)) { ip =
	 * request.getHeader("HTTP_CLIENT_IP"); } if (ip == null || ip.length() == 0
	 * || "unknown".equalsIgnoreCase(ip)) { ip =
	 * request.getHeader("HTTP_X_FORWARDED_FOR"); } if (ip == null ||
	 * ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { ip =
	 * request.getRemoteAddr(); } return ip; }
	 */
}
