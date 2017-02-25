package blueteam;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import battlecode.common.RobotType;

public class Gardener extends Robot {

	Gardener(RobotController rc) {
		super(rc);
	}

	@Override
	void step() throws GameActionException {
		// TODO Lukas

		// Generate a random direction
		Direction dir = randomDirection();

		// Randomly attempt to build a soldier or lumberjack in this
		// direction
		if (rc.canBuildRobot(RobotType.SOLDIER, dir) && Math.random() < .01) {
			rc.buildRobot(RobotType.SOLDIER, dir);
		} else if (rc.canBuildRobot(RobotType.LUMBERJACK, dir) && Math.random() < .01 && rc.isBuildReady()) {
			rc.buildRobot(RobotType.LUMBERJACK, dir);
		}

		// Move randomly
		tryMove(randomDirection());
	}

}
