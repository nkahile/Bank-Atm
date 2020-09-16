public class User {
	private String accountNumber;
	private String pin;
	private BankAccount savingAccount;
	private BankAccount checkingAccount;
	
	
	public User(String account, String pin, double intrestRate) {
		this.accountNumber = account;
		this.pin = pin;
		this.savingAccount = new SavingsAccount(intrestRate);
		this.checkingAccount = new CheckingAccount();
	}
	
	public void depositeIntoSavings(double money) {
		this.savingAccount.depositeMoney(money);
	}
	
	public boolean withDrawSavingMoney(double money) {
		return this.savingAccount.withDrawMoney(money);
	}
	
	public double getSavingsBalance() {
		return this.savingAccount.getBalance();
	}
	
	public void depositeIntoChecking(double money) {
		this.checkingAccount.depositeMoney(money);
	}

	public boolean withDrawChecking(double money) {
		return this.checkingAccount.withDrawMoney(money);
	}

	public double getCheckingBalance() {
		return this.checkingAccount.getBalance();
	}

	public boolean comparePin(String pin) {
		return this.pin.equals(pin);
	}
	
	public String getUserInfo() {
		return "Account Number: " + this.accountNumber+
				". With a Checking balance of: $"+this.getCheckingBalance()+
				" and a savings balance of $"+this.getSavingsBalance();
	}
	
	public void applyIntrest(int month) {
		((SavingsAccount) this.savingAccount).applyIntrestRates(month);
	}
	
	public String getSaveString() {
		return this.accountNumber+","+this.pin+
				","+this.getCheckingBalance()+","+this.getSavingsBalance()+
				","+((SavingsAccount) this.savingAccount).getIntrestRate();
	}
}
