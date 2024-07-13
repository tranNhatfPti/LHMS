/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class TenancyDeposit {
    private int tenancyDepositID;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String dateOfBirth;
    private String bookingDate;
    private String arriveDate;
    private double depositMoney;
    private Room roomID;
    private String description;
    private String CIC;
    private int statusDeposit;
    private int statusDelete;

    public TenancyDeposit() {
    }
    
    public TenancyDeposit(int tenancyDepositID, String fullName, String email, 
            String phoneNumber, String dateOfBirth, String bookingDate,String CIC, 
            String arriveDate, double depositMoney, Room roomID,
            String description, int statusDeposit, int statusDelete) {
        this.tenancyDepositID = tenancyDepositID;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.bookingDate = bookingDate;
        this.arriveDate = arriveDate;
        this.depositMoney = depositMoney;
        this.roomID = roomID;
        this.CIC=CIC;
        this.description = description;
        this.statusDeposit = statusDeposit;
        this.statusDelete = statusDelete;
    }

    public int getTenancyDepositID() {
        return tenancyDepositID;
    }

    public void setTenancyDepositID(int tenancyDepositID) {
        this.tenancyDepositID = tenancyDepositID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(String arriveDate) {
        this.arriveDate = arriveDate;
    }

    public double getDepositMoney() {
        return depositMoney;
    }

    public void setDepositMoney(double depositMoney) {
        this.depositMoney = depositMoney;
    }

    public Room getRoomID() {
        return roomID;
    }

    public void setRoomID(Room roomID) {
        this.roomID = roomID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatusDeposit() {
        return statusDeposit;
    }

    public void setStatusDeposit(int statusDeposit) {
        this.statusDeposit = statusDeposit;
    }

    public String getCIC() {
        return CIC;
    }

    public void setCIC(String CIC) {
        this.CIC = CIC;
    }

    public int getStatusDelete() {
        return statusDelete;
    }

    public void setStatusDelete(int statusDelete) {
        this.statusDelete = statusDelete;
    }

    @Override
    public String toString() {
        return "TenancyDeposit{" + "tenancyDepositID=" + tenancyDepositID + ", fullName=" + fullName + ", email=" + email + ", phoneNumber=" + phoneNumber + ", dateOfBirth=" + dateOfBirth + ", bookingDate=" + bookingDate + ", arriveDate=" + arriveDate + ", depositMoney=" + depositMoney + ", roomID=" + roomID + ", description=" + description + ", CIC=" + CIC + ", statusDeposit=" + statusDeposit + ", statusDelete=" + statusDelete + '}';
    }
    
    
}
