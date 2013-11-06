package si.fragment.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HeadLineFragment extends ListFragment {
	OnHeadLineSelectedListener mCallback;

	public interface OnHeadLineSelectedListener {
		public void onArticleSelected(int position);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setListAdapter(new ArrayAdapter<String>(getActivity(), 
			android.R.layout.simple_list_item_1, Content.headlines));
	}

	@Override
	public void onStart() {
		super.onStart();
		if(getFragmentManager().findFragmentById(R.id.article_fragment) != null) {
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallback = (OnHeadLineSelectedListener) activity;
		} catch (ClassCastException e){
			throw new ClassCastException(activity.toString() + 
				"implement OnHeadLinesSelectedListener");
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		//notify parent activity of selected item.
		mCallback.onArticleSelected(position);
		//set item as checked
		getListView().setItemChecked(position, true);
	}
}
