package yizy.HareGame;

import java.util.EventObject;

import yizy.HareGame.HareGame.RoleName;

public class RoleEvent extends EventObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 983490349755445930L;

	public RoleEvent(Object source) {
		super(source);
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

    public RoleName getWho() {
		return who;
	}

	public void setWho(RoleName who) {
		this.who = who;
	}

	private RoleName who;
    private Square from;
    private Square to;
}
