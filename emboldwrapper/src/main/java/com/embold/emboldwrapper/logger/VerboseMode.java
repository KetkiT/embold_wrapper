package com.embold.emboldwrapper.logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

/**
 * Enables verbose mode Do not add Logger as it will not enable verbose mode
 * 
 * @author Pranay
 *
 */
public class VerboseMode {
	public static boolean checkAndloadLog4jIfVerboseMode(String args[]) {
		boolean isverboseMode = false;
		if (ArrayUtils.isNotEmpty(args)) {
			for (String arg : args) {
				if ("-v".equalsIgnoreCase(arg)) {
					configure();
					isverboseMode = true;
					break;
				}
			}
		}
		return isverboseMode;
	}

	private static void configure() {
		String coronaHome = System.getenv("CORONA_HOME");
		ConfigurationSource source=null;
		if (StringUtils.isNotBlank(coronaHome)) {
			try (FileInputStream fileStream = new FileInputStream(
					new File(coronaHome + "/scanboxwrapper/config/log4j2-verbose.xml"))) {
				source = new ConfigurationSource(fileStream);
			} catch (IOException e) {
			}
		} else {
			try {
				source = new ConfigurationSource(ClassLoader.getSystemResourceAsStream("log4j2-verbose.xml"));
			} catch (IOException e) {
			}
		}
		if(null!=source)
			Configurator.initialize(null, source);
	}
}
