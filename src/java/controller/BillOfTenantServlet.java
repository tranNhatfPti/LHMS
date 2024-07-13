/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountInRoomDAO;
import dal.BillDAO;
import dal.ContractDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Account;
import model.Bill;
import model.Contract;
import model.PairPrimaryBill;

/**
 *
 * @author ASUS ZenBook
 */
@WebServlet(name = "BillOfTenantServlet", urlPatterns = {"/bill-of-tenant"})
public class BillOfTenantServlet extends HttpServlet {

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
            out.println("<title>Servlet BillOfTenantServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BillOfTenantServlet at " + request.getContextPath() + "</h1>");
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
        AccountInRoomDAO ard = new AccountInRoomDAO();
        BillDAO bd = new BillDAO();
        ContractDAO cd = new ContractDAO();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");

        Account accountTenant = (Account) session.getAttribute("account");
        int tenantId = accountTenant.getAccountID();

        String service = request.getParameter("service");

        if (service == null) {
            List<Bill> listBillOfTenant = searchBill(tenantId);
            
            request.setAttribute("listBillOfTenant", listBillOfTenant);
            request.getRequestDispatcher("/view/tenant/bill.jsp").forward(request, response);
        } else {
            if (service.equals("payment")) {
                String roomId = request.getParameter("roomId");
                String monthYear = request.getParameter("monthYear");

                Bill bill = bd.getBillByRoomIdAndMonthYear(roomId, monthYear);

                request.setAttribute("billNeedPay", bill);
                request.getRequestDispatcher("/view/tenant/payment.jsp").forward(request, response);
            }
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
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        BillDAO bd = new BillDAO();
        ContractDAO cd = new ContractDAO();

        String service = request.getParameter("service");
        int tenantId = ((Account) session.getAttribute("account")).getAccountID();

        if (service.equals("searchBill")) {
            String monthYear = request.getParameter("searchDate");
            String roomId = request.getParameter("roomId");
            String status_string = request.getParameter("status");
            int status = Integer.parseInt(status_string);

            List<Bill> listOfBill = bd.searchBillOfTenant(monthYear, roomId, status, tenantId);

            request.setAttribute("monthYear", monthYear);
            request.setAttribute("roomId", roomId);
            request.setAttribute("status", status);
            request.setAttribute("listBillOfTenant", listOfBill);
            request.getRequestDispatcher("/view/tenant/bill.jsp").forward(request, response);
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

    public static List<PairPrimaryBill> getMonthsBetween(String roomId, Date startDate, Date endDate) {
        List<PairPrimaryBill> months = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        // Sử dụng Calendar để thao tác với ngày tháng
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);

        PairPrimaryBill ppb;
        while (start.before(end) || start.equals(end)) {
            ppb = new PairPrimaryBill(roomId, sdf.format(start.getTime()));
            months.add(ppb);
            start.add(Calendar.MONTH, 1);
        }

        return months;
    }

    public static List<Bill> searchBill(int tenantId) {
        AccountInRoomDAO ard = new AccountInRoomDAO();
        BillDAO bd = new BillDAO();
        ContractDAO cd = new ContractDAO();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        
        // chỉ được lấy ra những bill của chính account này ký hđ hoặc là hiện tại đang ở trong accountInRoom
        // lấy ra roomId hiện tại mà tenant đang ở(có thể là đứng ra kí hđ hoặc không)
        List<String> listOfRoomId = ard.getRoomIdByAccountIdRenting(tenantId);

        // danh sách tất cả bill của tenant
        List<Bill> listBillOfTenant = new ArrayList<>();

        // cần xác định khoảng thời gian tenant đến ở(hợp đồng đầu tiên) để lấy chính xác bill trong khoảng tg đấy
        // giả sử thời gian bắt đầu hợp đồng là 2024-06
        // lấy ra tất cả hợp đồng của tenant này 
        List<Contract> currentContract = cd.getAllContractOfTenant(tenantId);

        // lấy ra tất cả tháng trong tất cả hợp đồng(contract is signed by this tenant)
        List<PairPrimaryBill> listMonthYearOfTenant = new ArrayList<>();
        for (Contract contract : currentContract) {
            String roomId = contract.getRoom().getRoomId();
            Date dateFrom = contract.getDateFrom();
            Date dateTo = contract.getDateTo();

            listMonthYearOfTenant.addAll(getMonthsBetween(roomId, dateFrom, dateTo));
        }

        // lấy ra tất cả các tháng trong tất cả hợp đồng(có thể không phải tenant này kí)
        for (String roomId : listOfRoomId) {
            Contract contract = cd.getCurrentContractOfTenant(roomId);

            if (contract != null) {
                Date dateFrom = contract.getDateFrom();
                Date dateTo = contract.getDateTo();

                listMonthYearOfTenant.addAll(getMonthsBetween(roomId, dateFrom, dateTo));
            }
        }

        // lấy tất cả bill hiện tại(kí hoặc ko kí) + tất cả bill của các phòng được kí
        for (PairPrimaryBill ppb : listMonthYearOfTenant) {
            String roomId = ppb.getRoomId();
            String monthYear = ppb.getMonthYear();
            Bill bill = bd.getBillByRoomIdAndMonthYear(roomId, monthYear);
            if (bill != null && !listBillOfTenant.contains(bill)) {
                listBillOfTenant.add(bill);
            }
        }
        return listBillOfTenant;
    }

}
