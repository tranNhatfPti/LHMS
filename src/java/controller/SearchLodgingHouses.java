/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.LodgingHousesDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.LodgingHouse;
import utils.Pagination;

/**
 *
 * @author admin
 */
public class SearchLodgingHouses extends HttpServlet {

    String dateMin, dateMax;
    String keyWord;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SearchLodgingHouses</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SearchLodgingHouses at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String infoSearch = request.getParameter("keyword");
        System.out.println(infoSearch);
        if (infoSearch == null) {
            infoSearch = keyWord;
        } else {
            keyWord = infoSearch;
        }
        LodgingHousesDAO dao = new LodgingHousesDAO();
        List<LodgingHouse> listSearch = dao.searchLodgingHouses(infoSearch);
        PrintWriter out = response.getWriter();
        String curentPageRaw = request.getParameter("curentPage");

        int numberPerPage = 3;
        Pagination<LodgingHouse> pagination = new Pagination<>(curentPageRaw, numberPerPage, listSearch);

        for (LodgingHouse lodgingHouse : pagination.getListObject()) {
            if (lodgingHouse.isStatusDelete()) {
                out.println("<div class=\"col-md-4 mb-4\">");
                out.println("<div class=\"card card-house\">");
                out.println("<div class=\"card-body\">");
                out.println("<a href=\"somePage?LodgingHouseID=" + lodgingHouse.getLodgingHouseId() + "\">");
                out.println("<i class=\"fas fa-home house-icon\"></i>");
                out.println("</a>");
                out.println("<h5 class=\"card-title mt-2\">" + lodgingHouse.getNameLodgingHouse() + "</h5>");
                out.println("<p class=\"card-text\">Địa chỉ: " + lodgingHouse.getProvince() + "-" + lodgingHouse.getDistrict() + "-" + lodgingHouse.getWard() + "</p>");
                out.println("<a class=\"btn btn-warning btn-sm\" href=\"update-info-lodging-house?lodgingHouseID=" + lodgingHouse.getLodgingHouseId() + "\">Sửa</a>");
                out.println("<form action=\"delete-lodging-house\" method=\"post\" style=\"display:inline;\">");
                out.println("<input type=\"hidden\" name=\"lodgingHouseId\" value=\"" + lodgingHouse.getLodgingHouseId() + "\">");
                out.println("<button class=\"btn btn-primary btn-sm trash full-width-btn\" type=\"submit\" title=\"Xóa\">");
                out.println("<i class=\"fas fa-trash-alt\"></i>");
                out.println("</button>");
                out.println("</form>");
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
            }
        }
        System.out.println(pagination.getNumberOfPage());
        out.println("<div class=\"pagination_style shop_page\">");
        out.println("<div class=\"page_number\">");
        out.println("<span style=\"font-size: 20px\">Pages: </span>");

        if (pagination.getCurentPage() > 1) {
            out.println("<a class=\"pagination-btn\" data-page=\"" + (pagination.getCurentPage() - 1) + "\" value=\"" + (pagination.getCurentPage() - 1) + "\" style=\"padding: 5px 10px; margin-right: 10px; font-size: 20px;\">Previous</a>");
        }

        for (int num = pagination.getStart(); num <= pagination.getEnd(); num++) {
            if (pagination.getCurentPage() == num) {
                out.println("<a class=\"pagination-btn active\" data-page=\"" + num + "\" value=\"" + num + "\" style=\"background-color: green; color: white; border: 1px solid greenyellow; width: 40px; height: 40px; display: inline-flex; justify-content: center; align-items: center; margin-right: 10px; font-size: 20px;\">" + num + "</a>");
            } else {
                out.println("<a class=\"pagination-btn\" data-page=\"" + num + "\" value=\"" + num + "\" style=\"padding: 5px 10px; margin-right: 10px; font-size: 20px;\">" + num + "</a>");
            }
        }

        if (pagination.getCurentPage() < pagination.getNumberOfPage()) {
            out.println("<a class=\"pagination-btn\" data-page=\"" + (pagination.getCurentPage() + 1) + "\" value=\"" + (pagination.getCurentPage() + 1) + "\" style=\"padding: 5px 10px; margin-right: 10px; font-size: 20px;\">Next</a>");
        }
        out.println("</div>");
        out.println("</div>");

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String dateMin_raw = request.getParameter("dateMin");
        System.out.println(dateMin);
        String dateMax_raw = request.getParameter("dateMax");
        if (dateMin_raw == null && dateMax_raw == null) {
            dateMin_raw = dateMin;
            dateMax_raw=dateMax;
            
        }
        else{
           dateMin=dateMin_raw ; 
           dateMax=dateMax_raw;
        }

        LodgingHousesDAO dao = new LodgingHousesDAO();
        List<LodgingHouse> listSearch = dao.searchLodgingHousesByDate(dateMin_raw, dateMax_raw);
        PrintWriter out = response.getWriter();
        String curentPageRaw = request.getParameter("curentPage");
        int numberPerPage = 3;
        Pagination<LodgingHouse> pagination = new Pagination<>(curentPageRaw, numberPerPage, listSearch);

        for (LodgingHouse lodgingHouse : pagination.getListObject()) {
            if (lodgingHouse.isStatusDelete()) {
                out.println("<div class=\"col-md-4 mb-4\">");
                out.println("<div class=\"card card-house\">");
                out.println("<div class=\"card-body\">");
                out.println("<a href=\"somePage?LodgingHouseID=" + lodgingHouse.getLodgingHouseId() + "\">");
                out.println("<i class=\"fas fa-home house-icon\"></i>");
                out.println("</a>");
                out.println("<h5 class=\"card-title mt-2\">" + lodgingHouse.getNameLodgingHouse() + "</h5>");
                out.println("<p class=\"card-text\">Địa chỉ: " + lodgingHouse.getProvince() + "-" + lodgingHouse.getDistrict() + "-" + lodgingHouse.getWard() + "</p>");
                out.println("<a class=\"btn btn-warning btn-sm\" href=\"update-info-lodging-house?lodgingHouseID=" + lodgingHouse.getLodgingHouseId() + "\">Sửa</a>");
                out.println("<form action=\"delete-lodging-house\" method=\"post\" style=\"display:inline;\">");
                out.println("<input type=\"hidden\" name=\"lodgingHouseId\" value=\"" + lodgingHouse.getLodgingHouseId() + "\">");
                out.println("<button class=\"btn btn-primary btn-sm trash full-width-btn\" type=\"submit\" title=\"Xóa\">");
                out.println("<i class=\"fas fa-trash-alt\"></i>");
                out.println("</button>");
                out.println("</form>");
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
            }
        }
        System.out.println(pagination.getNumberOfPage());
        out.println("<div class=\"pagination_style shop_page\">");
        out.println("<div class=\"page_number\">");
        out.println("<span style=\"font-size: 20px\">Pages: </span>");

        if (pagination.getCurentPage() > 1) {
            out.println("<a class=\"pagination-btn\" data-page=\"" + (pagination.getCurentPage() - 1) + "\" value=\"" + (pagination.getCurentPage() - 1) + "\" style=\"padding: 5px 10px; margin-right: 10px; font-size: 20px;\">Previous</a>");
        }

        for (int num = pagination.getStart(); num <= pagination.getEnd(); num++) {
            if (pagination.getCurentPage() == num) {
                out.println("<a class=\"pagination-btn active\" data-page=\"" + num + "\" value=\"" + num + "\" style=\"background-color: green; color: white; border: 1px solid greenyellow; width: 40px; height: 40px; display: inline-flex; justify-content: center; align-items: center; margin-right: 10px; font-size: 20px;\">" + num + "</a>");
            } else {
                out.println("<a class=\"pagination-btn\" data-page=\"" + num + "\" value=\"" + num + "\" style=\"padding: 5px 10px; margin-right: 10px; font-size: 20px;\">" + num + "</a>");
            }
        }

        if (pagination.getCurentPage() < pagination.getNumberOfPage()) {
            out.println("<a class=\"pagination-btn\" data-page=\"" + (pagination.getCurentPage() + 1) + "\" value=\"" + (pagination.getCurentPage() + 1) + "\" style=\"padding: 5px 10px; margin-right: 10px; font-size: 20px;\">Next</a>");
        }
        out.println("</div>");
        out.println("</div>");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
