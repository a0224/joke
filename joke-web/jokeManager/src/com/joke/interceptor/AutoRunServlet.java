package com.joke.interceptor;

import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class AutoRunServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {

		System.out.println(">>你可以在这里初始化一些东西>>>>>>>>>>>>>>>>>>>>>>>>>>");

		String v = this.getInitParameter("a");
		Enumeration e = this.getInitParameterNames();
		while (e.hasMoreElements()) {
			System.out.println(">>haha>>" + e.nextElement());
		}

	}

}