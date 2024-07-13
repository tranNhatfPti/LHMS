/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountDAO;
import dal.FeedbackDAO;
import dal.InformationOfUserDAO;
import dal.LodgingHousesDAO;
import dal.RoomDAO;
import dal.ServiceDAO;
import dal.ServiceOfLodgingHouseDAO;
import dal.ServiceOfRoomDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Account;
import model.InformationOfUser;
import model.LodgingHouse;
import model.Room;
import model.RoomLodging;
import model.Service;
import model.ServiceOfRoom;
import model.ServiceOfLodgingHouse;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "HomeTenantController", urlPatterns = {"/home-tenant"})
public class HomeTenantController extends HttpServlet {

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
        String service = request.getParameter("service");
        if (service == null) {
            service = "showLodgingInfor";
        }
        if (account == null) {
            request.getRequestDispatcher("view/login/login.jsp").forward(request, response);
        }
        int id = account.getAccountID();
        if (service.equals("showLodgingInfor")) {
            showHomePage(request, response, id);
        }
        if (service.equals("showService")) {
            showService(request, response, id);
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

    private static void showHomePage(HttpServletRequest request, HttpServletResponse response, int id) throws IOException, ServletException {
        AccountDAO accountDao = new AccountDAO();
        InformationOfUserDAO userDao = new InformationOfUserDAO();
        RoomDAO roomDao = new RoomDAO();
        FeedbackDAO feedDao = new FeedbackDAO();
        
        List<Room> rooms = roomDao.getRoomsByAccountId(id);
        Account showAcc = accountDao.getAccountById(id);
        InformationOfUser userInfor = userDao.getInformationByAccountID(id);
        if (!rooms.isEmpty()) {
            Room firstRoom = rooms.get(0);
            LodgingHouse lodging = firstRoom.getLodgingHouse();
            InformationOfUser manageInfor = userDao.getInformationByAccountID(lodging.getManageId());
            double star = feedDao.getStarByLodgingId(lodging.getLodgingHouseId());
            request.setAttribute("tagMenu", "showRoom");
            request.setAttribute("showService", "no");
            request.setAttribute("lodging", lodging);
            request.setAttribute("star", star);
            request.setAttribute("account", showAcc);
            request.setAttribute("manageInfor", manageInfor);
            request.setAttribute("userInfor", userInfor);
            request.getRequestDispatcher("view/tenant/home-page-tenant.jsp").forward(request, response);
        } else {
            request.setAttribute("checkRoom", "checkRoom");
            request.setAttribute("tagMenu", "showRoom");
            request.setAttribute("account", showAcc);
            request.setAttribute("userInfor", userInfor);
            request.getRequestDispatcher("view/tenant/home-page-tenant.jsp").forward(request, response);
        }

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

    private static void showService(HttpServletRequest request, HttpServletResponse response, int id) throws IOException, ServletException {
        
        //Khoi tao Dao
        AccountDAO accountDao = new AccountDAO();
        InformationOfUserDAO userDao = new InformationOfUserDAO();
        RoomDAO roomDao = new RoomDAO();
        ServiceDAO serviceDao = new ServiceDAO();
        ServiceOfLodgingHouseDAO serviceLoDao = new ServiceOfLodgingHouseDAO();
        
        //Create list room by id
        List<Room> rooms = roomDao.getRoomsByAccountId(id);
        
        //Get Account by id
        Account showAcc = accountDao.getAccountById(id);
        
        //Get user information by id
        InformationOfUser userInfor = userDao.getInformationByAccountID(id);
        
        //Get first room
        Room firstRoom = rooms.get(0);
        
        //Get lodging house
        LodgingHouse lodging = firstRoom.getLodgingHouse();
        
        //Get manager information
        InformationOfUser manageInfor = userDao.getInformationByAccountID(lodging.getManageId());
        
        //Get list of service room by lodging id and room id
        List<ServiceOfLodgingHouse> serviceLodging = serviceLoDao
                .getAllServiceOfLodgingHouseByLodgingHouseId(firstRoom.getLodgingHouse().getLodgingHouseId());
        
        //Create map service with name and price
        Map<String, Double> serviceNameAndPrice = new HashMap<>();
        Service service;
        for(ServiceOfLodgingHouse s : serviceLodging){
            service = serviceDao.getServiceById(s.getServiceId());
            serviceNameAndPrice.put(service.getServiceName(), s.getPrice());
        }
        
        request.setAttribute("tagMenu", "showRoom");
        request.setAttribute("lodging", lodging);
        request.setAttribute("serviceNameAndPrice", serviceNameAndPrice);
        request.setAttribute("account", showAcc);
        request.setAttribute("manageInfor", manageInfor);
        request.setAttribute("userInfor", userInfor);
        request.setAttribute("showService", "yes");
        request.getRequestDispatcher("view/tenant/home-page-tenant.jsp").forward(request, response);
    }
    
}
