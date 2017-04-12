package superawesomeplayer;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;

import java.util.Random;

/**
 * Created by adamj on 18/01/2017.
 */
public strictfp class Scout extends RobotPlayer {
    public Scout(RobotController rc) throws GameActionException {
        this.rc = rc;
        rand = new Random(rc.getID());
        while (true) {
            Clock.yield();
        }
    }
}
