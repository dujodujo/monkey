package si.enginestarter;

import si.enginestarter.Levels.MagneticEnemyDef.EnemyColor;

public class Levels {
	private static final String TAG = Levels.class.getName();
	
	static MagneticBeamDef b1 = new MagneticBeamDef(1600, 525, 75, 15, 0, MagneticBeamDef.BeamType.MetalStatic);
	
	static MagneticEnemyDef r1 = new MagneticEnemyDef(700, 500, MagneticEnemyDef.EnemyType.Bee, EnemyColor.Green);
	static MagneticEnemyDef r2 = new MagneticEnemyDef(750, 500, MagneticEnemyDef.EnemyType.Bee, EnemyColor.Yellow);
	
	static MagneticBoxDef mb1 = new MagneticBoxDef(600, 450, MagneticBoxDef.BoxSize.SMALL, MagneticBoxDef.BoxType.FRAGILE);
	static MagneticBoxDef mb2 = new MagneticBoxDef(700, 450, MagneticBoxDef.BoxSize.SMALL, MagneticBoxDef.BoxType.EXPLOSIVE);
	static MagneticBoxDef mb3 = new MagneticBoxDef(750, 450, MagneticBoxDef.BoxSize.SMALL, MagneticBoxDef.BoxType.EXPLOSIVE);
	
	static MagneticOrbDef ob1 = new MagneticOrbDef(400, 650, 2f, 2f, 90f);
	static MagneticOrbDef ob2 = new MagneticOrbDef(450, 650, 2f, 2f, 90f);
	
	static MagneticBalloonDef bd1 = new MagneticBalloonDef(700, 650);
	static MagneticBalloonDef bd2 = new MagneticBalloonDef(600, 650);
	
	static LevelDef[] levels = new LevelDef[] {
		new LevelDef(1, 1, 
		   new MagneticBoxDef[] {},
		   new MagneticBeamDef[] {b1},
           new MagneticEnemyDef[] {},
           new MagneticOrbDef[] {ob1, ob2},
           new MagneticBalloonDef[] {}),
       new LevelDef(2, 1, 
    	   new MagneticBoxDef[] {},
		   new MagneticBeamDef[] {},
		   new MagneticEnemyDef[] {},
		   new MagneticOrbDef[] {},
		   new MagneticBalloonDef[] {}),
       new LevelDef(3, 1, 
    	   new MagneticBoxDef[] {},
    	   new MagneticBeamDef[] {},
    	   new MagneticEnemyDef[] {},
    	   new MagneticOrbDef[] {},
    	   new MagneticBalloonDef[] {}),
	};
	
	public static LevelDef getLevelDef(int pLevelIndex, int pWorldIndex) {
		for(LevelDef currentLevelDef : levels)
			if(currentLevelDef.compare(pLevelIndex, pWorldIndex)) {
				return currentLevelDef;
			}
		return null;
	}
	
	public static boolean isNextLevel(LevelDef pCurrentLevel) {
		if(getLevelDef(pCurrentLevel.iLevel+1, pCurrentLevel.iWorld)!=null)
			return true;
		return false;
	}
	
	public static boolean isNextWorld(final LevelDef pCurrentLevel) {
		if(getLevelDef(0, pCurrentLevel.iWorld+1)!=null)
			return true;
		return false;
	}
	
	public static int getNumLevels(final int pWorldIndex) {
		int levelCounter = 0;
		for(LevelDef currentLevelDef : levels)
			if(currentLevelDef.iWorld == pWorldIndex)
				levelCounter++;
		return levelCounter;
	}
	
	public static class MagneticBalloonDef {
		protected float x;
		protected float y;

		public MagneticBalloonDef(float x, float y) {
			super();
			this.x = x;
			this.y = y;
		}
	}
	
	public static class MagneticOrbDef {
		protected final float duration;
		protected final float fade;
		protected final float rotationSpeed;

		protected float x;
		protected float y;

		public MagneticOrbDef(float x, float y, final float duration, 
				final float fade, final float rotationSpeed) {
			super();
			this.x = x;
			this.y = y;
			this.duration = duration;
			this.fade = fade;
			this.rotationSpeed = rotationSpeed;
		}
	}
	
	public static class MagneticEnemyDef {
		public enum EnemyType { Duck, Bee }
		protected float x;
		protected float y;
		public EnemyType enemyType;
		public EnemyColor enemyColor;
		
		public EnemyType getEnemyType() { return enemyType; }
		public EnemyColor getEnemyColor() { return enemyColor; }
		
		public MagneticEnemyDef(float pX, float pY, EnemyType enemyType, EnemyColor enemyColor) {
			this.x = pX;
			this.y = pY;
			this.enemyType = enemyType;
			this.enemyColor = enemyColor;
		}
		
		public enum EnemyColor {
			Red(0), Green(1), Yellow(2);
			int color;
			EnemyColor(int pIndex) { color = pIndex; }
		}
	}
	
	public static class MagneticBeamDef {
		public enum BeamType { MetalStatic, MetalDynamic, WoodenDynamic }
		protected float x;
		protected float y;
		protected float length;
		protected float height;
		protected float rotation;
		protected BeamType type;

		public MagneticBeamDef(float x, float y, float length, float height, float rotation, BeamType type) {
			super();
			this.x = x;
			this.y = y;
			this.length = length;
			this.height = height;
			this.rotation = rotation;
			this.type = type;
		}
		
		public BeamType getBeamType() {
			return this.type;
		}
	}
	
	public static class MagneticBoxDef {
		public BoxSize boxSize;
		public BoxType boxType;
		protected float x;
		protected float y;
		
		public MagneticBoxDef(float x, float y, BoxSize boxSize, BoxType boxType) {
			this.x = x;
			this.y = y;
			this.boxSize = boxSize;
			this.boxType = boxType;
		}
		
		public enum BoxSize {
			SMALL(0), LARGE(1);
			int size;
			BoxSize(int pIndex) { size = pIndex; }
		}

		public enum BoxType {
			NORMAL(0), EXPLOSIVE(1), FRAGILE(2);
			int type;
			BoxType(int typeIndex) { type = typeIndex; }
		}
	}
	
	public static class LevelDef {
		protected int iLevel;
		protected int iWorld;
		
		protected MagneticBoxDef[] boxesDef;
		protected MagneticBeamDef[] beams;
		protected MagneticEnemyDef[] enemies;
		protected MagneticOrbDef[] magneticOrbs;
		protected MagneticBalloonDef[] magneticBalloons;
		
		private LevelDef(int pLevelIndex, int pWorldIndex, MagneticBoxDef[] boxes, MagneticBeamDef[] beams,
				MagneticEnemyDef[] enemies, MagneticOrbDef[] magneticOrbs, MagneticBalloonDef[] balloons) {
			this.iLevel = pLevelIndex;
			this.iWorld = pWorldIndex;
			this.beams = beams;
			this.enemies = enemies;
			this.boxesDef = boxes;
			this.magneticOrbs = magneticOrbs;
			this.magneticBalloons = balloons;
		}
		
		public boolean compare(final int pLevelIndex, final int pWorldIndex) {
			if(pLevelIndex == iLevel)
				if(pWorldIndex == iWorld)
					return true;
			return false;
		}
	}
}