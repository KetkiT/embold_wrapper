package com.embold.emboldwrapper.autodownloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import com.embold.emboldwrapper.common.EmboldConstants;
import com.embold.emboldwrapper.env.CoronaHome;
import com.embold.emboldwrapper.env.OsCheck;
import com.embold.emboldwrapper.env.OsCheck.OSType;
import com.embold.emboldwrapper.utility.FileUtility;

public class VersionChecker {

	private static Logger logger = org.apache.logging.log4j.LogManager.getLogger(VersionChecker.class);
	private String versionNumber = "";
	
	private String getFileHash() throws IOException {
		String checksum = "";
		String coronaHome = CoronaHome.getExit();
		Collection<File> fileList = FileUtility.getAllFiles(coronaHome, new String[] { "properties" });

		if (CollectionUtils.isEmpty(fileList)) {
			logger.info("Could not find build information properties file in: " + coronaHome
					+ " Skipping corona version check.");
			return checksum;
		}
		// make sure there is only one build number properties present.
		for (File file : fileList) {
			String name = file.getName();
			if (!StringUtils.startsWith(name, "buildNumber-")) {
				continue;
			}
			String fileName = file.getAbsolutePath();
			versionNumber = StringUtils.substringBetween(fileName, "buildNumber-", ".");
			logger.info("Current version of corona : " + versionNumber);
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(fileName);
				checksum = DigestUtils.md5Hex(fis);
				logger.trace("Checksum for current version : " + checksum);
			} catch (IOException e) {
				logger.info("Error occurred while calculating checksum for file : " + fileName);
			} finally {
				if (fis != null) {
					fis.close();
				}
			}
		}
		return checksum;
	}
	/*
	 * int statusCode = download(accessToken, gammaBaseUrl); if (statusCode ==
	 * HttpURLConnection.HTTP_OK) { newCoronaDir =
	 * FileUtility.getParentDirOfDir(newCoronaArchiveFile); extract(); }
	 */

	public String getosType() {
		OsCheck.OSType ostype = OsCheck.getOperatingSystemType();
		String osTypeStr = "";
		if (ostype.equals(OSType.Windows)) {
			osTypeStr = "windows";
		} else if (ostype.equals(OSType.Linux)) {
			osTypeStr = "linux";
		} else if (ostype.equals(OSType.MacOS)) {
			osTypeStr = "macos";
		}
		return osTypeStr;
	}

	private HttpURLConnection makeRequest(String accessToken, String gammaBaseUrl) throws IOException {
		HttpURLConnection uc = (HttpURLConnection) new URL(gammaBaseUrl).openConnection();
		String token = "Bearer " + accessToken;
		uc.setRequestProperty("Authorization", token);
		return uc;
	}
	
	private String getUrl(String gammaBaseUrl,String accessToken,String osType,String checksum) {
		gammaBaseUrl = StringUtils.removeEnd(gammaBaseUrl, "/");
		StringBuilder sb = new StringBuilder(gammaBaseUrl);
		sb.append(EmboldConstants.BASE_URL).append("/").append(EmboldConstants.GAMMA_AUTODOWNLOAD_URL_EXTENSION).append("os=")
				.append(osType).append("&checksum=").append(checksum);
		return sb.toString();
		
	}
	
	public HttpURLConnection checkCoronaVerison(String accessToken, String gammaBaseUrl) throws IOException {
		String osType = getosType();
		String checksum = getFileHash();
		if (StringUtils.isBlank(checksum)) {
			logger.info("Checksum is blank. Skipping corona version check.");
			return null;
		}
		String sourceUrl =getUrl(gammaBaseUrl,accessToken,osType,checksum);
		logger.info("Checking version with url : "+sourceUrl);
		return makeRequest(accessToken, sourceUrl);		
	}
	//logger.info("Checking if latest corona is available");
	//logger.info("Checking completed.");
}
