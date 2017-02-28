package blueteam;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import battlecode.common.RobotInfo;

public class Archon extends Robot {

	private final int desiredNumberOfGardeners = 10;

	Archon(RobotController rc) {
		super(rc);
	}

	@Override
	void step() throws GameActionException {
		// move away from enemy
		RobotInfo[] robots = rc.senseNearbyRobots(-1, enemy);
		if (robots.length > 0) {
			Direction toEnemy = rc.getLocation().directionTo(robots[0].getLocation());
			tryMove(toEnemy.opposite());
		} else {
			tryMove(randomDirection());
		}

		// randomly attempt to build a gardener if we need more
		Direction dir = randomDirection();
		int numOfGardeners = rc.readBroadcast(0);
		if (numOfGardeners < desiredNumberOfGardeners && rc.canHireGardener(dir) && Math.random() < 0.5) {
			rc.hireGardener(dir);
			rc.broadcast(0, numOfGardeners + 1);
		}

		// buy some victory points randomly
		if (Math.random() < 0.05) {
			rc.donate(rc.getTeamBullets() / 4);
		}
	}

}
