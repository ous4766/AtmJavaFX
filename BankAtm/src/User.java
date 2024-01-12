import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.security.MessageDigest;
public class User {

    private String firstName; //name of user

    private String lastName; //name of user

    private String uuid; //id code for the user

    private byte pinHash[]; // stores the md5 hash of the pin to secure the pin data

    private ArrayList<Account> accounts; // the list of accounts for this user

    /**
     * Create a new user
     * @param firstName user first name
     * @param lastName user second name
     * @param pin      user account pin number
     * @param theBank the bank object from the users customer
     */
    public User(String firstName,String lastName, String pin, Bank theBank){


        //set user's name

        this.firstName = firstName;
        this.lastName = lastName;

        // store the pin md5 hash rather the original, security reasons
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
        this.pinHash = md.digest(pin.getBytes());

       //its never going to be called its just remove the error java gives
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

        // get a new,unique universal ID for the user
        this.uuid = theBank.getNewUserUUID();

        // create empty list of accounts
        this.accounts = new ArrayList<Account>();

        //print log
        System.out.printf("New user %s, %s with ID %s created.\n", firstName,lastName, this.uuid);
        
       
    }

    /**
     * add account for the user
     * @param anAcct the account to add
     */
    public void addAccount(Account anAcct) {
        this.accounts.add(anAcct);
    }
    public Account getAccount(int index) {
        if (index >= 0 && index < this.accounts.size()) {
            return this.accounts.get(index);
        }
        return null;
    }
    
    
    public String getUUID(){
        return this.uuid;
    }



    /**
     * check whether a given pin matches the true user pin
     * @param aPin the pin to check
     * @return whether teh pin is vlaid or not
     */
    public boolean validatePin(String aPin) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);

            //its never going to be called its just remove the error java gives
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
        }
        return false;
    }
    public String getFirstName(){
        return this.firstName;
    }
         public void printAccountsSummary(){

            System.out.printf("\n\n%s's accounts summary\n", this.firstName);
            for (int a = 0; a < this.accounts.size(); a++){
                System.out.printf("  %d) %s\n", a+1, this.accounts.get(a).getSummaryLine());
            }
    System.out.println();
    }

    /**
     *
     * @return the number accounts
     */
    public int numAccounts(){
        int size = this.accounts.size();
        return size;
    }

    /**
     * print transaction of a particular account
     * @param acctIDx the index of the account to use
     */
    public void printAcctTransHistory(int acctIDx){
        this.accounts.get(acctIDx).printTransHistory();
    }


    /**
     * get the balance of a particular account
     * @param acctIDx the index of the account to use
     * @return the balance of the account
     */
    public double getAcctBalance(int acctIDx){
        return this.accounts.get(acctIDx).getBalance();

    }

    /**
     * Get the UUID of a particular account
     * @param acctIDx the index of the account to use
     * @return uuid of account
     */
    public String getAcctUUID(int acctIDx){
        return this.accounts.get(acctIDx).getUUID();

    }
    public void addAcctTransaction(int acctIDx, double amount, String memo){
        this.accounts.get(acctIDx).addTransaction(amount,memo);
    }

       public List<Transaction> getAcctTransactions(int acctIDx) {
        if (acctIDx >= 0 && acctIDx < this.accounts.size()) {
            return this.accounts.get(acctIDx).getTransactions();
        }
        return null;
    }
    public ArrayList<Account> getAccounts() {
        return this.accounts;
    }
/*
    public Account addAccount(int i) {
        return null;
    }
    */
}
