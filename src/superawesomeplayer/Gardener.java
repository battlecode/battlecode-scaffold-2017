package superawesomeplayer;

import battlecode.common.*;

import java.util.Random;

/**
 * Created by adamj on 18/01/2017.
 */
public strictfp class Gardener extends RobotPlayer {
    public Gardener(RobotController rc) throws GameActionException {
        this.rc = rc;
        rand = new Random(rc.getID());
        while (true) {
            Clock.yield();
        }
    }
}
