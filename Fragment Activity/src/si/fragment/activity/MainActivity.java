package si.fragment.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup.LayoutParams;


public class MainActivity extends FragmentActivity
    implements HeadLineFragment.OnHeadLineSelectedListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.articles);
		
		if(findViewById(R.id.fragment_container) != null) {
			if(savedInstanceState != null) {
				return;
			}
			HeadLineFragment fragment = new HeadLineFragment();
			//pass intent's extras to fragment as arguments.
			fragment.setArguments(getIntent().getExtras());
			
			//add fragment to the fragment container.
			getSupportFragmentManager().beginTransaction()
			    .add(R.id.fragment_container, fragment).commit();
		}
	}

	public void onArticleSelected(int position) {
		ArticleFragment articleFragment = (ArticleFragment)
			getSupportFragmentManager().findFragmentById(R.id.article_fragment);
		if(articleFragment != null) {
			//article fragment is available -> two pane layout
			articleFragment.updateArticleView(position);
		} else {
			//fragment is not available ->one pane layout
			ArticleFragment nuFragment = new ArticleFragment();
			Bundle args = new Bundle();
			args.putInt(ArticleFragment.ARG_POSITION, position);
			nuFragment.setArguments(args);
			FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
			trans.replace(R.id.fragment_container, nuFragment);
			trans.addToBackStack(null);
			
			
			trans.commit();
		}
	}
}
