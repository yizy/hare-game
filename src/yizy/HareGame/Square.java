package yizy.HareGame;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

// Square is the square in board.
public class Square {
    
	// typedef
    public enum NeighborDirection {
    	UP, DOWN, LEFT, RIGHT, UPPERLEFT, UPPERRIGHT, LOWERLEFT, LOWERRIGHT,
    }
    
    // Step is used in findShortestPath to record info in each step.
    private class Step {
        public Step(Square square, int cost, int estimated) {
            this.square = square;
            this.cost = cost;
            this.estimated = estimated;
        }
        
        public Square square;
        public int cost;
        public int estimated;
    }
    
    // constructors
    public Square(int row, int col) {
    	this.setRow(row);
    	this.setColumn(col);
        this.setOccupied(false);
        neighbors = new HashMap<NeighborDirection, Square>();
    }
    
    // methods
    public boolean isNeighbor(Square square) {
        return neighbors.containsValue(square);
    }
    
    public NeighborDirection getNeighborDirection(Square neighbor) {
    	int rowDiff = row - neighbor.row;
    	int colDiff = column - neighbor.column;
    	
    	if (colDiff == 0) {
    		if (rowDiff > 0)
    			return NeighborDirection.DOWN;
    		else 
    			return NeighborDirection.UP;
    	} else if (colDiff > 0) {
            if (rowDiff > 0)
            	return NeighborDirection.UPPERLEFT;
            else if (rowDiff == 0)
            	return NeighborDirection.LEFT;
            else
            	return NeighborDirection.LOWERLEFT;
    	} else {
            if (rowDiff > 0)
            	return NeighborDirection.UPPERRIGHT;
            else if (rowDiff == 0)
            	return NeighborDirection.RIGHT;
            else
            	return NeighborDirection.LOWERRIGHT;
    	}
    }
    
    public Collection<Square> getAllNeighbors() {
    	return neighbors.values();
    }
    
    public int findShortestPath(Square destination) {
    	
        Comparator<Step> Order = new Comparator<Step>() {

			@Override
			public int compare(Step arg0, Step arg1) {
				// TODO Auto-generated method stub
				int val0 = arg0.cost + arg0.estimated;
				int val1 = arg1.cost + arg1.estimated;
                if (val0 < val1)
                	return -1;
                else if (val0 > val1)
                	return 1;
                
				return 0;
			}
        	
        };
    
        PriorityQueue<Step> frontier = new PriorityQueue<Step>(20, Order);
        frontier.add(new Step(this, 0, Math.abs(column - destination.column)));
        HashSet<Square> explored = new HashSet<Square>();
        
        while (true) {
        	if (frontier.isEmpty()) return -1;
            Step step = frontier.remove();
            if (step.square == destination) return step.cost + 1;
            explored.add(step.square);
            
            Collection<Square> neighbors = step.square.getAllNeighbors();
            for (Square neighbor : neighbors) {
            	if (!frontier.contains(neighbor) 
            			&& !explored.contains(neighbor)) 
                    frontier.add(new Step(neighbor, step.cost + 1,
                    		Math.abs(neighbor.column - destination.column)));
            }
        }
    }
    
    // access methods
    public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public boolean isOccupied() {
		return isOccupied;
	}

	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	public Square getNeighbor(NeighborDirection neighbor) {
		return neighbors.get(neighbor);
	}
    
	public void addNeghbor(NeighborDirection neighbor, Square square) {
        neighbors.put(neighbor, square);
	}

	// members
	private int row;
	private int column;
	private boolean isOccupied;
	private HashMap<NeighborDirection, Square> neighbors;
}
