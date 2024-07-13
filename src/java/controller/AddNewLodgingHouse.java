/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.LodgingHousesDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import model.LodgingHouse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author admin
 */
public class AddNewLodgingHouse extends HttpServlet {

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
            out.println("<title>Servlet AddNewLodgingHouse</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddNewLodgingHouse at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("view/landlord/Add-new-lodging-house.jsp").forward(request, response);
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

        String nameLodgingHouse = request.getParameter("nameLodging");
        String province = "";
        String district = "";
        String ward = request.getParameter("phuong");
        String addressDetail = request.getParameter("addressDetail");
        System.out.println("gia tri null" + addressDetail);
        String numberOfRoom_raw = request.getParameter("numberOfRoom");
        String status = request.getParameter("status");
        String img = request.getParameter("avatar");

        try {
            URL url = new URL("https://esgoo.net/api-tinhthanh/5/" + ward + ".htm");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read response
                StringBuilder sb = new StringBuilder();
                try (Scanner scanner = new Scanner(url.openStream())) {
                    while (scanner.hasNext()) {
                        sb.append(scanner.nextLine());
                    }
                }

                // Parse JSON response
                JSONParser parser = new JSONParser();
                JSONObject jsonResponse = (JSONObject) parser.parse(sb.toString());

                // Extract data object
                JSONObject data = (JSONObject) jsonResponse.get("data");

                // Extract location information
                String fullName = (String) data.get("full_name");
                String[] location = fullName.split(", ");
                ward += "," + location[0];

                district = location[1];

                province = location[2];

            } else {
                System.out.println("Error: HTTP request failed with response code " + responseCode);
            }
        } catch (IOException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

        boolean checkStaus = status.equalsIgnoreCase("1");

        LodgingHousesDAO d = new LodgingHousesDAO();
        List<LodgingHouse> list = d.getAllLodgingHouse();
        for (LodgingHouse lodgingHouse : list) {
            if (lodgingHouse.getNameLodgingHouse().equalsIgnoreCase(nameLodgingHouse)
                    && lodgingHouse.getProvince().equalsIgnoreCase(province.toUpperCase())
                    && lodgingHouse.getDistrict().equalsIgnoreCase(district.toUpperCase())
                    && lodgingHouse.getWard().equalsIgnoreCase(ward.toUpperCase())) {

                request.setAttribute("error", "Duplicate information");
                request.getRequestDispatcher("view/manager/Add-new-lodging-house.jsp").forward(request, response);
                return;
            }
        }

        int numberOfRoom = numberOfRoom_raw.isEmpty() ? 0 : Integer.parseInt(numberOfRoom_raw);
        Date currentDate = new Date();
        LodgingHouse lod = new LodgingHouse(nameLodgingHouse, province.toUpperCase(), district.toUpperCase(), ward.toUpperCase(), addressDetail, img, numberOfRoom, checkStaus, currentDate, true);
        d.addLodgingHouse(lod);

        response.sendRedirect("management-lodging-houses?index=1&nameLodgingHouse=" + nameLodgingHouse);

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
