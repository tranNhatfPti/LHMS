/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountDAO;
import dal.LodgingHousesDAO;
import dal.NotificationDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import model.Account;
import model.LodgingHouse;
import model.Notification;

/**
 *
 * @author admin
 */
public class AddNewManagerHouse extends HttpServlet {

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
            out.println("<title>Servlet AddNewManagerHouse</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddNewManagerHouse at " + request.getContextPath() + "</h1>");
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
        LodgingHousesDAO da = new LodgingHousesDAO();
        AccountDAO ac = new AccountDAO();
        List<Account> listAc = ac.getAllAccount();
        List<LodgingHouse> list = da.getLodgingHouseByManagerEmpty();
        request.setAttribute("listLodging", list);

        request.setAttribute("listAc", listAc);

        request.getRequestDispatcher("view/landlord/Add-new-manager.jsp").forward(request, response);
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
        String receiverEmails = request.getParameter("email");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        String description = request.getParameter("description");
        System.out.println(description);

        String lodgingId_raw = request.getParameter("nhatro");
        System.out.println(lodgingId_raw);

        LodgingHousesDAO da = new LodgingHousesDAO();
        LodgingHouse lodgingFound = da.getLodgingHouseById(Integer.parseInt(lodgingId_raw));

        // Capture the current date and time
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        // Add 7 days to the current date and time
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date futureDate = calendar.getTime();

        // Split the emails by comma
        String[] emails = receiverEmails.split(",");

        AccountDAO accountDAO = new AccountDAO();

        for (String email : emails) {
            email = email.trim();
            Account receiverAccount = accountDAO.getAccountByUserEmail(email);

            if (receiverAccount != null) {
                int receiverId = receiverAccount.getAccountID();
                // Create a new Notification object with the captured date and time
                Notification notification = new Notification(description + "," + lodgingFound.getNameLodgingHouse() + "," + lodgingId_raw, futureDate, receiverId, account.getAccountID(), 0, 1);
                NotificationDAO notificationDAO = new NotificationDAO();
                notificationDAO.insertNotification(notification);
            } else {
                System.out.println("Invalid email address: " + email);
            }
        }

        // Redirect to the home-controller servlet
        String redirectURL = "home-controller?message=" + URLEncoder.encode("Thông báo đã được gửi đi", "UTF-8");

        // Redirect to the new URL
        response.sendRedirect(redirectURL);

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
