package com.embold.emboldwrapper.opt;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * Class builds the options for reporting tool
 * @author KetkiT
 *
 */
public class EmboldWrapperOptBuilder {

	private final Options options = new Options();

	public EmboldWrapperOptBuilder() {
		buildOptions();
	}

	private void buildOptions() {
		//TODO change the name.
		Option json = new Option(EmboldWrapperOpt.SCAN_JSON.getName(),EmboldWrapperOpt.SCAN_JSON.getLongName(),true,"Mode");
		Option disableEmbold = new Option(EmboldWrapperOpt.DISABLE_UPDATE.getName(),EmboldWrapperOpt.DISABLE_UPDATE.getLongName(),false,"Disable update");
		Option verbose = new Option(EmboldWrapperOpt.VERBOSE.getName(),EmboldWrapperOpt.VERBOSE.getLongName(),false,"Verbose Mode");
		json.setRequired(true);
		disableEmbold.setRequired(false);
		verbose.setRequired(false);
		this.options.addOption(json);
		this.options.addOption(disableEmbold);
		this.options.addOption(verbose);
	}

	public Options getOptions() {
		return options;
	}
}
