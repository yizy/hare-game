package yizy.HareGame.UI;

import yizy.HareGame.HareGame.RoleName;
import yizy.HareGame.HareGameException;
import yizy.HareGame.Role;

public class HarePlayer extends Player {
    public HarePlayer(Role hare, RoleName role, boolean isAuto) {
        this.hare= hare;
    	this.isAuto = isAuto;
        this.role = role;
    }
    
    @Override
    public void selectRole(Role role) throws HareGameException {
    	if (hare != role)
            throw new HareGameException();
        selectedRole = role;
    }
    
	private Role hare;
}
