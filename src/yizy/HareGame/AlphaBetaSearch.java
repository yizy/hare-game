package yizy.HareGame;

import java.util.ArrayList;
import java.util.Collection;

import yizy.HareGame.HareGame.RoleName;
import yizy.HareGame.HareGameEvent.SuccessType;

public class AlphaBetaSearch {

	// typedef
	private class Result {
        public Result(int v, int cost) {
        	this.v = v;
        	this.cost = cost;
        }
    	public int v;
    	public int cost;
    }
	
    // methods
	public void searchNextMove(HareGame game, int limit, RoleName searcher) {
        this.limit = limit;
        this.searcher = searcher;
        depth = maxDetph = 0;
        pruningCounterOfMaxVaule = 0;
        pruningCounterOfMinVaule = 0;
        nodeCounter = 1;
        
		maxValue(game, ALPHA, BETA, UTIL_WIN);
	}
    
	private Result maxValue(HareGame game, int alpha, int beta, int total) {
        if (++depth > maxDetph)
        	maxDetph = depth;
        ++nodeCounter;
        
        if (terminalTest(game)) {
        	--depth;
        	return new Result(utility, depth);
        }
        
        if (cutoffTest(game, total)) {
        	--depth;
            return new Result(evaluation, depth);
        }
        
        Result result = new Result(-INFINITE, INFINITE);
        ArrayList<Role> roles = getMaxRoles(game);
        for (Role role : roles) {
        	Square current = role.getPosition();
        	Collection<Square> neighbors = role.canMoveTo();
            for (Square neighbor : neighbors) {
                role.setPosition(neighbor);
                
        		Result minResult = minValue(game, alpha, beta, total);
        		if (minResult.v > result.v) {
                    result = minResult;
                    if (depth == 1) {
                        fromSquare = current;
                        toSquare = neighbor;
                    }
        		} else if (minResult.v == result.v 
        				&& minResult.cost < result.cost) {
        			result = minResult;
                    if (depth == 1) {
                        fromSquare = current;
                        toSquare = neighbor;
                    }
        		}
        		
        		role.setPosition(current);

        		if (result.v >= beta) {
                    ++pruningCounterOfMaxVaule;
                    --depth;
        			return result;
        		}
                
        		alpha = Math.max(result.v, alpha);
        	}
        }
        
        --depth;
        return result;
	}
    
	private Result minValue(HareGame game, int alpha, int beta, int total) {
        if (++depth > maxDetph)
        	maxDetph = depth;
        ++nodeCounter;
        
        if (terminalTest(game)) {
        	--depth;
        	return new Result(utility, depth);
        }
        
//        if (depth == 2 && !game.isConnectedHounds())
//        	total /= 2;
        
        if (cutoffTest(game, total)) {
        	--depth;
        	return new Result(evaluation, depth);
        }
        
        Result result = new Result(INFINITE, INFINITE);
        ArrayList<Role> roles = getMinRoles(game);
        for (Role role : roles) {
        	Square current = role.getPosition();
        	Collection<Square> neighbors = role.canMoveTo();
        	for (Square neighbor : neighbors) {
                role.setPosition(neighbor);
                Result maxResult = maxValue(game, alpha,beta, total);
                if (maxResult.v < result.v)
                	result = maxResult;
                else if (maxResult.v == result.v 
                		&& maxResult.cost < result.cost)
                	result = maxResult;
                
        		role.setPosition(current);

        		if (result.v <= alpha) {
                    ++pruningCounterOfMinVaule;
                    --depth;
        			return result;
        		}
                
        		beta = Math.min(result.v, beta);
        	}
        }
        
        --depth;
        return result;
		
	}
    
	private boolean terminalTest(HareGame game) {
		if (game.checkSuccessOfHareSide() != SuccessType.NONE) {
            utility = (searcher == RoleName.HARE) ? UTIL_WIN : UTIL_LOSE;
            return true;
		}
		
		if (game.checkSuccessOfHoundSide() != SuccessType.NONE) {
            utility = (searcher == RoleName.HOUND) ? UTIL_WIN : UTIL_LOSE;
			return true;
		}
        
        return false;
	}
    
	private boolean cutoffTest(HareGame game, int total) {
        if (depth == limit) {
        	evaluation = (int)(game.evaluate(searcher, total));
            
        	
        	return true;
        }
		return false;
	}
    
	private ArrayList<Role> getMaxRoles(HareGame game) {
		if (searcher == RoleName.HARE) {
            ArrayList<Role> roles = new ArrayList<Role>();
            roles.add(game.getHare());
            return roles;
		}
        
		return game.getHounds();
	}
    
	private ArrayList<Role> getMinRoles(HareGame game) {
		if (searcher == RoleName.HOUND) {
            ArrayList<Role> roles = new ArrayList<Role>();
            roles.add(game.getHare());
            return roles;
		}
        
		return game.getHounds();
	}

    // access methods  
	public Square getFromSquare() {
		return fromSquare;
	}

	public Square getToSquare() {
		return toSquare;
	}
    
    public int getMaxDepth() {
    	return maxDetph;
    }
    
    public int getNodeCounter() {
    	return nodeCounter;
    }
    
    public int getPruningCounterOfMaxValue() {
    	return pruningCounterOfMaxVaule;
    }
    
    public int getPruningCounterOfMinValue() {
    	return pruningCounterOfMinVaule;
    }
	
	// members
    private static final int UTIL_WIN = 100;
    private static final int UTIL_LOSE = -100;
    private static final int ALPHA = Integer.MIN_VALUE;
    private static final int BETA = Integer.MAX_VALUE;
    private static final int INFINITE = Integer.MAX_VALUE;
    
    private Square fromSquare;
    private Square toSquare;
    private int depth;
    private int maxDetph;
    private int nodeCounter;
    private int pruningCounterOfMaxVaule;
    private int pruningCounterOfMinVaule;
    private int limit;
    private int utility;
    private int evaluation;
    private RoleName searcher;
}
