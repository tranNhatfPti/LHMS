/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dal.AccountDAO;
import dal.AccountInRoomDAO;
import dal.RoomDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.util.List;
import model.Account;
import model.AccountInRoom;
import model.Room;
import utils.Pagination;

/**
 *
 * @author admin
 */
public class AddRoomMate extends HttpServlet {

    private String roomId;

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
            out.println("<title>Servlet AddRoomMate</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddRoomMate at " + request.getContextPath() + "</h1>");
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
        AccountDAO ad = new AccountDAO();
        HttpSession session = request.getSession();
        Account ac = (Account) session.getAttribute("account");
        if (ac == null) {
            response.sendRedirect("LoginServlet"); // Redirect to login page if account is null
            return;
        }

        roomId = request.getParameter("roomID");
        System.out.println("ad room: " + roomId);
        AccountInRoomDAO aId = new AccountInRoomDAO();
        List<AccountInRoom> listAccountInRoom = aId.getAccountAllInRoomByIdRoom(roomId);
        for (AccountInRoom accountInRoom : listAccountInRoom) {
            System.out.println("add roommate" + accountInRoom
            );
        }
        List<Account> listAccount = ad.getInfoAccount();
        for (Account account : listAccount) {
            System.out.println(account);
        }
        for (Account account : listAccount) {
            if (account.getAccountID() == ac.getAccountID()) {
                listAccount.remove(account);
                break;
            }
        }

        for (AccountInRoom accountInRoom : listAccountInRoom) {
            for (Account account : listAccount) {

                if (account.getAccountID() == accountInRoom.getAccount().getAccountID()) {
                    listAccount.remove(account);
                }
                break;
            }
        }
        int numberPerPage = 7;
        String curentPageRaw = request.getParameter("curentPage");
        Pagination<Account> pagination = new Pagination<>(curentPageRaw, numberPerPage, listAccount);
        session.removeAttribute("pagination");
        session.setAttribute("pagination", pagination);
        request.getRequestDispatcher("view/tenant/add-roommate.jsp").forward(request, response);
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
        String selectedIdsJson = request.getParameter("selectedIds");

        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String jsonString = sb.toString();

// Parse JSON string to get the list of selected items
        ObjectMapper mapper = new ObjectMapper();
        List<Account> selectedData = mapper.readValue(jsonString, new TypeReference<List<Account>>() {
        });

        AccountInRoomDAO aId = new AccountInRoomDAO();
        RoomDAO rd = new RoomDAO();
        Room r = rd.getRoomsById(roomId);

        boolean roomFull = false;

        for (Account account : selectedData) {
            if (r.getMaxOfQuantity() >= aId.countNumberOfPersonInRoom(roomId)) {
                
                aId.insertRoom(new AccountInRoom(account,r));
            } else {
                roomFull = true;
            }
        }

        if (roomFull) {
            request.setAttribute("successADD", "Thông báo: Phòng đã đủ người.");
        } else {
            request.setAttribute("successADD", "Thêm người vào phòng trọ thành công.");
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
