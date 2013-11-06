package si.android.toy.model;

public class Category implements Comparable<Category> {
	private String title;
	private int icon;
	
	public Category(String title, int icon) {
		super();
	
		this.title = title;
		this.icon = icon;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getIcon() {
		return icon;
	}
	
	public void setIcon(int icon) {
		this.icon = icon;
	}
	
	public int compareTo(Category other) {
		return this.title.compareTo(other.title);
	}
}
