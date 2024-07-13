/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import dal.LodgingHousesDAO;
import dal.RoleOfStaffDAO;
import dal.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Account;
import model.Staff;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Admin
 */
public class AddStaffServlet extends HttpServlet {

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
            out.println("<title>Servlet AddStaffServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddStaffServlet at " + request.getContextPath() + "</h1>");
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
        RoleOfStaffDAO roleOfStaff = new RoleOfStaffDAO();
        HttpSession session = request.getSession();
        String lodgingHouseID_raw = (String) session.getAttribute("lodgingID");
        int lodgingHouseID = 0;
//        if(session.getAttribute("lodgingID") != null) {
//            lodgingHouseID = (int) session.getAttribute("lodgingID");
//        }
        try {
            if (lodgingHouseID_raw != null) {
                lodgingHouseID = Integer.parseInt(lodgingHouseID_raw);
            }
        } catch (Exception e) {
        }

        StaffDAO staffDAO = new StaffDAO();
        List<String> listEmail = new ArrayList<>();
        List<String> listPhoneNumber = new ArrayList<>();

        for (Staff s : staffDAO.getAllStaff()) {
            listEmail.add(s.getEmail());
            listPhoneNumber.add(s.getPhoneNumber());
        }
        String jsonListEmail = new Gson().toJson(listEmail);
        String jsonListPhoneNumber = new Gson().toJson(listPhoneNumber);
        request.setAttribute("listPhoneNumber", jsonListPhoneNumber);
        request.setAttribute("listEmail", jsonListEmail);
        request.setAttribute("listAllRoleStaff", roleOfStaff.getAll());
        request.getRequestDispatcher("view/manager/add-staff.jsp").forward(request, response);
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
        LodgingHousesDAO lodgeDAO = new LodgingHousesDAO();
        RoleOfStaffDAO roleOfStaff = new RoleOfStaffDAO();
        HttpSession session = request.getSession();
        //Account account = (Account) session.getAttribute("account");
        String logdingHouseID_raw = (String) session.getAttribute("lodgingID");
        String ward_raw = request.getParameter("phuong");
        String nameStaff = request.getParameter("name");
        String roleStaff_raw = request.getParameter("roleStaff");
        String phoneNumber = request.getParameter("phoneNumber");
        String email = request.getParameter("email");
        String addessDetail = request.getParameter("addressDetail");
        String salary_raw = request.getParameter("salary");
        StaffDAO staffDAO = new StaffDAO();
        String ward = "";
        String district = "";
        String province = "";
        int roleStaffID = 0;
        double salary = 0;
        String duplicatedEmail = "";
        String duplicatedPhoneNumber = "";
        int lodgingHouseID = 0;
        
        //get data from API 
        URL urlobj = new URL("https://esgoo.net/api-tinhthanh/5/" + ward_raw + ".htm");
        HttpURLConnection connection = (HttpURLConnection) urlobj.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int responseCode = connection.getResponseCode();
        try {

            if (responseCode == HttpURLConnection.HTTP_OK) {
                StringBuilder sb = new StringBuilder();
                Scanner sc = new Scanner(urlobj.openStream());
                while (sc.hasNext()) {
                    sb.append(sc.nextLine());
                }
                sc.close();
                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(sb.toString());
                JSONObject json1 = (JSONObject) parser.parse(String.valueOf(json.get("data")));
                // Get the value of the key "full_name"
                String fullName = (String) json1.get("full_name");
                String[] location = fullName.split(", ");
                ward = location[0];
                district = location[1];
                province = location[2];

            } else {
                System.out.println("Error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if ((roleStaff_raw != null) || (salary_raw != null)) {
                roleStaffID = Integer.parseInt(roleStaff_raw);
                salary = Double.parseDouble(salary_raw);
                lodgingHouseID = Integer.parseInt(logdingHouseID_raw);
            } else {
                request.setAttribute("notice", "Lỗi! Hãy thử lại !");
                request.getRequestDispatcher("view/manager/add-staff.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
//        if (logdingHouseID_raw != null) {
//            //lodgingHouseID = lodgeDAO.getLodgingHouseByManageID(account.getAccountID());
//            lodgingHouseID = Integer.parseInt(logdingHouseID_raw);
//        }
//        if (session.getAttribute("lodgingID") != null) {
//            lodgingHouseID = (int) session.getAttribute("lodgingID");
//        }
        System.out.println("LodgingHouseId" + lodgingHouseID);
        if (!(staffDAO.getExistStaffByEmail(email))
                && !(staffDAO.getExistStaffByPhoneNumber(phoneNumber))) {
            if (staffDAO.insertStaff(nameStaff, roleStaffID, phoneNumber, email,
                    province, district, ward, addessDetail,
                    salary, lodgingHouseID)) {
                response.sendRedirect("list-staff");
            } else {
                request.setAttribute("notice", "Thêm nhân viên mới không thành công !");
                request.getRequestDispatcher("view/manager/add-staff.jsp").forward(request, response);
            }
        } else {
            if ((staffDAO.getExistStaffByEmail(email))) {
                duplicatedEmail = "Tài khoản email đã tồn tại !";
            }
            if ((staffDAO.getExistStaffByPhoneNumber(phoneNumber))) {
                duplicatedPhoneNumber = "Số điện thoại này đã tồn tại !";
            }
            
            request.setAttribute("listAllRoleStaff", roleOfStaff.getAll());
            request.setAttribute("duplicatedEmail", duplicatedEmail);
            request.setAttribute("duplicatedPhoneNumber", duplicatedPhoneNumber);
            request.getRequestDispatcher("view/manager/add-staff.jsp").forward(request, response);
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
