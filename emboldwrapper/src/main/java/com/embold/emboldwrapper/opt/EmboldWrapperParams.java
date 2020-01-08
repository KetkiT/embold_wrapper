package com.embold.emboldwrapper.opt;

import org.apache.commons.cli.CommandLine;

import com.embold.emboldwrapper.exception.EmboldWrapperException;

/**
 * Class representing common parameters reporting tool
 * 
 * @author KetkiT
 * 
 */
public class EmboldWrapperParams {

	private String jsonPath;
	private boolean disableUpdate = false;
	private boolean verbose = false;

	public EmboldWrapperParams(CommandLine line) throws EmboldWrapperException {
		this.jsonPath = line.getOptionValue(EmboldWrapperOpt.SCAN_JSON.getName());
		if (line.getOptionValue(EmboldWrapperOpt.VERBOSE.getName()) != null) {
			this.verbose = true;
		}
		if (line.getOptionValue(EmboldWrapperOpt.DISABLE_UPDATE.getName()) != null) {
			this.disableUpdate = true;
		}

	}

	public String getJsonPath() {
		return jsonPath;
	}

	public boolean isUpdateDisabled() {
		return disableUpdate;
	}

	public boolean isVerbose() {
		return verbose;
	}
}
