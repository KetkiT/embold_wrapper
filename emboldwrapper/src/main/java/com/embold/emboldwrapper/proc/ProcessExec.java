package com.embold.emboldwrapper.proc;


import java.io.IOException;
import java.util.Arrays;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import com.embold.emboldwrapper.exception.EmboldWrapperException;


public class ProcessExec {
	private static org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(ProcessExec.class);
	public static void executeProcessSync(String executable, String[] args)
			throws EmboldWrapperException {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Executing synchronous command " + executable + " with args: " + Arrays.toString(args));
			}
			String strArg="";
			for(int i=0;i<args.length;i++)
				strArg = strArg + " " + args[i];
			if (logger.isDebugEnabled()) {
				logger.debug("Executing synchronous command " + executable + " with args: " + strArg);
			}
			CommandLine cmdLine = new CommandLine(executable);
			cmdLine.addArguments(args, true);
			DefaultExecutor executor = new DefaultExecutor();
			executor.execute(cmdLine);
		} catch (IOException e) {
			throw new EmboldWrapperException(e);
		}
	}

	static class ProcExecuteResultHandler extends DefaultExecuteResultHandler {

		@Override
		public void onProcessComplete(int exitValue) {
			// TODO Auto-generated method stub
			super.onProcessComplete(exitValue);
		}

		@Override
		public void onProcessFailed(ExecuteException e) {
			// TODO Auto-generated method stub
			super.onProcessFailed(e);
		}
	}
}
