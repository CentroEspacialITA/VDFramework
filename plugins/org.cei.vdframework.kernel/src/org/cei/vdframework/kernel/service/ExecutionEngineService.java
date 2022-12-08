package org.cei.vdframework.kernel.service;

import org.cei.vdframework.kernel.engine.IExecutionEngine;

public abstract class ExecutionEngineService<EngineType extends IExecutionEngine> implements IExecutionEngineService<EngineType> {
	// Engine that requested the service to run
		protected EngineType engine;
		
		public void init(EngineType engine) {
			// By default keep  - convenience for services which do not need to know
			// the model that is currently related to this instance of the execution
			this.engine = engine;
		}

		public void dispose(EngineType engine) {
			// By default do nothing - convenience for services which do not need to dispose
			// any resource.
			
		}
}
