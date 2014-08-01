package com.joke.interceptor;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharsetEncodingFilter implements Filter {

	private String encoding;

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		// 设置字符�?
		servletRequest.setCharacterEncoding("UTF-8");
		// servletResponse.setCharacterEncoding("UTF-8");
		// servletRequest.getAttribute(arg0)
		// servletRequest.setCharacterEncoding(arg0);
		// String data = servletRequest.getParameter("data");
		// System.out.println(data);
		// if(data!=null){
		// String dataDecod = URLDecoder.decode(data, "UTF-8");
		// System.out.println(dataDecod);
		// }
		filterChain.doFilter(servletRequest, servletResponse);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void destroy() {
	}

	/**
	 * @return the encoding
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * @param encoding
	 *            the encoding to set
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

}