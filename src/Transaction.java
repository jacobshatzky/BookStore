import java.util.UUID;

public class Transaction {
	private String shippingNumber;
	
	private String trackingStatus;
	
	public Transaction() {
		shippingNumber = UUID.randomUUID().toString();
		trackingStatus = "In progress";
	}

	public String getShippingNumber() {
		return shippingNumber;
	}

	public void setShippingNumber(String shippingNumber) {
		this.shippingNumber = shippingNumber;
	}
	
	public void deliver() {
		trackingStatus = "Delivered";
	}
	
	
}
