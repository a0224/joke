package com.joke.action;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.joke.pojo.UserPojo;
import com.joke.utils.Constant;
import com.joke.utils.DateUtil;
import com.joke.utils.MD5Utils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseAction extends ActionSupport {

	/** serialVersionUID */
	private static final long serialVersionUID = -8166922750963519338L;

	/** ��ҳ */
	protected static final String INDEX = "index";

	protected String result;
	protected String message;
	protected Integer totalProperty;
	protected String code;


	/**
	 * @return
	 */
	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * @return
	 */
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * @return
	 */
	public Map<String, Object> getSession() {
		ActionContext actionContext = ActionContext.getContext();

		Map<String, Object> session = actionContext.getSession();
		return session;
	}

	/**
	 * @param name
	 * @return
	 */
	public Object getSession(String name) {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		return session.get(name);
	}

	public UserPojo getUser() {
		UserPojo userPojo = (UserPojo) getSession().get(
				Constant.USER_INF_SESSION_NAME);
		return userPojo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() throws Exception {
		return executeProcess();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#getLocale()
	 */
	@Override
	public Locale getLocale() {
		return new Locale("en");
	}

	public String pageSkip() throws Exception {
		code = MD5Utils.GetMD5Code(DateUtil.fileName());
		return "success";
	}

	/**
	 * @return
	 * @throws Exception
	 */
	protected abstract String executeProcess() throws Exception;

	/**
	 * @return
	 */
	public static ApplicationContext getAppContext() {
		return WebApplicationContextUtils
				.getWebApplicationContext(ServletActionContext
						.getServletContext());
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
	 * @return the totalProperty
	 */
	public Integer getTotalProperty() {
		return totalProperty;
	}

	/**
	 * @param totalProperty
	 *            the totalProperty to set
	 */
	public void setTotalProperty(Integer totalProperty) {
		this.totalProperty = totalProperty;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
}
