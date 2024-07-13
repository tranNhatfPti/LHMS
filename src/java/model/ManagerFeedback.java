/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ASUS
 */
public class ManagerFeedback {
    private String roomName;
    private String name;
    private String monthYear;
    private int statusFeedback;
    private int tenantId;

    public ManagerFeedback(String roomName, String name, String monthYear, int statusFeedback,int tenantId) {
        this.roomName = roomName;
        this.name = name;
        this.monthYear = monthYear;
        this.statusFeedback = statusFeedback;
        this.tenantId = tenantId;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }
    
    public ManagerFeedback() {
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    public int getStatusFeedback() {
        return statusFeedback;
    }

    public void setStatusFeedback(int statusFeedback) {
        this.statusFeedback = statusFeedback;
    }
    
}
