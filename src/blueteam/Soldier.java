package blueteam;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.RobotInfo;
/**
 * Naive implementation: 
 * Moves randomly, changes direction only if the current direction is blocked.
 * 	x with 25 % probability, it moves in the direction of the opponents archon location.
 * If it can shoot, it shoots and stay in place
 * @author Tomas
 *
 */
public class Soldier extends Robot {

	private Direction moveDir;
	
	//probability that the chosen direction is random. 
	private double RANDOM_MOVE_PROB=.75;
	
	Soldier(RobotController rc) {
		super(rc);
		moveDir=randomDirection();
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
				//Do not move..
				return;
				
			}
		}

		// Move randomly
		if (!rc.canMove(moveDir,rc.getType().strideRadius/2)){
			if (Math.random()<RANDOM_MOVE_PROB) {
				moveDir = randomDirection();				
			} else {
				MapLocation[] archons = rc.getInitialArchonLocations(enemy);
				MapLocation rndArchon = archons[(int)Math.floor(Math.random()*archons.length)];
				moveDir=rc.getLocation().directionTo(rndArchon);				
			}
		}
		tryMove(moveDir);
	}

}
