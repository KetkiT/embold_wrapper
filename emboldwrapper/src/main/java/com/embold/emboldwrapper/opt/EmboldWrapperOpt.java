package com.embold.emboldwrapper.opt;

/**
 * Class representing common options 
 * @author KetkiT
 *
 */
public enum EmboldWrapperOpt {

	DISABLE_UPDATE("disbaleUpdate","Disable Update Check"),
	VERBOSE("v","verbose"),
	SCAN_JSON("c", "scansetting json");
	
	private String name;
	private String longName;

	EmboldWrapperOpt(String name, String longName) {
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
