package si.android.tapping.observer;

public interface Observer<T> {
	void addListener(OnChangeListener<T> listener);
	void removeListener(OnChangeListener<T> listener);
}
