/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author ASUS
 */
public class Feedback {
    private int feedbackId;
    private Date monthYear;
    private int accountId;
    private int lodgingId;
    private int star;
    private int cleaningService;
    private int electric;
    private int water;
    private int internet;
    private String otherResponse;
    private int feedbackStatus;
    private int servicePrice;

    public Feedback() {
    }

    public Feedback(int feedbackId, Date monthYear, int accountId, int lodgingId, int star, int cleaningService, int electric, int water, int internet, String otherResponse, int feedbackStatus, int servicePrice) {
        this.feedbackId = feedbackId;
        this.monthYear = monthYear;
        this.accountId = accountId;
        this.lodgingId = lodgingId;
        this.star = star;
        this.cleaningService = cleaningService;
        this.electric = electric;
        this.water = water;
        this.internet = internet;
        this.otherResponse = otherResponse;
        this.feedbackStatus = feedbackStatus;
        this.servicePrice = servicePrice;
    }

    public Feedback(Date monthYear, int accountId, int lodgingId, int star, int feedbackStatus) {
        this.monthYear = monthYear;
        this.accountId = accountId;
        this.lodgingId = lodgingId;
        this.star = star;
        this.feedbackStatus = feedbackStatus;
    }

    public Feedback(Date monthYear, int accountId, int lodgingId, int feedbackStatus) {
        this.monthYear = monthYear;
        this.accountId = accountId;
        this.lodgingId = lodgingId;
        this.feedbackStatus = feedbackStatus;
    }
    
    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Date getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(Date monthYear) {
        this.monthYear = monthYear;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getLodgingId() {
        return lodgingId;
    }

    public void setLodgingId(int lodgingId) {
        this.lodgingId = lodgingId;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getCleaningService() {
        return cleaningService;
    }

    public void setCleaningService(int cleaningService) {
        this.cleaningService = cleaningService;
    }

    public int getElectric() {
        return electric;
    }

    public void setElectric(int electric) {
        this.electric = electric;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public int getInternet() {
        return internet;
    }

    public void setInternet(int internet) {
        this.internet = internet;
    }

    public String getOtherResponse() {
        return otherResponse;
    }

    public void setOtherResponse(String otherResponse) {
        this.otherResponse = otherResponse;
    }

    public int getFeedbackStatus() {
        return feedbackStatus;
    }

    public void setFeedbackStatus(int feedbackStatus) {
        this.feedbackStatus = feedbackStatus;
    }

    public int getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(int servicePrice) {
        this.servicePrice = servicePrice;
    }

    @Override
    public String toString() {
        return "Feedback{" + "feedbackId=" + feedbackId + ", monthYear=" + monthYear + ", accountId=" + accountId + ", lodgingId=" + lodgingId + ", star=" + star + ", cleaningService=" + cleaningService + ", electric=" + electric + ", water=" + water + ", internet=" + internet + ", otherResponse=" + otherResponse + ", feedbackStatus=" + feedbackStatus + ", servicePrice=" + servicePrice + '}';
    }
    
    
}
