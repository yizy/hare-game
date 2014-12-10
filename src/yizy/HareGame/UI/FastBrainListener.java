package yizy.HareGame.UI;

import java.util.EventListener;

public interface FastBrainListener extends EventListener {
    public void onFastBrainStarted(FastBrainEvent e);
	public void onFastBrainCompleted(FastBrainEvent e);
}
