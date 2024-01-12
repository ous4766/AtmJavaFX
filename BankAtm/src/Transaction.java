import java.util.Date;

public class Transaction {

    private double amount;// amount of transaction

    private Date timestamp;// time and date of transaction

    private Account inAccount; // account which transaction was performed

    private String memo;

    /**
     * create a new transaction
     * @param amount the amount transacted
     * @param inAccount the account the transaction belong to
     */
    public Transaction(double amount, Account inAccount){

        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = "";
    }
    public Transaction(double amount,  String memo ,Account inAccount){
        this(amount,inAccount);

        this.memo= memo;

    }

    public double getAmount(){
        return this.amount;
    }

    /**
     * get a string to summarize transaction
     * @return the summary string
     */
    public String getSummaryLine(){
        if(this.amount >=0){
            return String.format("%s : £%.02f : %s \n", this.timestamp.toString(),this.amount,this.memo);
        }else{
            return String.format("%s : £(%.02f) : %s\n", this.timestamp.toString(),this.amount, this.memo);
        }

    }
}
