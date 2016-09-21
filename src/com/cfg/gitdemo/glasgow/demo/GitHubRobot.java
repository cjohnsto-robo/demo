package com.cfg.gitdemo.glasgow.demo;

import java.awt.Color;

import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import robocode.StatusEvent;
import robocode.WinEvent;

public class GitHubRobot extends ObservableRobot {

    private Color currentGunColour = null;

    public String getName() {
        return "Test Bot";
    }

    public void run() {
        while(keepRunning) {
            // Do robot things here...
        }
    }

    @Override
    public void onHitWall(HitWallEvent event) {
    	turnRight(24.0);
    	ahead(56.9);
    }

    @Override
    public void onHitRobot(HitRobotEvent event) {

    }

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {

    }

    @Override
    public void onWin(WinEvent event) {

    }

    @Override
    public void onStatus(StatusEvent event) {
    		setGunColor(Color.WHITE);
    }

}