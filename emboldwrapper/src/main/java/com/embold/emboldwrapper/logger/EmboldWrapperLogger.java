package com.embold.emboldwrapper.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Level;


public class EmboldWrapperLogger {

	public static final String GAMMA = "[EMBOLD]";
	public static final String DONE = "[DONE]";
	public static final String FAILED = "[FAILED]";
	public static final String RUNNING = "[RUNNING]";
	private static final String CARRIAGE_RETURN = "\r";
	public static Logger logger = LogManager.getLogger(EmboldWrapperLogger.class);
	public boolean isVerbose = false;
	
	public EmboldWrapperLogger(boolean isVerbose){
		this.isVerbose = isVerbose;
	}
	
	/*public void log(String info){
		logger.log(logLevel.get(),info);
	}
	
	public void logWithCarriageReturn(String info){
		logger.log(logLevel.get(),CARRIAGE_RETURN +info);
	}
	public ScanboxLogger(ILogLevel logLevel) {
		super(logLevel);
	}*/
	
	public EmboldWrapperLogger() {
		
	}
	
	public void running(String info){
		log(String.format("%s %-50s",GAMMA,info));
	}

	public void done(){
		log(String.format("\t%s\n",DONE));
	}
	
	public void failed(){
		log(String.format("\t%s\n",FAILED));
	}
	
	public void log(String info){
		if(!isVerbose) {
			logger.info(String.format("%s %-50s\n",GAMMA,info));	
		}
		else {
			logger.info(info);	
		}
		
		
	}
}
