package com.embold.emboldwrapper.proc;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteResultHandler;
import org.apache.commons.exec.ExecuteStreamHandler;
import org.apache.commons.exec.Executor;

public class CustomDefaultExecutor extends DefaultExecutor {
	
	private Process process = null;
	private CountDownLatch latch = new CountDownLatch(1);
	public CountDownLatch getLatch() {
		return latch;
	}

	public Process getProcess() {
		return process;
	}

	private Thread executorThread;
	
	/**
     * Get the worker thread being used for asynchronous execution.
     *
     * @return the worker thread
     */
    protected Thread getExecutorThread() {
        return executorThread;
    }
	
    @Override
    public void execute(final CommandLine command, ExecuteResultHandler handler)
            throws ExecuteException, IOException {
        execute(command, null, handler);
    }
    
	@Override
    public void execute(final CommandLine command, final Map environment,
            final ExecuteResultHandler handler) throws ExecuteException, IOException {

        if (this.getWorkingDirectory()!= null && !this.getWorkingDirectory().exists()) {
            throw new IOException(this.getWorkingDirectory() + " doesn't exist.");
        }

        final File workingDirectory = this.getWorkingDirectory();
        final ExecuteStreamHandler streamHandler = this.getStreamHandler();
        
        executorThread = new Thread() {
            public void run() {
                int exitValue = Executor.INVALID_EXITVALUE;
                try {                    
                    exitValue = customExecute(command, environment, workingDirectory, streamHandler);
                    handler.onProcessComplete(exitValue);
                } catch (ExecuteException e) {
                    handler.onProcessFailed(e);
                    latch.countDown();
                } catch(Exception e) {
                    handler.onProcessFailed(new ExecuteException("Execution failed", exitValue, e));
                    latch.countDown();
                }
            }
        };

        this.getExecutorThread().start();
    }
    
	
	@Override
    public int execute(final CommandLine command) throws ExecuteException,
            IOException {
        return execute(command, (Map) null);
    }
    
	@Override
    public int execute(final CommandLine command, Map environment)
            throws ExecuteException, IOException {

    	File workingDirectory = this.getWorkingDirectory();
        if (workingDirectory != null && !workingDirectory.exists()) {
            throw new IOException(workingDirectory + " doesn't exist.");
        }
        
        return customExecute(command, environment, workingDirectory, this.getStreamHandler());

    }
    /**
     * Execute an internal process.
     *
     * @param command the command to execute
     * @param environment the execution enviroment
     * @param dir the working directory
     * @param streams process the streams (in, out, err) of the process
     * @return the exit code of the process
     * @throws IOException executing the process failed
     */
    private int customExecute(final CommandLine command, final Map environment,
            final File dir, final ExecuteStreamHandler streams) throws IOException {

        process = this.launch(command, environment, dir);
        latch.countDown();
        try {
            streams.setProcessInputStream(process.getOutputStream());
            streams.setProcessOutputStream(process.getInputStream());
            streams.setProcessErrorStream(process.getErrorStream());
        } catch (IOException e) {
            process.destroy();
            throw e;
        }

        streams.start();

        try {

            // add the process to the list of those to destroy if the VM exits
            if(this.getProcessDestroyer() != null) {
              this.getProcessDestroyer().add(process);
            }

            // associate the watchdog with the newly created process
            if (this.getWatchdog() != null) {
            	this.getWatchdog().start(process);
            }

            int exitValue = Executor.INVALID_EXITVALUE;

            try {
                exitValue = process.waitFor();
            } catch (InterruptedException e) {
                process.destroy();
            }
            finally {
                // see http://bugs.sun.com/view_bug.do?bug_id=6420270
                // see https://issues.apache.org/jira/browse/EXEC-46
                // Process.waitFor should clear interrupt status when throwing InterruptedException
                // but we have to do that manually
                Thread.interrupted();
            }            

            if (this.getWatchdog() != null) {
            	this.getWatchdog().stop();
            }

            streams.stop();
            closeStreams(process);

            if (this.getWatchdog() != null) {
                try {
                	this.getWatchdog().checkException();
                } catch (IOException e) {
                    throw e;
                } catch (Exception e) {
                    throw new IOException(e.getMessage());
                }
            }

            if(this.isFailure(exitValue)) {
                throw new ExecuteException("Process exited with an error: " + exitValue, exitValue);
            }

            return exitValue;
        } finally {
            // remove the process to the list of those to destroy if the VM exits
            if(this.getProcessDestroyer() != null) {
              this.getProcessDestroyer().remove(process);
            }
        }
    }
	
	/**
     * Close the streams belonging to the given Process. 
     * 
     * @param process the Process.
     * 
     * @throws IOException closing one of the three streams failed
     */
    public void closeStreams(final Process process) throws IOException {
        IOException caught = null;
        try {
            process.getInputStream().close();
        }
        catch(IOException e) {
            caught = e;
        }
        try {
            process.getOutputStream().close();
        }
        catch(IOException e) {
            caught = e;
        }
        try {
            process.getErrorStream().close();
        }
        catch(IOException e) {
            caught = e;
        }
        if(caught != null) {
            throw caught;
        }
    }
}
