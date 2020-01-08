package com.embold.emboldwrapper.cli;
import com.embold.emboldwrapper.env.CoronaHome;
import com.embold.emboldwrapper.env.PathUtil;
import com.embold.emboldwrapper.exception.EmboldWrapperException;



public class CliCfg {

	private final static String CLI_PROP = "/coronacli/config/cli.properties";
	private final static String CLI_PROP_USERNAME = "gamma.username";
	private final static String CLI_PROP_PASSWORD = "gamma.password";
	private final static String CLI_PROP_URL = "gamma.url";
	private final static String CLI_LANG = "language";
	private final static String CLI_DATA_DIR = "data.dir";
	
	private CliCfg() {
	}

	private static CliCfg cliCfg = new CliCfg();
	private PropsCfgHandler cfg;

	public static CliCfg getCliCfg() {
		return cliCfg;
	}

	public static String configDir() throws EmboldWrapperException {
		return PathUtil.convertToUnixPath(CoronaHome.getThrow() + CLI_PROP);
	}

	public void init() throws EmboldWrapperException {
		cfg = new PropsCfgHandler();
		cfg.loadConfigFile(configDir());
	}
	
	public String getUsername(){
		return cfg.getString(CLI_PROP_USERNAME);
	}
	
	public String getPassword(){
		return cfg.getString(CLI_PROP_PASSWORD);
	}
	
	public String getBaseUrl(){
		return cfg.getString(CLI_PROP_URL);
	}
	
	public String getDataDir(){
		return cfg.getString(CLI_DATA_DIR);
	}
		
	public String getLanguage() {
		return cfg.getString(CLI_LANG);
	}
}
