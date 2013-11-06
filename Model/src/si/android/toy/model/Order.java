package si.android.toy.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
	private List<ToyLineItem> toys = new ArrayList<ToyLineItem>();
	private UserData userData;
	
	public Order() {
		this.userData = new UserData();
	}

	public UserData getUserData() {
		return userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}
	
	public List<ToyLineItem> getToys() {
		return toys;
	}
	
	public void addToy(Toy toy) {
		ToyLineItem toyItem = getToyLineItem(toy);
		if(toyItem != null) {
			toyItem.setQuantity(toyItem.getQuantity() + 1);
		} else {
			toys.add(new ToyLineItem(toy));
		}
	}
	
	public void removeToy(Toy toy) {
		ToyLineItem toyItem = getToyLineItem(toy);
		if(toyItem.getQuantity() == 1) {
			toys.remove(toyItem);
		} else {
			toyItem.setQuantity(toyItem.getQuantity() - 1);
		}
	}
	
	public void removeToys() {
		toys = new ArrayList<ToyLineItem>();
	}
	
	public int getToysCount() {
		int count = 0;
		for(ToyLineItem toy : toys) {
			count += toy.getQuantity();
		}
		return count;
	}
	
	public double getTotalPrice() {
		double total = 0;
		for(ToyLineItem toy : toys) {
			total += toy.getTotalPrice();
		}
		return total;
	}
	
	public ToyLineItem getToyLineItem(Toy toy) {
		for(ToyLineItem toyItem : toys) {
			if(toyItem.getName().equals(toy.getName())) {
				return toyItem;
			}
		}
		return null;
	}
}
