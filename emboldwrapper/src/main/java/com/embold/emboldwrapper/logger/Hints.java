package com.embold.emboldwrapper.logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Hints {

	public static final Map<String, List<String>>hints = new HashMap<>();
	public static final String HINT_VERIFY_CORONA_HOME = "Verify if environment variable CORONA_HOME is defined.";
	public static final String HINT_VERIFY_GAMMASCANSETTINGS = "Verify and validate input configuration file.";
	public static final String HINT_VERIFY_USERNAME_PASSWORD = "Verify user name and password.";
	public static final String HINT_VERIFY_GAMMASERVER_REACHABLE = "Verify if Embold Server is reachable.";
	public static final String HINT_VERIFY_SUBSYSTEM_UID = "Verify repository id(uid) in input configuration.";
	public static final String HINT_VERIFY_LOG_DIRECTORY_PERMISSION = "Verify the permission of log directory.";
	public static final String HINT_VERIFY_CORONA_HOME_CORRECT = "Verify if CORONA_HOME is pointed to correct location.";
	public static final String HINT_VERIFY_CORONA_VERSION = "Verify if Embold Version >= 1.7.0.0";
	public static final String HINT_DB_CONNECTION = "Verfiy Embold DB connection.";
	public static final String HINT_NO_FILES_FOUND = "Verify if base directory contains selected language files.";
	public static final String HINT_INVALID_LICENSE = "The LOC of repository tobe scanned is more than allowable limit.";
	public static final String HINT_INVALID_REGEX = "Verify if regex specified in exclusion is valid.";

	static{
		hints.put(Progress.INITIALIZINIG_GAMMASCANNER, new ArrayList<String>(){
			{
				add(HINT_VERIFY_CORONA_HOME);
				add(HINT_VERIFY_GAMMASCANSETTINGS);
			}
		});
		hints.put(Progress.DATA_VALIDATION, new ArrayList<String>(){
			{
				add(HINT_VERIFY_CORONA_HOME);
			}
		});
		hints.put(Progress.USER_VALIDATION, new ArrayList<String>(){
			{
				add(HINT_VERIFY_USERNAME_PASSWORD);
				add(HINT_VERIFY_GAMMASERVER_REACHABLE);
			}
		});
		hints.put(Progress.SUBSYSTEM_VALIDATION, new ArrayList<String>(){
			{
				add(HINT_VERIFY_SUBSYSTEM_UID);
				add(HINT_VERIFY_GAMMASERVER_REACHABLE);
				add(HINT_DB_CONNECTION);
			}
		});
		hints.put(Progress.PREPARING_TO_SCAN, new ArrayList<String>(){
			{
				add(HINT_VERIFY_CORONA_HOME);
			}
		});
		hints.put(Progress.PUBLISHING, new ArrayList<String>(){
			{
				add(HINT_VERIFY_GAMMASERVER_REACHABLE);
			}
		});
		hints.put(Progress.SCANNING, new ArrayList<String>(){
			{
				add(HINT_VERIFY_LOG_DIRECTORY_PERMISSION);
				add(HINT_VERIFY_CORONA_HOME_CORRECT);
				add(HINT_VERIFY_CORONA_VERSION);
			}
		});
		hints.put(Progress.IDENTIFYING_ANTI_PATTERNS, new ArrayList<String>(){
			{
				add(HINT_DB_CONNECTION);
			}
		});
		hints.put(Progress.SCANNING_FILES, new ArrayList<String>(){
			{
				add(HINT_NO_FILES_FOUND);
			}
		});
		hints.put(Progress.LICENSE_VALIDATION, new ArrayList<String>(){
			{
				add(HINT_VERIFY_GAMMASERVER_REACHABLE);
				add(HINT_INVALID_LICENSE);
				add(HINT_INVALID_REGEX);
			}
		});
	}
	
	public static List<String> getHints(String key) {
		return hints.get(key);
	}
	
	
}
