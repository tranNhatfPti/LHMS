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
import dal.InvestmentCostDAO;
import java.util.Calendar;
import java.util.Date;
import model.InvestmentCost;
import dal.TypeOfInvestmentCostDAO;

/**
 *
 * @author admin
 */
public class DeleteInvestmentCostsServlet extends HttpServlet {

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
            out.println("<title>Servlet DeleteInvestmentCostsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DeleteInvestmentCostsServlet at " + request.getContextPath() + "</h1>");
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
        String investmentCostsId_raw = request.getParameter("id");
        try {
            int investmentCostId = Integer.parseInt(investmentCostsId_raw);
            InvestmentCost investmentCost = investmentCostDAO.getInvestmentCostByID(investmentCostId);
            InvestmentCost newInvestmentCost = new InvestmentCost(investmentCost.getPrice(),
                    typeOfInvestmentCostDAO.getTypeOfInvestmentCostByID(investmentCost.getTypeOfInvestmentCosts().getId()),
                    investmentCost.getDateTime(),
                    investmentCost.getDescription(),
                    investmentCost.getAccount(),
                    investmentCost.getLodgingHouse(),
                    1,
                    3);
            investmentCostDAO.insertInvestmentCostConfirm(newInvestmentCost);

//            investmentCost.setStatusAccept(2);
//            investmentCost.setTypeAccept(3);
//            investmentCostDAO.UpdateInvestmentCost(investmentCost);
            //investmentCostDAO.DeleteInvestmentCost(investmentCostId);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Fail to delete!");
        }
        response.sendRedirect("investment-costs-servlet");
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
        TypeOfInvestmentCostDAO typeOfInvestmentCostDAO = new TypeOfInvestmentCostDAO();
        String idString = request.getParameter("ids");
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        if (idString != null && !idString.isEmpty()) {
            String[] ids = idString.split(",");
            if (checkAllDateBeforeDelete(ids, investmentCostDAO, response)) {
                for (String id : ids) {
                    int investmentCostId = Integer.parseInt(id);
                    InvestmentCost investmentCost = investmentCostDAO.getInvestmentCostByID(investmentCostId);
                    InvestmentCost newInvestmentCost = new InvestmentCost(investmentCost.getPrice(),
                            typeOfInvestmentCostDAO.getTypeOfInvestmentCostByID(investmentCost.getTypeOfInvestmentCosts().getId()),
                            investmentCost.getDateTime(),
                            investmentCost.getDescription(),
                            investmentCost.getAccount(),
                            investmentCost.getLodgingHouse(),
                            1,
                            3);
                    investmentCostDAO.insertInvestmentCostConfirm(newInvestmentCost);
                }
                out.print("Gủi yêu cầu xóa!");
            } else {
                out.print("Bạn không thể thực hiện do đã quá thời hạn!");
            }
        } else {
            out.print("Lỗi!");
        }
        out.flush();
    }

    /**
     * This function use to check date of investment cost. If 1 days in the past
     * will return false, else return true
     *
     * @param ids is array about all investment cost
     * @param investmentCostDAO is data access object of investment cost
     * @param response
     * @return status after checking all date of array investment costs
     */
    public boolean checkAllDateBeforeDelete(String[] ids, InvestmentCostDAO investmentCostDAO, HttpServletResponse response) {
        for (String id : ids) {
            int investmentCostId = Integer.parseInt(id);
            if (!checkDateBeforeDelete(investmentCostId, investmentCostDAO)) {
                return false;
            }
        }
        return true;
    }

    /**
     * This function to compare date of input investment costs with current date
     *
     * @param id is id of investment cost want to check
     * @param investmentCostDAO is data access object of investment cost
     * @return status after compare date of input investment cost and current
     * date
     */
    public boolean checkDateBeforeDelete(int id, InvestmentCostDAO investmentCostDAO) {
        // get current date
        Calendar currentCalendar = Calendar.getInstance();
        int currentYear = currentCalendar.get(Calendar.YEAR);
        int currentMonth = currentCalendar.get(Calendar.MONTH) + 1; // Lưu ý: Tháng bắt đầu từ 0

        // get date of investment costs
        Date investmentDate = investmentCostDAO.getInvestmentCostByID(id).getDateTime();
        Calendar investmentCalendar = Calendar.getInstance();
        investmentCalendar.setTime(investmentDate);
        int investmentYear = investmentCalendar.get(Calendar.YEAR);
        int investmentMonth = investmentCalendar.get(Calendar.MONTH) + 1; // Lưu ý: Tháng bắt đầu từ 0

        // return result after compare
        return currentYear == investmentYear && currentMonth == investmentMonth;
    }

}
