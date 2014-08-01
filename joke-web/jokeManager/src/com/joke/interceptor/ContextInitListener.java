package com.joke.interceptor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.joke.utils.Constant;


public class ContextInitListener implements ServletContextListener {

	/** log */
	private static Log log = LogFactory.getLog(ContextInitListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		Constant.props = null;
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		Constant.props = new Properties();
		InputStream inputStream = null;
		try {
			String osName = System.getProperty("os.name");
			log.debug("osName-------" + osName);
			if (osName.toLowerCase().contains("linux")) {
				inputStream = getClass().getResourceAsStream(
						"/baseconfig_linux.properties");
				Constant.props.load(inputStream);
			} else {
				inputStream = getClass().getResourceAsStream(
						"/baseconfig_win.properties");
				Constant.props.load(inputStream);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
