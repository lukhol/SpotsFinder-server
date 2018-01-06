package com.polibuda.pbl.interceptors;

import java.nio.charset.Charset;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class SecureEndpointsInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String authorizationHeader = request.getHeader("Authorization");
		if(StringUtils.isEmpty(authorizationHeader))
			return false;
		
		authorizationHeader = authorizationHeader.replaceAll("Basic " , "");
		String decodedCode = new String(Base64.getDecoder().decode(authorizationHeader), Charset.forName("UTF-8"));
		
		if(decodedCode.equals("spot:finder"))
			return true;
		else 
			return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
