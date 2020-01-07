package com.embold.emboldwrapper.autodownloader;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Logger;

import com.embold.emboldwrapper.env.CoronaHome;
import com.embold.emboldwrapper.exception.EmboldWrapperException;
import com.embold.emboldwrapper.utility.FileUtility;

public class Downloader {

	private static Logger logger = org.apache.logging.log4j.LogManager.getLogger(Downloader.class);
	private String latestCoronaDir = "corona_latest";
	private String newCoronaArchiveFile = "corona-archive.tar.gz";
	private DownloadDetails downloaderDetails = null;
	private final int SUCCESS = 200;
	private final int INTERNAL_ERROR = 501;

	private long download(HttpURLConnection uc) throws EmboldWrapperException {
		long t1 = System.currentTimeMillis();
		long noOfBytesCopied = 0;
		try {
			InputStream inputStream = uc.getInputStream();
			String fileName = FileUtility.createTempFileForAutoDownload(latestCoronaDir, newCoronaArchiveFile);
			newCoronaArchiveFile = fileName;
			FileOutputStream fileOS = new FileOutputStream(fileName);
			logger.info("Downloading latest corona .....");
			noOfBytesCopied = IOUtils.copy(inputStream, fileOS);
			// if size > 2 GB
			if (noOfBytesCopied < 0) {
				noOfBytesCopied = IOUtils.copyLarge(inputStream, fileOS);
			}
			logger.info("Downloading corona completed");
		} catch (IOException e) {
			logger.error("Error occurred while downloading file");
			throw new EmboldWrapperException("Could not download file", e);
		}
		long t2 = System.currentTimeMillis();
		logger.info("Latest corona file downloaded at location : " + newCoronaArchiveFile);
		logger.info(noOfBytesCopied + " bytes copied in " + (t2 - t1) / 1000 + " seconds");
		return noOfBytesCopied;
	}

	public int extract() throws EmboldWrapperException {
		logger.info("Extracting " + newCoronaArchiveFile + "  .....");
		long t1 = System.currentTimeMillis();
		int BUFFER_SIZE = 1024;
		latestCoronaDir = FileUtility.getAbsolutePath(latestCoronaDir);
		try (FileInputStream fis = new FileInputStream(newCoronaArchiveFile)) {
			try (TarArchiveInputStream tarIn = new TarArchiveInputStream(new GzipCompressorInputStream(fis))) {
				TarArchiveEntry entry;
				while ((entry = (TarArchiveEntry) tarIn.getNextEntry()) != null) {
					if (entry.isDirectory()) {
						File f = new File(latestCoronaDir + File.separator + entry.getName());
						boolean created = f.mkdir();
						if (!created) {
							logger.info(
									"Unable to create directory '%s', " + "during extraction of archive contents.\n",
									f.getAbsolutePath());
							return INTERNAL_ERROR;
						}
					} else {
						int count;
						byte data[] = new byte[BUFFER_SIZE];
						try (FileOutputStream fos = new FileOutputStream(
								latestCoronaDir + File.separator + entry.getName(), false)) {
							try (BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER_SIZE)) {
								while ((count = tarIn.read(data, 0, BUFFER_SIZE)) != -1) {
									dest.write(data, 0, count);
								}
							}
						}
					}
				}
				long t2 = System.currentTimeMillis();
				logger.info("Latest corona is extracted at :  " + latestCoronaDir);
				logger.info("File Extracted in " + (t2 - t1) / 1000 + " seconds");
			}
		} catch (IOException e) {
			logger.error("Error occurred while unzipping file : " + newCoronaArchiveFile);
			throw new EmboldWrapperException("Could not download file", e);
		}
		return SUCCESS;
	}

	public void downloadAndExtract(HttpURLConnection connection) throws EmboldWrapperException {
		long noOfBytesCopied = download(connection);
		// TODO specify exact size
		if (noOfBytesCopied > 0) {
			int statusCode = extract();
			if (statusCode == SUCCESS) {
				String coronaHome = CoronaHome.getExit();
				downloaderDetails = new DownloadDetails(coronaHome, latestCoronaDir);
				downloaderDetails.setDownloadedSize(noOfBytesCopied);
			} else {
				logger.info("Latest corona extraction failed.");
			}
		} else {
			logger.info("Latest corona downloading failed.");
		}

	}

	public DownloadDetails getDownloadDetails() {
		return downloaderDetails;
	}

	public class DownloadDetails {
		//private String coronaArchiveFileName;
		private String coronaHomeDir;
		private String latestCoronaDir;
		private long downloadedSize;
		private long extractedSize;

		DownloadDetails(String coronaHomeDir, String newCoronaHome) {
			this.coronaHomeDir = coronaHomeDir;
			this.latestCoronaDir = newCoronaHome;
		}

		public String getCoronaHomeDir() {
			return coronaHomeDir;
		}

		public String getLatestCoronaDir() {
			return latestCoronaDir;
		}

		public long getDownloadedSize() {
			return downloadedSize;
		}

		public long getExtractedSize() {
			return extractedSize;
		}

		public void setDownloadedSize(long downloadedSize) {
			this.downloadedSize = downloadedSize;
		}

		public void setExtractedSize(long extractedSize) {
			this.extractedSize = extractedSize;
		}

	}
}