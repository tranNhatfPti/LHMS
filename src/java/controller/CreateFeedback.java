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
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import model.Account;
import model.Feedback;
import model.InformationOfUser;
import model.Room;

/**
 *
 * @author ASUS
 */
@WebServlet(name="CreateFeedback", urlPatterns={"/create-feedback"})
public class CreateFeedback extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        //create Daos object.
        AccountDAO accountDao = new AccountDAO();
        InformationOfUserDAO userDao = new InformationOfUserDAO();
        FeedbackDAO feedDao = new FeedbackDAO();
        LodgingHousesDAO lodDao = new LodgingHousesDAO();
        RoomDAO roomDao = new RoomDAO();

        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            request.getRequestDispatcher("view/login/login.jsp").forward(request, response);
        }
        int id = account.getAccountID();
        
        //get lodging id by account id of manager.
        int lodgingHouseId = lodDao.getLodgingHouseByManageID(id);

        //Get list of room have tenant in lodginghouse.
        List<Room> rooms = roomDao.getRoomsByLodgingHouseIdTenant(lodgingHouseId,"");

        //Create account and infor to display on header and tag menu.
        Account showAcc = accountDao.getAccountById(id);
        InformationOfUser userInfor = userDao.getInformationByAccountID(id);
        Date currentDate = new Date();
        int checkInsert = 0;
        //create ojects for jsp.
        for (Room r : rooms) {
            //Get room name and tanant name through room.
            InformationOfUser tenantInfor = userDao.getTenantInfoByRoomId(r.getRoomId());
            int star = feedDao.getStarByAccountId(tenantInfor.getAccountID());
            Feedback feedback = new Feedback(currentDate, tenantInfor.getAccountID(), lodgingHouseId, star, 0);
            if(feedDao.insertFeedback(feedback)){
                checkInsert++;
            }
        }
        response.sendRedirect("feedback?service=showManageFeedback");
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
