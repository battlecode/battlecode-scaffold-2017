package blueteam;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;

public strictfp class RobotPlayer {
	/**
	 * run() is the method that is called when a robot is instantiated in the
	 * Battlecode world. If this method returns, the robot dies!
	 **/
	public static void run(RobotController rc) throws GameActionException {
		Robot robot;
		switch (rc.getType()) {
		case ARCHON:
			robot = new Archon(rc);
			break;
		case GARDENER:
			robot = new Gardener(rc);
			break;
		case SOLDIER:
			robot = new Soldier(rc);
			break;
		case LUMBERJACK:
			robot = new LumberJack(rc);
			break;
		case SCOUT:
			robot = new Scout(rc);
			break;
		case TANK:
			robot = new Tank(rc);
			break;
		default:
			throw new IllegalArgumentException("robot type not supported");
		}

		robot.run();
	}
}
