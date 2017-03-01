package blueteam;

import java.util.Optional;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.RobotInfo;
import battlecode.common.RobotType;
import battlecode.common.Team;
import battlecode.common.TreeInfo;

public class Scout extends Robot {

	Direction dir;
	final int ROTATE_MIN = 30;
	final int ROTATE_RANGE = 90;

	Scout(RobotController rc) {
		super(rc);
		dir = rc.getLocation().directionTo(rc.getInitialArchonLocations(enemy)[0]);
	}

	Optional<TreeInfo> findBulletTree() {
		TreeInfo[] trees = rc.senseNearbyTrees(-1, Team.NEUTRAL);
		if (trees.length > 0) {
			// res = Optional.of(trees[trees.length -1]);
			for (TreeInfo tree : trees) {
				if (tree.containedBullets > 0) {
					rc.setIndicatorDot(tree.getLocation(), 250, 0, 0);
					if (rc.canMove(tree.getLocation())) {
						return Optional.of(tree);
					}
				}
			}
		}
		return Optional.empty();
	}

	boolean shakeTheTree(TreeInfo tree) throws GameActionException {
		if (rc.canShake(tree.getLocation())) {
			rc.shake(tree.getLocation());
			return true;
		}
		return false;
	}

	@Override
	void step() {
		try {
			boolean danger = false;
			// avoid lumberjacks
			Optional<RobotInfo> lumberjack = getNearestRobot(RobotType.LUMBERJACK, rc.getType().sensorRadius / 4);
			if (lumberjack.isPresent()) {
				dir = new Direction(rc.getLocation(), lumberjack.get().getLocation()).opposite();
				if (rand.nextBoolean()) {
					dir = dir.rotateLeftDegrees(45);
				} else {
					dir = dir.rotateRightDegrees(45);
				}
				danger = true;
			}
			if (!danger) {
				Optional<TreeInfo> bulletTree = findBulletTree();
				if (bulletTree.isPresent()) {
					if (!shakeTheTree(bulletTree.get())) {
						// we need to get to this tree first
						MapLocation treeLoc = bulletTree.get().getLocation();
						if (rc.canMove(treeLoc) == true) {
							rc.move(treeLoc);
							return;
						}
					}
				}
			}

			// just move in direction
			if (!tryMove(dir, 20, 1))
				dir = dir.rotateRightDegrees(rand.nextInt(ROTATE_RANGE) + ROTATE_MIN);

		} catch (GameActionException e) {
			System.out.println("Scout Exception");
			e.printStackTrace();
		}
	}
}
