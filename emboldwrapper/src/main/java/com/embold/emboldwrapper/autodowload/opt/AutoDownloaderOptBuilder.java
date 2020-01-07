package com.embold.emboldwrapper.autodowload.opt;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * Class builds the options for reporting tool
 * @author KetkiT
 *
 */
public class AutoDownloaderOptBuilder {

	private final Options options = new Options();

	public AutoDownloaderOptBuilder() {
		buildOptions();
	}

	private void buildOptions() {
		Option accessToken = new Option(AutoDownloaderOpt.ACCESSTOKEN.getName(), AutoDownloaderOpt.ACCESSTOKEN.getLongName(), true,"Gamma access token");
		Option gammaUrl = new Option(AutoDownloaderOpt.GAMMAURL.getName(), AutoDownloaderOpt.GAMMAURL.getLongName(), true,"Gamma url");
		Option userName = new Option(AutoDownloaderOpt.USERNAME.getName(), AutoDownloaderOpt.USERNAME.getLongName(), true,"Username");
		Option password = new Option(AutoDownloaderOpt.PASSWORD.getName(), AutoDownloaderOpt.PASSWORD.getLongName(), true,"Password");
		accessToken.setRequired(false);
		gammaUrl.setRequired(true);
		userName.setRequired(false);
		password.setRequired(false);
		this.options.addOption(userName);
		this.options.addOption(password);
		this.options.addOption(accessToken);
		this.options.addOption(gammaUrl);
	}

	public Options getOptions() {
		return options;
	}
}
