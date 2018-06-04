package Model;

/**
 * Created by HOUSSEM on 03/05/2018.
 */

public class Payment {

    private int amount;
    private int reference;
    private long date;

    public Payment() {
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

}
