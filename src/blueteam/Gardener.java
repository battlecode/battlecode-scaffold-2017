package blueteam;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.RobotType;

public class Gardener extends Robot {

	Gardener(RobotController rc) {
		super(rc);
	}

	@Override
	void step() throws GameActionException {
		// TODO Lukas
		
		// Listen for home archon's location
		int xPos = rc.readBroadcast(0);
		int yPos = rc.readBroadcast(1);
		MapLocation archonLoc = new MapLocation(xPos, yPos);

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
