package com.embold.emboldwrapper.utility;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import com.embold.emboldwrapper.env.PathUtil;
import com.embold.emboldwrapper.env.CoronaLog;
import com.embold.emboldwrapper.env.CoronaHome;
import com.embold.emboldwrapper.env.OsCheck;


public class WrapperUtility {
	public static String getScanbxWrapperExecPath() {
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
	
	
	public static String getCliExecPath() {
		OsCheck.OSType ostype = OsCheck.getOperatingSystemType();
		String coronaHome = StringUtils.removeEnd(CoronaHome.getExit(),"/") ;
		String execPath = coronaHome + File.separator+
				"coronacli"+File.separator+"bin"+File.separator+"coronacli";
		switch (ostype) {
		case Windows:
			execPath = execPath + ".bat";
			break;
		default:
			break;
		}
		return execPath;
	}
	
	
	public static String getLogLocation(){
		return PathUtil.convertToUnixPath(StringUtils.isBlank(CoronaLog.get()) ? System.getProperty("user.dir") : CoronaLog.get());
	}
}
