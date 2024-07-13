/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Nguyễn Công Vinh
 */
public class TypeOfInvestmentCosts {
    private int Id;
    private String Name;
    private int StatusDelete;
    public TypeOfInvestmentCosts() {
    }

    
    public TypeOfInvestmentCosts(String Name) {
        this.Name = Name;
    }

    public TypeOfInvestmentCosts(int Id, String Name, int StatusDelete) {
        this.Id = Id;
        this.Name = Name;
        this.StatusDelete = StatusDelete;
    }
    
    public TypeOfInvestmentCosts(int Id, String Name) {
        this.Id = Id;
        this.Name = Name;
    }
  
    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getStatusDelete() {
        return StatusDelete;
    }

    public void setStatusDelete(int StatusDelete) {
        this.StatusDelete = StatusDelete;
    }
    
    
    
}
