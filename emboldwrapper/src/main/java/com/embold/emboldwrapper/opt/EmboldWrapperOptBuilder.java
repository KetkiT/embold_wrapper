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
		json.setRequired(true);
		disableEmbold.setRequired(false);
		this.options.addOption(json);
		this.options.addOption(disableEmbold);
	}

	public Options getOptions() {
		return options;
	}
}
