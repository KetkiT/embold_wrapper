package com.embold.emboldwrapper.proc;

import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.ExecuteWatchdog;

public class ExecProcessWrapper {

	private ExecuteWatchdog watchdog;
	private DefaultExecuteResultHandler resultHandler;

	public ExecProcessWrapper(ExecuteWatchdog watchdog, DefaultExecuteResultHandler resultHandler) {
		this.watchdog = watchdog;
		this.resultHandler = resultHandler;
	}

	public void waitFor(long timeoutMillis) throws InterruptedException {
		resultHandler.waitFor(timeoutMillis);
	}

	public void destroy() {
		// TODO This is a weird check - need something better to find out if the
		// process is running or not
		if (!resultHandler.hasResult()) {
			watchdog.destroyProcess();
		}
	}

	public boolean checkdestroy() {
		return watchdog.killedProcess();
	}

	public DefaultExecuteResultHandler resultHandler() {
		return resultHandler;
	}
}
