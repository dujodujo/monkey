package si.android.tapping.observer;

public class Counter extends SimpleObserver<Counter> {
	private int id = -1;
	private int counter = 0;
	private String label = "";
	private boolean locked = false;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
		notifyObservers(this);
	}
	
	public int getCounter() {
		return counter;
	}
	
	public void setCounter(int counter) {
		this.counter = counter;
		this.notifyObservers(this);
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
		this.notifyObservers(this);
	}
	
	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
		this.notifyObservers(this);
	}
	
	synchronized public Counter clone() {
		Counter counter = new Counter();
		counter.setId(this.id);
		counter.setLabel(this.label);
		counter.setCounter(this.counter);
		counter.setLocked(this.locked);
		return counter;
	}
	
	synchronized public void consume(Counter counter) {
		this.id = counter.getId();
		this.label = counter.getLabel();
		this.counter = counter.getCounter();
		this.locked = counter.isLocked();
		this.notifyObservers(this);
	}
}