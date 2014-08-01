/**
 *  Copyright(C) 2012 Pioneer Electronics Co., Ltd.
 *  All Right Reserved.
 */
package com.joke.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.joke.pojo.UserPojo;
import com.joke.utils.Constant;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * Descriptions
 * 
 * @version 2012-8-16
 * @author PSET
 * @since JDK1.6
 * 
 */
public class RoleCheckInterceptor extends MethodFilterInterceptor {

	/** serialVersionUID */
	private static final long serialVersionUID = 4017543892884903696L;
	/** log */
	private static Log log = LogFactory.getLog(RoleCheckInterceptor.class);

	private String result;
	private String message;
	private String jsessionid;
	private boolean timeout;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.opensymphony.xwork2.interceptor.MethodFilterInterceptor#doIntercept
	 * (com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {

		log.info("doIntercept start");

		// Get UserVo from session
		UserPojo userPojo = (UserPojo) invocation.getInvocationContext()
				.getSession().get(Constant.USER_INF_SESSION_NAME);

		// redirect to sessiontimeout page if UserVo is null
		if (null == userPojo) {
			log.info("userVo is null return sessiontimeout");
			setMessage("sessiontimeout");
			setResult("success");
			setTimeout(false);
			// return "sessiontimout";

			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json;charset=UTF-8");
			PrintWriter out = response.getWriter();
			// JSONObject result = new JSONObject();
			// result.put("timeout", true);
			// result.put("redirectUrl", request.getContextPath() + LOGIN_URL);
			// out.print(result);
			out.print("{\"timeout\":true}");
			out.flush();
			out.close();
			return null;
		} else if (userPojo.getUserRole() != Constant.ADMIN
				&& userPojo.getUserRole() != Constant.CPIS
				&& userPojo.getUserRole() != Constant.AIALSBUS
				&& userPojo.getUserRole() != Constant.DEVLEOPER
				&& userPojo.getUserRole() != Constant.CHANNEL) {
			log.info("此用户不能访问这个功能?");
			setMessage("很抱歉你没有访问这个功能的权限!");
			setResult("success");
			setTimeout(false);
			// return "sessiontimout";

			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json;charset=UTF-8");
			PrintWriter out = response.getWriter();
			// JSONObject result = new JSONObject();
			// result.put("timeout", true);
			// result.put("redirectUrl", request.getContextPath() + LOGIN_URL);
			// out.print(result);
			out.print("{\"roleover\":true}");
			out.flush();
			out.close();
			return null;
		}

		log.info("doIntercept end");

		return invocation.invoke();
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the jsessionid
	 */
	public String getJsessionid() {
		return jsessionid;
	}

	/**
	 * @param jsessionid
	 *            the jsessionid to set
	 */
	public void setJsessionid(String jsessionid) {
		this.jsessionid = jsessionid;
	}

	/**
	 * @return the timeout
	 */
	public boolean getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout
	 *            the timeout to set
	 */
	public void setTimeout(boolean timeout) {
		this.timeout = timeout;
	}

}
