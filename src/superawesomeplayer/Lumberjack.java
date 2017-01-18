package superawesomeplayer;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;

import java.util.Random;

/**
 * Created by adamj on 18/01/2017.
 */
public strictfp class Lumberjack extends RobotPlayer {
    public Lumberjack(RobotController rc) throws GameActionException {
        this.rc = rc;
        rand = new Random(rc.getID());
        while (true) {
            Clock.yield();
        }
    }
}
