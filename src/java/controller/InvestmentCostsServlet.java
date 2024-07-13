/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.InvestmentCost;
import dal.InvestmentCostDAO;
import dal.TypeOfInvestmentCostDAO;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.TypeOfInvestmentCosts;
import utils.Pagination;

/**
 *
 * @author admin
 */
public class InvestmentCostsServlet extends HttpServlet {

    List<InvestmentCost> listInvestmentCostsRepo;
    int statusAcceptRepo = 1;

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
            out.println("<title>Servlet InvestmentCostsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet InvestmentCostsServlet at " + request.getContextPath() + "</h1>");
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
         PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        InvestmentCostDAO investmentCostDAO = new InvestmentCostDAO();
        TypeOfInvestmentCostDAO typeOfInvestmentCostDAO = new TypeOfInvestmentCostDAO();

        String lodgingHouseIdRaw = (String)session.getAttribute("lodgingID");
        
        String statusAcceptRaw = request.getParameter("statusAccept");
        int lodgingHouseId;
        int statusAccept;
        try {
           
            
            lodgingHouseId = Integer.parseInt(lodgingHouseIdRaw);
            if (statusAcceptRaw != null) {
                statusAccept = Integer.parseInt(statusAcceptRaw);
                statusAcceptRepo = statusAccept;
            }
            
            //int lodgingHouseId = 1;
            List<TypeOfInvestmentCosts> listTypeOfInvestmentCosts = typeOfInvestmentCostDAO.getAllTypeOfInvestmentCost();
            List<InvestmentCost> listInvestmentCost = investmentCostDAO.getAllInvestmentCostByLodgingHouseId(lodgingHouseId, statusAcceptRepo);
            listInvestmentCostsRepo = listInvestmentCost;
            int numberPerPage = 4;
            String curentPageRaw = request.getParameter("curentPage");
            Pagination<InvestmentCost> pagination = new Pagination<>(curentPageRaw, numberPerPage, listInvestmentCostsRepo);
            session.removeAttribute("pagination");
            session.setAttribute("active", statusAcceptRepo);         
            session.setAttribute("pagination", pagination);
            request.setAttribute("listTypeOfInvestmentCosts", listTypeOfInvestmentCosts);
            request.getRequestDispatcher("view/manager/table-data-investment-costs.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            out.print(e);
            
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
