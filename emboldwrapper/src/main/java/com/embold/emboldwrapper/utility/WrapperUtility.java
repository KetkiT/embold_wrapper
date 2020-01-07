package com.embold.emboldwrapper.utility;

import java.io.File;

import org.apache.commons.lang.StringUtils;

import com.embold.emboldwrapper.env.CoronaHome;
import com.embold.emboldwrapper.env.OsCheck;


public class WrapperUtility {
	public static String getExecPath() {
		OsCheck.OSType ostype = OsCheck.getOperatingSystemType();
		String coronaHome = StringUtils.removeEnd(CoronaHome.getExit(),"/") ;
		String execPath = coronaHome + File.separator+
				"scanboxwrapper"+File.separator+"bin"+File.separator+"gammascanner";
		switch (ostype) {
		case Windows:
			execPath = execPath + ".bat";
			break;
		default:
			break;
		}
		return execPath;
	}
}
