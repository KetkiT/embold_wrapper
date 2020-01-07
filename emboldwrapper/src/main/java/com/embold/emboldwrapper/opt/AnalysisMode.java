package com.embold.emboldwrapper.opt;

public enum AnalysisMode {
	REMOTE("r", "Remote Scan"), PLUGIN("p", "Plugin"), CLI("c", "Corona Cli");
	private String name;
	private String longName;

	AnalysisMode(String name) {
		this.name = name;
	}

	AnalysisMode(String name, String longName) {
		this.name = name;
		this.longName = longName;
	}

	public String getName() {
		return name;
	}

	public String getLongName() {
		return longName;
	}

}
