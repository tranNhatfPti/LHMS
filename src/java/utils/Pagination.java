/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class Pagination<object> {

    private int numberOfPage, curentPage = 1, numberPerPage, start, end;

    private List<object> listObject = new ArrayList<>();

    public Pagination() {
    }

    public Pagination(String currentPageRaw, int numberPerPage, List<object> listObjectInput) {

        if (!listObjectInput.isEmpty()) {
            int currentPageTempt = 1;
            if (currentPageRaw != null) {
                currentPageTempt = Integer.parseInt(currentPageRaw);
                if (currentPageTempt < 1) {
                    currentPageTempt = 1;
                }
            }
            this.curentPage = currentPageTempt;
            this.numberPerPage = numberPerPage;
            this.numberOfPage = (listObjectInput.size() % numberPerPage == 0 ? (listObjectInput.size() / numberPerPage) : ((listObjectInput.size() / numberPerPage)) + 1);
            this.start = (curentPage - 1) * numberPerPage;
            this.end = Math.min(curentPage * numberPerPage, listObjectInput.size());
            List<object> listObjectTempt = new ArrayList<>();
            for (int i = start; i < end; i++) {
                listObjectTempt.add(listObjectInput.get(i));
            }
            this.listObject = listObjectTempt;
            switch (this.numberOfPage) {
                case 1:
                    this.start = 1;
                    this.end = 1;
                    break;
                case 2:
                    this.start = 1;
                    this.end = 2;
                    break;
                case 3:
                    this.start = 1;
                    this.end = 3;
                    break;
                default:
                    if (this.curentPage == this.numberOfPage) {
                        this.start = this.numberOfPage - 2;
                        this.end = this.curentPage;
                    } else if (this.curentPage == 1) {
                        this.start = 1;
                        this.end = this.curentPage + 2;
                    } else {
                        this.start = this.curentPage - 1;
                        this.end = this.curentPage + 1;
                    }
                    break;
            }

        }
    }

    public int getNumberOfPage() {
        return numberOfPage;
    }

    public void setNumberOfPage(int numberOfPage) {
        this.numberOfPage = numberOfPage;
    }

    public int getCurentPage() {
        return curentPage;
    }

    public void setCurentPage(int curentPage) {
        this.curentPage = curentPage;
    }

    public int getNumberPerPage() {
        return numberPerPage;
    }

    public void setNumberPerPage(int numberPerPage) {
        this.numberPerPage = numberPerPage;
    }

    public List<object> getListObject() {
        return listObject;
    }

    public void setListObject(List<object> listObject) {
        this.listObject = listObject;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

}
