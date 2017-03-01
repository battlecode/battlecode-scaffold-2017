package blueteam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

import battlecode.common.BulletInfo;
import battlecode.common.Clock;
import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.RobotInfo;
import battlecode.common.RobotType;
import battlecode.common.Team;

abstract public class Robot {
	RobotController rc;
	Team enemy;
	Random rand;

	Robot(RobotController rc) {
		this.rc = rc;
		enemy = rc.getTeam().opponent();
		rand = new Random();
	}

	void run() throws GameActionException {
		while (true) {
			dodge();
			step();
			Clock.yield();
		}
	}

	abstract void step() throws GameActionException;

	/**
	 * Returns a random Direction
	 *
	 * @return a random Direction
	 */
	Direction randomDirection() {
		return new Direction((float) Math.random() * 2 * (float) Math.PI);
	}

	/**
	 * Attempts to move in a given direction, while avoiding small obstacles
	 * directly in the path.
	 *
	 * @param dir
	 *            The intended direction of movement
	 * @return true if a move was performed
	 * @throws GameActionException
	 */
	boolean tryMove(Direction dir) {
		return tryMove(dir, 20, 3);
	}

	/**
	 * Attempts to move in a given direction, while avoiding small obstacles
	 * direction in the path.
	 *
	 * @param dir
	 *            The intended direction of movement
	 * @param degreeOffset
	 *            Spacing between checked directions (degrees)
	 * @param checksPerSide
	 *            Number of extra directions checked on each side, if intended
	 *            direction was unavailable
	 * @return true if a move was performed
	 * @throws GameActionException
	 */
	boolean tryMove(Direction dir, float degreeOffset, int checksPerSide) {
		try {
			// First, try intended direction
			if (rc.canMove(dir)) {
				rc.move(dir);
				return true;
			}

			// Now try a bunch of similar angles
			int currentCheck = 1;

			while (currentCheck <= checksPerSide) {
				// Try the offset of the left side
				if (rc.canMove(dir.rotateLeftDegrees(degreeOffset * currentCheck))) {
					rc.move(dir.rotateLeftDegrees(degreeOffset * currentCheck));
					return true;
				}
				// Try the offset on the right side
				if (rc.canMove(dir.rotateRightDegrees(degreeOffset * currentCheck))) {
					rc.move(dir.rotateRightDegrees(degreeOffset * currentCheck));
					return true;
				}
				// No move performed, try slightly further
				currentCheck++;
			}

			// A move never happened, so return false.
			return false;
		} catch (GameActionException e) {
			// this can't actually happen since we always ask canMove first
			return false;
		}
	}

	/**
	 * A slightly more complicated example function, this returns true if the
	 * given bullet is on a collision course with the current robot. Doesn't
	 * take into account objects between the bullet and this robot.
	 *
	 * @param bullet
	 *            The bullet in question
	 * @return True if the line of the bullet's path intersects with this
	 *         robot's current position.
	 */
	boolean willCollideWithMe(BulletInfo bullet) {
		MapLocation myLocation = rc.getLocation();

		// Get relevant bullet information
		Direction propagationDirection = bullet.dir;
		MapLocation bulletLocation = bullet.location;

		// Calculate bullet relations to this robot
		Direction directionToRobot = bulletLocation.directionTo(myLocation);
		float distToRobot = bulletLocation.distanceTo(myLocation);
		float theta = propagationDirection.radiansBetween(directionToRobot);

		// If theta > 90 degrees, then the bullet is traveling away from us and
		// we can break early
		if (Math.abs(theta) > Math.PI / 2) {
			return false;
		}

		// distToRobot is our hypotenuse, theta is our angle, and we want to
		// know this length of the opposite leg.
		// This is the distance of a line that goes from myLocation and
		// intersects perpendicularly with propagationDirection.
		// This corresponds to the smallest radius circle centered at our
		// location that would intersect with the
		// line that is the path of the bullet.
		float perpendicularDist = (float) Math.abs(distToRobot * Math.sin(theta));

		return (perpendicularDist <= rc.getType().bodyRadius);
	}

	/**
	 * Try to move in direction perpendicular to bullet trajectory
	 *
	 * @param bullet
	 * @return true if move succeeded
	 */
	boolean trySidestep(BulletInfo bullet) {
		Direction towards = bullet.getDir();
		return (tryMove(towards.rotateRightDegrees(90)) || tryMove(towards.rotateLeftDegrees(90)));
	}

	/**
	 * Try to dodge all bullets in range.
	 */
	void dodge() {
		BulletInfo[] bullets = rc.senseNearbyBullets();
		Arrays.stream(bullets).filter(x -> willCollideWithMe(x)).forEach(x -> trySidestep(x));
	}

	ArrayList<RobotInfo> filterByType(RobotInfo[] robots, RobotType type) {
		ArrayList<RobotInfo> res = new ArrayList<>();
		for (RobotInfo robot : robots) {
			if (robot.getType() == type)
				res.add(robot);
		}
		return res;
	}

	Optional<RobotInfo> getNearestRobot(RobotType type, float maxRadius) {
		RobotInfo[] close_enemies = rc.senseNearbyRobots(maxRadius, enemy);
		ArrayList<RobotInfo> robots = filterByType(close_enemies, type);
		if (robots.size() > 0) {
			rc.setIndicatorDot(robots.get(0).getLocation(), 0, 250, 0);
			return Optional.of(robots.get(0));
		}
		return Optional.empty();
	}
}
