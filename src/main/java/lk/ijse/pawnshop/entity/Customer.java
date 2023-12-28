package lk.ijse.pawnshop.entity;

public class Customer {
    private String id;
    private String name;
    private String contactNo;
    private String NIC;
    private String address;
    private String email;

    public Customer() {
    }

    public Customer(String id, String name, String contactNo, String NIC, String address, String email) {
        this.id = id;
        this.name = name;
        this.contactNo = contactNo;
        this.NIC = NIC;
        this.address = address;
        this.email = email;
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

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
