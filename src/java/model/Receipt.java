/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author admin
 */
public class Receipt {
    private int receiptId;
    private int price;
    private Date dateTime;
    private String description;
    private int accountId;
    private int lodgingHouseId;
    private boolean status;

    // Constructor
    public Receipt(int receiptId, int price, Date dateTime, String description, int accountId, int lodgingHouseId,boolean status) {
        this.receiptId = receiptId;
        this.price = price;
        this.dateTime = dateTime;
        this.description = description;
        this.accountId = accountId;
        this.lodgingHouseId = lodgingHouseId;
        this.status=status;
    }

    public Receipt(int price, Date dateTime, String description, int accountId, int lodgingHouseId,boolean status) {
        this.price = price;
        this.dateTime = dateTime;
        this.description = description;
        this.accountId = accountId;
        this.lodgingHouseId = lodgingHouseId;
        this.status=status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Receipt() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    // Getters and setters
    public int getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getLodgingHouseId() {
        return lodgingHouseId;
    }

    public void setLodgingHouseId(int lodgingHouseId) {
        this.lodgingHouseId = lodgingHouseId;
    }

    // toString method for printing
    @Override
    public String toString() {
        return "Receipt{" +
                "receiptId=" + receiptId +
                ", price=" + price +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", accountId=" + accountId +
                ", lodgingHouseId=" + lodgingHouseId +
                '}';
    }
}
