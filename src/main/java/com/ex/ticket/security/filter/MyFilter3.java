package com.ex.ticket.security.filter;

import java.io.IOException;
import java.io.PrintWriter;

import com.ex.ticket.common.PalagoStatic;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MyFilter3 implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
		IOException,
		ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse)response;

		if(req.getMethod().equals("POST")){
			String headerAuth = req.getHeader(PalagoStatic.AUTH_HEADER);
			System.out.println("filter 3 POST 요청 됨");
			System.out.println("headerAuth : " + headerAuth);
			if(headerAuth.equals("cos")) {
				chain.doFilter(request, response);
			}else {
				PrintWriter outPrintWriter = res.getWriter();
				outPrintWriter.println("not allowed");
			}
		}

	}
}
