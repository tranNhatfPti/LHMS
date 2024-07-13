/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountDAO;
import dal.InformationOfUserDAO;
import dal.RoomDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.InformationOfUser;
import model.RoomLodging;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "RoomController", urlPatterns = {"/room"})
public class RoomController extends HttpServlet {

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
        processRequest(request, response);
        HttpSession session = request.getSession();
        AccountDAO accountDao = new AccountDAO();
        InformationOfUserDAO userDao = new InformationOfUserDAO();
        Account account = (Account) session.getAttribute("account");
        String service = request.getParameter("Service");
        if (service == null) {
            service = "showRoomInfor";
        }
        if (account == null) {
            request.getRequestDispatcher("view/login/login.jsp").forward(request, response);
        }
        int id = account.getAccountID();
        if (service.equals("showRoomInfor")) {
            RoomDAO roomDa = new RoomDAO();
            RoomLodging room = roomDa.getRoomById(id);
            Account showAcc = accountDao.getAccountById(id);
            InformationOfUser userInfor = userDao.getInformationByAccountID(id);
            if (room == null) {
                request.setAttribute("tagMenu", "showRoom");
                request.setAttribute("showAcc", showAcc);
                request.setAttribute("userInfor", userInfor);
                request.setAttribute("checkRoom", "Yes");
                request.getRequestDispatcher("view/tenant/room-information.jsp").forward(request, response);
            } else {
                request.setAttribute("tagMenu", "showRoom");
                request.setAttribute("room", room);
                request.setAttribute("account", showAcc);
                request.setAttribute("userInfor", userInfor);
                request.getRequestDispatcher("view/tenant/room-information.jsp").forward(request, response);
            }

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
