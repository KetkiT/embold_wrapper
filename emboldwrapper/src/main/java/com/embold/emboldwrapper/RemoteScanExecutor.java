package com.embold.emboldwrapper;

import org.apache.logging.log4j.Logger;
import com.embold.emboldwrapper.autodownloader.Autodownloader;
import com.embold.emboldwrapper.exception.EmboldWrapperException;
import com.embold.emboldwrapper.proc.ProcessExec;
import com.embold.emboldwrapper.utility.WrapperUtility;

public class RemoteScanExecutor {
	private final static Logger logger = org.apache.logging.log4j.LogManager.getLogger(RemoteScanExecutor.class);

	public void update(GammaAccess gammaAccess) {
		Autodownloader downloader = new Autodownloader();
		downloader.updateCoronaVersionIfNotLatest(gammaAccess);
	}

	public void executeScanboxwrapper(String[] args) throws EmboldWrapperException {
		try {
			ProcessExec.executeProcessSync(WrapperUtility.getScanbxWrapperExecPath(), args);
		} catch (EmboldWrapperException e) {
			logger.error("Error occurred running scanboxwrapper", e);
		}
	}

}
