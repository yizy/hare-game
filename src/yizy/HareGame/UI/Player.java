package yizy.HareGame.UI;

import java.util.ArrayList;

import yizy.HareGame.*;
import yizy.HareGame.HareGame.RoleName;

public abstract class Player {
    
    // methods
	public void newTurn(HareGame game, int level, FastBrainListener listener)
			throws CloneNotSupportedException {
		if (isAuto) {
			// auto
			Role hare = new Hare(game.getHare().getPosition());
            ArrayList<Role> hounds = new ArrayList<Role>();
            for (Role hound : game.getHounds())
            	hounds.add(new Hound(hound.getPosition()));
            HareGame newGame = new HareGame(hare, hounds, false);
            FastBrain brain = new FastBrain(newGame, level, role);
			brain.setListener(listener);
			Thread auto = new Thread(brain);
			auto.start();
		}
	}

	public void selectRole(Role role) throws HareGameException {
	}

	public void moveTo(Square position) throws HareGameException {
		if (selectedRole == null)
			return;
		selectedRole.moveTo(position);
	}

    // access methods
	public boolean isAuto() {
		return isAuto;
	}

	public void setAuto(boolean isAuto) {
		this.isAuto = isAuto;
	}

	public boolean hasSelected() {
		return selectedRole != null;
	}

	public Role getSelectedRole() {
		return selectedRole;
	}

	public RoleName getRole() {
		return this.role;
	}

    // members
	protected Role selectedRole;
	protected boolean isAuto;
	protected RoleName role;
}