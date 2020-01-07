package com.embold.emboldwrapper.env;

import org.apache.commons.configuration.EnvironmentConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.embold.emboldwrapper.exception.EmboldWrapperException;


public class CoronaHome {

	private static Logger logger = LogManager.getLogger(CoronaHome.class);

	public static String getExit() {
		String coronaHome = getInner();
		if (coronaHome == null) {
			//System.out.println("ERROR!!!! CORONA_HOME not defined");
			logger.error("ERROR!!!! CORONA_HOME not defined");
			System.exit(-1);
		}

		return coronaHome;
	}

	public static String getThrow() throws EmboldWrapperException {
		String coronaHome = getInner();
		if (coronaHome == null) {
			//System.out.println("ERROR!!!! CORONA_HOME not defined");
			logger.error("ERROR!!!! CORONA_HOME not defined");
			throw new EmboldWrapperException("ERROR!!!! CORONA_HOME not defined");
		}

		return coronaHome;
	}
	
	public static String get() {
		return getInner();
	}

	private static String getInner() {
		String coronaHome = System.getProperty("CORONA_HOME");
		if (coronaHome == null) {
			coronaHome = new EnvironmentConfiguration()
					.getString("CORONA_HOME");
		}

		if (coronaHome != null) {
			if (!coronaHome.endsWith("/") && !coronaHome.endsWith("\\")) {
				coronaHome += '/';
			}
		}

		return coronaHome;
	}
}
