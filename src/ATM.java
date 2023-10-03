
import java.util.Scanner;
public class ATM {
    public static void main(String[] args) {
        // initialize scanner
        Scanner sc = new Scanner(System.in);

        //initialize bank

        Bank theBank = new Bank("bank of oussama");

        //add a use to bank and create a savings account

        User aUser = theBank.AddUser("Bob", "Smith", "1234");

        // add a checking account for user

        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;
        while (true) {
            //stay in login prompt till successful
            curUser = ATM.mainMenuPrompt(theBank, sc);

            //stay in main meu util user quits
            ATM.printUserMenu(curUser, sc);
        }
    }

    /**
     * print ATM login menu
     *
     * @param theBank theBank object whose account to use
     * @param sc      scanner object for the user input
     * @return
     */
    public static User mainMenuPrompt(Bank theBank, Scanner sc) {

        //inits
        String userID;
        String pin;
        User authUser;

        //prompt the user for id/pin until a correct one is reached
        do {
            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
            System.out.print("Enter user ID: ");
            userID = sc.nextLine();
            System.out.print("Enter pin: ");
            pin = sc.nextLine();

            //try get the user object corresponding to the ID and pin combo
            authUser = theBank.userLogin(userID, pin);
            if (authUser == null) {
                System.out.println("Incorrect user ID/ pin. " + "please try again.");
            }
        } while (authUser == null); // continue looping until successful login

        return authUser;
    }

    public static void printUserMenu(User theUser, Scanner sc) {

        // print a summary of the user accounts
        theUser.printAccountsSummary();

        int choice;

        //user menu

        do {
            System.out.printf("Welcome %s, what would you like to do?\n", theUser.getFirstName());
            System.out.println("1) Show account transaction history");
            System.out.println("2) Withdrawal");
            System.out.println("3) Deposit");
            System.out.println("4) Transfer");
            System.out.println("5) Quit");
            System.out.println();
            System.out.println("Enter choice: ");
            choice = sc.nextInt();

            if (choice < 1 || choice > 5) {
                System.out.println("Invalid choice, please choice 1-5");
            }
            while (choice < 1 || choice > 5) ;
            // process the choice
            switch (choice) {
                case 1:
                    ATM.showTransHistory(theUser, sc);
                    break;
                case 2:
                    ATM.withdrwalFunds(theUser, sc);
                    break;
                case 3:
                    ATM.depositFunds(theUser, sc);
                    break;
                case 4:
                    ATM.transferFunds(theUser, sc);
                    break;
                case 5:
                    ATM.Exit(theUser, sc);
                    break;
            }

            //redisplay the menu unless user wants to quit
            if (choice != 5) {
                ATM.printUserMenu(theUser, sc);
            }
        } while (choice == 5);
    }

    /**
     * to show the transaction
     * @param theUser the logged-in user
     * @param sc the scanner object for user input
     */

    public static void Exit(User theUser, Scanner sc) {

        System.exit(5);
    }

    public static void showTransHistory(User theUser, Scanner sc) {

        int theAcct;
        do {
            System.out.printf("enter the number (1-%d)" + "whose transactions you want to see: ", theUser.numAccounts());
            theAcct = sc.nextInt()-1;
            if (theAcct < 0 || theAcct >= theUser.numAccounts()) {
                System.out.println("invalid account");
            }
        } while (theAcct < 0 || theAcct >= theUser.numAccounts());

        theUser.printAcctTransHistory(theAcct);
    }

    /**
     * process transferring the funds from one account to another
     * @param theUser the logged-in user
     * @param sc the scanner object for user input
     */
    public static void transferFunds(User theUser, Scanner sc) {

        //inits
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;

        //get account to transfer from

        do{
            System.out.printf("Enter the number (1-%d) of the account \n"+  "to transfer from: ", theUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if(fromAcct < 0 || fromAcct >= theUser.numAccounts()){
                System.out.println("invalid account, try again");
            }
        }while(fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);

       //get the account to transfer to
        do{
            System.out.printf("Enter the number (1-%d) of the account \n"+  "to transfer from: ", theUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if(toAcct < 0 || toAcct >= theUser.numAccounts()){
                System.out.println("invalid account, try again");
            }
        }while(toAcct < 0 || toAcct >= theUser.numAccounts());
        // get the amount to transfer
        do{
            System.out.printf("Enter the amount to transfer (maximum £%.02f): £", acctBal);
            amount = sc.nextDouble();
            if(amount < 0){
                System.out.println("enter the amount above zero. ");            
            } else if (amount > acctBal) {
                System.out.printf("Amount must be not be greater then you balance\n " + "balance of £%.02f.\n", acctBal);
            }
        }while (amount < 0 || amount >= acctBal);

        // transfer
        theUser.addAcctTransaction(fromAcct, -1*amount, String.format("Transfer to account %s", theUser.getAcctUUID(toAcct)));
        theUser.addAcctTransaction(toAcct, amount, String.format("Transfer to account %s", theUser.getAcctUUID(fromAcct)));
    }

    /**
     * process the fund withdraw from account
     * @param theUser the logged-in user
     * @param sc the scanner object for user input
     */
    public static void withdrwalFunds(User theUser, Scanner sc) {

        //inits
        int fromAcct;
        double amount;
        double acctBal;
        String memo;

        //get account to transfer from

        do{
            System.out.printf("Enter the number (1-%d) of the account \n"+  "to transfer from: ", theUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if(fromAcct < 0 || fromAcct >= theUser.numAccounts()){
                System.out.println("invalid account, try again");
            }
        }while(fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);

        // get the amount to transfer
        do{
            System.out.printf("Enter the amount to withdrawal from (maximum £%.02f): £", acctBal);
            amount = sc.nextDouble();
            if(amount < 0){
                System.out.println("enter the amount above zero. ");
            } else if (amount > acctBal) {
                System.out.printf("Amount must be not be greater then you balance\n " + "balance of £%.02f.\n", acctBal);
            }
        }while (amount < 0 || amount >= acctBal);
        // to make the rest of the previous input
        sc.nextLine();
        // get the memo
        System.out.println("Enter a memo: ");
        memo = sc.nextLine();

        // does the withdrawl
        theUser.addAcctTransaction(fromAcct, -1*amount, memo);
    }

    /**
     * process a fund to deposit an account
     * @param theUser the logged-in user
     * @param sc the scanner object for user input
     */
    public static void depositFunds(User theUser, Scanner sc){

        //inits
        int toAcct;
        double amount;
        double acctBal;
        String memo;

        //get account to transfer from

        do{
            System.out.printf("Enter the number (1-%d) of the account \n"+  "to transfer from: ", theUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if(toAcct < 0 || toAcct >= theUser.numAccounts()){
                System.out.println("invalid account, try again");
            }
        }while(toAcct < 0 || toAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(toAcct);

        // get the amount to transfer
        do{
            System.out.printf("Enter the amount to deposit in (maximum £%.02f): £", acctBal);
            amount = sc.nextDouble();
            if(amount < 0) {
                System.out.println("enter the amount above zero. ");
            }
        }while (amount < 0);
        // to make the rest of the previous input
        sc.nextLine();
        // get the memo
        System.out.println("Enter a memo: ");
        memo = sc.nextLine();

        // does the deposit
        theUser.addAcctTransaction(toAcct, amount, memo);
    }
}
