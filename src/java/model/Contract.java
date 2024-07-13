/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;
import org.eclipse.jdt.internal.compiler.parser.Parser;

/**
 *
 * @author admin
 */
public class Contract {
    private Room room;
    private Account tenantId;
    private Account Manager;
    private Date dateFrom;
    private Date dateTo;
    private int statusDelete;
    private int statusAccept;
    private long contractDeposit;
    private int typeOfAccept;
    private int roleCreateContract;
    public Contract(Room room, Account tenantId, Account Manager, Date dateFrom, Date dateTo, int statusDelete, int statusAccept, long contractDeposit, int typeOfAccept, int roleCreateContract) {
        this.room = room;
        this.tenantId = tenantId;
        this.Manager = Manager;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.statusDelete = statusDelete;
        this.statusAccept = statusAccept;
        this.contractDeposit = contractDeposit;
        this.typeOfAccept = typeOfAccept;
        this.roleCreateContract = roleCreateContract;
    }

    public Contract(Room room, Account tenant, Account manager, java.sql.Date date, java.sql.Date date0, int aInt, int aInt0) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Contract() {
    }

    public Room getRoom() {
        return room;
    }

    public long getContractDeposit() {
        return contractDeposit;
    }

    public void setContractDeposit(long contractDeposit) {
        this.contractDeposit = contractDeposit;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Account getTenantId() {
        return tenantId;
    }

    public void setTenantId(Account tenantId) {
        this.tenantId = tenantId;
    }

    public Account getManager() {
        return Manager;
    }

    public void setManager(Account Manager) {
        this.Manager = Manager;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public int getStatusDelete() {
        return statusDelete;
    }

    public void setStatusDelete(int statusDelete) {
        this.statusDelete = statusDelete;
    }

    public int getStatusAccept() {
        return statusAccept;
    }

    public void setStatusAccept(int statusAccept) {
        this.statusAccept = statusAccept;
    }

  
  
    public int getTypeOfAccept() {
        return typeOfAccept;
    }

    public void setTypeOfAccept(int typeOfAccept) {
        this.typeOfAccept = typeOfAccept;
    }

    public int getRoleCreateContract() {
        return roleCreateContract;
    }

    public void setRoleCreateContract(int roleCreateContract) {
        this.roleCreateContract = roleCreateContract;
    }

    @Override
    public String toString() {
        return "Contract{" + "room=" + room + ", tenantId=" + tenantId + ", Manager=" + Manager + ", dateFrom=" + dateFrom + ", dateTo=" + dateTo + ", statusDelete=" + statusDelete + ", statusAccept=" + statusAccept + ", contractDeposit=" + contractDeposit + ", typeOfAccept=" + typeOfAccept + ", roleCreateContract=" + roleCreateContract + '}';
    }
    
    
}
