package com.embold.emboldwrapper;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.Logger;

import com.embold.emboldwrapper.common.EmboldConstants;
import com.embold.emboldwrapper.exception.EmboldWrapperException;
import com.embold.emboldwrapper.json.JsonParser;
import com.embold.emboldwrapper.opt.EmboldWrapperOptBuilder;
import com.embold.emboldwrapper.opt.EmboldWrapperParams;

public class EmboldRemoteScanWrapperMain {
	private final static Logger logger = org.apache.logging.log4j.LogManager
			.getLogger(EmboldRemoteScanWrapperMain.class);

	public static void main(String[] args) {

		EmboldWrapperOptBuilder optBuilder = new EmboldWrapperOptBuilder();
		try {
			CommandLineParser parser = new DefaultParser();
			CommandLine line = parser.parse(optBuilder.getOptions(), args);
			EmboldWrapperParams emboldWrapper = new EmboldWrapperParams(line);
			RemoteScanExecutor scanExecutor = new RemoteScanExecutor();
			try {
				GammaAccess gammaAccess = getGammaAccessDetails(emboldWrapper);
				scanExecutor.update(gammaAccess);
			} catch (EmboldWrapperException ex) {
				logger.error("\nError in auto updating corona : Reason: ",ex);
			}
			scanExecutor.executeScanboxwrapper(args);
		} catch (ParseException e) {
			print("Embold Remote Scan Wrapper : ", optBuilder.getOptions());
			System.exit(-1);
		} catch (EmboldWrapperException e) {
			logger.error("\nError in EmboldWrapper. Reason: ", e);
		}
	}

	private static GammaAccess getGammaAccessDetails(EmboldWrapperParams emboldWrapper) throws EmboldWrapperException {
		String jsonPath = emboldWrapper.getJsonPath();
		File configFile = new File(jsonPath);
		JsonParser jsonParser = new JsonParser(configFile);
		return new GammaAccess(jsonParser.getGammaAccessJsonObj(EmboldConstants.GAMMA_ACCESS));
	}

	private static void print(String progName, Options options) {
		HelpFormatter h = new HelpFormatter();
		h.setOptionComparator(null);
		h.printHelp(progName, options, true);
	}
}
