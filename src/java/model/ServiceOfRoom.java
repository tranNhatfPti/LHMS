
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class ServiceOfRoom {

    private ServiceOfLodgingHouse serviceOfLodgingHouse;
    private Room room;
    private double price;
    private int statusAccept;
    private int statusDelete;
    

    public ServiceOfRoom() {
    }

    public ServiceOfRoom(ServiceOfLodgingHouse serviceOfLodgingHouse, Room room, double price, int statusAccept, int statusDelete ) {
        this.serviceOfLodgingHouse = serviceOfLodgingHouse;
        this.room = room;
        this.price = price;
        this.statusAccept = statusAccept;
        this.statusDelete = statusDelete;
        
    }
    public ServiceOfRoom(ServiceOfLodgingHouse serviceOfLodgingHouse, Room room) {
        this.serviceOfLodgingHouse = serviceOfLodgingHouse;
        this.room = room;
    }

    public ServiceOfRoom(int aInt, int aInt0, double aDouble) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStatusAccept() {
        return statusAccept;
    }

    public void setStatusAccept(int statusAccept) {
        this.statusAccept = statusAccept;
    }

    public int getStatusDelete() {
        return statusDelete;
    }

    public void setStatusDelete(int statusDelete) {
        this.statusDelete = statusDelete;
    }
    public ServiceOfLodgingHouse getServiceOfLodgingHouse() {
        return serviceOfLodgingHouse;
    }

    public void setServiceOfLodgingHouse(ServiceOfLodgingHouse serviceOfLodgingHouse) {
        this.serviceOfLodgingHouse = serviceOfLodgingHouse;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getServiceId() {
        return getServiceOfLodgingHouse().getServiceId();
    }

}

