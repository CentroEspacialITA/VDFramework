package org.cei.vdframework.kernel.service;


import org.cei.vdframework.kernel.engine.IExecutionEngine;

public interface IExecutionEngineService<E extends IExecutionEngine>{
	/**
	 * Let any service know the model on which the current execution is focused.
	 * Users are not intended to call directly this operation. It is automatically
	 * called as soon as the execution begin
	 * 
	 * @param engine engine notifying this service
	 */
	public void init(E engine);

	/**
	 * Let any service release all its resources when the execution terminates Users
	 * are not intended to call directly this operation. It is automatically called
	 * as soon as the execution terminates
	 * 
	 *  @param engine engine notifying this service
	 */
	public void dispose(E engine);
	
	
}
