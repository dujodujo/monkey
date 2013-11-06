package si.android.tapping.observer;

import java.util.ArrayList;

public class SimpleObserver<T> implements Observer<T> {
	private ArrayList<OnChangeListener<T>> listeners = new ArrayList<OnChangeListener<T>>();
	
	public void addListener(OnChangeListener<T> listener) {
		synchronized(listeners) {
			listeners.add(listener);
		}
	}

	public void removeListener(OnChangeListener<T> listener) {
		synchronized(listeners) {
			listeners.remove(listener);
		}
	}
	
	protected void notifyObservers(final T model) {
		synchronized(listeners) {
			for (OnChangeListener<T> listener : listeners) {
				listener.onChange(model);
			}
		}
	}
}
