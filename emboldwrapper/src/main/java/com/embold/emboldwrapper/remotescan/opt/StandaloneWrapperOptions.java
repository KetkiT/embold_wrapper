package com.embold.emboldwrapper.remotescan.opt;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
public class StandaloneWrapperOptions {
	private final Options options = new Options();

	public StandaloneWrapperOptions() {
		buildOptions();
	}

	private void buildOptions() {
		Option json = new Option(ScanBoxWrapperOpt.SCAN_JSON.getName(), ScanBoxWrapperOpt.SCAN_JSON.getLongName(), true,"Scan settings json file path");
		json.setRequired(true);
		this.options.addOption(json);
	}
}
