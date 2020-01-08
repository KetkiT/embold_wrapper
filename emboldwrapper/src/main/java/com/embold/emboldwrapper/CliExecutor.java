package com.embold.emboldwrapper;

import org.apache.logging.log4j.Logger;

import com.embold.emboldwrapper.autodownloader.Autodownloader;
import com.embold.emboldwrapper.exception.EmboldWrapperException;
import com.embold.emboldwrapper.proc.ProcessExec;
import com.embold.emboldwrapper.utility.WrapperUtility;

public class CliExecutor {
	private final static Logger logger = org.apache.logging.log4j.LogManager.getLogger(CliExecutor.class);

	public void executeCli(String[] args) throws EmboldWrapperException {
		try {
			ProcessExec.executeProcessSync(WrapperUtility.getCliExecPath(), args);
		} catch (EmboldWrapperException e) {
			logger.error("Error occurred while validating corona version", e);
		}
	}

	public void update(GammaAccess gammaAccess) {
		Autodownloader downloader = new Autodownloader();
		downloader.updateCoronaVersionIfNotLatest(gammaAccess);
	}

}
