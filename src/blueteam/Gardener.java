package blueteam;

import battlecode.common.*;

public class Gardener extends Robot {

	Gardener(RobotController rc) {
		super(rc);
	}
    enum GardenerState {
        FINDING,BUILDING
    }
    GardenerState state = GardenerState.FINDING;
    /**
     * Searches for suitable location to start building hexagonal tree garden
     *
     * @param radius radius to search for other gardeners
     * @return true if is gardener in suitable place
     */
	boolean findSpot(float radius) {
	    // get robots in radius
        RobotInfo[] nearbyRobots = rc.senseNearbyRobots(radius);
        for (RobotInfo robot : nearbyRobots) {
            // check for archrons and gardeners around
            //TODO make cleverer location finding
            if (robot.getType().equals(RobotType.GARDENER) || robot.getType().equals(RobotType.ARCHON)) {
                Direction dir;
                // TODO use constants
                // go away from gardener/archon or go in direction of enemy
                // TODO is Math.random best way ?
                if (Math.random() > 0.2) {
                    if (Math.random() > 0.5)
                        dir = rc.getLocation().directionTo(rc.getInitialArchonLocations(enemy)[0]).rotateLeftDegrees((float)Math.random()*60);
                    else
                        dir = rc.getLocation().directionTo(rc.getInitialArchonLocations(enemy)[0]).rotateRightDegrees((float)Math.random()*60);
                }
                else
                    dir = rc.getLocation().directionTo(robot.getLocation()).opposite();
                // get away from other gardeners or archons in somehow random direction
                // TODO use constants
                dir = dir.rotateLeftDegrees((float)Math.random()*60 - 30);
                // black dot in debugging
                rc.setIndicatorDot(rc.getLocation().add(dir),1,1,1);

                try {
                    tryMove(dir);
                } catch (GameActionException e) {

                }
                return false;
            }
        }
        return true;
    }

    /**
     * Default value for radius should be 3, (gardener,tree,space,tree,gardener),
     * but should be changed if building tanks (as they take 2 radius)
     * @return
     */
    boolean findSpot(){
        return findSpot(6.0f);
    }

    /**
     *
     * Builds tree every 30 degrees
     * @return true if was planted
     */
    boolean buildGarden() {
        boolean ret  = false;
        Direction dir = Direction.WEST;
        for (int i = 0; i < 5; i++) {
            //TODO keep list of planted trees
            try {
                if (rc.canPlantTree(dir)) {
                    rc.plantTree(dir);
                    ret = true;
                    //TODO mby break earlier??
                }
            }
            catch (GameActionException e)
            {
                //should not happen, mby when we check and plant in different rounds
            }
            dir = dir.rotateRightDegrees(60);
        }
        return ret;
    }

    /**
     * tries to water all plants around robot. Assuming starting dir is WEST
     */
    void waterGarden()
    {
        Direction dir = Direction.WEST;
        MapLocation loc = rc.getLocation();
        for (int i = 0; i < 5; i++) {
            try {
                loc = loc.add(dir,1.5f);
                if (rc.canWater(loc)) {
                    rc.water(loc);
                }
            }
            catch (GameActionException e)
            {
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
     * @param type of robot
     * @return if successful
     */
    boolean build(RobotType type) {
        Direction dir =  Direction.WEST.rotateLeftDegrees(60);
        // wait until build is rdy
        if (!rc.isBuildReady())
            Clock.yield();
        if (rc.canBuildRobot(type,dir)) {
            try {
                rc.buildRobot(type,dir);
            } catch (GameActionException e) {
                return false;
            }
            return true;
        }
        else {
            return false;
        }
    }
	@Override
	void step(){
		// TODO Lukas

		switch (state)
        {
            case FINDING:
                if (findSpot()) {
                    state = GardenerState.BUILDING;
                    System.out.println("Gardener ID: "+ rc.getID() +" found place to build garden");
                }
                else {
                    try{
                        tryMove(randomDirection());
                    }
                    catch (GameActionException e)
                    {}
                }
                break;
            case BUILDING:
                buildGarden();
                waterGarden();
                //TODO decide according to game state whether to build lumberjacks,
                //soldiers or tanks. Logic how many gardeners should be build should be in archon
                double rnd = Math.random();
                if (rnd < 0.1)
                    build(RobotType.LUMBERJACK);
                else if (rnd < 0.9)
                    build(RobotType.SOLDIER);
                else
                    build(RobotType.SCOUT); //TODO build just one
                break;
        }
	}
}
