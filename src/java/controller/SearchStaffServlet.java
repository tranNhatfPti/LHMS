/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.LodgingHousesDAO;
import dal.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import model.Account;
import model.Staff;
import utils.Pagination;

/**
 *
 * @author Admin
 */
public class SearchStaffServlet extends HttpServlet {

    //String infoSearch;
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
            out.println("<title>Servlet SearchStaffServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SearchStaffServlet at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/html;charset=UTF-8");
        String currentPageRaw = request.getParameter("curentPage");
        HttpSession session = request.getSession();
        LodgingHousesDAO lodgingHousesDAO = new LodgingHousesDAO();
        StaffDAO staffDAO = new StaffDAO();
        String infoSearch = request.getParameter("keyword");
        int lodgingHouseID = 0;
        String logdingHouseID_raw = (String) session.getAttribute("lodgingID");
        try {
            if(logdingHouseID_raw != null) {
                lodgingHouseID  = Integer.parseInt(logdingHouseID_raw);
            }
        } catch (Exception e) {
        }
//        if(session.getAttribute("lodgingID") != null) {
//            lodgingHouseID = (int) session.getAttribute("lodgingID");
//        }
        if (infoSearch == null) {
            infoSearch = "";
        }
        NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
        formatter.setGroupingUsed(true);
        formatter.setMaximumFractionDigits(0);
        List<Staff> listStaff;
        if ("".equals(infoSearch) || infoSearch.isBlank()) {
            listStaff = staffDAO.getAllStaffByLodgingHouseID(lodgingHouseID);
        } else {
            listStaff = staffDAO.getListStaffBySearch(infoSearch, lodgingHouseID);
        }
        int numberPerPage = 6;
        Pagination<Staff> pagination = new Pagination<>(currentPageRaw, numberPerPage, listStaff);
        PrintWriter out = response.getWriter();

        out.println("<table class=\"table table-hover table-bordered js-copytextarea\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"sampleTable\">");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th width=\"10\"><input type=\"checkbox\" value=\"0\" id=\"all\" name=\"delete\"></th>");
        out.println("<th>Tên nhân viên</th>");
        out.println("<th>Chức vụ</th>");
        out.println("<th>Số điện thoại</th>");
        out.println("<th>Email</th>");
        out.println("<th>Địa chỉ</th>");
        out.println("<th>Lương</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");
        try {
            for (Staff s : pagination.getListObject()) {
                out.println("<tr>");
                out.println("<td width=\"10\"><input type=\"checkbox\" id=\"" + s.getStaffID() + "\" name=\"staffIds\" value=\"" + s.getStaffID() + "\"></td>");
                out.println("<td>" + s.getNameStaff() + "</td>");
                if (s.getRoleStaffID() != null) {
                    if (s.getRoleStaffID().getStatusActive() == 1) {
                        out.println("<td>" + s.getRoleStaffID().getRoleStaffName() + "</td>");
                    }
                } else {
                    out.println("<td style=\"color: red\"><small>Chức vụ này hiện không tồn tại!</small></td>");
                }
                out.println("<td>" + s.getPhoneNumber() + "</td>");
                out.println("<td>" + s.getEmail() + "</td>");
                out.println("<td>" + s.getAddressDetail() + ", " + s.getWard() + ", " + s.getDistrict() + ", " + s.getProvince() + "</td>");
                out.println("<td>" + formatter.format(s.getSalary()) + "</td>");
                out.println("<td>");
                out.println("<button class='btn btn-primary btn-sm trash' type='button' title='Xóa' onclick=\"myFunction('" + s.getStaffID() + "')\">");
                out.println("<i class='fas fa-trash-alt'></i>");
                out.println("</button>");
                out.println("<a class='btn btn-primary btn-sm edit' title='Sửa' href='load-data-staff?staffID=" + s.getStaffID() + "'>");
                out.println("<i class='fas fa-edit'></i>");
                out.println("</a>");
                out.println("</td>");
                out.println("</tr>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        out.println("</tbody>");
        out.println("</table>");

        if (!"".equals(infoSearch) && infoSearch != null || pagination.getListObject().isEmpty()) {
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
