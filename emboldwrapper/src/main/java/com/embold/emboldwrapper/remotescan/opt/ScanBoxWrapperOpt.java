package com.embold.emboldwrapper.remotescan.opt;

public enum ScanBoxWrapperOpt {
	
	SCAN_JSON("c", "scansetting json");
	
	private String name;
	private String longName;

	ScanBoxWrapperOpt(String name, String longName) {
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
