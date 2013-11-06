package si.android.toy;

import si.android.toy.model.Address;
import si.android.toy.model.Order;
import si.android.toy.model.UserData;
import android.app.Application;


public class ApplicationState extends Application {
	private Order order;
	
	public ApplicationState() {
		this.order = new Order();
	}
	
	public void setUserData(UserData userData) {
		order.setUserData(userData);
	}
	
	public UserData getUserData() {
		return this.order.getUserData();
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	public Order getOrder() {
		return order;
	}
	
	public void setAddress(Address address) {
		order.getUserData().setAddress(address);
	}
	
	public Address getAddress() {
		return order.getUserData().getAddress();
	}
}
