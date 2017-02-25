package blueteam;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;

public class Archon extends Robot {

	Archon(RobotController rc) {
		super(rc);
	}

	@Override
	void step() throws GameActionException {
		// Generate a random direction
		Direction dir = randomDirection();

		// Randomly attempt to build a gardener in this direction
		if (rc.canHireGardener(dir) && Math.random() < .01) {
			rc.hireGardener(dir);
		}

		// Move randomly
		tryMove(randomDirection());

		// Broadcast archon's location for other robots on the team to
		// know
		MapLocation myLocation = rc.getLocation();
		rc.broadcast(0, (int) myLocation.x);
		rc.broadcast(1, (int) myLocation.y);

	}

}
