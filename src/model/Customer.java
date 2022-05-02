package model;

/**
 * This class models the Customer data that is retrieved from the mysql database. Data members map to attributes in the database. Class includes data member getter and setter methods.
 */
public class Customer {

    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private int divisionId;
    private String countryName;

    /**
     * Class constructor. Sets data members upon instantiation.<br><br>
     * @param customerId the Customer's ID
     * @param customerName the Customer's name
     * @param address the Customer's address
     * @param postalCode the Customer's postal code
     * @param phone the Customer's phone number
     * @param divisionId the Customer's division ID number
     * @see table.CustomersManager
     */
    public Customer(int customerId, String customerName, String address, String postalCode, String phone, int divisionId){
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
    }

    /**
     * Gets the Customer's ID number<br><br>
     * @return the Customer's ID number
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets the Customer's ID number<br><br>
     * @param customerId the Customer's ID number
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets the customer's name<br><br>
     * @return the Customer's name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the Customer's Name<br><br>
     * @param customerName the Customer's name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Gets the Customer's address<br><br>
     * @return the Customer's address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the Customer's address<br><br>
     * @param address the Customer's address
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * Gets the Customer's postal code<br><br>
     * @return the Customer's postal code
     */
    public String getPostalCode() {
        return postalCode;
    }
    /**
     * Sets the Customer's postal code<br><br>
     * @param postalCode the Customer's postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    /**
     * Gets the Customer's phone number<br><br>
     * @return the Customer's phone number
     */
    public String getPhone() {
        return phone;
    }
    /**
     * Sets the Customer's phone<br><br>
     * @param phone the Customer's phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**
     * Gets the Customer's division ID number<br><br>
     * @return the Customer's division ID number
     */
    public int getDivisionId() {
        return divisionId;
    }
    /**
     * Sets the Customer's divisionId<br><br>
     * @param divisionId the Customer's divisionId
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
    /**
     * Gets the Customer's country name<br><br>
     * @return the Customer's country name
     */
    public String getCountryName() {
        return countryName;
    }
    /**
     * Sets the Customer's country name<br><br>
     * @param countryName the Customer's country name
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

}
