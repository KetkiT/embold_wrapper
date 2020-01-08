package com.embold.emboldwrapper.utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.Logger;

import com.embold.emboldwrapper.env.CoronaHome;
import com.embold.emboldwrapper.env.PathUtil;
import com.embold.emboldwrapper.exception.EmboldWrapperException;

/**
 * 
 * @author Ketki
 *
 */
public class FileUtility {

	private static Logger logger = org.apache.logging.log4j.LogManager.getLogger(FileUtility.class);

	public static Collection<File> getAllFiles(String fileBaseDir, String ext[]) {
		Collection<File> list = new ArrayList<File>();
		File f = new File(fileBaseDir);
		if (f.isDirectory()) {
			list = FileUtils.listFiles(new File(fileBaseDir), ext, true);
		} else if (f.isFile() && FileFilter.isSupportedFileType(f.getAbsolutePath(), ext)) {
			list.add(f);
		}
		return list;
	}

	public static void cleanAndCreateDir(String dirPath) throws EmboldWrapperException {
		try {
			File dir = createDirWithWritePermission(new File(dirPath), dirPath);
			FileUtils.cleanDirectory(dir);
		} catch (Exception e) {
			throw new EmboldWrapperException("Unable to clean or create directory at " + dirPath, e);
		}
	}

	public static File createDirWithWritePermission(File dir, String dataDir) throws EmboldWrapperException {
		logger.info("Creating dir {} if does not exists and marking with write permission", dir.getAbsolutePath());
		String dataDirFile = (new File(dataDir)).getAbsolutePath();
		logger.debug("dataDir : " + dataDir + "\n dataDirFile(getAbsolutePath) : " + dataDirFile);
		try {
			if (!dir.exists()) {
				if (!dir.mkdirs()) {
					throw new EmboldWrapperException("Unable to create directory at :" + dir.getAbsolutePath());
				}
			}

			if (dir.getAbsolutePath().equals(dataDirFile)) {
				// makeDirectoriesWritable(dir);
			} else {
				File currentDir = dir.getParentFile();
				while (currentDir.isDirectory() && (!currentDir.getAbsolutePath().equals(dataDirFile))) {
					// makeDirectoriesWritable(currentDir);
					currentDir = currentDir.getParentFile();
				}
			}
			return dir;
		} catch (Exception e) {
			throw new EmboldWrapperException("Unable to create directory at " + dir.getAbsolutePath(), e);
		}
	}

	public static String getRelativePath(String filePath, String baseDir) {
		String path = StringUtils.replace(PathUtil.convertToUnixPath(filePath), PathUtil.convertToUnixPath(baseDir),
				"");
		return StringUtils.removeStart(path, "/");
	}

	public static Boolean areSamePath(String first, String second) {
		if (first == null || second == null)
			return false;
		return StringUtils.equalsIgnoreCase(PathUtil.convertToUnixPath(first), PathUtil.convertToUnixPath(second));
	}

	public static String getAbsolutePath(String newCoronaDir) {
		String path = "";
		String coronaHome = CoronaHome.getExit();
		String parentHome = FileUtility.getParentDirOfDir(coronaHome);
		if (StringUtils.endsWith(parentHome, File.separator)) {
			path = parentHome + newCoronaDir;
		} else {
			path = parentHome + File.separator + newCoronaDir;
		}

		return path;
	}

	public static String getParentDirOfDir(String coronaHome) {
		File file = new File(coronaHome);
		String parentPath = file.getAbsoluteFile().getParent();
		return parentPath;
	}

	public static String createTempFileForAutoDownload(String newCoronaDir, String newCoronaArchiveFile)
			throws EmboldWrapperException {
		String fileName = "";
		String path = getAbsolutePath(newCoronaDir);
		File directory = new File(path);
		if (directory.exists()) {
			try {
				logger.debug("Directory " + path + " already exists. Deleting existing directory");
				FileUtils.deleteDirectory(directory);
			} catch (IOException e) {
				throw new EmboldWrapperException("Error occurred while deleting directory", e);
			}
		}
		try {
			directory.mkdirs();
			File f2 = new File(directory + File.separator + newCoronaArchiveFile);
			f2.createNewFile();
			fileName = f2.getAbsolutePath();

		} catch (IOException e) {
			throw new EmboldWrapperException("Error occurred while creating file", e);
		}
		return fileName;
	}

	public static void copyExistingCofigFile(String coronaHome, String newCoronaPath) {
		String coronaCliFilePath = getCliConfigDir(coronaHome) + File.separator + "cli.properties";
		String coronaNew = getCliConfigDir(newCoronaPath);
		File destination = new File(coronaNew);
		File coronaCliFile = new File(coronaCliFilePath);
		if (!coronaCliFile.exists()) {
			logger.debug(" " + coronaCliFilePath + " does not exist.\n Skipping back up.");
			return;
		}
		try {
			FileUtils.copyFileToDirectory(coronaCliFile, destination);
		} catch (IOException e) {
			logger.error("Error occurred coping cli.properties files", e);
		}
	}

	public static String getCliConfigDir(String coronaHome) {
		return coronaHome + File.separator + "coronacli" + File.separator + "config";
	}

	public static void backUpExistingCorona(File coronaHome, File backupDir) throws EmboldWrapperException {
		try {
			if (backupDir.exists()) {
				FileUtils.deleteDirectory(backupDir);
			}
			FileUtils.copyDirectory(coronaHome, backupDir, true);

		} catch (IOException e) {
			throw new EmboldWrapperException("Error occurred while taking backup of existing corona", e);
		}
	}

	public static void copyDir(File sourceFolder, File destinationFolder) throws IOException {
		File[] sourceFiles = sourceFolder.listFiles();
		File[] destinationFiles = destinationFolder.listFiles();
		for (File f : destinationFiles) {
			if (f.isDirectory()) {
				FileUtils.deleteDirectory(f);
			} else {
				f.delete();
			}
		}
		for (File f : sourceFiles) {
			if (f.isDirectory()) {
				FileUtils.copyDirectory(f,
						new File(destinationFolder.getAbsolutePath() + File.separator + f.getName()),false);
			} else {
				FileUtils.copyFileToDirectory(f, destinationFolder);
			}
		}
	}

	public void createBackup(String sourcePath, String destinationPath) {
		File sourceFolder = new File(sourcePath);
		File destinationFolder = new File(destinationPath);
		try {
			Files.copy(sourceFolder.toPath(), destinationFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			logger.error("Error occurred coping cli.properties files", e);
		}
	}

	public static void cleanUp(File backupDir) throws EmboldWrapperException {
		try {
			if (backupDir.exists()) {
				FileUtils.deleteDirectory(backupDir);
			}
		} catch (IOException e) {
			throw new EmboldWrapperException("Error occurred while cleaning back up data. Please delete dir : "
					+ backupDir.getAbsolutePath() + " manually", e);
		}
	}

	public static void rollBack(File sourceFolder, File destinationFolder) throws EmboldWrapperException {
		try {
			copyDir(sourceFolder, destinationFolder);
		} catch (IOException e) {
			throw new EmboldWrapperException(
					"Error occurred while retrieving original corona.",e);
		}
	}

}
