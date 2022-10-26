package com.springbook.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
@Component
public class PaypalUtils {
	public String getBaseURL(HttpServletRequest request) {
		String scheme = request.getScheme();
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();
		String contextPath = request.getContextPath();
		StringBuffer url = new StringBuffer();
		url.append(scheme).append("://").append(serverName);
		if((serverPort != 80) && (serverPort != 433)) {
			url.append(":").append(serverPort);
		}
		url.append(contextPath);
		if(url.toString().endsWith("/")) {
			url.append("/");
		}
		
		return url.toString();
	}
}
