/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.RoomDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Account;
import model.Room;
import utils.Pagination;

/**
 *
 * @author admin
 */
public class RoomTenantServlet extends HttpServlet {

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
            out.println("<title>Servlet RoomTenantServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RoomTenantServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        RoomDAO roomDAO = new RoomDAO();
        if (account == null) {
            System.out.println("null");
        }
        List<Room> listRoom = roomDAO.getRoomsByTenantID(account.getAccountID());
        int numberPerPage = 4;
        String curentPageRaw = request.getParameter("curentPage");

        Pagination paginationRoom = new Pagination(curentPageRaw, numberPerPage, listRoom);
        request.setAttribute("paginationRoomTenant", paginationRoom);
        request.getRequestDispatcher("view/tenant/table-room-tenant.jsp").forward(request, response);
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
        String keyWord = request.getParameter("keyword");
        System.out.println(keyWord);
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        RoomDAO roomDAO = new RoomDAO();
        List<Room> listRoom = roomDAO.getRoomsByTenantIDAndKeyword(account.getAccountID(), keyWord);
        int numberPerPage = 4;
        String curentPageRaw = request.getParameter("curentPage");
        Pagination<Room> paginationRoom = new Pagination(curentPageRaw, numberPerPage, listRoom);
        PrintWriter out = response.getWriter();
        out.println("<div id=\"content\">");
        out.println("    <table id=\"sampleTable\" class=\"table table-hover table-bordered js-copytextarea\">");
        out.println("        <thead>");
        out.println("            <tr>");
        out.println("                <th>Tên phòng</th>");
        out.println("                <th>Giá</th>");
        out.println("                <th>Số người tối đa</th>");
        out.println("                <th>Miêu tả</th>");
        out.println("                <th>Ảnh</th>");
        out.println("            </tr>");
        out.println("        </thead>");
        out.println("        <tbody>");
        for (Room o : paginationRoom.getListObject()) {
            out.println("            <tr onclick=\"goToRoomDetail('" + o.getRoomId() + "')\">");
            out.println("                <td>" + o.getRoomName() + "</td>");
            out.println("                <td>" + o.getPrice() + "</td>");
            out.println("                <td>" + o.getMaxOfQuantity() + "</td>");
            out.println("                <td>" + o.getDescription() + "</td>");
            out.println("                <td><img src=\"" + o.getImage() + "\" alt=\"Image description\" style=\"height: 60px; width: 60px\"></td>");
            out.println("            </tr>");
        }
        out.println("        </tbody>");
        out.println("    </table>");
        // Pagination
        out.println("    <div class=\"pagination\">");
        int currentPage = paginationRoom.getCurentPage();
        if (currentPage > 1) {
            out.println("        <a href=\"javascript:void(0)\" onclick=\"loadPage(" + (currentPage - 1) + ")\">&laquo;</a>");
        }
        for (int num = paginationRoom.getStart(); num <= paginationRoom.getEnd(); num++) {
            if (num != 0) {
                if (num == currentPage) {
                    out.println("        <a href=\"javascript:void(0)\" class=\"active\" onclick=\"loadPage(" + num + ")\">" + num + "</a>");
                } else {
                    out.println("        <a href=\"javascript:void(0)\" onclick=\"loadPage(" + num + ")\">" + num + "</a>");
                }
            }
        }
        if (paginationRoom.getNumberOfPage() > currentPage) {
            out.println("        <a href=\"javascript:void(0)\" onclick=\"loadPage(" + (currentPage + 1) + ")\">&raquo;</a>");
        }
        out.println("    </div>");
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
