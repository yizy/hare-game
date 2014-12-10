package yizy.HareGame;

import java.util.ArrayList;
import java.util.Collection;

import yizy.HareGame.Square.NeighborDirection;

public abstract class Role {
    
	// constructor
    public Role(Square position) {
        this.position = position;
        position.setOccupied(true);
    }
    
    // methods
    public abstract void moveTo(Square neighbor) throws HareGameException;
    public Collection<Square> canMoveTo() {
        ArrayList<Square> neighbors = new ArrayList<Square>();
        for (NeighborDirection dir : directions) {
        	Square neighbor = position.getNeighbor(dir);
        	if (neighbor != null && !neighbor.isOccupied())
        		neighbors.add(neighbor);
        }
        
        return neighbors;
    }
    
    // access methods
    public Square getPosition() {
    	return position;
    }
    
    public void setPosition(Square square) {
    	if (position != null)
    		position.setOccupied(false);
        square.setOccupied(true);
    	position = square;
    }
    
	public void setListener(RoleListener listener) {
		this.listener = listener;
	}

    // member
	protected Square position;
    protected RoleListener listener;
    protected NeighborDirection[] directions;
}
