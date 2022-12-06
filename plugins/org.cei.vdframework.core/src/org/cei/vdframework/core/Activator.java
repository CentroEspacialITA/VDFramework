package org.cei.vdframework.core;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;


public class Activator extends Plugin {

	// The plug-in ID
		public static final String PLUGIN_ID = "org.cei.vdframework.core";

		// The shared instance
		private static Activator plugin;
		
		/**
		 * The constructor
		 */
		public Activator() {
			// Nothing needed for now
			
		}

		/**
		 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
		 */
		@Override
		public void start(BundleContext context) throws Exception {
			super.start(context);
			plugin = this;
		}

		/**
		 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
		 */
		@Override
		public void stop(BundleContext context) throws Exception {
			plugin = null;
			super.stop(context);
		}

		/**
		 * Returns the shared instance
		 *
		 * @return the shared instance
		 */
	
		public static Activator getDefault() {
			return plugin;
		}
	
	
	
	
}
