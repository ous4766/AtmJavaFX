import java.util.Date;

public class TransferTransaction extends Transaction {
    private Account srcAccount;
    private Account destAccount;

    public TransferTransaction(double amount, Account srcAccount, Account destAccount) {
        super(amount, destAccount);
        this.srcAccount = srcAccount;
        this.destAccount = destAccount;
    }

    public Account getSrcAccount() {
        return srcAccount;
    }

    public Account getDestAccount() {
        return destAccount;
    }

    @Override
    public String getSummaryLine() {
        if (getAmount() >= 0) {
            return String.format("Transfer to %s: £%.2f", destAccount.getUUID(), getAmount());
        } else {
            return String.format("Transfer from %s: £%.2f", srcAccount.getUUID(), -getAmount());
        }
    }
}
