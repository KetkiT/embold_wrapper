package com.embold.emboldwrapper.env;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.Logger;

import com.embold.emboldwrapper.exception.EmboldWrapperException;


public class PathUtil {
	private static Logger logger = org.apache.logging.log4j.LogManager.getLogger(PathUtil.class);

	public static String stripPath(String absPath, String prefix, boolean unixStyle) throws EmboldWrapperException {
		if(StringUtils.isEmpty(absPath)) {
			return absPath;
		} else if(StringUtils.isEmpty(prefix)) {
			return FilenameUtils.separatorsToUnix(absPath);
		}
		
		String path = "";
		String convAbsPath = FilenameUtils.normalize(new File(absPath).toString());
		String prefixPath = FilenameUtils.normalize(new File(prefix).toString());

		if (StringUtils.equals(convAbsPath, prefixPath)) {
			path = convAbsPath;
		} else {
//			String convAbsCanonicalPath = "";
//			String convPrefixCanonicalPath = "";
				// Canonical path returns actual/physical case-sensitive path of
				// the
				// file.

//				convAbsCanonicalPath = new File(convAbsPath).getAbsolutePath();
//				convPrefixCanonicalPath = new File(prefixPath).getAbsolutePath();
			
			// Removing canonical path logic above, as it returns actual path (after resolving sym links)
			// Strip path logic doesn't work as expected then (as the passed in path is different from canonical path)
			path = StringUtils.substringAfter(convAbsPath, prefixPath);
			path = StringUtils.removeStart(path, File.separator);
		}

		if (unixStyle)
			path = FilenameUtils.separatorsToUnix(path);

		return path;
	}

	public static String stripPathNoThrow(String absPath, String prefix, boolean unixStyle) {
		try {
			return stripPath(absPath, prefix, unixStyle);
		} catch (com.embold.emboldwrapper.exception.EmboldWrapperException e) {
			logger.error("Error while stripping path", e);
			return absPath;
		}
	}

	public static String[] convertToUnixPath(String path[]) {
		String[] unixPath = new String[path.length];
		for (int i = 0; i < path.length; i++) {
			unixPath[i] = convertToUnixPath(path[i]);
		}
		return unixPath;
	}

	public static String convertToUnixPath(String path) {
		return FilenameUtils.separatorsToUnix(path);
	}
}
