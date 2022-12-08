package org.cei.vdframework.kernel.engine;

@SuppressWarnings("serial")
final public class ExecutionEngineException extends Exception {
	/**
	 * ID of the engine from which this exception was raised
	 */
	private String engineID;
	
	/**
	 * Execution engine status at the exception time
	 */
	private ExecutionEngineStatus engineStatus; 
	
	public ExecutionEngineException(String ID, ExecutionEngineStatus status, String message) {
		super(message);
		this.engineID = ID;
		this.engineStatus = status;
		
	}
	
	public ExecutionEngineException(String ID, ExecutionEngineStatus status, String message, Throwable cause) {
		super(message, cause);
		this.engineID = ID;
		this.engineStatus = status;
	}
	
	public String getEngineID() {
		return engineID;
	}
	
	public ExecutionEngineStatus getEngineStatus() {
		return engineStatus;
	}
	
}
