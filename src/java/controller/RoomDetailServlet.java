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
import jakarta.servlet.http.HttpSession;
import dal.RoomDAO;
import dal.ServiceDAO;
import model.Room;
import dal.ServiceOfLodgingHouseDAO;
import dal.ServiceOfRoomDAO;
import java.util.ArrayList;
import java.util.List;
import model.ServiceOfLodgingHouse;
import model.Service;
import model.ServiceOfRoom;
import model.LodgingHouse;
import dal.LodgingHousesDAO;
import model.Account;
import dal.AccountDAO;
import dal.ContractDAO;
import model.InformationOfUser;
import dal.InformationOfUserDAO;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import model.Contract;

/**
 *
 * @author admin
 */
public class RoomDetailServlet extends HttpServlet {

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
            out.println("<title>Servlet RoomDetailServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RoomDetailServlet at " + request.getContextPath() + "</h1>");
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
        RoomDAO roomDAO = new RoomDAO();
        ServiceOfLodgingHouseDAO serviceOfLodgingHouseDAO = new ServiceOfLodgingHouseDAO();
        ServiceDAO serviceDAO = new ServiceDAO();
        ServiceOfRoomDAO serviceOfRoomDAO = new ServiceOfRoomDAO();
        LodgingHousesDAO lodgingHousesDAO = new LodgingHousesDAO();
        AccountDAO accountDAO = new AccountDAO();
        InformationOfUserDAO informationOfUserDAO = new InformationOfUserDAO();
        ContractDAO contractDAO = new ContractDAO();
        String roomID = request.getParameter("id");
        session.setAttribute("roomId", roomID);
        Room room = roomDAO.getRoomsById(roomID);
        String lodgingHouseIDRaw = (String) session.getAttribute("lodgingID");

        try {

            int lodgingHouseID = Integer.parseInt(lodgingHouseIDRaw);
            LodgingHouse lodgingHouse = lodgingHousesDAO.getLodgingHouseById(lodgingHouseID);
            InformationOfUser manager = informationOfUserDAO.getManagerInfoByLodgingHouseId(lodgingHouseID);
            List<ServiceOfRoom> listServiceOfRoom = serviceOfRoomDAO.getServiceOfRoom(lodgingHouseID, roomID);
            List<ServiceOfLodgingHouse> listServiceOfLodgingHouse = serviceOfLodgingHouseDAO.getAllServiceOfLodgingHouseByLodgingHouseId(lodgingHouseID);
            List<Service> listService = new ArrayList<>();
            for (ServiceOfLodgingHouse serviceOfLodgingHouse : listServiceOfLodgingHouse) {
                listService.add(serviceDAO.getServiceById(serviceOfLodgingHouse.getServiceId()));
            }

            Calendar currentCalendar = Calendar.getInstance();
            Date today = currentCalendar.getTime();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(today);
            
            Contract contract = contractDAO.getContractByDateAndTenant(roomID, formattedDate);
            System.out.println("roomID:" + roomID);
            System.out.println("date:" + formattedDate);
            System.out.println(contract);
            if (contract != null ) {
                System.out.println(contract);
                request.setAttribute("contract", contract);
            }else{
                System.out.println("vl ko có");
            }

            request.setAttribute("lodgingHouse", lodgingHouse);

            request.setAttribute("manager", manager);
            request.setAttribute("listService", listService);
            request.setAttribute("listServiceOfRoom", listServiceOfRoom);

        } catch (NumberFormatException e) {
            System.out.println(e);
        }

        request.setAttribute("roomByID", room);
        request.getRequestDispatcher("view/manager/room-detail.jsp").forward(request, response);
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
