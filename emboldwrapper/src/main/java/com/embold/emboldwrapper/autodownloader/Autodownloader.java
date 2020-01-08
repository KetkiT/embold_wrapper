package com.embold.emboldwrapper.autodownloader;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import com.embold.emboldwrapper.GammaAccess;
import com.embold.emboldwrapper.UserValidator;
import com.embold.emboldwrapper.autodownloader.Downloader.DownloadDetails;
import com.embold.emboldwrapper.common.EmboldConstants;
import com.embold.emboldwrapper.exception.EmboldWrapperException;

public class Autodownloader {
	private final static Logger logger = org.apache.logging.log4j.LogManager
			.getLogger(Autodownloader.class);
	public void updateCoronaVersionIfNotLatest(GammaAccess gammaAccess) {
		try {
			UserValidator validator = new UserValidator();
			validator.validateUser(gammaAccess);
			String accessToken = validator.getValidAccessToken();
			VersionChecker versionChecker = new VersionChecker();
			HttpURLConnection connection = versionChecker.checkCoronaVerison(accessToken, gammaAccess.getUrl());
			if (connection != null) {
				int respCode = connection.getResponseCode();
				if (respCode == HttpURLConnection.HTTP_NO_CONTENT) {
					//ProgressLogger.LOG.prog("Already updated");
					return;
				}
				if (respCode == HttpURLConnection.HTTP_OK) {
				//	ProgressLogger.LOG.prog("Updating Corona");
					Downloader download = new Downloader();
					download.downloadAndExtract(connection);
					DownloadDetails details = download.getDownloadDetails();
					if (details != null) {
						String coronaHome = details.getCoronaHomeDir();
						String latestCoronaDir = details.getLatestCoronaDir();
						Updator updator = new Updator();
						latestCoronaDir += File.separator+EmboldConstants.CORONA;
						updator.update(coronaHome, latestCoronaDir);
					}
				}
			}
		} catch (IOException e) {
			logger.info("Error occurred while updating latest corona", e);
		} catch (EmboldWrapperException e) {
			logger.error("\nError updating latest corona. Reason: " + e.getMessage(), e);
		}
	}
}
