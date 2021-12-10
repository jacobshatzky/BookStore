public class Publisher {
	private String ID;
	
	private String name;
	
	private String address;
	
	private String phone;
	
	private String email;
	
	private double bankAccount;
	
	public Publisher() {
		
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(double bankAccount) {
		this.bankAccount = bankAccount;
	}
	
	
	
}