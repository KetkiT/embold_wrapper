package com.embold.emboldwrapper.cli;

import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import com.embold.emboldwrapper.exception.EmboldWrapperException;

public class PropsCfgHandler {

	private PropertiesConfiguration cfg;
	public void loadConfigFile(String propertiesFile) throws EmboldWrapperException {
		try {
			cfg = new PropertiesConfiguration(propertiesFile);
		} catch (ConfigurationException e) {
			throw new EmboldWrapperException(e);
		}
	}

	public PropertiesConfiguration getConfig() {
		return cfg;
	}

	public String getString(String key) {
		return cfg.getString(key);
	}
	
	public long getLong(String key) {
		return cfg.getLong(key);
	}

	public int getInt(String key) {
		return cfg.getInt(key);
	}

	public int getInt(String key,int defaultValue) {
		return cfg.getInt(key,defaultValue);
	}
	
	public boolean getBoolean(String key) {
		return cfg.getBoolean(key);
	}
	
	public boolean getBoolean(String key, boolean defaultValue) {
		return cfg.getBoolean(key, defaultValue);
	}
	
	public boolean hasKey(String key){
		return cfg.containsKey(key);
	}
	
	public List<Object> getList(String key){
	return cfg.getList(key);
	}
}
