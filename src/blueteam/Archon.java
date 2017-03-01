package blueteam;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import battlecode.common.RobotInfo;

public class Archon extends Robot {

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
		int numOfGardeners = rc.readBroadcast(TeamConstants.GARDENERS_COUNT_CHANNEL);
		if (numOfGardeners < TeamConstants.DESIRED_NUMBER_OF_GARDENERS && rc.canHireGardener(dir)
				&& Math.random() < 0.5) {
			rc.hireGardener(dir);
			rc.broadcast(TeamConstants.GARDENERS_COUNT_CHANNEL, numOfGardeners + 1);
		}

		// buy some victory points randomly
		if (Math.random() < 0.05) {
			rc.donate(rc.getTeamBullets() / 4);
		}
	}

}
