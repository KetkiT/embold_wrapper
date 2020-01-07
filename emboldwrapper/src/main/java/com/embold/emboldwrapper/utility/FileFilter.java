package com.embold.emboldwrapper.utility;

import org.apache.commons.io.FilenameUtils;

public class FileFilter {
	
	public static boolean isSupportedFileType(String filePath, String[] ext) {
		String fileExt = FilenameUtils.getExtension(filePath);
		for (String e : ext) {
			if (e.equals(fileExt)) {
				return true;
			}
		}
		return false;
	}
}
