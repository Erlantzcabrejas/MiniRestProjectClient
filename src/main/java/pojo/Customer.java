package pojo;

public class Customer {

    private Long reference;
    private String name;
    private String address1;
    private String address2;
    private String town;
    private String county;
    private String country;
    private String postcode;

    public Customer(Long reference, String name, String address1, String address2, String town, String county,
                    String country, String postcode) {
        super();
        this.reference = reference;
        this.name = name;
        this.address1 = address1;
        this.address2 = address2;
        this.town = town;
        this.county = county;
        this.country = country;
        this.postcode = postcode;
    }

    public Long getReference() {
        return reference;
    }

    public void setReference(Long reference) {
        this.reference = reference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @Override
    public String toString() {
        return "Customers{" +
                "reference=" + reference +
                ", name='" + name + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", town='" + town + '\'' +
                ", county='" + county + '\'' +
                ", country='" + country + '\'' +
                ", postcode='" + postcode + '\'' +
                '}';
    }
}
