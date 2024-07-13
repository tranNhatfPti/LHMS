/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.TypeOfInvestmentCosts;
import utils.Pagination;
import dal.TypeOfInvestmentCostDAO;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author admin
 */
public class TypeOfInvestmentCostsServlet extends HttpServlet {
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
        TypeOfInvestmentCostDAO tyeOfInvestmentCostsDAO = new TypeOfInvestmentCostDAO();
        List<TypeOfInvestmentCosts> listTypeOfInvestmentCosts = tyeOfInvestmentCostsDAO.getAllTypeOfInvestmentCost();
        HttpSession session = request.getSession();
        int numberPerPage = 4;
        String curentPageRaw = request.getParameter("curentPage");
        Pagination<TypeOfInvestmentCosts> pagination = new Pagination<>(curentPageRaw, numberPerPage, listTypeOfInvestmentCosts);
        session.setAttribute("paginationTypeOfInvestmentCost", pagination);
        request.getRequestDispatcher("view/landlord/table-data-type-of-investment-costs.jsp").forward(request, response);
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

    }

}
