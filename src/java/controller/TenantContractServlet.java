/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountInRoomDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.Contract;
import dal.ContractDAO;
import java.util.List;
import utils.Pagination;
import dal.LodgingHousesDAO;
import dal.RoomDAO;
import model.AccountInRoom;
import model.Room;

/**
 *
 * @author admin
 */
public class TenantContractServlet extends HttpServlet {

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
            out.println("<title>Servlet TenantContractServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TenantContractServlet at " + request.getContextPath() + "</h1>");
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
        String statusAccept = request.getParameter("statusAccept");
        if (statusAccept != null) {
            this.statusAcceptRepo = Integer.parseInt(statusAccept);
        }
        LodgingHousesDAO lodgingHousesDAO = new LodgingHousesDAO();
        Account account = (Account) session.getAttribute("account");
        ContractDAO contractDAO = new ContractDAO();
        System.out.println("StatusAccept" + statusAcceptRepo);
        List<Contract> listContractOfTenant = contractDAO.getContractForTenant(account.getAccountID(), statusAcceptRepo);
        System.out.println("size:" + listContractOfTenant.size());
        String curentPageRaw = request.getParameter("curentPage");
        request.setAttribute("active", statusAcceptRepo);
        session.setAttribute("statusAccept", statusAcceptRepo);
        int numberPerPage = 4;
        Pagination paginationContractOfTenant = new Pagination(curentPageRaw, numberPerPage, listContractOfTenant);
        request.setAttribute("paginationContractOfTenant", paginationContractOfTenant);
        request.getRequestDispatcher("view/tenant/table-contract-of-tenant.jsp").forward(request, response);
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
        ContractDAO contractDAO = new ContractDAO();
        RoomDAO roomDAO = new RoomDAO();
        System.out.println("-------------------------------------");
        String roomId = request.getParameter("roomId");
        String tenantId = request.getParameter("tenantId");
        String dateFrom = request.getParameter("dateFrom");
        String dateTo = request.getParameter("dateTo");
        String service = request.getParameter("service");
        String typeAcceptRaw = request.getParameter("typeAccept");
        System.out.println(typeAcceptRaw);
        //xử lí confirm
        try {
            int typeAccept = Integer.parseInt(typeAcceptRaw);
            int serviceParse = Integer.parseInt(service);
            int tenantIdParse = Integer.parseInt(tenantId);

            Contract contractCheck = contractDAO.getContractToHandle(roomId, tenantIdParse, dateFrom, dateTo, typeAccept);
            System.out.println(contractCheck.toString());
            if (contractCheck != null) {//tồn tại
                System.out.println("Tồn tại");
                contractCheck.setStatusAccept(serviceParse);
                contractDAO.updateContract(contractCheck);
                if (typeAccept == 3 && serviceParse == 2) {
                    Contract c = contractDAO.getContractByPrimaryKey(roomId, tenantIdParse, dateFrom, dateTo, 1, 2);
                    System.out.println(c.toString());
                    c.setStatusDelete(0);
                    contractDAO.updateContract(c);
                }
            } else { //chưa tồn tại
                System.out.println("chưa Tồn tại");
                Contract contract = contractDAO.getContractByPrimaryKey(roomId, tenantIdParse, dateFrom, dateTo, typeAccept, 1);
                contract.setStatusAccept(serviceParse);
                contractDAO.updateContract(contract);
                if (typeAccept == 3 && serviceParse == 2) {
                    Contract c = contractDAO.getContractByPrimaryKey(roomId, tenantIdParse, dateFrom, dateTo, 1, 2);
                    c.setStatusDelete(0);
                    contractDAO.updateContract(c);
                }
            }
            System.out.println(typeAccept);
            AccountInRoomDAO accountInRoomDAO = new AccountInRoomDAO();
            Room room = roomDAO.getRoomsById(roomId);
            AccountInRoom accountInRoom = accountInRoomDAO.getAccountRoomsById(roomId, account.getAccountID());

            if (serviceParse == 2) { //đồng ý
                if (typeAccept == 1) {
                    room.setStatus(2);
                    if (accountInRoom == null) {
                        accountInRoom = new AccountInRoom(account, room);
                        accountInRoomDAO.insertAccountInRoom(accountInRoom);
                    }
                } else {
                    System.out.println("vô đây ạ");
                    room.setStatus(0);
                    if(accountInRoom != null){
                         accountInRoomDAO.deleteAccountInRoom(accountInRoom);
                    }
                   
                }
            } else {//ko đồng ý
                if (typeAccept == 1) {
                    room.setStatus(0);
                }
            }
            roomDAO.updateRoom(room);
        } catch (NumberFormatException e) {
            System.out.println(e);
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

}
