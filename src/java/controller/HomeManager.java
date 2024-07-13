/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.LodgingHousesDAO;
import dal.RoomDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.LodgingHouse;
import model.Room;
import utils.Pagination;

/**
 *
 * @author admin
 */
public class HomeManager extends HttpServlet {

    String lodgingIDRepo;
    String currentPageRepo;

    String roomStatusRepo;
    String minPriceRepo;
    String maxPriceRepo;

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

        RoomDAO roomDAO = new RoomDAO();
        HttpSession session = request.getSession();
        String lodgingID = request.getParameter("LodgingHouseID");
        if (lodgingID != null) {
            lodgingIDRepo = lodgingID;
        }
        int numberPerPage = 3;
        String curentPageRaw = request.getParameter("curentPage");
        if (curentPageRaw != null) {
            currentPageRepo = curentPageRaw;
        }
        String message = request.getParameter("message");
        System.out.println("Controler: " + message);
        if (message != null) {
            request.setAttribute("alertMessage", message);
        }
        session.setAttribute("lodgingID", lodgingIDRepo);
        List<Room> listRoom = roomDAO.getRoomsByLodgingHouseId(Integer.parseInt(lodgingIDRepo));
        LodgingHousesDAO ld = new LodgingHousesDAO();
        LodgingHouse l = ld.getLodgingHouseById(Integer.parseInt(lodgingID));
        request.setAttribute("lodging", l);
        System.out.println("Home-manager : " + l);
        Pagination<Room> pagination = new Pagination<>(currentPageRepo, numberPerPage, listRoom);
        request.setAttribute("paginationRoom", pagination);
        request.getRequestDispatcher("view/manager/Home-manager.jsp").forward(request, response);

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

        String message = request.getParameter("message");
        System.out.println("Controler: " + message);
        if (message != null) {
            request.setAttribute("alertMessage", message);
        }

        RoomDAO roomDAO = new RoomDAO();
        HttpSession session = request.getSession();
        String lodgingID = request.getParameter("LodgingHouseID");

        if (lodgingID != null) {
            lodgingIDRepo = lodgingID;
        }

        int numberPerPage = 3;
        String curentPageRaw = request.getParameter("curentPage");
        if (curentPageRaw != null) {
            currentPageRepo = curentPageRaw;
        }
        System.out.println("Page:  " + currentPageRepo);

        session.setAttribute("lodgingID", lodgingIDRepo);
        List<Room> listRoom = roomDAO.getRoomsByLodgingHouseId(Integer.parseInt(lodgingIDRepo));

        Pagination<Room> pagination = new Pagination<>(currentPageRepo, numberPerPage, listRoom);
        if (pagination.getListObject().size() == 0 && currentPageRepo != null) {
            currentPageRepo = (Integer.parseInt(currentPageRepo) - 1) + "";
            pagination = new Pagination<>(currentPageRepo, numberPerPage, listRoom);
        }

        request.setAttribute("paginationRoom", pagination);
        request.getRequestDispatcher("view/manager/Home-manager.jsp").forward(request, response);
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
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        RoomDAO roomDAO = new RoomDAO();
        String keyWord = request.getParameter("keyword");
        String roomStatus = request.getParameter("roomStatus");
        String minPrice = request.getParameter("minPrice");
        String maxPrice = request.getParameter("maxPrice");

        if (roomStatus != null) {
            roomStatusRepo = roomStatus;
        }
        if (minPrice != null) {
            minPriceRepo = minPrice;
        }
        if (maxPrice != null) {
            maxPriceRepo = maxPrice;
        }

        System.out.println("Key:" + keyWord);
        System.out.println("status:" + roomStatusRepo);
        System.out.println("minPrice:" + minPriceRepo);
        System.out.println("MaxPrice:" + maxPriceRepo);

        int numberPerPage = 3;
        String curentPageRaw = request.getParameter("curentPage");
        if (curentPageRaw != null) {
            currentPageRepo = curentPageRaw;
        }
        List<Room> listRoom = new ArrayList<>();
        if (keyWord != null) {
            listRoom = roomDAO.getRoomsByKeyword(Integer.parseInt(lodgingIDRepo), keyWord);
            System.out.println("vô");
        } else {
            System.out.println("alooo");
            listRoom = roomDAO.getRoomsBySelect(Integer.parseInt(lodgingIDRepo), minPriceRepo, maxPriceRepo, roomStatusRepo);

        }
        System.out.println(listRoom.size());

        Pagination<Room> pagination = new Pagination<>(currentPageRepo, numberPerPage, listRoom);

        PrintWriter out = response.getWriter();

        out.println("<div class=\"row\">");
        for (Room o : pagination.getListObject()) {
            out.println("<div class=\"col-md-4\">");
            out.println("    <div class=\"room-card\">");
            out.println("        <div class=\"row\">");
            out.println("            <div class=\"col-sm-12\">");
            out.println("                <a href=\"room-detail-servlet?id=" + o.getRoomId() + "\"><img src=\"" + o.getImage() + "\" alt=\"alt\"/></a>");
            out.println("            </div>");
            out.println("            <div class=\"col-sm-12\">");
            out.println("                <table style=\"border: none\">");
            out.println("                    <tr>");
            out.println("                        <td>Giá thuê:</td>");
            out.println("                        <td>" + o.getPrice() + " VND</td>");
            out.println("                    </tr>");
            out.println("                    <tr>");
            out.println("                        <td>Trạng thái:</td>");
            if (o.getStatus() == 0) {
                out.println("                    <td>Còn</td>");
            } else if (o.getStatus() == 1) {
                out.println("                    <td>Đang chờ</td>");
            } else if (o.getStatus() == 2) {
                out.println("                    <td>Hết</td>");
            }
            out.println("                    </tr>");
            out.println("                    <tr>");
            out.println("                        <td>Số lượng tối đa:</td>");
            out.println("                        <td>" + o.getMaxOfQuantity() + " người/Phòng</td>");
            out.println("                    </tr>");
            if (account.getRoleId() == 1) {
                out.println("                    <tr>");
                out.println("                        <td colspan=\"2\">");
                if (o.getStatus() == 0) {
                    out.println("                            <a href=\"update-room-servlet?id=" + o.getRoomId() + "\" class=\"btn btn-edit btn-sm\">Chỉnh sửa</a>");
                    out.println("                            <button class=\"btn btn-primary btn-sm edit\" type=\"button\" onclick=\"deleteRowById('" + o.getRoomId() + "')\">Xóa</button>");
                } else {
                    out.println("                            <a class=\"btn btn-edit btn-sm disabled\" onclick=\"return false;\" style=\"pointer-events: none;\">Chỉnh sửa</a>");
                    out.println("                            <button class=\"btn btn-primary btn-sm edit\" type=\"button\" disabled>Xóa</button>");
                }
                out.println("                        </td>");
                out.println("                    </tr>");
            }
            out.println("                </table>");
            out.println("            </div>");
            out.println("        </div>");
            out.println("    </div>");
            out.println("</div>");
        }
        out.println("</div>");

        out.print("<div class=\"pagination\">");
        if (pagination.getCurentPage() > 1) {
            out.print("<a class=\"pagination-btn\" data-page=\"" + (pagination.getCurentPage() - 1) + "\" value=\"" + (pagination.getCurentPage() - 1) + "\">&laquo;</a>");
        }
        for (int num = pagination.getStart(); num <= pagination.getEnd(); num++) {
            if (num != 0) {
                if (num == pagination.getCurentPage()) {
                    out.print("<a class=\"pagination-btn active\" data-page=\"" + num + "\" value=\"" + num + "\">" + num + "</a>");
                } else {
                    out.print("<a class=\"pagination-btn\" data-page=\"" + num + "\" value=\"" + num + "\">" + num + "</a>");
                }
            }
        }
        if (pagination.getNumberOfPage() > pagination.getCurentPage()) {
            out.print("<a class=\"pagination-btn\" data-page=\"" + (pagination.getCurentPage() + 1) + "\" value=\"" + (pagination.getCurentPage() + 1) + "\">&raquo;</a>");
        }
        out.print("</div>");

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
