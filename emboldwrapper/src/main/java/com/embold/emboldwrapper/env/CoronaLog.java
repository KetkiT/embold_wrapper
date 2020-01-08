package com.embold.emboldwrapper.env;

import org.apache.commons.configuration.EnvironmentConfiguration;
public class CoronaLog {

	public static String get() {
		String coronaLog = System.getProperty("CORONA_LOG");
		if (coronaLog == null) {
			coronaLog = new EnvironmentConfiguration().getString("CORONA_LOG");
		}
		if (coronaLog != null) {
			if (!coronaLog.endsWith("/") && !coronaLog.endsWith("\\")) {
				coronaLog += '/';
			}
		}
		return coronaLog;
	}

}
