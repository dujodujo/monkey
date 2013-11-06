package si.enginestarter;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.util.color.Color;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;

public class OptionsLayer extends ManagedLayer {
	private static String NAME = OptionsLayer.class.getName();
	
	private Sprite layerBackground;
	private static OptionsLayer instance;
	
	public static OptionsLayer getInstance() {
		if(instance == null)
			instance = new OptionsLayer();
		return instance;
	}
	
	IUpdateHandler slideInUpdateHandler = new IUpdateHandler() {

		@Override
		public void onUpdate(float pSecondsElapsed) {
			/*
			Log.d(NAME, "OptionsLayer:slideInUpdateHandler:onUpdate");
			if(OptionsLayer.getInstance().layerBackground.getY() > 0.f) {
				float y = OptionsLayer.getInstance().getY() - (pSecondsElapsed * OptionsLayer.PPS);
				OptionsLayer.getInstance().setY(Math.max(y, 0));
			} else {
				ResourceManager.getInstance().getEngine().unregisterUpdateHandler(this);
			}
			*/
			ResourceManager.getInstance().getEngine().unregisterUpdateHandler(this);
		}

		@Override public void reset() {}
	};
	
	IUpdateHandler slideOutUpdateHandler=new IUpdateHandler() {

		@Override
		public void onUpdate(float pSecondsElapsed) {
			Log.d(NAME, "OptionsLayer:slideOutUpdateHandler:onUpdate");
			//if(OptionsLayer.getInstance().layerBackground != null)
			/*
			float y1 = ResourceManager.getInstance().getCameraHeight();
			float y2 = OptionsLayer.this.layerBackground.getHeight();
			if(OptionsLayer.this.layerBackground.getY() < (y1+y2)/2) {
				OptionsLayer.this.layerBackground.setY(Math.min(OptionsLayer.this.layerBackground.getY() + 
					(pSecondsElapsed * OptionsLayer.PPS), (y1+y2)/2));
			} else {
				ResourceManager.getInstance().getEngine().unregisterUpdateHandler(this);
				SceneManager.getInstance().hideLayer();
			}
			*/
		}

		@Override public void reset() {}
	};

	@Override
	public void onHideLayer() {
		ResourceManager.getInstance().getEngine().unregisterUpdateHandler(this.slideOutUpdateHandler);
	}
	
	@Override
	public void onShowLayer() {
		Log.d(NAME, "OptionsLayer:onShowLayer");
		ResourceManager.getInstance().getEngine().registerUpdateHandler(this.slideInUpdateHandler);
	}

	@Override
	public void onUnloadLayer() {}

	@Override
	public void onLoadLayer() {
		Log.d(NAME, "onLoadLayer");
		if(loaded)
			return;
		this.loaded = true;
		
		//this.setTouchAreaBindingOnActionDownEnabled(true);
		//this.setTouchAreaBindingOnActionMoveEnabled(true);
		
		float cameraWidth = ResourceManager.getInstance().getCameraWidth();
		float cameraHeight = ResourceManager.getInstance().getCameraHeight();
		
		/*
		final AButton backButton = new AButton(200f, 200f, ResourceManager.getInstance().arrow1Tex) {
			@Override
			public void onClick() {
				OptionsLayer.this.onHideLayer();
			}
		};
		
		final AButton resetButton = new AButton(200f, 400f, ResourceManager.getInstance().levelButtonTex) {
			@Override
			public void onClick() {
				ResourceManager.getInstance().getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						final AlertDialog.Builder builder = new AlertDialog.Builder(ResourceManager.getInstance().getActivity());
						builder.setTitle("Reset");
						builder.setIcon(R.drawable.ic_launcher);
						builder.setMessage("OK").setPositiveButton("Reset Data", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(final DialogInterface dialog, final int id) {
								int startCount = GameActivity.getIntFromSharedPreferences(GameActivity.SHARED_PREFS_ACTIVITY_START_COUNT);
								ResourceManager.getInstance().getActivity().getSharedPreferences(GameActivity.SHARED_PREFS_MAIN, 
									0).edit().clear().putInt(GameActivity.SHARED_PREFS_ACTIVITY_START_COUNT, startCount).apply();
							}
						}).setNegativeButton("NO", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(final DialogInterface dialog, final int id) {
								
							}
						}).setCancelable(false);
						final AlertDialog alert = builder.create();
						alert.show();
					}
				});
			}
		};
		*/
		
		final Rectangle rect = new Rectangle(0f, 0f, ResourceManager.getInstance().getCameraWidth(), 
			ResourceManager.getInstance().getCameraHeight(),
			ResourceManager.getInstance().getActivity().getVertexBufferObjectManager());
		rect.setColor(0f, 0f, 0f, .8f);
		attachChild(rect);
		
		layerBackground = new Sprite(0, cameraHeight/4f, ResourceManager.levelLayerTex,
			ResourceManager.getInstance().getActivity().getVertexBufferObjectManager());
		layerBackground.setScale(1f);
		layerBackground.setAlpha(1f);
		attachChild(layerBackground);
		
		//layerBackground.setRotationCenter(0, 100);
		//layerBackground.attachChild(backButton);
		//registerTouchArea(backButton);
	}
}