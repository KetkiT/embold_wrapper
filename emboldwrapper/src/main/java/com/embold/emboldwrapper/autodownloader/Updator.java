package com.embold.emboldwrapper.autodownloader;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.Logger;

import com.embold.emboldwrapper.common.EmboldConstants;
import com.embold.emboldwrapper.exception.EmboldWrapperException;
import com.embold.emboldwrapper.logger.ProgressLogger;
import com.embold.emboldwrapper.utility.FileUtility;

public class Updator {
	private static Logger logger = org.apache.logging.log4j.LogManager.getLogger(Updator.class);
	
	public void update(String coronaHome, String latestCoronaDir) {
		FileUtility.copyExistingCofigFile(coronaHome, latestCoronaDir);
		String backUpDirName = EmboldConstants.CORONA_BACKUP;
		File backupDir = new File(FileUtility.getParentDirOfDir(coronaHome) + File.separator + backUpDirName);
		File existingCorona = new File(coronaHome);
		try {
			logger.info("Taking backup");
			FileUtility.backUpExistingCorona(existingCorona, backupDir);
			logger.info("Backup taken at : " + backupDir);
		} catch (EmboldWrapperException ex) {
			logger.error("corona failed : " + ex.getMessage(), ex);
			logger.info("Skipping replacing corona.");
			return;
		}
		logger.info("Updating existing corona with latest");
		File sourceFolder = new File(latestCoronaDir);
		File destinationFolder = new File(coronaHome);
		try {
			FileUtility.copyDir(sourceFolder, destinationFolder);
		} catch (IOException ex) {
			logger.error("Replacing corona failed : " + ex.getMessage(), ex);
			logger.info("Original corona is kept at:  "+backupDir.getAbsolutePath());
			logger.info("Latest corona is kept at: "+latestCoronaDir);
			return ;
			/*try {
				logger.error("Rolling back to original corona");
				FileUtility.rollBack(backupDir, existingCorona);
			} catch (EmboldWrapperException e) {
				
				logger.error("Corona auto update failed : " ,e);
				return;

			}*/
		}
		try {
			logger.info("Cleaning temp directories");
			FileUtility.cleanUp(backupDir);
			FileUtility.cleanUp(sourceFolder);
			logger.info("Cleaning completed");
		} catch (EmboldWrapperException e) {
			logger.error("Cleaning temp dir failed : " + e.getMessage(), e);
		}
		ProgressLogger.LOG.done();
		logger.info("Updation completed");

	}
}
