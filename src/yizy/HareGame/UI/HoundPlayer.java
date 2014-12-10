package yizy.HareGame.UI;

import java.util.ArrayList;

import yizy.HareGame.HareGame.RoleName;
import yizy.HareGame.HareGameException;
import yizy.HareGame.Role;

public class HoundPlayer extends Player {
    public HoundPlayer(ArrayList<Role> hounds, RoleName role, boolean isAuto) {
    	this.hounds = hounds;
    	this.isAuto = isAuto;
        this.role = role;
    }
    
    @Override
    public void selectRole(Role role) throws HareGameException {
    	if (!hounds.contains(role))
            throw new HareGameException();
        selectedRole = role;
    }
    
    ArrayList<Role> hounds;

}
