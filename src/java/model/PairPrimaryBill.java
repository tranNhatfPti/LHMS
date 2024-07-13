/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ASUS ZenBook
 */
public class PairPrimaryBill {
    private String roomId;
    private String monthYear;

    public PairPrimaryBill() {
    }

    public PairPrimaryBill(String roomId, String monthYear) {
        this.roomId = roomId;
        this.monthYear = monthYear;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }
    
}
