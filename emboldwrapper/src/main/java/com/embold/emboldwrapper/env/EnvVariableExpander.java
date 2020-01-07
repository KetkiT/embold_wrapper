package com.embold.emboldwrapper.env;

import java.io.File;

import org.apache.commons.configuration.EnvironmentConfiguration;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

public class EnvVariableExpander {
	private static EnvironmentConfiguration env = new EnvironmentConfiguration();

	public static String expandEnvironmentVariables(String value) {
		if (value == null) {
			return null;
		}

		String path = FilenameUtils.normalize(value);
		path = FilenameUtils.separatorsToSystem(path);
		if (path == null) {
			// original value
			return value;
		}
		String[] frags = StringUtils.split(path, File.separator);

		String prefix = "";
		if (StringUtils.startsWith(path, File.separator)) {
			prefix = getPrefix(path);
		}

		String result = prefix;

		for (int i = 0; i < frags.length; ++i) {
			String frag = StringUtils.trim(frags[i]);
			String envValue = frag;
			if (containsEnvVar(frag)) {
				String var = frag.replaceAll("\\$|\\%", "");
				envValue = resolveEnvVar(var);
				if (StringUtils.isEmpty(envValue)) {
					envValue = frag;
				} else {
					if (containsEnvVar(envValue)) {
						// Resolve this path as well
						envValue = expandEnvironmentVariables(envValue);
					}
				}
			}

			result += envValue;
			if (i < frags.length - 1) {
				result += File.separator;
			}
		}

		return result;
	}

	private static boolean containsEnvVar(String frag) {
		return StringUtils.startsWith(frag, "$") || StringUtils.startsWith(frag, "%");
	}

	private static String resolveEnvVar(String var) {
		String envValue = EnvVariableUtils.getEnvVarValue(var);
		if (StringUtils.isEmpty(envValue)) {
			envValue = env.getString(var);
		}

		if (StringUtils.isNotEmpty(envValue)) {
			envValue = FilenameUtils.separatorsToSystem(envValue);
		}

		return envValue;
	}

	private static String getPrefix(String path) {
		String prefix = "";
		for (int i = 0; i < path.length(); ++i) {
			char c = path.charAt(i);
			if (c == File.separatorChar) {
				prefix += c;
			} else {
				break;
			}
		}
		return prefix;
	}
}
