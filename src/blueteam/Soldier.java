package blueteam;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import battlecode.common.RobotInfo;

public class Soldier extends Robot {

	Soldier(RobotController rc) {
		super(rc);
	}

	@Override
	void step() throws GameActionException {
		// TODO Tom

		// See if there are any nearby enemy robots
		RobotInfo[] robots = rc.senseNearbyRobots(-1, enemy);

		// If there are some...
		if (robots.length > 0) {
			// And we have enough bullets, and haven't attacked yet this
			// turn...
			if (rc.canFireSingleShot()) {
				// ...Then fire a bullet in the direction of the enemy.
				rc.fireSingleShot(rc.getLocation().directionTo(robots[0].location));
			}
		}

		// Move randomly
		tryMove(randomDirection());
	}

}
