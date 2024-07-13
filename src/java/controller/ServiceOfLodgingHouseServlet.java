/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountDAO;
import dal.InformationOfUserDAO;
import dal.ServiceDAO;
import dal.ServiceOfLodgingHouseDAO;
import dal.ServiceOfRoomDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.Service;
import model.ServiceOfLodgingHouse;

/**
 *
 * @author ASUS ZenBook
 */
@WebServlet(name = "ServiceOfLodgingHouseServlet", urlPatterns = {"/service-of-lodginghouse"})
public class ServiceOfLodgingHouseServlet extends HttpServlet {

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
            out.println("<title>Servlet ServiceOfLodgingHouseServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServiceOfLodgingHouseServlet at " + request.getContextPath() + "</h1>");
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
        PrintWriter out = response.getWriter();
        ServiceOfRoomDAO srd = new ServiceOfRoomDAO();
        ServiceOfLodgingHouseDAO sld = new ServiceOfLodgingHouseDAO();
        ServiceDAO sd = new ServiceDAO();

        int lodgingHouseId = (int) session.getAttribute("lodgingID");

        String service = request.getParameter("service");

        if (service == null) {
            List<ServiceOfLodgingHouse> listSLD = sld.getAllServiceOfLodgingHouseByLodgingHouseId(lodgingHouseId);
            request.setAttribute("listSLD", listSLD);
            request.getRequestDispatcher("/view/manager/service-of-lodginghouse-manage.jsp").forward(request, response);
        } else {
            if (service.equals("insertServiceOfLodgingHouse")) {
                String serviceId_string = request.getParameter("serviceId");
                String price_string = request.getParameter("price");

                int serviceId = Integer.parseInt(serviceId_string);
                double price = Double.parseDouble(price_string);

                ServiceOfLodgingHouse serviceOfLodgingHouse = new ServiceOfLodgingHouse(serviceId, lodgingHouseId, price);

                // Thiết lập kiểu nội dung phản hồi
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");

                if (sld.insertServiceOfLodgingHouse(serviceOfLodgingHouse) != 0) {
                    // Gửi phản hồi về client
                    out.write("OK");
                } else {
                    // Gửi phản hồi về client
                    out.write("ERROR");
                }
            }
            
            if(service.equals("requestUpdateService")){
                String serviceId_string = request.getParameter("serviceId");
                int serviceId = Integer.parseInt(serviceId_string);
                
                ServiceOfLodgingHouse serviceNeedUpdate = sld.getServiceByLodgingHouseIdAndServiceId(lodgingHouseId, serviceId);
                
                request.setAttribute("serviceNeedUpdate", serviceNeedUpdate);
                request.getRequestDispatcher("/view/manager/update-service-of-lodginghouse.jsp").forward(request, response);
            }
            
            if(service.equals("updateServiceOfLodgingHouse")){
                String serviceId_string = request.getParameter("serviceId");
                String price_string = request.getParameter("price");

                int serviceId = Integer.parseInt(serviceId_string);
                double price = Double.parseDouble(price_string);
                
                ServiceOfLodgingHouse serviceOfLodgingHouse = new ServiceOfLodgingHouse(serviceId, lodgingHouseId, price);
                
                if (sld.updateServiceOfLodgingHouse(serviceOfLodgingHouse) != 0) {
                    // Gửi phản hồi về client
                    out.write("OK");
                } else {
                    // Gửi phản hồi về client
                    out.write("ERROR");
                }
            }
            
            if(service.equals("deleteServiceOfLodgingHouse")){
                String serviceId_string = request.getParameter("serviceId");
                int serviceId = Integer.parseInt(serviceId_string);
                
                if(srd.checkServiceBeingUsedAnyRoom(lodgingHouseId, serviceId)){
                    out.write("USING");
                } else {
                    if(sld.deleteServiceOfLodgingHouse(lodgingHouseId, serviceId) != 0){
                        out.write("OK");
                    } else {
                        out.write("ERROR");
                    }     
                }
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        AccountDAO ad = new AccountDAO();
        InformationOfUserDAO iud = new InformationOfUserDAO();
        ServiceOfLodgingHouseDAO sld = new ServiceOfLodgingHouseDAO();

        String service = request.getParameter("service");
        int lodgingHouseId = (int) session.getAttribute("lodgingID");

        if (service == null) {
            request.getRequestDispatcher("/view/manager/service-of-lodginghouse-manage.jsp").forward(request, response);
        } else {
            
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
