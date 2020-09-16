public class SavingsAccount extends BankAccount{
	private double intrestRate;
	public SavingsAccount(double rate) {
		super();
		this.intrestRate = rate;
	}
	
	public void applyIntrestRates(int month) {
		double balance = this.getBalance()*(1+(this.intrestRate*month));
		this.setBalance(balance);
	}
	
	public double getIntrestRate() {
		return this.intrestRate;
	}
}
