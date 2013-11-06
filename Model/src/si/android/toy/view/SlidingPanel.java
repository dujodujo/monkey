package si.android.toy.view;

import si.android.toy.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;

public class SlidingPanel extends LinearLayout {
	private int speed = 30;
	private boolean isOpen = false;
	private Animation fadeOut = null;
	private Context context = null;
	
	public SlidingPanel(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		this.context = context;
		
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SlidingPanel, 0, 0);
		speed = typedArray.getInt(R.styleable.SlidingPanel_speed, 30);
		typedArray.recycle();
		
	}
	
	public void toggle() {
		TranslateAnimation animation = null;
		
		Animation.AnimationListener collapseListener = new Animation.AnimationListener() {
			public void onAnimationEnd(Animation animation) {
				setVisibility(View.GONE);
				isOpen = false;
			}

			public void onAnimationRepeat(Animation animation) {}

			public void onAnimationStart(Animation animation) {}
		};
		
		this.isOpen = !isOpen;
		
		if(isOpen) {
			this.setVisibility(View.VISIBLE);
			animation = new TranslateAnimation(getWidth(), 0.0f, 0.0f, 0.0f);
		} else {
			animation = new TranslateAnimation(0.0f, getWidth(), 0.0f, 0.0f);
			animation.setAnimationListener(collapseListener);
		}
		
		animation.setDuration(speed);
		animation.setInterpolator(new AccelerateInterpolator(1.0f));
		startAnimation(animation);
	}
}
