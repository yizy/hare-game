package yizy.HareGame;

import java.util.EventObject;

import yizy.HareGame.HareGame.RoleName;

public class HareGameEvent extends EventObject {
    public enum SuccessType {
    	HOUNDS_TRAP, HARE_ESCAPE, HOUNDS_STALL, NONE
    }
    
	private static final long serialVersionUID = -8455135102292542122L;

	public HareGameEvent(Object source) {
		super(source);
	}
    
    public RoleName getWiner() {
		return winer;
	}

	public void setWiner(RoleName winer) {
		this.winer = winer;
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

	public SuccessType getSuccessType() {
		return successType;
	}

	public void setSuccessType(SuccessType successType) {
		this.successType = successType;
	}

	private RoleName winer;
    private SuccessType successType;
    private Square from;
    private Square to;
}
