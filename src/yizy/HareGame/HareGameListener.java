package yizy.HareGame;

import java.util.EventListener;

public interface HareGameListener extends EventListener {
    public void onRoleMoved(HareGameEvent e);
    public void onNextMoveReady(HareGameEvent e);
	public void onSucceed(HareGameEvent e);
}
