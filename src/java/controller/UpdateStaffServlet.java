/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.LodgingHousesDAO;
import dal.RoleOfStaffDAO;
import dal.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Admin
 */
public class UpdateStaffServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdateStaffServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateStaffServlet at " + request.getContextPath() + "</h1>");
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
        request.setAttribute("notice",
                "Hãy chọn nhân viên cần thay đổi thông tin !");
        request.getRequestDispatcher("view/manager/update-staff.jsp").forward(request, response);
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
        LodgingHousesDAO lodgeDAO = new LodgingHousesDAO();
        RoleOfStaffDAO roleOfStaffDAO = new RoleOfStaffDAO();
        String ward_raw = request.getParameter("phuong");
        String staffID_raw = request.getParameter("staffID");
        String nameStaff = request.getParameter("nameStaff");
        String roleStaff_raw = request.getParameter("roleStaff");
        String phoneNumber = request.getParameter("phoneNumber");
        String email = request.getParameter("email");
        String addessDetail = request.getParameter("addressDetail");
        String salary_raw = request.getParameter("salaryInput");
        String lodgingHouse_raw = request.getParameter("lodgingHouse");

        StaffDAO staffDAO = new StaffDAO();
        String ward = "";
        String district = "";
        String province = "";
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
        String noticeEmail = "";
        String noticePhoneNumber = "";
        int staffID = 0;
        int roleStaffID = 0;
        double salary = 0;
        int lodgingHouse = 0;
        try {
            if (staffID_raw != null && roleStaff_raw != null 
                    && salary_raw != null && lodgingHouse_raw != null) {
                staffID = Integer.parseInt(staffID_raw);
                roleStaffID = Integer.parseInt(roleStaff_raw);
                salary = Double.parseDouble(salary_raw);
                lodgingHouse = Integer.parseInt(lodgingHouse_raw);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!staffDAO.checkExistEmail(staffID, email)
                && !staffDAO.checkExistPhoneNumber(staffID, phoneNumber)) {
            if (staffDAO.updateStaff(staffID, nameStaff, roleStaffID, phoneNumber,
                    email, province, district, ward,
                    addessDetail, salary, lodgingHouse)) {
                response.sendRedirect("list-staff");
            } else {
                request.setAttribute("notice",
                        "Hãy chọn nhân viên cần thay đổi thông tin !");
                request.getRequestDispatcher("view/manager/update-staff.jsp").forward(request, response);
            }
        } else {
            if (staffDAO.checkExistEmail(staffID, email)) {
                noticeEmail = "Tài khoản email đã tồn tại !";
            }
            if (staffDAO.checkExistPhoneNumber(staffID, phoneNumber)) {
                noticePhoneNumber = "Số điện thoại đã tồn tại !";
            }
            try {
                if (staffID_raw != null) {
                    staffID = Integer.parseInt(staffID_raw);
                    request.setAttribute("getLodgingHouseByID",
                            lodgeDAO.getLodgingHouseById(staffDAO.getStaffByID(staffID)
                                    .getLodgingHouseID().getLodgingHouseId()));
                    request.setAttribute("getStaffByID", staffDAO.getStaffByID(staffID));
                    request.setAttribute("roleStaff", staffDAO.getStaffByID(staffID).getRoleStaffID());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            request.setAttribute("getAllRoleStaff", roleOfStaffDAO.getAll());
            request.setAttribute("existEmail", noticeEmail);
            request.setAttribute("existPhoneNumber", noticePhoneNumber);
            request.getRequestDispatcher("view/manager/update-staff.jsp").forward(request, response);
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
