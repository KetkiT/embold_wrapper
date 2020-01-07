package com.embold.emboldwrapper.env;

import java.lang.reflect.Field;
import java.util.Map;

import org.apache.commons.configuration.EnvironmentConfiguration;

import org.apache.commons.lang.StringUtils;

public class EnvVariableUtils {
	public static String getEnvVarValue(String var) {
		String envValue = System.getProperty(var);
		if (StringUtils.isEmpty(envValue)) {
			EnvironmentConfiguration env = new EnvironmentConfiguration();
			envValue = env.getString(var);
		}
		return envValue;
	}
	
	
	@SuppressWarnings("unchecked")
	public static void updateCoronaHome(String coronaHome) {
		Map<String, String> env =  System.getenv();
		try {
			Field field = env.getClass().getDeclaredField("CORONA_HOME");
			field.setAccessible(true);
			((Map<String, String>) field.get(env)).put("CORONA_HOME", coronaHome);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}
}
