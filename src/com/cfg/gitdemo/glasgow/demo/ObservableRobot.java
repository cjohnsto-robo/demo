package com.cfg.gitdemo.glasgow.demo;

import robocode.Robot;

public abstract class ObservableRobot extends Robot {

	protected volatile boolean keepRunning=true;

	public final boolean isRunnable() {
		return keepRunning;
	}

	public final void shutdown() {
		keepRunning=false;
	}
}
