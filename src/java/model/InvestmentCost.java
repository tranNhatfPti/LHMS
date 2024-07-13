/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;


/**
 *
 * @author Nguyễn Công Vinh
 */
public class InvestmentCost {
    private int investmentCostID;
    private double price;
    private TypeOfInvestmentCosts typeOfInvestmentCosts;
    private Date dateTime; 
    private String description;
    private Account account;
    private InformationOfUser informationOfUser;
    private LodgingHouse lodgingHouse;
    private int StatusDelete;
    private int StatusAccept;
    private int TypeAccept;

    public InvestmentCost(int investmentCostID, double price, TypeOfInvestmentCosts typeOfInvestmentCosts, Date dateTime, String description, Account account, LodgingHouse lodgingHouse, int StatusDelete, int StatusAccept, int TypeAccept) {
        this.investmentCostID = investmentCostID;
        this.price = price;
        this.typeOfInvestmentCosts = typeOfInvestmentCosts;
        this.dateTime = dateTime;
        this.description = description;
        this.account = account;
        this.lodgingHouse = lodgingHouse;
        this.StatusDelete = StatusDelete;
        this.StatusAccept = StatusAccept;
        this.TypeAccept = TypeAccept;
    }

    public InvestmentCost(double price, TypeOfInvestmentCosts typeOfInvestmentCosts, Date dateTime, String description, Account account, LodgingHouse lodgingHouse, int StatusDelete, int TypeAccept) {
        this.price = price;
        this.typeOfInvestmentCosts = typeOfInvestmentCosts;
        this.dateTime = dateTime;
        this.description = description;
        this.account = account;
        this.lodgingHouse = lodgingHouse;
        this.StatusDelete = StatusDelete;
        this.TypeAccept = TypeAccept;
    }
    
    

    public InvestmentCost(int investmentCostID, double price, TypeOfInvestmentCosts typeOfInvestmentCosts, Date dateTime, String description, Account account, LodgingHouse lodgingHouse) {
        this.investmentCostID = investmentCostID;
        this.price = price;
        this.typeOfInvestmentCosts = typeOfInvestmentCosts;
        this.dateTime = dateTime;
        this.description = description;
        this.account = account;
        this.lodgingHouse = lodgingHouse;
    }
    
    

    public InvestmentCost(double price, TypeOfInvestmentCosts typeOfInvestmentCosts, Date dateTime, String description, Account account, LodgingHouse lodgingHouse) {
        this.price = price;
        this.typeOfInvestmentCosts = typeOfInvestmentCosts;
        this.dateTime = dateTime;
        this.description = description;
        this.account = account;
        this.lodgingHouse = lodgingHouse;
    }

    public InvestmentCost(int investmentCostID, double price, Date dateTime, String description, LodgingHouse lodgingHouse) {
        this.investmentCostID = investmentCostID;
        this.price = price;
        this.dateTime = dateTime;
        this.description = description;
        this.lodgingHouse = lodgingHouse;
    }

    public InvestmentCost(int investmentCostID, double price, Date dateTime, String description, Account account, LodgingHouse lodgingHouse) {
        this.investmentCostID = investmentCostID;
        this.price = price;
        this.dateTime = dateTime;
        this.description = description;
        this.account = account;
        this.lodgingHouse = lodgingHouse;
    }
    
    public int getInvestmentCostID() {
        return investmentCostID;
    }

    public void setInvestmentCostID(int investmentCostID) {
        this.investmentCostID = investmentCostID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public TypeOfInvestmentCosts getTypeOfInvestmentCosts() {
        return typeOfInvestmentCosts;
    }

    public void setTypeOfInvestmentCosts(TypeOfInvestmentCosts typeOfInvestmentCosts) {
        this.typeOfInvestmentCosts = typeOfInvestmentCosts;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public LodgingHouse getLodgingHouse() {
        return lodgingHouse;
    }

    public void setLodgingHouse(LodgingHouse lodgingHouse) {
        this.lodgingHouse = lodgingHouse;
    }   

    public int getStatusDelete() {
        return StatusDelete;
    }

    public void setStatusDelete(int StatusDelete) {
        this.StatusDelete = StatusDelete;
    }

    public int getStatusAccept() {
        return StatusAccept;
    }

    public void setStatusAccept(int StatusAccept) {
        this.StatusAccept = StatusAccept;
    }

    public int getTypeAccept() {
        return TypeAccept;
    }

    public void setTypeAccept(int TypeAccept) {
        this.TypeAccept = TypeAccept;
    }

    public InformationOfUser getInformationOfUser() {
        return informationOfUser;
    }

    public void setInformationOfUser(InformationOfUser informationOfUser) {
        this.informationOfUser = informationOfUser;
    }
    
    
}
