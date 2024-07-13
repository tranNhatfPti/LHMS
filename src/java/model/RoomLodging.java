/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ASUS
 */
public class RoomLodging {
    private int roomId;
    private double price;
    private int maxOfQuantity;
    private String image,description;
    private int lodgingHouseId,accountID;
    private String lodgingHouseName,province,district,ward,addressDetail,imageLodging;
    private int numberOfRoom;

    public RoomLodging(int roomId, double price, int maxOfQuantity, String image, 
            String description, int lodgingHouseId, int accountID, String lodgingHouseName,
            String province, String district, String ward, String addressDetail,
            String imageLodging, int numberOfRoom) {
        this.roomId = roomId;
        this.price = price;
        this.maxOfQuantity = maxOfQuantity;
        this.image = image;
        this.description = description;
        this.lodgingHouseId = lodgingHouseId;
        this.accountID = accountID;
        this.lodgingHouseName = lodgingHouseName;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.addressDetail = addressDetail;
        this.imageLodging = imageLodging;
        this.numberOfRoom = numberOfRoom;
    }

    public RoomLodging() {
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
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

    public int getLodgingHouseId() {
        return lodgingHouseId;
    }

    public void setLodgingHouseId(int lodgingHouseId) {
        this.lodgingHouseId = lodgingHouseId;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getLodgingHouseName() {
        return lodgingHouseName;
    }

    public void setLodgingHouseName(String lodgingHouseName) {
        this.lodgingHouseName = lodgingHouseName;
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

    public String getImageLodging() {
        return imageLodging;
    }

    public void setImageLodging(String imageLodging) {
        this.imageLodging = imageLodging;
    }

    public int getNumberOfRoom() {
        return numberOfRoom;
    }

    public void setNumberOfRoom(int numberOfRoom) {
        this.numberOfRoom = numberOfRoom;
    }
    
    @Override
    public String toString() {
        return "RoomLoding{" + "roomId=" + roomId + ", price=" + price + ", maxOfQuantity=" + maxOfQuantity + ", image=" + image + ", description=" + description + ", lodgingHouseId=" + lodgingHouseId + ", accountID=" + accountID + ", lodgingHouseName=" + lodgingHouseName + ", province=" + province + ", district=" + district + ", ward=" + ward + ", addressDetail=" + addressDetail + ", imageLodging=" + imageLodging + '}';
    }
    
    
}
