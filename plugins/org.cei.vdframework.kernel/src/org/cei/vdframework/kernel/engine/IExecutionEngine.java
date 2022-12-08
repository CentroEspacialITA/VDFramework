package org.cei.vdframework.kernel.engine;


import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.debug.core.model.ITerminate;


public interface IExecutionEngine extends ITerminate {

	/**
	 * Set the ID associated with this engine
	 * 
	 * @param id the id
	 */
	void setID(final String id);
	
	/**
	 * Return the ID associated with this engine
	 * 
	 * @return the id
	 */
	String getID();
	
	/**
	 * Return the configuration attached to this engine
	 * 
	 * @return the configuration
	 */
	EngineConfiguration<?> getConfiguration();
	
	/**
	 * Run this engine based on the provided configuration
	 * 
	 * @param configuration
	 * 	the configuration providing information regarding the execution
	 *  that needs to be performed
	 * 
	 * @param monitor
	 *  provide the opportunity to report progress
	 *  
	 * @exception ExecutionEngineException
	 */
	void run(final EngineConfiguration<?> configuration, SubMonitor monitor) throws ExecutionEngineException;
	
	
	
}
