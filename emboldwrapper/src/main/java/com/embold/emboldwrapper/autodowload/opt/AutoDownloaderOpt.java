package com.embold.emboldwrapper.autodowload.opt;

/**
 * Class representing common options 
 * @author KetkiT
 *
 */
public enum AutoDownloaderOpt {

	USERNAME("u","Username"),PASSWORD("p","Password"),ACCESSTOKEN("a","Gamma access Token"), GAMMAURL("u","Gamma url"), MODE("m","Mode");
	
	private String name;
	private String longName;

	AutoDownloaderOpt(String name, String longName) {
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
