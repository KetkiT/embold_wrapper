package com.embold.emboldwrapper.autodowload.opt;
import org.apache.commons.cli.CommandLine;
import com.embold.emboldwrapper.exception.EmboldWrapperException;


/**
 * Class representing common parameters reporting tool
 * 
 * @author KetkiT
 * 
 */
public class AutoDownloaderParams {

	
	private String gammaAccessToken;
	private String gammaUrl;	
	private String username;
	private String password;
	public AutoDownloaderParams(CommandLine line) throws EmboldWrapperException {
		this.gammaUrl = line.getOptionValue(AutoDownloaderOpt.GAMMAURL.getName());
		this.gammaAccessToken = line.getOptionValue(AutoDownloaderOpt.ACCESSTOKEN.getName());
		this.username = line.getOptionValue(AutoDownloaderOpt.USERNAME.getName());
		this.password = line.getOptionValue(AutoDownloaderOpt.PASSWORD.getName());
	}

	public String getAccessToken() {
		return gammaAccessToken;
	}

	public String getGammaUrl() {
		return gammaUrl;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	

}
