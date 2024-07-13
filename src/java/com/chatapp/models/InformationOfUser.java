package com.chatapp.models;

import model.*;
import java.util.Date;

/**
 *
 * @author ASUS ZenBook
 */
public class InformationOfUser {
    private int accountID;
    private String fullName, province, district, ward, addressDetail;
    private boolean status;
    private String avatar, cic;
    private Date dob;
    private String phoneNumber;
    private int gender;  

    public InformationOfUser() {
    }

    public InformationOfUser(int accountID, boolean status) {
        this.accountID = accountID;
        this.status = status;
    }

    public InformationOfUser(int accountID, String fullName, String province, String district, String ward, String addressDetail, boolean status, String avatar, String cic, Date dob, String phoneNumber, int gender) {
        this.accountID = accountID;
        this.fullName = fullName;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.addressDetail = addressDetail;
        this.status = status;
        this.avatar = avatar;
        this.cic = cic;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }   
    
    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCic() {
        return cic;
    }

    public void setCic(String cic) {
        this.cic = cic;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }
        
}
