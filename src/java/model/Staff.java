/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class Staff {
    private int staffID;
    private String nameStaff;
    private RoleOfStaff roleStaffID;
    private String phoneNumber;
    private String email;
    private String province;
    private String district;
    private String ward;
    private String addressDetail;
    private double salary;
    private LodgingHouse lodgingHouseID;

    public Staff() {
    }

    public Staff(int staffID, String nameStaff, RoleOfStaff roleStaffID,
            String phoneNumber, String email, String province, String district,
            String ward, String addressDetail, double salary, 
            LodgingHouse lodgingHouseID) {
        this.staffID = staffID;
        this.nameStaff = nameStaff;
        this.roleStaffID = roleStaffID;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.addressDetail = addressDetail;
        this.salary = salary;
        this.lodgingHouseID = lodgingHouseID;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int StaffID) {
        this.staffID = StaffID;
    }

    public String getNameStaff() {
        return nameStaff;
    }

    public void setNameStaff(String nameStaff) {
        this.nameStaff = nameStaff;
    }

    public RoleOfStaff getRoleStaffID() {
        return roleStaffID;
    }

    public void setRoleStaffID(RoleOfStaff roleStaffID) {
        this.roleStaffID = roleStaffID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public LodgingHouse getLodgingHouseID() {
        return lodgingHouseID;
    }

    public void setLodgingHouseID(LodgingHouse lodgingHouseID) {
        this.lodgingHouseID = lodgingHouseID;
    }

    @Override
    public String toString() {
        return "Staff{" + "StaffID=" + staffID + ", nameStaff=" + nameStaff +
                ", roleStaffID=" + roleStaffID + ", phoneNumber=" 
                + phoneNumber + ", email=" + email + ", province=" 
                + province + ", district=" + district + ", ward=" + ward +
                ", addressDetail=" + addressDetail + ", salary=" + salary + 
                ", lodgingHouseID=" + lodgingHouseID + '}';
    }
    
    
    
}
