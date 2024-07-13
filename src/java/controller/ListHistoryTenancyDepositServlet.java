/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.TenancyDepositDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import model.TenancyDeposit;
import utils.Pagination;

/**
 *
 * @author Admin
 */
public class ListHistoryTenancyDepositServlet extends HttpServlet {

    List<TenancyDeposit> listHistoryTenancyDeposit;

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
            out.println("<title>Servlet ListHistoryTenancyDepositServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListHistoryTenancyDepositServlet at " + request.getContextPath() + "</h1>");
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
        String roomId = request.getParameter("roomId");
        String statusDeleteRaw = request.getParameter("statusAccept");
        TenancyDepositDAO tenancyDepositDAO = new TenancyDepositDAO();
        String curentPageRaw = request.getParameter("curentPage");
        int statusDelete = 0;
        int numberPerPage = 6;
        try {
            if (statusDeleteRaw != null) {
                statusDelete = Integer.parseInt(statusDeleteRaw);
//                System.out.println(statusDelete);
//                System.out.println(statusAcceptRepo);
            }
            listHistoryTenancyDeposit = tenancyDepositDAO.getAllTenancyDepositByRoomID(roomId, statusDelete);
            Pagination<TenancyDeposit> pagination = new Pagination<>(curentPageRaw, numberPerPage, listHistoryTenancyDeposit);
            session.removeAttribute("pagination");
            session.setAttribute("active", statusDelete);
            //System.out.println(listHistoryTenancyDeposit.size());
            //System.out.println(pagination.getNumberOfPage());
            session.setAttribute("pagination", pagination);
            request.getRequestDispatcher("view/manager/list-history-tenancy-deposit.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
        processRequest(request, response);
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
