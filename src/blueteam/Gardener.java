package blueteam;

import battlecode.common.Clock;
import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.RobotInfo;
import battlecode.common.RobotType;
import battlecode.common.TreeInfo;

public class Gardener extends Robot {

	Gardener(RobotController rc) {
		super(rc);
	}

	enum GardenerState {
		FINDING, BUILDING, STARTING, ONLYSOLDIERS, LETSCHOP
	}

	GardenerState state = GardenerState.STARTING;
	Integer roundCounter = 0;
	Direction currentDir = randomDirection();
	boolean lumberjackbuilded = false;
	boolean scoutbuilded = false;

	/**
	 * Searches for suitable location to start building hexagonal tree garden
	 *
	 * @param radius radius to search for other gardeners
	 * @return true if is gardener in suitable place
	 */
	boolean findSpot(float radius) {
		// get robots in radius
		RobotInfo[] nearbyRobots = rc.senseNearbyRobots(radius);
		//TODO use constants
		if (roundCounter > 25) {
			roundCounter = 0;
			currentDir = randomDirection();
		}
		boolean rdytobuild = true;
		//check for robots
		for (RobotInfo robot : nearbyRobots)
			if (robot.getTeam().equals(rc.getTeam()))
				if (robot.getType().equals(RobotType.GARDENER) || robot.getType().equals(RobotType.ARCHON))
					rdytobuild = false;
		try {
			if (!rc.onTheMap(rc.getLocation(),radius)) {
				rdytobuild = false;
				roundCounter += 5;
			}
		}
		catch (GameActionException e) {
			System.out.println("bad radius in find loc function!");
			rdytobuild = false;
		}
		if (!rdytobuild)
			tryMove(currentDir);
		return rdytobuild;
	}

	/**
	 * Default value for radius should be 3, (gardener,tree,space,tree,gardener),
	 * but should be changed if building tanks (as they take 2 radius)
	 *
	 * @return
	 */
	boolean findSpot() {
		return findSpot(6.0f);
	}

	/**
	 * Builds tree every 30 degrees
	 *
	 * @return true if was planted
	 */
	boolean buildGarden() {
		boolean ret = false;
		Direction dir = Direction.WEST;
		for (int i = 0; i < 5; i++) {
			//TODO keep list of planted trees
			try {
				if (rc.canPlantTree(dir)) {
					rc.plantTree(dir);
					ret = true;
					//TODO mby break earlier??
				}
			} catch (GameActionException e) {
				//should not happen, mby when we check and plant in different rounds
			}
			dir = dir.rotateRightDegrees(60);
		}
		return ret;
	}

	/**
	 * tries to water all plants around robot. Assuming starting dir is WEST
	 */
	void waterGarden() {
		Direction dir = Direction.WEST;
		MapLocation loc = rc.getLocation();
		for (int i = 0; i < 5; i++) {
			try {
				loc = loc.add(dir, 1.5f);
				if (rc.canWater(loc)) {
					rc.water(loc);
				}
			} catch (GameActionException e) {
				//should not happen, mby when we check and plant in different rounds
			}
			dir = dir.rotateRightDegrees(60);
			loc = rc.getLocation();
			// assuming watering was successful, we need to wait a rount
			Clock.yield();
		}
	}

	/**
	 * builds desired robot
	 *
	 * @param type of robot
	 * @return if successful
	 */
	boolean build(RobotType type, boolean random) {
		// wait until build is rdy
		if (!rc.isBuildReady())
			Clock.yield();
		Direction dir;
		if (!random)
			dir = Direction.WEST.rotateLeftDegrees(60);
		else
			dir = randomDirection();
		if (rc.canBuildRobot(type, dir)) {
			try {
				rc.buildRobot(type, dir);
			} catch (GameActionException e) {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * assuming were in garden so build direction is NOT random
	 * @param type of robot
	 * @return if successful
	 */
	boolean build(RobotType type) {
		return build(type,false);
	}

	/**
	 * checks whether enemy Archon is nearby,
	 * used in init phase
	 * @return true if is near
	 */
	boolean isEnemyArchonNear()	{
		//TODO use constants
		RobotInfo[] nearbyRobots = rc.senseNearbyRobots();
		for (RobotInfo robot : nearbyRobots) {
			if (robot.getType() == RobotType.ARCHON && robot.getTeam() == enemy)
				return true;
		}
		return false;
	}

	/**
	 * Checks whether there is a lot (const) trees around
	 * @return true if is more than TODO const tree around
	 */
	boolean isInWoods() {
		TreeInfo[] nearbyTrees = rc.senseNearbyTrees(6.0f);
		Integer curr = 0;
		for (TreeInfo tree : nearbyTrees)
			if (!tree.getTeam().equals(rc.getTeam()))
				curr++;
		//TODO const
		if (curr > 6)
			return true;
		else
			return false;
	}
	/**
	 * States:
	 *
	 * STARTING <-> ONLYSOLDIER
	 * 		\
	 * 		\-----> FINDING <-> LETSCHOP
	 * 					\
	 * 					\-----> BUILDING
	 *
	 *
	 */
	@Override void step() {
		roundCounter++;
		switch (state) {
		case STARTING:
			currentDir = randomDirection();
			if (isEnemyArchonNear()) {
				state = GardenerState.ONLYSOLDIERS;
			}
			else
				state = GardenerState.FINDING;
			break;
		case FINDING:
			if (isInWoods() && !lumberjackbuilded){
				state = GardenerState.LETSCHOP;
				lumberjackbuilded = true;
			}
			else if (findSpot())
				state = GardenerState.BUILDING;
			break;
		case BUILDING:
			// try to build one tree in garden
			buildGarden();
			// try to build robots
			if (scoutbuilded)
				while (!build(RobotType.SOLDIER))
					Clock.yield();
			else {
				while (!build(RobotType.SCOUT))
					Clock.yield();
				scoutbuilded = true;
			}
			// water garden
			waterGarden();
			// try to build again
			if (scoutbuilded)
				build(RobotType.SOLDIER);
			else {
				build(RobotType.SCOUT);
				scoutbuilded = true;
			}
			break;
		case ONLYSOLDIERS:
			// while enemy archon is nearby build soldiers !!
			if (isEnemyArchonNear())
				build(RobotType.SOLDIER);
			else
				state = GardenerState.FINDING;
			break;
		case LETSCHOP:
			roundCounter = 0;
			// build atleast one lumberjack and than try to build another in next 35 rounds
			for (int i = 0; i < 2; i++) {
				roundCounter = 0;
				while (!build(RobotType.LUMBERJACK,true) && (roundCounter < 35 || i == 0)) {
					roundCounter++;
					Clock.yield();
				}
			}
			state = GardenerState.FINDING;
			break;
		}
	}
}
