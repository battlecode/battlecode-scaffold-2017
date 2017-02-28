package blueteam;

import battlecode.common.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;


public class Scout extends Robot {

	Direction dir;
	Team enemy;
	Random rand;
	final int ROTATE_MIN = 30;
	final int ROTATE_RANGE = 90;

	Scout(RobotController rc) {
		super(rc);
		enemy = rc.getTeam().opponent();
		dir = rc.getLocation().directionTo(rc.getInitialArchonLocations(enemy)[0]);
		rand = new Random();
	}

	Optional<TreeInfo> findBulletTree(){
		TreeInfo[] trees = rc.senseNearbyTrees(-1,Team.NEUTRAL);
		if(trees.length > 0){
			//res = Optional.of(trees[trees.length -1]);
			for (TreeInfo tree:trees) {
				if(tree.containedBullets > 0){
					rc.setIndicatorDot(tree.getLocation(),250,0,0);
					if(rc.canMove(tree.getLocation())) {
						return Optional.of(tree);
					}
				}
			}
		}
		return Optional.empty();
	}

	boolean shakeTheTree(TreeInfo tree) throws GameActionException{
		if(rc.canShake(tree.getLocation())){
			rc.shake(tree.getLocation());
			return true;
		}
		return false;
	}

	Optional<RobotInfo> nearLumberjack(){
		RobotInfo[] close_enemies = rc.senseNearbyRobots(rc.getType().sensorRadius/4, enemy);
		ArrayList<RobotInfo> lumberjacks = filterByType(close_enemies,RobotType.LUMBERJACK);
		if(lumberjacks.size() > 0) {
			rc.setIndicatorDot(lumberjacks.get(0).getLocation(), 0, 250, 0);
			return Optional.of(lumberjacks.get(0));
		}
		return Optional.empty();
	}

	Optional<RobotInfo> nearGardener(){
		RobotInfo[] enemies = rc.senseNearbyRobots(rc.getType().sensorRadius, enemy);
		ArrayList<RobotInfo> gardeners = filterByType(enemies,RobotType.GARDENER);
		if(gardeners.size()>0)
			return Optional.of(gardeners.get(0));
		return Optional.empty();
	}

	@Override
	void step(){
		try {
			boolean danger = false;
			// avoid lumberjacks
			Optional<RobotInfo> lumberjack = nearLumberjack();
			if(lumberjack.isPresent()){
				dir = new Direction(rc.getLocation(), lumberjack.get().getLocation()).opposite();
				if(rand.nextBoolean()){
					dir = dir.rotateLeftDegrees(45);
				}else{
					dir = dir.rotateRightDegrees(45);
				}
				danger = true;
			}
			if(!danger){
				Optional<TreeInfo> bulletTree =  findBulletTree();
				if(bulletTree.isPresent()){
					if(!shakeTheTree(bulletTree.get())){
						// we need to get to this tree first
						MapLocation treeLoc = bulletTree.get().getLocation();
						if(rc.canMove(treeLoc) == true){
							rc.move(treeLoc);
							return;
						}
					}
				}
			}

			//just move in direction
			if(!moveInDir(dir))
				dir = dir.rotateRightDegrees(rand.nextInt(ROTATE_RANGE) + ROTATE_MIN);

		} catch (GameActionException e) {
			System.out.println("Scout Exception");
			e.printStackTrace();
		}
	}

	boolean moveInDir(Direction dir) throws GameActionException{
		if(rc.canMove(dir)){
			rc.move(dir,1);
			return true;
		}else{
			return false;
		}
	}

	ArrayList<RobotInfo> filterByType(RobotInfo[] robots, RobotType type){
		ArrayList<RobotInfo> res = new ArrayList<>();
		for(RobotInfo robot:robots){
			if(robot.getType() == type)
				res.add(robot);
		}
		return res;
	}

}
