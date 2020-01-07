package com.embold.emboldwrapper;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;

import org.apache.logging.log4j.Logger;

import com.embold.emboldwrapper.autodownloader.Downloader;
import com.embold.emboldwrapper.autodownloader.Updator;
import com.embold.emboldwrapper.autodownloader.VersionChecker;
import com.embold.emboldwrapper.autodownloader.Downloader.DownloadDetails;
import com.embold.emboldwrapper.common.EmboldConstants;
import com.embold.emboldwrapper.exception.EmboldWrapperException;
import com.embold.emboldwrapper.json.JsonParser;
import com.embold.emboldwrapper.opt.EmboldWrapperParams;
import com.embold.emboldwrapper.proc.ProcessExec;
import com.embold.emboldwrapper.utility.WrapperUtility;

public class RemoteScanExecutor {
	private final static Logger logger = org.apache.logging.log4j.LogManager.getLogger(RemoteScanExecutor.class);

	public void updateCoronaVersionIfNotLatest(EmboldWrapperParams emboldWrapper) {
		try {
			String jsonPath = emboldWrapper.getJsonPath();
			File configFile = new File(jsonPath);
			JsonParser jsonParser = new JsonParser(configFile);
			GammaAccess gammaAccess = new GammaAccess(jsonParser.getGammaAccessJsonObj(EmboldConstants.GAMMA_ACCESS));
			UserValidator validator = new UserValidator();
			validator.validateUser(gammaAccess);
			String accessToken = validator.getValidAccessToken();
			VersionChecker versionChecker = new VersionChecker();
			HttpURLConnection connection = versionChecker.checkCoronaVerison(accessToken, gammaAccess.getUrl());
			if (connection != null) {
				int respCode = connection.getResponseCode();
				if (respCode == HttpURLConnection.HTTP_NO_CONTENT) {
					logger.info("Corona already updated.");
					return;
				}
				if (respCode == HttpURLConnection.HTTP_OK) {
					logger.info("Corona on this machine needs to be updated.");
					Downloader download = new Downloader();
					download.downloadAndExtract(connection);
					DownloadDetails details = download.getDownloadDetails();
					if (details != null) {
						String coronaHome = details.getCoronaHomeDir();
						String latestCoronaDir = details.getLatestCoronaDir();
						Updator updator = new Updator();
						updator.update(coronaHome + File.separator, latestCoronaDir + File.separator + "corona");
					}
				}
			}
		} catch (IOException e) {
			logger.info("Error occurred while updating latest corona", e);
		} catch (EmboldWrapperException e) {
			logger.error("\nError updating latest corona. Reason: " + e.getMessage(), e);
		}
	}

	public void executeScanboxwrapper(String[] args) throws EmboldWrapperException {
		try {
			ProcessExec.executeProcessSync(WrapperUtility.getExecPath(), args);
		} catch (EmboldWrapperException e) {
			logger.error("Error occurred while validating corona version", e);
		}
	}

}
