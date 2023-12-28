package lk.ijse.pawnshop.entity;

public class Employee {
    private String id;
    private String name;
    private String address;
    private String position;
    private double salary;

    public Employee() {
    }

    public Employee(String id, String name, String address, String position, double salary) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.position = position;
        this.salary = salary;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
