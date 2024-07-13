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
import dal.AccountDAO;
import dal.InformationOfUserDAO;
import dal.LodgingHousesDAO;
import dal.TypeOfInvestmentCostDAO;
import dal.InvestmentCostDAO;
import java.util.ArrayList;
import java.util.List;
import model.InvestmentCost;
import model.Account;
import model.LodgingHouse;
import model.TypeOfInvestmentCosts;
import model.InformationOfUser;
/**
 *
 * @author admin
 */
public class UpdateInvestmentCostsServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdateInvestmentCostsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateInvestmentCostsServlet at " + request.getContextPath() + "</h1>");
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
        InvestmentCostDAO investmentCostDAO = new InvestmentCostDAO();
        TypeOfInvestmentCostDAO typeOfInvestmentCostDAO = new TypeOfInvestmentCostDAO();
        InformationOfUserDAO informationOfUserDAO = new InformationOfUserDAO();
        //List<Account> listManager = new ArrayList<>();
        //List<LodgingHouse> listLodgingHouse = new ArrayList<>();
        List<TypeOfInvestmentCosts> listTypeOfInvestmentCosts = new ArrayList<>();
        String investmentCostsIdRaw = request.getParameter("id");

        try {
            int investmentCostsId = Integer.parseInt(investmentCostsIdRaw);
            InvestmentCost investmentCost = investmentCostDAO.getInvestmentCostByID(investmentCostsId);
            
            
            InformationOfUser informationOfUser = informationOfUserDAO.getInformationByAccountID(investmentCost.getAccount().getAccountID());
            LodgingHouse lodgingHouse = investmentCost.getLodgingHouse();
            if (investmentCost.getTypeOfInvestmentCosts() == null) {
                listTypeOfInvestmentCosts = typeOfInvestmentCostDAO.getAllTypeOfInvestmentCost();
            } else {
                listTypeOfInvestmentCosts.add(investmentCost.getTypeOfInvestmentCosts());
                for (TypeOfInvestmentCosts typeOfInvestmentCost : typeOfInvestmentCostDAO.getAllTypeOfInvestmentCost()) {
                    if (typeOfInvestmentCost.getId() != investmentCost.getTypeOfInvestmentCosts().getId()) {
                        listTypeOfInvestmentCosts.add(typeOfInvestmentCost);
                    }
                }
            }
            request.setAttribute("investmentCost", investmentCost);
            System.out.println(informationOfUser);
            request.setAttribute("informanager", informationOfUser);
            request.setAttribute("lodgingHouse", lodgingHouse);
            request.setAttribute("listTypeOfInvestmentCosts", listTypeOfInvestmentCosts);
            request.getRequestDispatcher("view/manager/update-investment-costs.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Fail to update");
            response.sendRedirect("investment-costs-servlet");
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
        InvestmentCostDAO investmentCostDAO = new InvestmentCostDAO();
        AccountDAO accountDAO = new AccountDAO();
        TypeOfInvestmentCostDAO typeOfInvestmentCostDAO = new TypeOfInvestmentCostDAO();
        LodgingHousesDAO lodgingHousesDAO = new LodgingHousesDAO();
        String investmentCostIdRaw = request.getParameter("investmentCostId");
        System.out.println(investmentCostIdRaw);
        String priceRaw = request.getParameter("price");
        String description = request.getParameter("description");;
        String typeOfInvestmentCostRaw = request.getParameter("typeOfInvestmentCost");

        try {
            int investmentCostId = Integer.parseInt(investmentCostIdRaw);
            double price = Double.parseDouble(priceRaw);
            int typeOfInvestmentCostId = Integer.parseInt(typeOfInvestmentCostRaw);

            InvestmentCost investmentCost = investmentCostDAO.getInvestmentCostByID(investmentCostId);

            InvestmentCost newInvestmentCost = new InvestmentCost(price,
                    typeOfInvestmentCostDAO.getTypeOfInvestmentCostByID(typeOfInvestmentCostId),
                    investmentCost.getDateTime(),
                    description,
                    investmentCost.getAccount(),
                    investmentCost.getLodgingHouse(),
                    1,
                    2);
            investmentCostDAO.insertInvestmentCostConfirm(newInvestmentCost);
//            investmentCost.setPrice(price);
//            investmentCost.setTypeOfInvestmentCosts(typeOfInvestmentCostDAO.getTypeOfInvestmentCostByID(typeOfInvestmentCostId));
//            investmentCost.setDescription(description);
//            investmentCost.setStatusAccept(2);
//            investmentCost.setTypeAccept(2);
//            investmentCostDAO.UpdateInvestmentCost(investmentCost);
            response.sendRedirect("investment-costs-servlet");
        } catch (NumberFormatException e) {
            System.out.println("kjabcoqw");
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
