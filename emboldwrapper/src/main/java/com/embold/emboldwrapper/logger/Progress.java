package com.embold.emboldwrapper.logger;



public class Progress {

	public static final String INITIALIZINIG_GAMMASCANNER = "INITIALIZING GAMMASCANNER";
	public static final String USER_VALIDATION = "AUTHENTICATING USER";
	public static final String DATA_VALIDATION = "CHECKING EMBOLD ENVIRONMENT";
	public static final String SUBSYSTEM_VALIDATION = "VERIFYING REPOSITORY AND DB CONNECTION";
	public static final String LICENSE_VALIDATION = "VERIFYING EMBOLD LICENSE";
	public static final String PREPARING_TO_SCAN = "PREPARING TO SCAN";	
	public static final String SCANNING = "LAUNCHING SCAN";
	public static final String STARTING_ANALYSER = "STARTED REVIEW PROCESS";
	public static final String PUBLISHING = "PUBLISHING RESULTS";
	public static final String SYNC_REPO_TO_LATEST_COMMIT = "SYNCHING REPO TO LATEST COMMIT";
	public static final String STARTING = "STARTING";
	public static final String SHUTTING_DOWN_SCAN ="SHUTTING DOWN SCAN";
	public static final String COLLECTING_METRICS = "COLLECTING METRICS";
	public static final String CALCULATING_RATINGS = "CALCULATING RATINGS";
	public static final String SCANNING_FILES = "SCANNING FILES";
	public static final String PREPROCESSING_DATA = "PREPROCESSING DATA";
	public static final String IDENTIFYING_ANTI_PATTERNS = "IDENTIFYING ANTI-PATTERNS";
	public static final String DETECTING_CODE_ISSUES = "DETECTING CODE ISSUES :";
	public static final String INTEGRATING_CODE_COVERAGE_RESULTS = "INTEGRATING CODE COVERAGE RESULTS :";
	public static final String INTEGRATING_UNIT_TEST_RESULTS = "INTEGRATING UNIT TEST RESULTS :";
	public static final String IDENTIFYING_HIGH_RISK_COMPONENTS = "IDENTIFYING HIGH RISK COMPONENTS";
	/*private static final Map<String,String> translator = new HashMap<String,String>(){
		*//**
		 * 
		 *//*
		private static final long serialVersionUID = 1L;

		{
			put(Activity.STARTUP.name(), STARTING);
			put(Activity.CLEANUP.name(), SHUTTING_DOWN_SCAN);
			put(Activity.COLLECT_METRICS.name(), COLLECTING_METRICS);
			put(Activity.CONSOLIDATION.name(), CALCULATING_RATINGS);
			put(Activity.PARSING.name(), SCANNING_FILES);
			put(Activity.PARSING_DATA_PREPROCESS.name(), PREPROCESSING_DATA);
			put(Activity.RUN_CHECKS.name(),IDENTIFYING_ANTI_PATTERNS );
			put(ModuleType.PARSER.name(), SCANNING_FILES);
			put(ModuleType.CODE_ISSUES.name(),DETECTING_CODE_ISSUES);
			put(ModuleType.COVERAGE.name(),INTEGRATING_CODE_COVERAGE_RESULTS);
			put(ModuleType.UNIT_TESTS.name(),INTEGRATING_UNIT_TEST_RESULTS);
			put(ModuleType.RELEVANCE.name(),IDENTIFYING_HIGH_RISK_COMPONENTS);
		}
	};
	
	public static String translate(String str){
		return translator.get(str) == null ? str : translator.get(str);
	}*/
}
