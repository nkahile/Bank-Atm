Goals;
-The goal for this project is to build a basic atm program that can help in simple depositing, checking, and reteive data.

Inputs;
-The user's choices either they want to deposite, withdraw, check balance, or if they are an admin,..etc


Outputs;
-based on the user's commands, for example if they want to see all accounts(which is only available for the admin) they will have to press"3" and it will display on the consule.


Steps:
- Create five classes (main, user, ATM, BankAccount, CheckingAccount, SavingAccount)

-For the bankAccount class i built an abstract class to handle all the transactions logic
thats common for both saving and checking account.

-And for the checking account it inherted all the functions from the parent class which is
the bank account 

-For savings account it also inhearted all the functions from bank account in addition it handled all the interest rate applied to
savings

-For the user it handled all the info that was unique to the user including the two bank accounts
which are savings and checkings, in addition all transaction through any bank account came through
the user's class

-Also the user class, it handled all the user's summary and hanlded all the reload of info we wanted to reload.
-and for the AtM it handled all the loading and saving all the bank data. In addition it verfied that the user exsited in the system and helped verfy that the pin was 
correct for each account, it also it enabled the admin to access their private portal which allows them to add or delete users, apply intrest rates, or list all the 
users in the bank system. Finally the ATM allowed the normal user to access the portal which allows them to be able to check balance, deposite, or withdraw from both their
checking or savings account.

- Finally Main just created the ATM object and ran it
