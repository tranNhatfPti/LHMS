/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.AccountDAO;
import dal.InformationOfUserDAO;
import dal.ReceiptDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Account;
import model.InformationOfUser;
import model.Receipt;

/**
 *
 * @author ASUS
 */
@WebServlet(name="ReceiptTenantController", urlPatterns={"/cost-incurred"})
public class ReceiptTenantController extends HttpServlet {
   
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
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        if(account==null){
            request.getRequestDispatcher("view/login/login.jsp").forward(request, response);
        }
        int id = account.getAccountID();
        //Create Dao objects.
        ReceiptDAO reDao = new ReceiptDAO();
        AccountDAO accountDao = new AccountDAO();
        InformationOfUserDAO userDao = new InformationOfUserDAO();
        
        Account showAcc = accountDao.getAccountById(id);
        InformationOfUser userInfor = userDao.getInformationByAccountID(id);
        String service = request.getParameter("service");
        if(service==null){
            service="showListCost";
        }
        if(service.equals("showListCost")){
            int index = Integer.parseInt(request.getParameter("index"));
            String pay = request.getParameter("pay");
            int status1;
            int status2;
            if(pay.equals("all")){
                status1 = 1;
                status2 = 0;
            }else if(pay.equals("unpaid")){
                status1 = 0;
                status2 = 0;
            }else{
                status1 = 1;
                status2 = 1;
            }
            int endPage = reDao.getTotalReceiptByAccountId(id,status1, status2)/5;
            if(reDao.getTotalReceiptByAccountId(id,status1, status2) % 5 !=0){
                endPage++;
            }
            List<Receipt> listReceipt = reDao.getAllReceiptByAccountId(id, (index-1)*5,status1, status2);
            
            
            request.setAttribute("tagMenu", "showCostIncurred");
            request.setAttribute("endPage", endPage);
            request.setAttribute("index", index);
            request.setAttribute("pay", pay);
            request.setAttribute("totalReceipt", reDao.getTotalReceiptByAccountId(id,status1, status2));
            request.setAttribute("listReceipt", listReceipt);
            request.setAttribute("account", showAcc);
            request.setAttribute("userInfor", userInfor);
            request.getRequestDispatcher("view/tenant/cost-incurred.jsp").forward(request, response);
        }if(service.equals("pay")){
            String idReceipt = request.getParameter("id");
            Receipt receipt = reDao.getReceiptByReceiptId(Integer.parseInt(idReceipt));
            request.setAttribute("account", showAcc);
            request.setAttribute("userInfor", userInfor);
            request.setAttribute("receipt", receipt);
            request.getRequestDispatcher("/view/tenant/payment-cost-incurred.jsp").forward(request, response);
        }
        
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
        ReceiptDAO reDao = new ReceiptDAO();
        String service = request.getParameter("service");
        if(service==null){
            service="";
        }
        if(service.equals("update")){
            String idReceipt = request.getParameter("id");
            reDao.updateStatus(Integer.parseInt(idReceipt), 1);
            response.sendRedirect("cost-incurred?service=showListCost&index=1&pay=all");
        }
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
