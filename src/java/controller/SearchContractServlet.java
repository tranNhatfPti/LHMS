/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ContractDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Account;
import model.Contract;
import utils.Pagination;

/**
 *
 * @author admin
 */
public class SearchContractServlet extends HttpServlet {

    String keyWordRepo;

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
            out.println("<title>Servlet SearchContractServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SearchContractServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        ContractDAO contractDAO = new ContractDAO();
        String keyWord = request.getParameter("keyword");
        if(keyWord != null){
            keyWordRepo = keyWord;
        }
        int statusAccept = (Integer) session.getAttribute("statusAccept");
        String roomId = (String) session.getAttribute("roomId");
        Account account = (Account) session.getAttribute("account");

        List<Contract> listContract = null;
        if (roomId != null) {
            listContract = contractDAO.getContractInRoomByKeyword(roomId, statusAccept, keyWordRepo);
        } else {
            listContract = contractDAO.getContractForTenantByKeyword(statusAccept, keyWordRepo, account.getAccountID());
        }

        for (Contract contract : listContract) {
            System.out.println(contract.getContractDeposit());
        }
        System.out.println("statusAccept:" + statusAccept);
        int numberPerPage = 4;
        String curentPageRaw = request.getParameter("curentPage");
        Pagination<Contract> pagination = new Pagination(curentPageRaw, numberPerPage, listContract);

// Mở đoạn mã HTML sử dụng out.println
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<form id=\"contractForm\" action=\"" + request.getContextPath() + "/send-contract-servlet\" method=\"post\" style=\"display: none;\">");
        out.println("<input type=\"hidden\" id=\"room\" name=\"room\">");
        out.println("<input type=\"hidden\" id=\"email\" name=\"email\">");
        out.println("<input type=\"hidden\" id=\"dateFrom\" name=\"dateFrom\">");
        out.println("<input type=\"hidden\" id=\"dateTo\" name=\"dateTo\">");
        out.println("<input type=\"hidden\" id=\"deposit\" name=\"deposit\">");
        out.println("<input type=\"hidden\" id=\"check\" name=\"check\" value=\"1\">");
        out.println("</form>");

        out.println("<table id=\"sampleTable\" class=\"table table-hover table-bordered js-copytextarea\">");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>Email người thuê</th>");
        out.println("<th>Phòng</th>");
        out.println("<th>Ngày bắt đầu</th>");
        out.println("<th>Ngày kết thúc</th>");
        out.println("<th>Tiền cọc</th>");
        out.println("<th>Loại yêu cầu từ quản lí</th>");
        out.println("<th>Xem hợp đồng</th>");
        if (statusAccept == 2) {
            out.println("<th>Xóa hợp đồng</th>");
        }
        if (statusAccept == 1) {
            out.println("<th>Phê duyệt</th>");
        }
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");

        for (Contract contract : pagination.getListObject()) {
            out.println("<tr>");
            out.println("<td>" + contract.getTenantId().getEmail() + "</td>");
            out.println("<td>" + contract.getRoom().getRoomName() + "</td>");
            out.println("<td>" + contract.getDateTo() + "</td>");
            out.println("<td>" + contract.getDateFrom() + "</td>");
            out.println("<td>" + contract.getContractDeposit() + " VND" + "</td>");
            if (contract.getTypeOfAccept() == 1) {
                out.println("<td>Thêm</td>");
            } else if (contract.getTypeOfAccept() == 2) {
                out.println("<td>Sửa</td>");
            } else if (contract.getTypeOfAccept() == 3) {
                out.println("<td>Xóa</td>");
            }

            out.println("<td class=\"table-td-center\">");
            out.println("<button class=\"btn btn-primary send-form-btn\" type=\"button\"");
            out.println("data-room=\"" + contract.getRoom().getRoomId() + "\"");
            out.println("data-email=\"" + contract.getTenantId().getEmail() + "\"");
            out.println("data-date-from=\"" + contract.getDateFrom() + "\"");
            out.println("data-date-to=\"" + contract.getDateTo() + "\"");
            out.println("data-deposit=\"" + contract.getContractDeposit() + "\">");
            out.println("<i class='bx bx-edit'></i>");
            out.println("</button>");
            out.println("</td>");

            if (contract.getStatusAccept() == 2 && contract.getTypeOfAccept() != 3) {
                out.println("<td>");
                out.println("<button class=\"btn btn-primary send-form-btnDelete\" type=\"button\" onclick=\"confirmDelete('" + contract.getRoom().getRoomId() + "', '" + contract.getTenantId().getAccountID() + "', '" + contract.getDateFrom() + "', '" + contract.getDateTo() + "')\">");
                out.println("<i class=\"fas fa-trash-alt\"></i>");
                out.println("</button>");
                out.println("</td>");
            }

            if (contract.getRoleCreateContract() == 3 && contract.getStatusAccept() == 1) {
                out.println("<td class=\"table-td-center\">");
                out.println("<button class=\"btn btn-primary btn-sm\" type=\"button\" title=\"Đồng ý\" onclick=\"confirmAccept('" + contract.getRoom().getRoomId() + "', '" + contract.getTenantId().getAccountID() + "', '" + contract.getDateFrom() + "', '" + contract.getDateTo() + "', 2, '" + contract.getTypeOfAccept() + "')\">");
                out.println("<i class=\"fas fa-check fa-flip\" style=\"--fa-primary-color: #d91c1c; --fa-secondary-color: #d91c1c;\"></i>");
                out.println("</button>");
                out.println("<button class=\"btn btn-primary btn-sm\" type=\"button\" title=\"Không đồng ý\" onclick=\"confirmAccept('" + contract.getRoom().getRoomId() + "', '" + contract.getTenantId().getAccountID() + "', '" + contract.getDateFrom() + "', '" + contract.getDateTo() + "', 3, '" + contract.getTypeOfAccept() + "')\">");
                out.println("<i class=\"fa fa-window-close\"></i>");
                out.println("</button>");
                out.println("</td>");
            }

            out.println("</tr>");
        }
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

        out.close();

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
