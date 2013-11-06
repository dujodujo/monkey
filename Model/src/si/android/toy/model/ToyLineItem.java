package si.android.toy.model;

public class ToyLineItem extends Toy {
	private int quantity;

	public ToyLineItem() {
		super();

		this.quantity = 1;
	}

	public ToyLineItem(Toy toy) {
		super(toy.getName(), toy.getPrice());
		
		this.quantity = 1;
	}
	
	public int getQuantity() {
		return this.quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public double getTotalPrice() {
		return getQuantity() * getPrice();
	}
}
