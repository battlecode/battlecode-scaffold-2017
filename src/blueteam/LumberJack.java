package blueteam;

import java.util.Arrays;
import java.util.Optional;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.GameConstants;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.RobotInfo;
import battlecode.common.RobotType;
import battlecode.common.TreeInfo;
/**
 * Naive implementation:
 * 
 * Chops the nearest (non-friendly) tree. (Regardless on the treeInfo.containedBullets)
 * Is aggressive: prefers attacking if enemy is in sight.
 * Moves randomly, changes direction only if the current direction is blocked.
 * @author Tomas
 *
 */
public class LumberJack extends Robot {

	private Direction moveDirection;

	LumberJack(RobotController rc) {
		super(rc);
		moveDirection=randomDirection();
	}

	
	@Override
	void step() throws GameActionException {
		// TODO Tom
	
		// See if there are any enemy robots within striking range
		// (distance 1 from lumberjack's radius)
		RobotInfo[] robots = rc
				.senseNearbyRobots(RobotType.LUMBERJACK.bodyRadius + GameConstants.LUMBERJACK_STRIKE_RADIUS, enemy);

		MapLocation myLocation = rc.getLocation();
		if (robots.length > 0 && !rc.hasAttacked()) {
			// Use strike() to hit all nearby robots!
			rc.strike();
			return;
		}
		// No close robots, so search for robots within sight radius
		robots = rc.senseNearbyRobots(-1, enemy);
		// If there is a robot, move towards it
		if (robots.length > 0) {

			MapLocation enemyLocation = robots[0].getLocation();
			Direction toEnemy = myLocation.directionTo(enemyLocation);
			
			tryMove(toEnemy);
			return;
		}
		TreeInfo[] trees = rc.senseNearbyTrees();
		for (TreeInfo treeInfo : trees) {
			if (rc.canChop(treeInfo.ID) && treeInfo.team!=rc.getTeam()) {
				rc.chop(treeInfo.ID);
				return;
			}
		}
		// no tree can be chopped -> go to the nearest (enemy) tree:
		Optional<TreeInfo> nearest = Arrays.stream(trees)
				.filter(x->x.team!=rc.getTeam())
				.findFirst();
		if (nearest.isPresent()) {
			Direction dirToTree = myLocation.directionTo(nearest.get().getLocation());
			tryMove(dirToTree);
			rc.setIndicatorDot(myLocation, 0, 255, 0);
			return;
		}
		// No tree in sight -> move randomly:
		if (!rc.canMove(moveDirection,rc.getType().strideRadius/2)) {
			rc.setIndicatorDot(myLocation, 255, 0, 0);
			moveDirection=randomDirection();			
		}
		tryMove(moveDirection);

	}

}
