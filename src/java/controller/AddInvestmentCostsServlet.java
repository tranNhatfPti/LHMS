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
import java.util.List;
import model.Account;
import model.LodgingHouse;
import model.InvestmentCost;
import model.TypeOfInvestmentCosts;
import dal.AccountDAO;
import dal.LodgingHousesDAO;
import dal.InvestmentCostDAO;
import dal.TypeOfInvestmentCostDAO;
import jakarta.servlet.http.HttpSession;
import java.util.Date;
import model.InformationOfUser;
import dal.InformationOfUserDAO;
/**
 *
 * @author admin
 */
public class AddInvestmentCostsServlet extends HttpServlet {

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
            out.println("<title>Servlet AddInvestmentCostsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddInvestmentCostsServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        AccountDAO accountDAO = new AccountDAO();
        LodgingHousesDAO lodgingHousesDAO = new LodgingHousesDAO();
        TypeOfInvestmentCostDAO typeOfInvestmentCostDAO = new TypeOfInvestmentCostDAO();
        String lodgingHouseIdRaw = (String) session.getAttribute("lodgingID");
        //will delete after merge code
        int lodgingHouseId;
        try {
            lodgingHouseId = Integer.parseInt(lodgingHouseIdRaw);
        } catch (NumberFormatException e) {
            lodgingHouseId = 1;
        }
        Account accountIdRaw = (Account) session.getAttribute("account");
        //will delete after merge code
        int accountId;
        if (accountIdRaw != null) {
            accountId = accountIdRaw.getAccountID();
        } else {
            accountId = 1;
        }
        LodgingHouse lodgingHouse = lodgingHousesDAO.getLodgingHouseById(lodgingHouseId);
        InformationOfUserDAO informationOfUserDAO = new InformationOfUserDAO();
        InformationOfUser informationOfUser = informationOfUserDAO.getInformationByAccountID(accountId);
        
        List<TypeOfInvestmentCosts> lisTypeOfInvestmentCosts = typeOfInvestmentCostDAO.getAllTypeOfInvestmentCost();
        request.setAttribute("inforOfUser", informationOfUser);
        request.setAttribute("lodgingHouse", lodgingHouse);
        request.setAttribute("lisTypeOfInvestmentCosts", lisTypeOfInvestmentCosts);
        request.getRequestDispatcher("view/manager/add-investment-costs.jsp").forward(request, response);
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
        InvestmentCostDAO investmentCostDAO = new InvestmentCostDAO();
        LodgingHousesDAO lodgingHousesDAO = new LodgingHousesDAO();
        AccountDAO accountDAO = new AccountDAO();
        TypeOfInvestmentCostDAO typeOfInvestmentCostDAO = new TypeOfInvestmentCostDAO();
        String price_raw = request.getParameter("price");
        String description = request.getParameter("description");
        String managerId_raw = request.getParameter("managerId");
        String lodgingHouseId_raw = request.getParameter("lodgingHouseId");
        String typeOfInvestmentCost_raw = request.getParameter("typeOfInvestmentCost");
        Date date = new Date();
        try {
            double price = Double.parseDouble(price_raw);
            int managerId = Integer.parseInt(managerId_raw);
            int lodgingHouseId = Integer.parseInt(lodgingHouseId_raw);
            int typeOfInvestmentCost = Integer.parseInt(typeOfInvestmentCost_raw);
            InvestmentCost investmentCost = new InvestmentCost(price,
                    typeOfInvestmentCostDAO.getTypeOfInvestmentCostByID(typeOfInvestmentCost),
                    date, description, accountDAO.getAccountById(managerId),
                    lodgingHousesDAO.getLodgingHouseById(lodgingHouseId));
            investmentCostDAO.insertInvestmentCost(investmentCost);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Fail to insert!");
        }
        response.sendRedirect("investment-costs-servlet");
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
