/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author admin
 */
public class Room {
    private String roomId;
    private double price;
    private int maxOfQuantity;
    private int Status;
    private String image;
    private String description;
    private LodgingHouse lodgingHouse;
    private int StatusDelete;
    private String RoomName;

    public Room(String roomId, double price, int maxOfQuantity, int Status, String image, String description, LodgingHouse lodgingHouse, int StatusDelete, String RoomName) {
        this.roomId = roomId;
        this.price = price;
        this.maxOfQuantity = maxOfQuantity;
        this.Status = Status;
        this.image = image;
        this.description = description;
        this.lodgingHouse = lodgingHouse;
        this.StatusDelete = StatusDelete;
        this.RoomName = RoomName;
    }

    public Room(String string, double aDouble, int aInt, int aInt0, String string0, String string1, LodgingHouse lodgingHouseById, String string2) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
    
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getMaxOfQuantity() {
        return maxOfQuantity;
    }

    public void setMaxOfQuantity(int maxOfQuantity) {
        this.maxOfQuantity = maxOfQuantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LodgingHouse getLodgingHouse() {
        return lodgingHouse;
    }

    public void setLodgingHouse(LodgingHouse lodgingHouse) {
        this.lodgingHouse = lodgingHouse;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public int getStatusDelete() {
        return StatusDelete;
    }

    public void setStatusDelete(int StatusDelete) {
        this.StatusDelete = StatusDelete;
    }

    public String getRoomName() {
        return RoomName;
    }

    public void setRoomName(String RoomName) {
        this.RoomName = RoomName;
    }
     
}
