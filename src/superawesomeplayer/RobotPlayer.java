package superawesomeplayer;
import battlecode.common.*;

import java.util.Random;

/**
 * Created by adamj on 18/01/2017.
 */
public strictfp class RobotPlayer {
    // Constants (like broadcast channels etc)
    // TODO: Add broadcasting channels

    // Variables needed in all robots
    RobotController rc;
    Random rand;

    public static void run(RobotController rc) throws GameActionException {
        switch (rc.getType()) {
            case ARCHON:
                new Archon(rc);
                break;
            case GARDENER:
                new Gardener(rc);
                break;
            case SOLDIER:
                new Soldier(rc);
                break;
            case LUMBERJACK:
                new Lumberjack(rc);
                break;
            case SCOUT:
                new Scout(rc);
                break;
            case TANK:
                new Tank(rc);
                break;
        }
    }
}
