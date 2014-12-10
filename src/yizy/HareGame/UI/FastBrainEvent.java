package yizy.HareGame.UI;

import java.util.EventObject;

import yizy.HareGame.Role;
import yizy.HareGame.Square;

public class FastBrainEvent extends EventObject {
	private static final long serialVersionUID = 7401147576278257008L;
    
    // constructor
	public FastBrainEvent(Object arg0) {
		super(arg0);
	}
    
    // access methods
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}

	public Square getSquare() {
		return square;
	}

	public void setSquare(Square node) {
		this.square = node;
	}

	public Square getFrom() {
		return from;
	}

	public void setFrom(Square from) {
		this.from = from;
	}

	public Square getTo() {
		return to;
	}

	public void setTo(Square to) {
		this.to = to;
	}

	public int getMaxDetph() {
		return maxDetph;
	}

	public void setMaxDetph(int maxDetph) {
		this.maxDetph = maxDetph;
	}

	public int getNodeCounter() {
		return nodeCounter;
	}

	public void setNodeCounter(int nodeCounter) {
		this.nodeCounter = nodeCounter;
	}

	public int getPruningCounterOfMaxVaule() {
		return pruningCounterOfMaxVaule;
	}

	public void setPruningCounterOfMaxVaule(int pruningCounterOfMaxVaule) {
		this.pruningCounterOfMaxVaule = pruningCounterOfMaxVaule;
	}

	public int getPruningCounterOfMinVaule() {
		return pruningCounterOfMinVaule;
	}

	public void setPruningCounterOfMinVaule(int pruningCounterOfMinVaule) {
		this.pruningCounterOfMinVaule = pruningCounterOfMinVaule;
	}

    // members
	private Role role;
	private Square square;
    private Square from;
    private Square to;
    private int maxDetph;
    private int nodeCounter;
    private int pruningCounterOfMaxVaule;
    private int pruningCounterOfMinVaule;
}
