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
public class Notification {

    private int notificationId;
    private String notificationMessage;
    private Date notificationDateTime;
    private int receiverId;
    private int senderId;
    private int confirmationStatus;
    private int typeOfNotification;

    public Notification(int notificationId, String notificationMessage, Date notificationDateTime, int receiverId, int senderId, int confirmationStatus,int typeOfNotification) {
        this.notificationId = notificationId;
        this.notificationMessage = notificationMessage;
        this.notificationDateTime = notificationDateTime;

        this.receiverId = receiverId;
        this.senderId = senderId;
        this.confirmationStatus = confirmationStatus;
        this.typeOfNotification=typeOfNotification;
    }
 public Notification(String notificationMessage, Date notificationDateTime,int receiverId, int senderId, int confirmationStatus,int typeOfNotification) {
        this.notificationMessage = notificationMessage;
        this.notificationDateTime = notificationDateTime;
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.confirmationStatus = confirmationStatus;
        
        this.typeOfNotification=typeOfNotification;
    }

    public Notification(String string, Date currentDate, int accountID, int id, int i) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public Date getNotificationDateTime() {
        return notificationDateTime;
    }

    public int getTypeOfNotification() {
        return typeOfNotification;
    }

    public void setTypeOfNotification(int typeOfNotification) {
        this.typeOfNotification = typeOfNotification;
    }

    public void setNotificationDateTime(Date notificationDateTime) {
        this.notificationDateTime = notificationDateTime;
    }


    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int isConfirmationStatus() {
        return confirmationStatus;
    }

    public void setConfirmationStatus(int confirmationStatus) {
        this.confirmationStatus = confirmationStatus;
    }

    @Override
    public String toString() {
        return "Notification{" + "notificationId=" + notificationId + ", notificationMessage=" + notificationMessage + ", notificationDateTime=" + notificationDateTime + ", receiverId=" + receiverId + ", senderId=" + senderId + ", confirmationStatus=" + confirmationStatus + '}';
    }

}
