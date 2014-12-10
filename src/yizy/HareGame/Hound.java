package yizy.HareGame;

import yizy.HareGame.HareGame.RoleName;
import yizy.HareGame.Square.NeighborDirection;

public class Hound extends Role {
    public Hound(Square position) {
    	super(position);
        directions = new NeighborDirection[]{ 
        		NeighborDirection.DOWN, 
        		NeighborDirection.UP, 
        		NeighborDirection.RIGHT, 
        		NeighborDirection.LOWERRIGHT, 
        		NeighborDirection.UPPERRIGHT,
        };
    }

	@Override
	public void moveTo(Square square) throws HareGameException {
		// TODO Auto-generated method stub
        if (square == null || !square.isNeighbor(position)
        		|| square.isOccupied()) 
        	throw new HareGameException();
        
        int rowDiff = square.getRow() - position.getRow();
        int colDiff = square.getColumn() - position.getColumn();
        if (rowDiff > 1 || rowDiff < -1)
        	throw new HareGameException();
        if (colDiff > 1 || colDiff < 0)
        	throw new HareGameException();
        
        Square old = position;
        setPosition(square);
	
		if (listener != null) {
			RoleEvent e = new RoleEvent(this);
            e.setWho(RoleName.HOUND);
			e.setFrom(old);
			e.setTo(position);
			listener.onRoleMoved(e);
		}
	}
}
