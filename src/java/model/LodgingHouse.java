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
public class LodgingHouse {

    private int lodgingHouseId;
    private String nameLodgingHouse;
    private String province;
    private String district;
    private String ward;
    private String addressDetail;
    private String img;
    private int numberOfRoom;
    private int manageId;
    private boolean status;
    private Date order_date;
    private boolean statusDelete;

    public LodgingHouse(int lodgingHouseId, String nameLodgingHouse, String province, String district, String ward, String addressDetail, String img, int numberOfRoom, boolean status, Date order_date, boolean statusDelete) {
        this.lodgingHouseId = lodgingHouseId;
        this.nameLodgingHouse = nameLodgingHouse;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.addressDetail = addressDetail;
        this.img = img;
        this.numberOfRoom = numberOfRoom;
        this.status = status;
        this.order_date = order_date;
        this.statusDelete = statusDelete;
    }

    public LodgingHouse(String nameLodgingHouse, String province, String district, String ward, String addressDetail, String img, int numberOfRoom, boolean status, Date order_date, boolean statusDelete) {

        this.nameLodgingHouse = nameLodgingHouse;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.addressDetail = addressDetail;
        this.img = img;
        this.numberOfRoom = numberOfRoom;
        this.status = status;
        this.order_date = order_date;
        this.statusDelete = statusDelete;
    }

    public LodgingHouse(String nameLodgingHouse, String province, String district, String ward, String addressDetail, String img, int numberOfRoom, boolean status, Date order_date) {

        this.nameLodgingHouse = nameLodgingHouse;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.addressDetail = addressDetail;
        this.img = img;
        this.numberOfRoom = numberOfRoom;
        this.status = status;
        this.order_date = order_date;
    }

    public LodgingHouse(int lodgingHouseID, String nameLodgingHouse, String province, String district, String ward, String addressDetail, String img, boolean status, Date order_date) {
        this.lodgingHouseId = lodgingHouseID;
        this.nameLodgingHouse = nameLodgingHouse;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.addressDetail = addressDetail;
        this.img = img;
        this.status = status;
        this.order_date = order_date;
    }

    public LodgingHouse(int lodgingHouseID, String nameLodgingHouse, String province, String district, String ward, String addressDetail, String img, int numberOfRoom, int manageId, boolean status, Date order_date) {
        this.lodgingHouseId = lodgingHouseID;
        this.nameLodgingHouse = nameLodgingHouse;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.addressDetail = addressDetail;
        this.img = img;
        this.numberOfRoom = numberOfRoom;
        this.manageId = manageId;
        this.status = status;
        this.order_date = order_date;
    }

    public LodgingHouse(int lodgingHouseId, String nameLodgingHouse, String province, String district, String ward, String addressDetail, Date order_date) {
        this.lodgingHouseId = lodgingHouseId;
        this.nameLodgingHouse = nameLodgingHouse;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.addressDetail = addressDetail;
        this.order_date = order_date;
    }

    public LodgingHouse(int lodgingHouseId, String nameLodgingHouse, String province, String district, String ward, String addressDetail, String img, boolean status) {
        this.lodgingHouseId = lodgingHouseId;
        this.nameLodgingHouse = nameLodgingHouse;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.addressDetail = addressDetail;
        this.img = img;
        this.status = status;
    }

    public LodgingHouse(String nameLodgingHouse, String province, String district, String ward, String addressDetail, String img, boolean status, Date order_date) {
        this.nameLodgingHouse = nameLodgingHouse;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.addressDetail = addressDetail;
//        this.manageID = manageID;
        this.img = img;
        this.status = status;
        this.order_date = order_date;
    }

    public boolean isStatusDelete() {
        return statusDelete;
    }

    public void setStatusDelete(boolean statusDelete) {
        this.statusDelete = statusDelete;
    }

    public int getNumberOfRoom() {
        return numberOfRoom;
    }

    public void setNumberOfRoom(int numberOfRoom) {
        this.numberOfRoom = numberOfRoom;
    }

    public int getManageId() {
        return manageId;
    }

    public void setManageId(int manageId) {
        this.manageId = manageId;
    }

    public int getLodgingHouseId() {
        return lodgingHouseId;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setLodgingHouseId(int lodgingHouseId) {
        this.lodgingHouseId = lodgingHouseId;
    }

    public String getNameLodgingHouse() {
        return nameLodgingHouse;
    }

    public void setNameLodgingHouse(String nameLodgingHouse) {
        this.nameLodgingHouse = nameLodgingHouse;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public LodgingHouse() {
    }

    @Override
    public String toString() {
        return "LodgingHouse{" + "lodgingHouseId=" + lodgingHouseId + ", nameLodgingHouse=" + nameLodgingHouse + ", province=" + province + ", district=" + district + ", ward=" + ward + ", img=" + img + ", order_date=" + order_date + '}';
    }

}
