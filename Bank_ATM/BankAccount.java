public abstract class BankAccount {
	private double balance;
	public BankAccount() {
		this.balance = 0;
	}
	
	public void depositeMoney(double money) {
		this.balance += money;
	}
	
	public boolean withDrawMoney(double money) {
		if((this.balance-money) <0.0) {
			return false;
		}
		this.balance -= money;
		return true;
	}
	
	public double getBalance() {
		return this.balance;
	}
	
	protected void setBalance(double money) {
		this.balance = money;
	}
}
