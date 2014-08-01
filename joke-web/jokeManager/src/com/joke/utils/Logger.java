package com.joke.utils;

// TODO: Auto-generated Javadoc
/**
 * The Class Logger.
 */
public class Logger {

	/** The info logger. */
	private static org.apache.log4j.Logger infoLogger;
	
	/** The debug logger. */
	private static org.apache.log4j.Logger debugLogger;
	
	/** The error logger. */
	private static org.apache.log4j.Logger errorLogger;
	
	/** The warn logger. */
	private static org.apache.log4j.Logger warnLogger;

	static {
		infoLogger = org.apache.log4j.Logger.getLogger("INFO");
		debugLogger = org.apache.log4j.Logger.getLogger("DEBUG");
		errorLogger = org.apache.log4j.Logger.getLogger("ERROR");
		warnLogger = org.apache.log4j.Logger.getLogger("WARN");
	}

	/**
	 * Info.
	 *
	 * @param message the message
	 */
	public static void info(Object message) {
		infoLogger.info(message);
	}

	/**
	 * Debug.
	 *
	 * @param message the message
	 */
	public static void debug(Object message) {
		debugLogger.debug(message);
	}

	/**
	 * Error.
	 *
	 * @param message the message
	 */
	public static void error(Object message) {
		errorLogger.error(message);
	}
	
	/**
	 * Error.
	 *
	 * @param message the message
	 * @param t the exception
	 */
	public static void error(Object message, Throwable t) {
		errorLogger.error(message, t);
	}

	/**
	 * Warn.
	 *
	 * @param message the message
	 */
	public static void warn(Object message) {
		warnLogger.warn(message);
	}
}
