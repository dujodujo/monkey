package si.enginestarter;

import org.andengine.engine.handler.IUpdateHandler;

import android.util.Log;

public class GameManager implements IUpdateHandler {
	private static String NAME = GameManager.class.getName();
	
	private static GameManager instance;
	private GameLevel level;
	private GameLevelObjective objective;

	protected static int SCORE = 0;
	private int currentScore = 0;
	
	public interface GameLevelObjective {
		public boolean isLevelComplete();
		public void onLevelComplete();
		public boolean isLevelRestart();
		public void onLevelRestart();
	}

	public GameManager() { ResourceManager.getInstance().getEngine().registerUpdateHandler(this); }
	
	public static GameManager getInstance() {
		if(instance == null)
			instance = new GameManager();
		return instance;
	}
	
	public int getScore() { return currentScore; }
	
	public void addScore(int score) { currentScore += score; }
	
	public void removeScore(int score) { currentScore -= score; }
	
	public void resetGame() { this.currentScore = GameManager.SCORE; }
	
	@Override
	public void onUpdate(float pSecondsElapsed) {
		//Log.d(NAME, "onUpdate");
		if(objective != null) {
			if(objective.isLevelComplete()) {
				objective.onLevelComplete();
				objective = null;
			} else if(objective.isLevelRestart()) {
				objective.onLevelRestart();
				objective = null;
			}
		}
	}
	
	@Override
	public void reset() {}
	
	public void setGameLevel(GameLevel gameLevel) { instance.level = gameLevel; }
	
	public static GameLevel getGameLevel() { return instance.level; }
	
	public static void setGameLevelObjective(GameLevelObjective objective) { instance.objective = objective; }
	
	public static GameLevelObjective getGameLevelObjective() { return instance.objective; }
	
	public static void clearGameLevelObjective() { instance.objective = null; }
}
