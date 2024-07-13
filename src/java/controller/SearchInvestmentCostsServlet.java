/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.InvestmentCostDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.List;
import model.InvestmentCost;
import utils.Pagination;
import model.Account;

/**
 *
 * @author admin
 */
public class SearchInvestmentCostsServlet extends HttpServlet {

    String keyWordSave, typeOfInvestmentCostId = null, dateFrom = null, dateTo = null;
    // int managerId = 2, logingHouseId = 1;

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
            out.println("<title>Servlet SearchInvestmentCostsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SearchInvestmentCostsServlet at " + request.getContextPath() + "</h1>");
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
        //update after merger code
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        HttpSession session = request.getSession();
        String lodgingHouseIdRaw = (String) session.getAttribute("lodgingID");
        Account account = (Account) session.getAttribute("account");
        int statusAcceptRaw = (int) session.getAttribute("active");
        int lodgingHouseId = Integer.parseInt(lodgingHouseIdRaw);

        String dateFromRaw = request.getParameter("dateFrom");
        String dateToRaw = request.getParameter("dateTo");
        String keyWordRaw = request.getParameter("keyword");
        String typeOfInvestmentCostIdRaw = request.getParameter("typeOfInvestmentCostSearch");
        if (typeOfInvestmentCostIdRaw != null) {
            typeOfInvestmentCostId = typeOfInvestmentCostIdRaw;
            keyWordSave = null;
        }
        if (dateFromRaw != null) {
            dateFrom = dateFromRaw;
            keyWordSave = null;
        }
        if (dateToRaw != null) {
            dateTo = dateToRaw;
            keyWordSave = null;
        }
        if (keyWordRaw != null) {
            keyWordSave = keyWordRaw;
        } else {
            keyWordRaw = keyWordSave;
        }
        System.out.println("lodg:" + lodgingHouseId);
        System.out.println("typeId:" + typeOfInvestmentCostId);
        System.out.println("DateForm: " + dateFrom);
        System.out.println("DateTo: " + dateTo);
        
        System.out.println("----------------------------------------");
        System.out.println(keyWordRaw);
        System.out.println(keyWordSave);
        InvestmentCostDAO investmentCostDAO = new InvestmentCostDAO();
        List<InvestmentCost> listInvestmentCosts;
        if (keyWordSave == null) {
            System.out.println("here");
            listInvestmentCosts = investmentCostDAO.getAllInvestmentCostBySelect(lodgingHouseId, typeOfInvestmentCostId, dateFrom, dateTo, statusAcceptRaw);
        } else {
            System.out.println("check");
            listInvestmentCosts = investmentCostDAO.getAllInvestmentCostByKeyWord(keyWordRaw, lodgingHouseId, statusAcceptRaw);
            System.out.println(listInvestmentCosts.size());
        }
        int numberPerPage = 4;
        String curentPageRaw = request.getParameter("curentPage");
        System.out.println("-----------------------Currentpage:" + curentPageRaw );
        Pagination<InvestmentCost> pagination = new Pagination<>(curentPageRaw, numberPerPage, listInvestmentCosts);
        PrintWriter out = response.getWriter();
         
        out.println("<div id=\"content\">");
        out.println("    <table class=\"table table-hover table-bordered js-copytextarea\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"sampleTable\">");
        out.println("        <thead>");
        out.println("            <tr>");
        out.println("                <th width=\"10\"><input type=\"checkbox\" value=\"0\" id=\"all\" name=\"deleteAll\"></th>");
        out.println("                <th>Tên trọ</th>");
        out.println("                <th>Quản lí</th>");
        out.println("                <th>Ngày</th>");
        out.println("                <th>Loại phí đầu tư</th>");
        out.println("                <th>Miêu tả</th>");
        out.println("                <th>Số tiền(VND)</th>");

        if (account.getRoleId() == 2 && statusAcceptRaw == 1) {

            out.println("<th width=\"100\">Tính năng</th>");
        }
        if (account.getRoleId() == 1) {
            out.println("<th width=\"100\">Loại yêu cầu phê duyệt</th>");
            out.println("<th width=\"100\">Phê duyệt</th>");
        }
        out.println("            </tr>");
        out.println("        </thead>");
        out.println("        <tbody>");

        for (InvestmentCost listInvestmentCost : pagination.getListObject()) {
            out.println("            <tr>");
            out.println("                <td width=\"10\"><input type=\"checkbox\" id=\"" + listInvestmentCost.getInvestmentCostID() + "\" name=\"delete\" value=\"" + listInvestmentCost.getInvestmentCostID() + "\"></td>");
            out.println("                <td>" + listInvestmentCost.getLodgingHouse().getNameLodgingHouse() + "</td>");
            out.println("                <td>" + listInvestmentCost.getInformationOfUser().getFullName() + "</td>");
            out.println("                <td class=\"date\">" + sdf.format(listInvestmentCost.getDateTime()) + "</td>");
            if (listInvestmentCost.getTypeOfInvestmentCosts() != null) {
                out.println("                <td>" + listInvestmentCost.getTypeOfInvestmentCosts().getName() + "</td>");
            } else {
                out.println("                <td> </td>");
            }
            out.println("                <td>" + listInvestmentCost.getDescription() + "</td>");
            out.println("                <td>" + listInvestmentCost.getPrice() + "</td>");

            if (account.getRoleId() == 2 && statusAcceptRaw == 1) {
                    out.println("    <td class=\"table-td-center\">");
                    out.println("        <button class=\"btn btn-primary btn-sm trash\" type=\"button\" title=\"Xóa\" data-date=\"" + listInvestmentCost.getDateTime() + "\" data-id=\"" + listInvestmentCost.getInvestmentCostID() + "\">");
                    out.println("            <i class=\"fas fa-trash-alt\"></i>");
                    out.println("        </button>");
                    out.println("        <button class=\"btn btn-primary btn-sm edit\" type=\"button\" title=\"Sửa\" id=\"show-emp\" data-date=\"" + listInvestmentCost.getDateTime() + "\" data-id=\"" + listInvestmentCost.getInvestmentCostID() + "\">");
                    out.println("            <i class=\"fas fa-edit\"></i>");
                    out.println("        </button>");
                    out.println("    </td>");               
            }
            if (account.getRoleId() == 1) {
                System.out.println("Đã vào 1");
                System.out.println(listInvestmentCost.getTypeAccept());
                if (listInvestmentCost.getTypeAccept() == 1) {
                    out.println("<td>Thêm</td>");
                }
                if (listInvestmentCost.getTypeAccept() == 2) {
                    out.println("<td>Sửa</td>");
                }
                if (listInvestmentCost.getTypeAccept() == 3) {
                    out.println("<td>Xóa</td>");
                }

                if (listInvestmentCost.getStatusAccept() == 2) {
                    out.println("        <td class=\"table-td-center\">");
                    out.println("            <button class=\"btn btn-primary btn-sm\" type=\"button\" title=\"Đồng ý\" onclick=\"confirmAccept(" + listInvestmentCost.getInvestmentCostID() + ", 1)\">");
                    out.println("                <i class=\"fas fa-check fa-flip\" style=\"--fa-primary-color: #d91c1c; --fa-secondary-color: #d91c1c;\"></i>");
                    out.println("            </button>");
                    out.println("            <button class=\"btn btn-primary btn-sm\" type=\"button\" title=\"Không đồng ý\" onclick=\"confirmAccept(" + listInvestmentCost.getInvestmentCostID() + ", 3)\">");
                    out.println("                <i class=\"fa fa-window-close\"></i>");
                    out.println("            </button>");
                    out.println("        </td>");
                }

                if (listInvestmentCost.getStatusAccept() == 1) {
                    out.println("        <td class=\"table-td-center\">");
                    out.println("            <button class=\"btn btn-primary btn-sm\">");
                    out.println("                <i class=\"fas fa-check fa-flip btn-sm\"></i>");
                    out.println("            </button>");
                    out.println("        </td>");
                }

                if (listInvestmentCost.getStatusAccept() == 3) {
                    out.println("        <td class=\"table-td-center\">");
                    out.println("            <button class=\"btn btn-primary btn-sm\">");
                    out.println("                <i class=\"fa fa-window-close\"></i>");
                    out.println("            </button>");
                    out.println("        </td>");
                }
            }
            out.println("</tr>");
        }
        out.println("</tbody>");
        out.println("</table>");
        out.print("<div class=\"pagination\">");
        if (pagination.getCurentPage() > 1) {
            out.print("<a class=\"pagination-btn\" data-page=\"" + (pagination.getCurentPage() - 1) + "\" value=\"" + (pagination.getCurentPage() - 1) + "\">&laquo;</a>");
        }
        for (int num = pagination.getStart(); num <= pagination.getEnd(); num++) {
            if (num != 0) {
                if (num == pagination.getCurentPage()) {
                    out.print("<a class=\"pagination-btn active\" data-page=\"" + num + "\" value=\"" + num + "\">" + num + "</a>");
                } else {
                    out.print("<a class=\"pagination-btn\" data-page=\"" + num + "\" value=\"" + num + "\">" + num + "</a>");
                }
            }
        }
        if (pagination.getNumberOfPage() > pagination.getCurentPage()) {
            out.print("<a class=\"pagination-btn\" data-page=\"" + (pagination.getCurentPage() + 1) + "\" value=\"" + (pagination.getCurentPage() + 1) + "\">&raquo;</a>");
        }
        out.print("</div>");
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
