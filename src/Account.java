import java.util.ArrayList;

public class Account {

    private String name; // name of account

    private String memo;

    private  String  uuid; // account id number

    private User holder; // the user who owns this account

    private ArrayList<Transaction> transactions; // list of transaction in this account

    /**
     *
     * @param name name of the account
     * @param holder the object of the user that holds this account
     * @param theBank the bank that issues the account
     */
    public Account(String name, User holder, Bank theBank){
        // addas
        //set account name and holder
        this.name = name;
        this.holder = holder;

        // new account uuid

        this.uuid = theBank.getNewAccountUUID();

        //init transactions
        this.transactions = new ArrayList<Transaction>();


    }
        public String getUUID(){
        return this.uuid;
        }

    /**
     * get summary line for the account
     * @return the string summary
     */
    public String getSummaryLine(){
        // get account balance
            double balance = this.getBalance();

            // format summary line,depending on the balance is negative

            if (balance >=0){

                return String.format("%s : £%.02f : %s", this.uuid, balance,this.name);
            }else{
                return String.format("%s : £(%.02f) : %s", this.uuid, balance,this.name);

            }
        }

    /**
     * get the balance of the accounts by adding the amount o transactions
     * @return
     */
    public double getBalance(){

        double balance = 0;

        for (Transaction t : this.transactions) {
            balance += t.getAmount();
        }
        return balance;
        }

    /**
     * print transaction history of the amount
     */
    public void printTransHistory(){
        System.out.printf("\nTransaction history for account %s\n", this.uuid);
        for (int t = this.transactions.size()-1; t>=0; t--){
            System.out.print(this.transactions.get(t).getSummaryLine());
        }
        System.out.println();
    }

    /**
     * to create new transaction
     * @param amount the amount transacted
     */
    public void addTransaction(double amount, String memo){
        //create a new transaction object and add to the list
        Transaction newTrans = new Transaction(amount,memo,this);
        this.transactions.add(newTrans);
    }
}
