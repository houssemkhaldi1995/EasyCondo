package Model;

/**
 * Created by HOUSSEM on 03/05/2018.
 */

public class Bill {

    private long date;
    private int reference;
    private int key;

    public Bill() {
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
