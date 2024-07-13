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
import model.InvestmentCost;
import dal.InvestmentCostDAO;
import java.util.List;

/**
 *
 * @author admin
 */
public class ConfirmInvestmentCostsServlet extends HttpServlet {

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
            out.println("<title>Servlet ConfirmInvestmentCostsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ConfirmInvestmentCostsServlet at " + request.getContextPath() + "</h1>");
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
        System.out.println("-----------------------------------------------");
        String investmentCostsServletIdRaw = request.getParameter("id");
        String serviceRaw = request.getParameter("service");
        InvestmentCostDAO investmentCostDAO = new InvestmentCostDAO();
        try {
            int investmentCostsServletId = Integer.parseInt(investmentCostsServletIdRaw);
            int service = Integer.parseInt(serviceRaw);
            InvestmentCost investmentCost = investmentCostDAO.getInvestmentCostByID(investmentCostsServletId);
            InvestmentCost oldInvestmentCost = null;
            List<InvestmentCost> listInvestmentCosts = investmentCostDAO.getAllInvestmentCostByLodgingHouseId(investmentCost.getLodgingHouse().getLodgingHouseId(),
                    1);
            System.out.println(investmentCost.getDescription());
            for (InvestmentCost listInvestmentCost : listInvestmentCosts) {
                if (listInvestmentCost.getDateTime().equals(investmentCost.getDateTime())
                        && listInvestmentCost.getAccount().getAccountID() == investmentCost.getAccount().getAccountID()
                        && listInvestmentCost.getLodgingHouse().getLodgingHouseId() == investmentCost.getLodgingHouse().getLodgingHouseId()
                        && listInvestmentCost.getStatusDelete() == investmentCost.getStatusDelete()) {
                    oldInvestmentCost = listInvestmentCost;
                    break;
                }
            }
            if (service == 3) { //reject
                investmentCost.setStatusAccept(3);
                investmentCostDAO.updateInvestmentCost(investmentCost);
            } else {
                if (service == 1) { //oke
                    if (investmentCost.getTypeAccept() == 3) {
                        List<InvestmentCost> listInvestmentCost1 = investmentCostDAO.getAllInvestmentCostByLodgingHouseId(investmentCost.getLodgingHouse().getLodgingHouseId(), 2);
                        for (InvestmentCost listInvestmentCost : listInvestmentCost1) {
                            if (listInvestmentCost.getDateTime().equals(investmentCost.getDateTime())
                                    && listInvestmentCost.getAccount().getAccountID() == investmentCost.getAccount().getAccountID()
                                    && listInvestmentCost.getLodgingHouse().getLodgingHouseId() == investmentCost.getLodgingHouse().getLodgingHouseId()
                                    && listInvestmentCost.getTypeAccept() == 2) {
                                investmentCostDAO.deleteInvestmentCost(listInvestmentCost.getInvestmentCostID());
                            }
                        }
                        investmentCostDAO.deleteInvestmentCost(investmentCostsServletId);
                        if (oldInvestmentCost != null) {
                            investmentCostDAO.deleteInvestmentCost(oldInvestmentCost.getInvestmentCostID());
                        }
                    }
                    if (investmentCost.getTypeAccept() == 1) {
                        investmentCost.setStatusAccept(1);
                        investmentCostDAO.updateInvestmentCost(investmentCost);
                    }
                    if (investmentCost.getTypeAccept() == 2) {
                        if (oldInvestmentCost != null) {
                            oldInvestmentCost.setDescription(investmentCost.getDescription());
                            oldInvestmentCost.setPrice(investmentCost.getPrice());
                            oldInvestmentCost.setTypeOfInvestmentCosts(investmentCost.getTypeOfInvestmentCosts());
                            investmentCostDAO.updateInvestmentCost(oldInvestmentCost);
                        }
                        investmentCostDAO.deleteInvestmentCost(investmentCostsServletId);
                    }
                }
            }
            response.sendRedirect("investment-costs-servlet");
        } catch (NumberFormatException e) {

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
