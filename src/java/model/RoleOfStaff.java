/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class RoleOfStaff {
    private int roleStaffID;
    private String roleStaffName;
    private int statusActive;

    public RoleOfStaff(int roleStaffID, String roleStaffName, int statusActive) {
        this.roleStaffID = roleStaffID;
        this.roleStaffName = roleStaffName;
        this.statusActive = statusActive;
    }

    public RoleOfStaff() {
    }
    
    public int getRoleStaffID() {
        return roleStaffID;
    }

    public void setRoleStaffID(int roleStaffID) {
        this.roleStaffID = roleStaffID;
    }

    public String getRoleStaffName() {
        return roleStaffName;
    }

    public void setRoleStaffName(String roleStaffName) {
        this.roleStaffName = roleStaffName;
    }

    public int getStatusActive() {
        return statusActive;
    }

    public void setStatusActive(int statusActive) {
        this.statusActive = statusActive;
    }
    
    @Override
    public String toString() {
        return "RoleOfStaff{" + "roleStaffID=" + roleStaffID +
                ", roleStaffName=" + roleStaffName + '}';
    }
    
    
}
