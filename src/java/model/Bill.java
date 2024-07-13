/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Objects;

/**
 *
 * @author ASUS ZenBook
 */
public class Bill {
    private String roomId;
    private String monthYear;
    private double waterOld, waterNew, priceWater, electronicOld, 
            electronicNew, priceElectronic, missing, paid;
    private int status;
    private double priceOtherServices;

    public Bill() {
    }

    public Bill(String roomId, String monthYear, double waterOld, double waterNew, double priceWater, double electronicOld, double electronicNew, double priceElectronic, double missing, double paid, int status, double priceOtherServices) {
        this.roomId = roomId;
        this.monthYear = monthYear;
        this.waterOld = waterOld;
        this.waterNew = waterNew;
        this.priceWater = priceWater;
        this.electronicOld = electronicOld;
        this.electronicNew = electronicNew;
        this.priceElectronic = priceElectronic;
        this.priceOtherServices = priceOtherServices;
        this.paid = paid;
        this.missing = missing;
        this.status = status;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    public double getWaterOld() {
        return waterOld;
    }

    public void setWaterOld(double waterOld) {
        this.waterOld = waterOld;
    }

    public double getWaterNew() {
        return waterNew;
    }

    public void setWaterNew(double waterNew) {
        this.waterNew = waterNew;
    }

    public double getPriceWater() {
        return priceWater;
    }

    public void setPriceWater(double priceWater) {
        this.priceWater = priceWater;
    }

    public double getElectronicOld() {
        return electronicOld;
    }

    public void setElectronicOld(double electronicOld) {
        this.electronicOld = electronicOld;
    }

    public double getElectronicNew() {
        return electronicNew;
    }

    public void setElectronicNew(double electronicNew) {
        this.electronicNew = electronicNew;
    }

    public double getPriceElectronic() {
        return priceElectronic;
    }

    public void setPriceElectronic(double priceElectronic) {
        this.priceElectronic = priceElectronic;
    }

    public double getPriceOtherServices() {
        return priceOtherServices;
    }

    public void setPriceOtherServices(double priceOtherServices) {
        this.priceOtherServices = priceOtherServices;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public double getMissing() {
        return missing;
    }

    public void setMissing(double missing) {
        this.missing = missing;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Bill other = (Bill) obj;
        if (Double.doubleToLongBits(this.waterOld) != Double.doubleToLongBits(other.waterOld)) {
            return false;
        }
        if (Double.doubleToLongBits(this.waterNew) != Double.doubleToLongBits(other.waterNew)) {
            return false;
        }
        if (Double.doubleToLongBits(this.priceWater) != Double.doubleToLongBits(other.priceWater)) {
            return false;
        }
        if (Double.doubleToLongBits(this.electronicOld) != Double.doubleToLongBits(other.electronicOld)) {
            return false;
        }
        if (Double.doubleToLongBits(this.electronicNew) != Double.doubleToLongBits(other.electronicNew)) {
            return false;
        }
        if (Double.doubleToLongBits(this.priceElectronic) != Double.doubleToLongBits(other.priceElectronic)) {
            return false;
        }
        if (Double.doubleToLongBits(this.missing) != Double.doubleToLongBits(other.missing)) {
            return false;
        }
        if (Double.doubleToLongBits(this.paid) != Double.doubleToLongBits(other.paid)) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (Double.doubleToLongBits(this.priceOtherServices) != Double.doubleToLongBits(other.priceOtherServices)) {
            return false;
        }
        if (!Objects.equals(this.roomId, other.roomId)) {
            return false;
        }
        return Objects.equals(this.monthYear, other.monthYear);
    }

    @Override
    public String toString() {
        return roomId + " " + monthYear + " " + waterOld + " " + waterNew + " " + priceWater + " " +
                electronicOld + " " + electronicNew + " " + priceElectronic + " " + missing + " " + 
                paid + " " + status + " " + priceOtherServices;
    }
    
}
