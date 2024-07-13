/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.LodgingHousesDAO;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.LodgingHouse;

/**
 *
 * @author admin
 */
public class ManagementLodgingHouses extends HttpServlet {

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
        String indexPage=request.getParameter("index");
        int index=Integer.parseInt(indexPage);
        LodgingHousesDAO d = new LodgingHousesDAO();
        List<LodgingHouse> list = d.pagingLodgingHouse(index);
        for (LodgingHouse lodgingHouse : list) {
            String fullName = lodgingHouse.getWard();
            String[] location = fullName.split(",");
            lodgingHouse.setWard(location[1]);
            System.out.println(lodgingHouse.getWard());

        }
        int countLodgingHouse=d.getTotalLodgingHouse();
        int endPage=countLodgingHouse/3;
        if(countLodgingHouse%3!=0){
            endPage++;
        }
                request.setAttribute("tag", index);
        request.setAttribute("endPage", endPage);
        request.setAttribute("list", list);

        request.getRequestDispatcher("view/manager/Management-lodging-houses.jsp").forward(request, response);

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
        processRequest(request, response);
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
