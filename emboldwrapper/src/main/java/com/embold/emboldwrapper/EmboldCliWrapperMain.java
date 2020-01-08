package com.embold.emboldwrapper;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.Logger;

import com.embold.emboldwrapper.cli.CliCfg;
import com.embold.emboldwrapper.exception.EmboldWrapperException;

public class EmboldCliWrapperMain {
	private final static Logger logger = org.apache.logging.log4j.LogManager.getLogger(EmboldCliWrapperMain.class);

	public static void main(String[] args) {
		CliExecutor cliExecutor = new CliExecutor();
		try {
			CliCfg cliCfg = CliCfg.getCliCfg();
			cliCfg.init();
			GammaAccess gammaAccess = new GammaAccess(cliCfg.getBaseUrl(), cliCfg.getUsername(), cliCfg.getPassword(),
					"");
			if (StringUtils.isBlank(gammaAccess.getUserName()) && StringUtils.isBlank(gammaAccess.getPassword())) {
				logger.info("Invalid username/password. Please add valid credentials in cli.properties");
				return;
			}
			cliExecutor.update(gammaAccess);
		} catch (EmboldWrapperException e) {
			logger.error("\nError in auto updating corona : Reason: " + e.getMessage(), e);
		}
		try {
			cliExecutor.executeCli(args);
		} catch (EmboldWrapperException e) {
			logger.error("Error occurred running cli", e);
		}
	}

}
