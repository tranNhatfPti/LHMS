/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import dal.TypeOfInvestmentCostDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.TypeOfInvestmentCosts;

/**
 *
 * @author admin
 */
public class UpdateTypeOfInvestmentCostsServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdateTypeOfInvestmentCostsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateTypeOfInvestmentCostsServlet at " + request.getContextPath() + "</h1>");
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
        TypeOfInvestmentCostDAO typeOfInvestmentCostDAO = new TypeOfInvestmentCostDAO();
        String idTypeOfInvestmentCost = request.getParameter("id");
        try {
            int idTypeOfInvestmentCostInteger = Integer.parseInt(idTypeOfInvestmentCost);        
            List<TypeOfInvestmentCosts> listTypeOfInvestmentCosts = typeOfInvestmentCostDAO.getAllTypeOfInvestmentCost();
            List<String> listDes = new ArrayList<>();
            for (TypeOfInvestmentCosts listTypeOfInvestmentCost : listTypeOfInvestmentCosts) {
                listDes.add(listTypeOfInvestmentCost.getName());
            }
            String jsonList = new Gson().toJson(listDes);
            request.setAttribute("jsonListTypeOfInvestmentCosts", jsonList);

            request.setAttribute("typeOfInvestmentCost",
                    typeOfInvestmentCostDAO.getTypeOfInvestmentCostByID(idTypeOfInvestmentCostInteger));

            request.getRequestDispatcher("view/landlord/update-type-of-investment-costs.jsp").forward(request, response);
        } catch (NumberFormatException e) {
//            String error = "Fail to update type of investment cost";
//            request.setAttribute("errorDeleteTypeOfInvestmentCost", error);
//            request.getRequestDispatcher("type-of-investment-costs-servlet").forward(request, response);
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
        TypeOfInvestmentCostDAO tyeOfInvestmentCostsDAO = new TypeOfInvestmentCostDAO();
        String idRaw = request.getParameter("id");
        String name = request.getParameter("name");
        boolean checkName = true;
        List<TypeOfInvestmentCosts> listTypeOfInvestmentCosts = tyeOfInvestmentCostsDAO.getAllTypeOfInvestmentCost();
        for (TypeOfInvestmentCosts listTypeOfInvestmentCost : listTypeOfInvestmentCosts) {
            if (name.equalsIgnoreCase(listTypeOfInvestmentCost.getName())) {
                checkName = false;
            }
        }
        try {
            int id = Integer.parseInt(idRaw);
            if (checkName == true) {
                TypeOfInvestmentCosts typeOfInvestmentCosts = new TypeOfInvestmentCosts(id, name);
                tyeOfInvestmentCostsDAO.updateTypeOfInvestmentCost(typeOfInvestmentCosts);
                response.sendRedirect("type-of-investment-costs-servlet");
            } else {
                request.setAttribute("name", name);
                request.setAttribute("error", "Loại phí đầu tư này đã có");
                request.setAttribute("typeOfInvestmentCost", tyeOfInvestmentCostsDAO.getTypeOfInvestmentCostByID(id));
                request.getRequestDispatcher("view/landlord/update-type-of-investment-costs.jsp").forward(request, response);
            }
        } catch (IOException e) {
        }
    }

}
