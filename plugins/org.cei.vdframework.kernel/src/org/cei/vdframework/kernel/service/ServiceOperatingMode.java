package org.cei.vdframework.kernel.service;

public enum ServiceOperatingMode {
	NORMAL, // Services (animation, debug, etc) are notified by engine at runtime
	QUIET, // Services (animation, debug, etc) are NOT notified by engine at runtime
}
