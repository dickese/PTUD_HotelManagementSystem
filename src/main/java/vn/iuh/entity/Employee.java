package vn.iuh.entity;

import java.sql.Date;

public class Employee {
    private String id;
    private String employeeName;
    private String CCCD;
    private Date birthDate;

    public Employee() {
    }

    public Employee(String id, String employeeName, String CCCD, Date birthDate) {
        this.id = id;
        this.employeeName = employeeName;
        this.CCCD = CCCD;
        this.birthDate = birthDate;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}