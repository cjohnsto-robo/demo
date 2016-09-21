package com.cfg.gitdemo.glasgow.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.awt.Color;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Collection;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import static org.mockito.Mockito.times;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import net.sf.robocode.security.IHiddenStatusHelper;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.Robot;
import robocode.RobotStatus;
import robocode.ScannedRobotEvent;
import robocode.StatusEvent;
import robocode.robotinterfaces.peer.IStandardRobotPeer;

public class GitHubRobotTest {

	private static double PI = 3.141592653589793;

    private static Class<? extends ObservableRobot> robotClass;

	@Test
	public void testNopBot() throws Exception {
		ObservableRobot robot = instantiateRobot();
		IStandardRobotPeer peer = spy(IStandardRobotPeer.class);
		robot.setPeer(peer);

		Thread robotThread = startRobot(robot);

		stopRobot(robot, robotThread);
	}

	@Test
	public void testNamedBot() throws Exception {
		ObservableRobot robot = instantiateRobot();
		IStandardRobotPeer peer = spy(IStandardRobotPeer.class);
		robot.setPeer(peer);

		assertNotNull(robot.getName());
	}

	private IHiddenStatusHelper getStatusHelper() {
		IHiddenStatusHelper statusHelper=null;
		Method method;

		// pure evil from robocode
		try {
			method = RobotStatus.class.getDeclaredMethod("createHiddenSerializer");
			method.setAccessible(true);
			statusHelper = (IHiddenStatusHelper) method.invoke(null);
			method.setAccessible(false);
		} catch (Throwable t) {
			fail(t.getMessage());
		}
		return statusHelper;
	}

	// ----------------------------------------------------------------------

	private Thread startRobot(Robot robot) {
		Thread robotThread = new Thread(robot);
		robotThread.start();
		Thread.yield();

		return robotThread;
	}

	private void stopRobot(ObservableRobot robot, Thread robotThread) {
		robot.shutdown();

		try {
			robotThread.join(1);
		} catch (InterruptedException e) {
			fail("Robot didn't stop in time");
		}
	}

    private static synchronized ObservableRobot instantiateRobot() throws Exception {
        if (null == robotClass) {
            ClassLoader classLoader = GitHubRobotTest.class.getClassLoader();
            Collection<URL> urls = ClasspathHelper.forPackage(GitHubRobotTest.class.getPackage().getName(), classLoader);

            ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
            configurationBuilder.setClassLoaders(new ClassLoader[] {classLoader});
	        configurationBuilder.setUrls(urls);

            Reflections reflections = new Reflections(configurationBuilder);

            Set<Class<? extends ObservableRobot>> subTypesOf = reflections.getSubTypesOf(ObservableRobot.class);

            assertEquals(1, subTypesOf.size());

            robotClass = subTypesOf.iterator().next();
        }

	    return robotClass.newInstance();
	}
}
