/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Contract;
import dal.ContractDAO;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.LodgingHouse;
import utils.Pagination;
import dal.LodgingHousesDAO;
import model.Account;

/**
 *
 * @author admin
 */
public class ViewOldContractServlet extends HttpServlet {

    int statusAcceptRepo;

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
            out.println("<title>Servlet ViewOldContractServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewOldContractServlet at " + request.getContextPath() + "</h1>");
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
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String statusAccept = request.getParameter("statusAccept");
        if (statusAccept != null) {
            this.statusAcceptRepo = Integer.parseInt(statusAccept);
        }
        LodgingHousesDAO lodgingHousesDAO = new LodgingHousesDAO();
        String lodgingHouseIDRaw = (String) session.getAttribute("lodgingID");
        int lodgingHouseID = Integer.parseInt(lodgingHouseIDRaw);
        String roomId = (String) session.getAttribute("roomId");
        out.print(roomId);
        ContractDAO contractDAO = new ContractDAO();

        List<Contract> listContract = contractDAO.getContractByRoomId(roomId, this.statusAcceptRepo, 1);
        int numberPerPage = 4;

        LodgingHouse lodgingHouse = lodgingHousesDAO.getLodgingHouseById(lodgingHouseID);
        request.setAttribute("lodgingHouse", lodgingHouse);
        request.setAttribute("active", statusAcceptRepo);
        session.setAttribute("statusAccept", statusAcceptRepo);
        String curentPageRaw = request.getParameter("curentPage");
        Pagination paginationOldContract = new Pagination(curentPageRaw, numberPerPage, listContract);
        request.setAttribute("paginationOldContract", paginationOldContract);
        request.getRequestDispatcher("view/manager/table-old-room-contract.jsp").forward(request, response);

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
