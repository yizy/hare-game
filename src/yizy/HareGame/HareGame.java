package yizy.HareGame;

import java.util.ArrayList;
import java.util.Collection;

import yizy.HareGame.HareGameEvent.SuccessType;

public class HareGame implements RoleListener {
    // typedef
    public enum RoleName {
    	HOUND, HARE;
    	@Override public String toString() {
    		//only capitalize the first letter
    		String s = super.toString();
    		return s.substring(0, 1) + s.substring(1).toLowerCase();
    	} 
    }
	
    // constructors
    public HareGame() {
    	
    }
   
    public HareGame(Role hare, ArrayList<Role> hounds, boolean listenToRoles) {
    	this.hare = hare;
    	this.hounds = hounds;
        if (listenToRoles) {
        	this.hare.setListener(this);
        	for (int i = 0; i < hounds.size(); ++i)
        		hounds.get(i).setListener(this);
        }
    }
    
    // methods
    public Role findRoleByPosition(Square position) {
    	if (hare.getPosition() == position)
    		return hare;
        
        for (int i = 0; i < hounds.size(); ++i)
        	if (hounds.get(i).getPosition() == position)
        		return hounds.get(i);
        
    	return null;
    }
    
    public SuccessType checkSuccessOfHareSide() {
        if (houndsMoveCounter == 10)
        	return SuccessType.HOUNDS_STALL;
        
        for (int i = 0; i < hounds.size(); ++i) {
            Square harePosition = hare.getPosition();
            Square houndPosition = hounds.get(i).getPosition();
        	if (harePosition.getColumn() >= houndPosition.getColumn())
                return SuccessType.NONE;
        }
        
    	return SuccessType.HARE_ESCAPE;
    }
    
    public SuccessType checkSuccessOfHoundSide() {
    	Collection<Square> nodes = hare.canMoveTo();
        return nodes.size() == 0 ? SuccessType.HOUNDS_TRAP : SuccessType.NONE;
    }
    
    public int evaluate(RoleName rolename, int total) {
    	int percent = total;
    	
    	if (!isConnectedHounds())
            percent /= 2;
    	
        Square harePosition = hare.getPosition();
    	for (Role hound : hounds) {
            Square houndPosition = hound.getPosition();
            percent -= houndPosition.findShortestPath(harePosition);
    	}
        
    	if (rolename == RoleName.HOUND)
    		return percent;//(int)(percent * total/100);
    	else
    		return percent;//(int)(1 - percent * total/100);
    }
    
    public boolean isConnectedHounds() {
    	int count = 2;
        Role hound0 = hounds.get(0);
        Role hound1 = hounds.get(1);
        Role hound2 = hounds.get(2);
        
        if (hound0.getPosition().isNeighbor(hound1.getPosition()))
        	--count;
        if (hound0.getPosition().isNeighbor(hound2.getPosition()))
        	--count;
        if (count == 2)
            return false;
        if (count == 0)
        	return true;
        return hound1.getPosition().isNeighbor(hound2.getPosition());
    }
    
    // event handlers
    @Override
    public void onRoleMoved(RoleEvent e) {
    	if (e.getWho() == RoleName.HOUND) {
    		if (e.getFrom().getColumn() == e.getTo().getColumn())
    			++houndsMoveCounter;
    		else
    			houndsMoveCounter = 0;
    	}
    			
        if (listener != null) {
            	HareGameEvent movedEvent = new HareGameEvent(this);
            	movedEvent.setFrom(e.getFrom());
            	movedEvent.setTo(e.getTo());
            	listener.onRoleMoved(movedEvent);
                
            SuccessType type;
            if ((type = checkSuccessOfHoundSide()) != SuccessType.NONE) {
            	HareGameEvent succeedEvent= new HareGameEvent(this);
            	succeedEvent.setWiner(RoleName.HOUND);
                succeedEvent.setSuccessType(type);
                listener.onSucceed(succeedEvent);
            } else if ((type = checkSuccessOfHareSide()) != SuccessType.NONE) {
            	HareGameEvent succeedEvent = new HareGameEvent(this);
            	succeedEvent.setWiner(RoleName.HARE);
                succeedEvent.setSuccessType(type);
            	listener.onSucceed(succeedEvent);
            } else {
            	listener.onNextMoveReady(new HareGameEvent(this));
            }
        }
	}
    
    // access methods
	public Role getHare() { 
		return hare;
	}
    
	public ArrayList<Role> getHounds() {
		return hounds;
	}
    
	public void setListener(HareGameListener listener) {
		this.listener = listener;
	}

    // members
	private Role hare;
    private ArrayList<Role> hounds = new ArrayList<Role>();
    private HareGameListener listener;
    private int houndsMoveCounter;
}