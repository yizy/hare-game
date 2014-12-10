package yizy.HareGame.UI;


import yizy.HareGame.AlphaBetaSearch;
import yizy.HareGame.HareGame;
import yizy.HareGame.HareGame.RoleName;

public class FastBrain implements Runnable {
    private HareGame game;
    private RoleName searcher;
    private int level;
    private FastBrainListener listener;

    public FastBrain(HareGame game, int level, RoleName searcher) {
    	this.game = game;
        this.level = level;
        this.searcher = searcher;
    }
    
	@Override
	public void run() {
		// TODO Auto-generated method stub
        if (listener != null) {
        	listener.onFastBrainStarted(new FastBrainEvent(this));
        }
        AlphaBetaSearch search = new AlphaBetaSearch();
        search.searchNextMove(game, level, searcher);
        if (listener != null) {
            FastBrainEvent e = new FastBrainEvent(this);
            e.setFrom(search.getFromSquare());
            e.setTo(search.getToSquare());
            e.setMaxDetph(search.getMaxDepth());
            e.setNodeCounter(search.getNodeCounter());
            e.setPruningCounterOfMaxVaule(search.getPruningCounterOfMaxValue());
            e.setPruningCounterOfMinVaule(search.getPruningCounterOfMinValue());
        	listener.onFastBrainCompleted(e);
        }
       
	}

	public void setListener(FastBrainListener listener) {
		this.listener = listener;
	}
	
}
