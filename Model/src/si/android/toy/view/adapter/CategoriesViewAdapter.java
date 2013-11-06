package si.android.toy.view.adapter;

import java.util.List;

import si.android.toy.ApplicationState;
import si.android.toy.R;
import si.android.toy.model.Category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoriesViewAdapter extends BaseAdapter {
	private final LayoutInflater inflater;
	private ApplicationState appState;
	private final List<Category> categories;
	
	public CategoriesViewAdapter(ApplicationState appState, List<Category> categories) {
		super();
		
		this.appState = appState;
		this.categories = categories;
		this.inflater = 
			(LayoutInflater) this.appState.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public int getCount() {
		return categories.size();
	}

	public Category getItem(int position) {
		return categories.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.grid_cell, null);
			holder = getViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		customizeView(position, holder);
		return convertView;
	}
	
	private ViewHolder getViewHolder(View view) {
		TextView categoryTitle = (TextView)view.findViewById(R.id.grid_item_text);
		ImageView categoryImage = (ImageView)view.findViewById(R.id.grid_item_image);
		
		return new ViewHolder(categoryTitle, categoryImage);
	}
	
	protected void customizeView(int poisition, ViewHolder holder) {
		Category category = categories.get(poisition);
		
		holder.getCategoryName().setText(category.getTitle());
		customizeCategoryImage(category, holder.getCategoryImage());
	}
	
	protected void customizeCategoryImage(Category category, ImageView categoryImage) {
		categoryImage.setMaxWidth(0);
		categoryImage.setVisibility(View.VISIBLE);
		categoryImage.setImageResource(category.getIcon());
	}
	
	class ViewHolder {
		private ImageView categoryImage;
		private TextView categoryName;
		
		public ViewHolder(TextView categoryName, ImageView categoryImage) {
			super();
			
			this.categoryName = categoryName;
			this.categoryImage = categoryImage;
		}
		
		public TextView getCategoryName() {
			return categoryName;
		}
		
		public ImageView getCategoryImage() {
			return categoryImage;
		}
	}
}
