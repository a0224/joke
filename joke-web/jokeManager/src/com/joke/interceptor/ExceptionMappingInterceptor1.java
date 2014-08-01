/*
 * Copyright 2002-2006,2009 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.joke.interceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.config.entities.ExceptionMappingConfig;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.ExceptionHolder;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 * <!-- START SNIPPET: description -->
 * 
 * This interceptor forms the core functionality of the exception handling
 * feature. Exception handling allows you to map an exception to a result code,
 * just as if the action returned a result code instead of throwing an
 * unexpected exception. When an exception is encountered, it is wrapped with an
 * {@link ExceptionHolder} and pushed on the stack, providing easy access to the
 * exception from within your result.
 * 
 * <b>Note:</b> While you can configure exception mapping in your configuration
 * file at any point, the configuration will not have any effect if this
 * interceptor is not in the interceptor stack for your actions. It is
 * recommended that you make this interceptor the first interceptor on the
 * stack, ensuring that it has full access to catch any exception, even those
 * caused by other interceptors.
 * 
 * <!-- END SNIPPET: description -->
 * 
 * <p/>
 * <u>Interceptor parameters:</u>
 * 
 * <!-- START SNIPPET: parameters -->
 * 
 * <ul>
 * 
 * <li>logEnabled (optional) - Should exceptions also be logged? (boolean
 * true|false)</li>
 * 
 * <li>logLevel (optional) - what log level should we use (
 * <code>trace, debug, info, warn, error, fatal</code>)? - defaut is
 * <code>debug</code></li>
 * 
 * <li>logCategory (optional) - If provided we would use this category (eg.
 * <code>com.mycompany.app</code>). Default is to use
 * <code>com.opensymphony.xwork2.interceptor.ExceptionMappingInterceptor</code>.
 * </li>
 * 
 * </ul>
 * 
 * The parameters above enables us to log all thrown exceptions with stacktace
 * in our own logfile, and present a friendly webpage (with no stacktrace) to
 * the end user.
 * 
 * <!-- END SNIPPET: parameters -->
 * 
 * <p/>
 * <u>Extending the interceptor:</u>
 * 
 * <p/>
 * 
 * <!-- START SNIPPET: extending -->
 * 
 * If you want to add custom handling for publishing the Exception, you may
 * override
 * {@link #publishException(com.opensymphony.xwork2.ActionInvocation, ExceptionHolder)}
 * . The default implementation pushes the given ExceptionHolder on value stack.
 * A custom implementation could add additional logging etc.
 * 
 * <!-- END SNIPPET: extending -->
 * 
 * <p/>
 * <u>Example code:</u>
 * 
 * <pre>
 * <!-- START SNIPPET: example -->
 * <xwork>
 *     <package name="default" extends="xwork-default">
 *         <global-results>
 *             <result name="error" type="freemarker">error.ftl</result>
 *         </global-results>
 * 
 *         <global-exception-mappings>
 *             <exception-mapping exception="java.lang.Exception" result="error"/>
 *         </global-exception-mappings>
 * 
 *         <action name="test">
 *             <interceptor-ref name="exception"/>
 *             <interceptor-ref name="basicStack"/>
 *             <exception-mapping exception="com.acme.CustomException" result="custom_error"/>
 *             <result name="custom_error">custom_error.ftl</result>
 *             <result name="success" type="freemarker">test.ftl</result>
 *         </action>
 *     </package>
 * </xwork>
 * <!-- END SNIPPET: example -->
 * </pre>
 * 
 * <p/>
 * This second example will also log the exceptions using our own category
 * <code>com.mycompany.app.unhandled<code> at WARN level. 
 * 
 * <pre>
 * <!-- START SNIPPET: example2 -->
 * <xwork>
 *   <package name="something" extends="xwork-default">
 *      <interceptors>
 *          <interceptor-stack name="exceptionmappingStack">
 *              <interceptor-ref name="exception">
 *                  <param name="logEnabled">true</param>
 *                  <param name="logCategory">com.mycompany.app.unhandled</param>
 *                  <param name="logLevel">WARN</param>	        		
 *              </interceptor-ref>	
 *              <interceptor-ref name="i18n"/>
 *              <interceptor-ref name="staticParams"/>
 *              <interceptor-ref name="params"/>
 *              <interceptor-ref name="validation">
 *                  <param name="excludeMethods">input,back,cancel,browse</param>
 *              </interceptor-ref>
 *          </interceptor-stack>
 *      </interceptors>
 * 
 *      <default-interceptor-ref name="exceptionmappingStack"/>
 *    
 *      <global-results>
 *           <result name="unhandledException">/unhandled-exception.jsp</result>
 *      </global-results>
 * 
 *      <global-exception-mappings>
 *           <exception-mapping exception="java.lang.Exception" result="unhandledException"/>
 *      </global-exception-mappings>
 *        
 *      <action name="exceptionDemo" class="org.apache.struts2.showcase.exceptionmapping.ExceptionMappingAction">
 *          <exception-mapping exception="org.apache.struts2.showcase.exceptionmapping.ExceptionMappingException"
 *                             result="damm"/>
 *          <result name="input">index.jsp</result>
 *          <result name="success">success.jsp</result>            
 *          <result name="damm">damm.jsp</result>
 *      </action>
 * 
 *   </package>
 * </xwork>
 * <!-- END SNIPPET: example2 -->
 * </pre>
 * 
 * @author Matthew E. Porter (matthew dot porter at metissian dot com)
 * @author Claus Ibsen
 */
public class ExceptionMappingInterceptor1 extends AbstractInterceptor {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 789515280471540994L;

	protected static final Logger LOG = LoggerFactory
			.getLogger(ExceptionMappingInterceptor1.class);

	protected Logger categoryLogger;
	protected boolean logEnabled = false;
	protected String logCategory;
	protected String logLevel;

	public boolean isLogEnabled() {
		return logEnabled;
	}

	public void setLogEnabled(boolean logEnabled) {
		this.logEnabled = logEnabled;
	}

	public String getLogCategory() {
		return logCategory;
	}

	public void setLogCategory(String logCatgory) {
		this.logCategory = logCatgory;
	}

	public String getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String result;

		try {
			result = invocation.invoke();
		} catch (Exception e) {
			ActionContext actionContext = invocation.getInvocationContext();
			HttpServletRequest request = (HttpServletRequest) actionContext
					.get(StrutsStatics.HTTP_REQUEST);
			if (isAjaxRequest(request)) {// 如果是ajax请求方式
				ValueStack stack = invocation.getStack();
				List<ExceptionMappingConfig> exceptionMappings = invocation
						.getProxy().getConfig().getExceptionMappings();
				JsonHandlerException je = new JsonHandlerException(e);
				String mappedResult = this.findResultFromExceptions(
						exceptionMappings, je);
				result = mappedResult;
				Map<String, Object> dataMap = new HashMap<String, Object>();
				stack.set("dataMap", dataMap);
				dataMap.put("result", "500");
				
				StringBuffer msg = new StringBuffer(e.toString() + "\n");
				LOG.error(e.toString());
				StackTraceElement[] trace = e.getStackTrace();
				for (int i = 0; i < trace.length; i++)
					msg.append("\tat " + trace[i] + "\n");
				ServletActionContext.getRequest().getSession()
						.setAttribute("errMsg", msg);
				dataMap.put("errMsg", e.toString());
			} else {// 默认处理方式
				if (isLogEnabled()) {
					handleLogging(e);
				}
				List<ExceptionMappingConfig> exceptionMappings = invocation
						.getProxy().getConfig().getExceptionMappings();
				String mappedResult = this.findResultFromExceptions(
						exceptionMappings, e);
				if (mappedResult != null) {
					result = mappedResult;
					publishException(invocation, new ExceptionHolder(e));
				} else {
					throw e;
				}
				invocation.getStack();
				invocation.getInvocationContext().get(Action.ERROR);
				// invocation.getStack().findString("exceptionStack");
				invocation.getInvocationContext().get(Action.ERROR);
			}
		}

		return result;
	}

	private boolean isAjaxRequest(HttpServletRequest request) {
		String header = request.getHeader("X-Requested-With");
		if (header != null && "XMLHttpRequest".equals(header))
			return true;
		else
			return false;
	}
	
	private boolean isUpload(HttpServletRequest request) {
		String header = request.getHeader("Content-Type");
		if (header != null && "multipart/form-data".equals(header))
			return true;
		else
			return false;
	}
	
	

	/**
	 * Handles the logging of the exception.
	 * 
	 * @param e
	 *            the exception to log.
	 */
	protected void handleLogging(Exception e) {
		if (logCategory != null) {
			if (categoryLogger == null) {
				// init category logger
				categoryLogger = LoggerFactory.getLogger(logCategory);
			}
			doLog(categoryLogger, e);
		} else {
			doLog(LOG, e);
		}
	}

	/**
	 * Performs the actual logging.
	 * 
	 * @param logger
	 *            the provided logger to use.
	 * @param e
	 *            the exception to log.
	 */
	protected void doLog(Logger logger, Exception e) {
		if (logLevel == null) {
			logger.debug(e.getMessage(), e);
			return;
		}

		if ("trace".equalsIgnoreCase(logLevel)) {
			logger.trace(e.getMessage(), e);
		} else if ("debug".equalsIgnoreCase(logLevel)) {
			logger.debug(e.getMessage(), e);
		} else if ("info".equalsIgnoreCase(logLevel)) {
			logger.info(e.getMessage(), e);
		} else if ("warn".equalsIgnoreCase(logLevel)) {
			logger.warn(e.getMessage(), e);
		} else if ("error".equalsIgnoreCase(logLevel)) {
			logger.error(e.getMessage(), e);
		} else if ("fatal".equalsIgnoreCase(logLevel)) {
			logger.fatal(e.getMessage(), e);
		} else {
			throw new IllegalArgumentException("LogLevel [" + logLevel
					+ "] is not supported");
		}
	}

	protected String findResultFromExceptions(
			List<ExceptionMappingConfig> exceptionMappings, Throwable t) {
		String result = null;

		// Check for specific exception mappings.
		if (exceptionMappings != null) {
			int deepest = Integer.MAX_VALUE;
			for (Object exceptionMapping : exceptionMappings) {
				ExceptionMappingConfig exceptionMappingConfig = (ExceptionMappingConfig) exceptionMapping;
				int depth = getDepth(
						exceptionMappingConfig.getExceptionClassName(), t);
				if (depth >= 0 && depth < deepest) {
					deepest = depth;
					result = exceptionMappingConfig.getResult();
				}
			}
		}

		return result;
	}

	/**
	 * Return the depth to the superclass matching. 0 means ex matches exactly.
	 * Returns -1 if there's no match. Otherwise, returns depth. Lowest depth
	 * wins.
	 * 
	 * @param exceptionMapping
	 *            the mapping classname
	 * @param t
	 *            the cause
	 * @return the depth, if not found -1 is returned.
	 */
	public int getDepth(String exceptionMapping, Throwable t) {
		return getDepth(exceptionMapping, t.getClass(), 0);
	}

	private int getDepth(String exceptionMapping, Class exceptionClass,
			int depth) {
		if (exceptionClass.getName().contains(exceptionMapping)) {
			// Found it!
			return depth;
		}
		// If we've gone as far as we can go and haven't found it...
		if (exceptionClass.equals(Throwable.class)) {
			return -1;
		}
		return getDepth(exceptionMapping, exceptionClass.getSuperclass(),
				depth + 1);
	}

	/**
	 * Default implementation to handle ExceptionHolder publishing. Pushes given
	 * ExceptionHolder on the stack. Subclasses may override this to customize
	 * publishing.
	 * 
	 * @param invocation
	 *            The invocation to publish Exception for.
	 * @param exceptionHolder
	 *            The exceptionHolder wrapping the Exception to publish.
	 */
	protected void publishException(ActionInvocation invocation,
			ExceptionHolder exceptionHolder) {
		invocation.getStack().push(exceptionHolder);
	}
}
