package si.enginestarter;

import si.enginestarter.Levels.MagneticEnemyDef;
import si.enginestarter.Levels.MagneticEnemyDef.EnemyColor;

public class MagneticEnemy {
	
	private static MagneticEnemy instance;
	
	public static MagneticEnemy getInstance() {
		if(instance == null)
			instance = new MagneticEnemy();
		return instance;
	}

	public MagneticEnemy() {}
	
	public static MagneticBee createMagneticBee(float pX, float pY, EnemyColor enemyColor, GameLevel pGameLevel) {
		return new MagneticBee(pX, pY, enemyColor, pGameLevel); 
	}
}
