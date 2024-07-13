package model;

import java.util.Date;

public class Account {

    private int accountID;
    private String email;
    private String username, password;
    private int roleId;
    private String typeOfLogin;
 
    private String fullName;
    private String province;
    private String district;
    
     private String ward;
    private String addressDetail;
    private boolean status;
        private String avatar;
    private String cic;
    private Date dob;
    private String phoneNumber;
    private int gender;
    public Account() {
    }
    public Account(int accountID, String email, String fullName, String province, String district, String ward, String addressDetail, String avatar, Date dob, String phoneNumber, int gender) {
        this.accountID = accountID;
        this.email = email;
        this.fullName = fullName;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.addressDetail = addressDetail;
        this.avatar = avatar;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }
    public Account(int accountID, String email, String username, String password, int roleId, String typeOfLogin) {
        this.accountID = accountID;
        this.email = email;
        this.username = username;
        this.password = password;
        this.roleId = roleId;
        this.typeOfLogin = typeOfLogin;
    }

    public Account(int accountID, String fullName, String province, String district, String ward, String addressDetail, String email, String phoneNumber, int gender, String username, String avatar, String cic, Date dob) {
        this.accountID = accountID;
        this.email = email;
        this.username = username;
    }

    public Account(String email, String username, String password, int roleId, String typeOfLogin) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.roleId = roleId;
        this.typeOfLogin = typeOfLogin;
    }
    
    public Account(String email, int roleId, String typeOfLogin) {
        this.email = email;
        this.roleId = roleId;
        this.typeOfLogin = typeOfLogin;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getTypeOfLogin() {
        return typeOfLogin;
    }

    public void setTypeOfLogin(String typeOfLogin) {
        this.typeOfLogin = typeOfLogin;
    }

    @Override
    public String toString() {
        return super.toString(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

}
