/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.LodgingHousesDAO;
import jakarta.servlet.RequestDispatcher;
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
public class UpdateInfoLodgingHouse extends HttpServlet {

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
            out.println("<title>Servlet UpdateInfoLodgingHouse</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateInfoLodgingHouse at " + request.getContextPath() + "</h1>");
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
        String id = request.getParameter("lodgingHouseID");
        String error = request.getParameter("error");
        LodgingHousesDAO lodgingHouseDAO = new LodgingHousesDAO();
        LodgingHouse lodgingHouse = lodgingHouseDAO.getLodgingHouseById(Integer.parseInt(id));
        String fullName = lodgingHouse.getWard();
        String[] location = fullName.split(",");
        lodgingHouse.setWard(location[1]);
        String wardID = location[0];
        request.setAttribute("wardID", wardID);

        request.setAttribute("error", error);
        request.setAttribute("lodgingHouse", lodgingHouse);
        request.getRequestDispatcher("view/manager/Update-Lodging-House.jsp").forward(request, response);

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
        String nameLodgingHouse = request.getParameter("nameLodging");
        String province = "";
        String district = "";
        String ward = request.getParameter("phuong");
        String oldWard = request.getParameter("currentWard");
        System.out.println("Old ward" + oldWard);
        String addressDetail = request.getParameter("addressDetatil");
        String numberOfRoom_raw = request.getParameter("numberOfRoom");
        String status = request.getParameter("status");
        String img = request.getParameter("avatar");
        System.out.println(img);
        String oldImg = request.getParameter("oldImage");
        String lodgingHouseID_raw = request.getParameter("lodgingHouseId");
        if (ward.isEmpty()) {
            try {
                URL url = new URL("https://esgoo.net/api-tinhthanh/5/" + oldWard + ".htm");
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
                    oldWard += "," + location[0];
                    district = location[1];
                    province = location[2];
                    System.out.println(oldWard);
                    System.out.println(province);
                    System.out.println(district);
                } else {
                    System.out.println("Error: HTTP request failed with response code " + responseCode);
                }
            } catch (IOException | ParseException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
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
                    System.out.println(ward);
                    System.out.println(province);
                    System.out.println(district);
                } else {
                    System.out.println("Error: HTTP request failed with response code " + responseCode);
                }
            } catch (IOException | ParseException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        if (img.isEmpty()) {
            img = oldImg;
        }
        boolean checkStaus = false;
        if (status.equalsIgnoreCase("1")) {
            checkStaus = true;
        }
        LodgingHousesDAO d = new LodgingHousesDAO();
        List<LodgingHouse> list = d.getAllLodgingHouse();
        for (LodgingHouse lodgingHouse : list) {
            if (lodgingHouse.getLodgingHouseId() != Integer.parseInt(lodgingHouseID_raw) && lodgingHouse.getNameLodgingHouse().equalsIgnoreCase(nameLodgingHouse)
                    && lodgingHouse.getProvince().equalsIgnoreCase(province.toUpperCase())
                    && lodgingHouse.getDistrict().equalsIgnoreCase(district.toUpperCase())
                    && lodgingHouse.getWard().equalsIgnoreCase(ward.toUpperCase())) {
                String error = "Duplicate information";
                String redirectURL = "update-info-lodging-house?lodgingHouseID=" + lodgingHouseID_raw + "&error=" + error;
                response.sendRedirect(redirectURL);
                return;
            }
        }
        int numberOfRoom;
        if (numberOfRoom_raw.isEmpty()) {
            numberOfRoom = 0;
        } else {
            numberOfRoom = Integer.parseInt(numberOfRoom_raw);
        }
        Date currentDate = new Date();
        LodgingHouse lod = new LodgingHouse(Integer.parseInt(lodgingHouseID_raw), nameLodgingHouse, province.toUpperCase(), district.toUpperCase(), ward.toUpperCase(), addressDetail, img, numberOfRoom, checkStaus, currentDate, true);
        d.UpdateLodgingHouse(lod);
        response.sendRedirect("management-lodging-houses?index=1");
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
