package Model;

/**
 * Created by HOUSSEM on 14/04/2018.
 */

public class Condo {
    private String id;
    private String name;
    private String address;
    private String city;
    private String country;
    private int postalCode;
    private int numBlocs;
    private int numBlocHouses;

    public Condo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public int getNumBlocs() {
        return numBlocs;
    }

    public void setNumBlocs(int numBlocs) {
        this.numBlocs = numBlocs;
    }

    public int getNumBlocHouses() {
        return numBlocHouses;
    }

    public void setNumBlocHouses(int numBlocHouses) {
        this.numBlocHouses = numBlocHouses;
    }
}
