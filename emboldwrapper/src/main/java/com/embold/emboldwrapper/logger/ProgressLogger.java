package com.embold.emboldwrapper.logger;


import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.embold.emboldwrapper.utility.WrapperUtility;



public enum ProgressLogger {
	LOG;
	private EmboldWrapperLogger customLogger;
	private boolean verboseMode;
	private Object lock = new Object();
	private String lastMessage = "";
	private boolean markedFailed = false;
	private static String LINE_SEPARATOR = "--------------------------------------------------------";
	private ProgressLogger(){
	}

	private boolean isInitialized(){
		return customLogger!=null?true:false;
	} 
	public void logErrorInfo(String errorMsg){
		if(isInitialized() && !verboseMode){
			customLogger.log(LINE_SEPARATOR);
			customLogger.log("UPDATION FAILURE");
			customLogger.log("Reason : " + errorMsg);
			customLogger.log(LINE_SEPARATOR);
			customLogger.log("For more info look into logs at " + WrapperUtility.getLogLocation());
		}
	}

	public void logSuccessInfo(long seconds){
		if(isInitialized() && !verboseMode){
			if(StringUtils.isNotBlank(lastMessage))
				done();
			Date date= new Date();
			long time = date.getTime();
			Timestamp ts = new Timestamp(time);
			customLogger.log(LINE_SEPARATOR);
			customLogger.log("UPDATION SUCCESSFUL");
			customLogger.log(LINE_SEPARATOR);
			customLogger.log("Total Time : "+seconds +" sec");
			customLogger.log("Finished at : "+ts);
			customLogger.log(LINE_SEPARATOR);
		}
	}
	public void init(EmboldWrapperLogger customLogger, boolean verboseMode){
		this.customLogger = customLogger;
		this.verboseMode = verboseMode;
		if(!verboseMode){
			customLogger.log(LINE_SEPARATOR);
			customLogger.log("CHECKING CORONA VERSION");
			customLogger.log(LINE_SEPARATOR);
		}
	}

	public void done(){
		synchronized (lock) {
			if(isInitialized()){
				if(StringUtils.isNotBlank(lastMessage))
					customLogger.done();
				lastMessage = "";
				markedFailed = false;
			}
		}
	}

	public void failed(){
		synchronized (lock) {
			if(isInitialized() && StringUtils.isNotBlank(lastMessage) && !markedFailed){
				customLogger.failed();
				markedFailed = true;
			}	
		}
	}
	
	public void prog(String info){
		synchronized (lock) {
			if(isInitialized() && !verboseMode){
				if(StringUtils.isNotBlank(lastMessage))
					customLogger.done();
				lastMessage = info;
				customLogger.running(info);
			}	
		}
	}

	public void failAndDisposeWithError(String errorMsg){
		synchronized (lock) {
			failed();
			logErrorInfo(errorMsg);
		}
	}
}
