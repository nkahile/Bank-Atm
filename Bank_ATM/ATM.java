import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Random;

public class ATM {
	private HashMap<String, User> userList;
	private Scanner scanner;
	private Random r = new Random();

	public ATM() {
		this.userList = new HashMap<String, User>();
		this.loadData();
		this.scanner = new Scanner(System.in);
	}

	private void loadData() {
		String row = "";
		try {
			BufferedReader csvReader = new BufferedReader(new FileReader("./bankStatement.csv"));
			while ((row = csvReader.readLine()) != null) {
				String[] data = row.split(",");
				double intrestRate = Double.parseDouble(data[4]);
				double checkingValue = Double.parseDouble(data[2]);
				double savingValue = Double.parseDouble(data[3]);

				User user = new User(data[0], data[1], intrestRate);
				user.depositeIntoChecking(checkingValue);
				user.depositeIntoSavings(savingValue);

				this.userList.put(data[0], user);
			}

			csvReader.close();
			System.out.println("Bank Data Succussfully Loaded");
		} catch (IOException e) {
			
			//e.printStackTrace();
		}
	}

	private void saveData() {
		try {
			File csvFile = new File("./bankStatement.csv");
			FileWriter csvWriter = new FileWriter(csvFile);
			for (String account : this.userList.keySet()) {
				String data = this.userList.get(account).getSaveString();
				csvWriter.append(data);
				csvWriter.append('\n');
			}

			csvWriter.flush();
			csvWriter.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		System.out.println("Data Saved");
	}

	public void start() {
		System.out.println("Welcome to Your ATM");
		while (true) {
			System.out.println("Please enter your Account Number: ");
			String account = this.scanner.nextLine();
			if (account.equals("00000")) {
				System.out.println("Please enter your PIN: ");
				String pin = this.scanner.nextLine();
				if (pin.equals("12345")) {
					this.loadAdminOptions();
					String randomString = this.scanner.nextLine();
					continue;
				} else {
					System.out.println("PIN was incorrect, please start over.");
				}
			} else if (this.userList.containsKey(account)) {
				System.out.println("Please enter your PIN: ");
				String pin = this.scanner.nextLine();
				if (this.userList.get(account).comparePin(pin)) {
					this.loadUserOptions(this.userList.get(account));
					String randomString = this.scanner.nextLine();
					continue;
				} else {
					System.out.println("PIN was incorrect, please start over.");
				}
			} else {
				this.saveData();
				break;
			}
		}
	}

	private void loadAdminOptions() {
		System.out.println("Welcome Admin");
		System.out.println("Please enter the number for one of the following options: ");
		boolean continueGoing = true;

		while (continueGoing) {
			System.out.println("Add User (1)");
			System.out.println("Delete User (2)");
			System.out.println("List All Users (3)");
			System.out.println("Apply Intrest to all Savings (4)");
			System.out.println("Return To Menu (5)");
			System.out.println("Exit (6)");

			int option = this.scanner.nextInt();
			switch (option) {
			case 1:
				this.addUser();
				break;
			case 2:
				this.deleteUser();
				break;
			case 3:
				this.listUsers();
				break;
			case 4:
				this.applyIntrestRates();
				break;
			case 5:
				continueGoing = false;
				break;
			case 6:
				this.saveData();
				System.exit(0);
			default:
				System.out.println("Please select a valid option");
			}
		}
	}

	private String generateRandomNumberString() {
		String numbers = "0123456789";
		String value = "";
		for (int i = 0; i < 5; i++) {
			value = value + numbers.charAt(this.r.nextInt(numbers.length()));
		}

		return value;
	}

	private void addUser() {
		while (true) {
			String acountNumber = this.generateRandomNumberString();
			if (!this.userList.containsKey(acountNumber)) {
				String pin = this.generateRandomNumberString();
				System.out.println("At what intrest would you like to create the savings account?");
				double intrestRate = this.scanner.nextDouble();
				User user = new User(acountNumber, pin, intrestRate);
				this.userList.put(acountNumber, user);
				System.out.println(
						"Created a New User With the Account Number of " + acountNumber + " and the Pin of " + pin);
				break;
			}
		}
	}

	private void deleteUser() {
		System.out.println("Please enter your Account Number of the user you would like to remove: ");
		String account = this.scanner.nextLine();
		if (this.userList.containsKey(account)) {
			if (this.userList.get(account).getCheckingBalance() == 0
					&& this.userList.get(account).getSavingsBalance() == 0) {
				this.userList.remove(account);
				System.out.println("Successfully remove User: " + account);
			} else {
				System.out.println("Couldn't remove User because their account isn't empty");
			}
		} else {
			System.out.println("Account doesn't exist");
		}
	}

	private void listUsers() {
		System.out.println("The following are the list of registerd users:");
		for (String account : this.userList.keySet()) {
			System.out.println(this.userList.get(account).getUserInfo());
		}
	}

	private void applyIntrestRates() {
		System.out.println("For how many month would you like to apply the intrest rates:");
		int month = this.scanner.nextInt();
		for (String account : this.userList.keySet()) {
			this.userList.get(account).applyIntrest(month);
		}
	}

	private void loadUserOptions(User user) {
		System.out.println("Welcome User");
		System.out.println("Please enter the number for one of the following options: ");
		boolean continueGoing = true;

		while (continueGoing) {
			System.out.println("Get Checking Balance (1)");
			System.out.println("Deposite into Checking (2)");
			System.out.println("Withdraw From Checking (3)");
			System.out.println("Get Savings Balance (4)");
			System.out.println("Deposite Into Savings (5)");
			System.out.println("Withdraw from Savings (6)");
			System.out.println("Return To Menu (7)");
			System.out.println("Exit (8)");

			int option = this.scanner.nextInt();
			switch (option) {
			case 1:
				System.out.println("Your Checking balance is " + user.getCheckingBalance());
				break;
			case 2:
				System.out.println("Please Enter the Amount you would like to deposite into your checking: ");
				double checkingDeposite = this.scanner.nextDouble();
				if (checkingDeposite < 0) {
					System.out.println("Can't remove money with a deposite");
				} else {
					user.depositeIntoChecking(checkingDeposite);
				}
				break;
			case 3:
				System.out.println("Please Enter the Amount ou would like to withdraw from your checking: ");
				double checkingWithDraw = this.scanner.nextDouble();
				if (checkingWithDraw < 0) {
					System.out.println("Can't withdraw a negative");
				} else {
					boolean canWithDrawFromChecking = user.withDrawChecking(checkingWithDraw);
					if (canWithDrawFromChecking) {
						System.out.println("Successfully withdrew the money");
					} else {
						System.out.println("Couldn't withdraw the money because of insufficient balance");
					}
				}
				break;
			case 4:
				System.out.println("Your Savings balance is " + user.getSavingsBalance());
				break;
			case 5:
				System.out.println("Please Enter the Amount you would like to deposite into your savings: ");
				double savingsDeposite = this.scanner.nextDouble();
				if (savingsDeposite < 0) {
					System.out.println("Can't remove money with a deposite");
				} else {
					user.depositeIntoSavings(savingsDeposite);
				}
				break;
			case 6:
				System.out.println("Please Enter the Amount ou would like to withdraw from your checking: ");
				double savingsWithDraw = this.scanner.nextDouble();
				if (savingsWithDraw < 0) {
					System.out.println("Can't withdraw a negative");
				} else {
					boolean canWithDrawFromSavings = user.withDrawSavingMoney(savingsWithDraw);
					if (canWithDrawFromSavings) {
						System.out.println("Successfully withdrew the money");
					} else {
						System.out.println("Couldn't withdraw the money because of insufficient balance");
					}
				}
				break;
			case 7:
				continueGoing = false;
				break;
			case 8:
				this.saveData();
				System.exit(0);
			default:
				System.out.println("Please select a valid option");
			}
		}

	}

}
