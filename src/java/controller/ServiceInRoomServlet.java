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
import dal.RoomDAO;
import dal.ServiceDAO;
import dal.ServiceOfLodgingHouseDAO;
import model.ServiceOfRoom;
import model.Room;
import model.Service;
import dal.ServiceOfRoomDAO;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.ServiceOfLodgingHouse;

/**
 *
 * @author Admin
 */
public class ServiceInRoomServlet extends HttpServlet {

    List<ServiceOfRoom> listServiceOfRoomGlobal;

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
            out.println("<title>Servlet ServiceInRoomServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServiceInRoomServlet at " + request.getContextPath() + "</h1>");
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
        String roleTypeRaw = request.getParameter("roleType");
        String statusAcceptRaw = request.getParameter("statusAccept");
        String roomId = request.getParameter("id");
        RoomDAO roomDAO = new RoomDAO();
        ServiceOfRoomDAO serviceOfRoomDAO = new ServiceOfRoomDAO();
        ServiceOfLodgingHouseDAO serviceOfLodgingHouseDAO = new ServiceOfLodgingHouseDAO();
        int roleType = 0;
        int statusAccpet = 0;
        ServiceDAO serviceDAO = new ServiceDAO();
        ContractDAO contractDAO = new ContractDAO();
        //Account acc = (Account) session.getAttribute("account");

        try {
            if (roleTypeRaw != null && statusAcceptRaw != null) {
                roleType = Integer.parseInt(roleTypeRaw);
                statusAccpet = Integer.parseInt(statusAcceptRaw);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (roleType == 3 && statusAccpet == 0) {

            request.setAttribute("serviceOfLodgingHouse", serviceOfLodgingHouseDAO.getAllServiceOfLodgingHouseByLodgingHouseId(roomDAO.getRoomsById(roomId).getLodgingHouse().getLodgingHouseId()));
            request.setAttribute("listServiceName", serviceDAO.getAllService());
            if (listServiceOfRoomGlobal != null) {
                request.setAttribute("listServiceAccept", listServiceOfRoomGlobal);
            } else {
                request.setAttribute("listServiceAccept", serviceOfRoomDAO.getServiceOfRoomAccpeted(roomDAO.getRoomsById(roomId).getLodgingHouse().getLodgingHouseId(), roomId));
            }
        } else if (roleType == 2 && statusAccpet == 0) {
            request.setAttribute("listServicePending", serviceOfRoomDAO.getServiceOfRoomRequesPending(roomDAO.getRoomsById(roomId).getLodgingHouse().getLodgingHouseId(), roomId));
            request.setAttribute("listServiceName", serviceDAO.getAllService());
        } else if (roleType == 3 && statusAccpet == 1) {
            if (listServiceOfRoomGlobal != null) {
                request.setAttribute("listServiceAccept", listServiceOfRoomGlobal);

            } else {
                request.setAttribute("listServiceAccept", serviceOfRoomDAO.getServiceOfRoomAccpeted(roomDAO.getRoomsById(roomId).getLodgingHouse().getLodgingHouseId(), roomId));
            }
            request.setAttribute("listServiceName", serviceDAO.getAllService());
        } else if (roleType == 2 && statusAccpet == 1) {
            //request.setAttribute("listServiceAccept", serviceOfRoomDAO.getServiceOfRoomAccpeted(roomDAO.getRoomsById(roomId).getLodgingHouse().getLodgingHouseId(), roomId));
            if (listServiceOfRoomGlobal != null) {
                request.setAttribute("listServiceAccept", listServiceOfRoomGlobal);
            } else {
                request.setAttribute("listServiceAccept", serviceOfRoomDAO.getServiceOfRoomAccpeted(roomDAO.getRoomsById(roomId).getLodgingHouse().getLodgingHouseId(), roomId));
            }
            request.setAttribute("listServiceName", serviceDAO.getAllService());
        } else if (roleType == 1 && statusAccpet == 0) {
            if (listServiceOfRoomGlobal != null) {
                request.setAttribute("listServiceAccept", listServiceOfRoomGlobal);

            } else {
                request.setAttribute("listServiceAccept", serviceOfRoomDAO.getServiceOfRoomAccpeted(roomDAO.getRoomsById(roomId).getLodgingHouse().getLodgingHouseId(), roomId));
            }
            request.setAttribute("listServiceName", serviceDAO.getAllService());
        }
        request.setAttribute("tenantService", contractDAO.getContractByRoomId(roomId));
        session.setAttribute("active", statusAccpet);
        request.getRequestDispatcher("view/manager/list-service-of-room.jsp").forward(request, response);

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
        String roomId = (String) session.getAttribute("roomId");
        String[] serviceIdRaw = request.getParameterValues("service");
        String serviceAction = request.getParameter("serviceAction");

        Account acc = (Account) session.getAttribute("account");
        int statusAccpet = 0;
        RoomDAO roomDAO = new RoomDAO();
        int roleType = 0;
        ServiceOfRoomDAO serviceOfRoomDAO = new ServiceOfRoomDAO();
        ServiceOfLodgingHouseDAO serviceOfLodgingHouseDAO = new ServiceOfLodgingHouseDAO();
        for (int i = 0; i < serviceIdRaw.length; i++) {
            System.out.println(Integer.parseInt(serviceIdRaw[i]));
        }
        System.out.println(session.getAttribute("active"));

        if (acc.getRoleId() == 3) {
            List<ServiceOfRoom> listCurrent = serviceOfRoomDAO.getServiceOfRoomAccpeted(roomDAO.getRoomsById(roomId).getLodgingHouse().getLodgingHouseId(), roomId);
            if (listCurrent.size() != 0) {
                System.out.println("11111111");
                if (serviceIdRaw != null) {
                    serviceOfRoomDAO.resetServiceInRoom(roomId);
                    for (int i = 0; i < serviceIdRaw.length; i++) {
//                        serviceOfRoomDAO.deleteServiceOfRoomByServiceId(roomId,Integer.parseInt(serviceIdRaw[i]));
                        ServiceOfRoom serviceOfRoom = new ServiceOfRoom();
                        serviceOfRoom.setServiceOfLodgingHouse(serviceOfLodgingHouseDAO.getAllServiceOfLodgingHouseUnique(roomDAO.getRoomsById(roomId).getLodgingHouse().getLodgingHouseId(), Integer.parseInt(serviceIdRaw[i])));
                        serviceOfRoom.setRoom(roomDAO.getRoomsById(roomId));
                        serviceOfRoom.setPrice(serviceOfLodgingHouseDAO.getAllServiceOfLodgingHouseUnique(roomDAO.getRoomsById(roomId).getLodgingHouse().getLodgingHouseId(), Integer.parseInt(serviceIdRaw[i])).getPrice());
                        serviceOfRoom.setStatusAccept(0);
                        serviceOfRoom.setStatusDelete(0);
                        serviceOfRoomDAO.insertServiceOfRoom(serviceOfRoom);
                    }
                }
            } else {
                if (serviceIdRaw != null) {
                    serviceOfRoomDAO.deleteServiceOfRoomPendingByServiceId(roomId);
                    for (int i = 0; i < serviceIdRaw.length; i++) {
                        ServiceOfRoom serviceOfRoom = new ServiceOfRoom();
                        serviceOfRoom.setServiceOfLodgingHouse(serviceOfLodgingHouseDAO.getAllServiceOfLodgingHouseUnique(roomDAO.getRoomsById(roomId).getLodgingHouse().getLodgingHouseId(), Integer.parseInt(serviceIdRaw[i])));
                        serviceOfRoom.setRoom(roomDAO.getRoomsById(roomId));
                        serviceOfRoom.setPrice(serviceOfLodgingHouseDAO.getAllServiceOfLodgingHouseUnique(roomDAO.getRoomsById(roomId).getLodgingHouse().getLodgingHouseId(), Integer.parseInt(serviceIdRaw[i])).getPrice());
                        serviceOfRoom.setStatusAccept(0);
                        serviceOfRoom.setStatusDelete(0);
                        serviceOfRoomDAO.insertServiceOfRoom(serviceOfRoom);
                    }
                }
            }
        } else if (acc.getRoleId() == 2) {
            if (serviceAction.equals("accept")) {
                listServiceOfRoomGlobal = new ArrayList<>();
                List<ServiceOfRoom> listCurrent = serviceOfRoomDAO.getServiceOfRoomAccpeted(roomDAO.getRoomsById(roomId).getLodgingHouse().getLodgingHouseId(), roomId);
                serviceOfRoomDAO.resetServiceInRoom(roomId);
                for (int i = 0; i < serviceIdRaw.length; i++) {
                    ServiceOfRoom serviceOfRoomAccept = new ServiceOfRoom();
                    serviceOfRoomAccept.setServiceOfLodgingHouse(serviceOfLodgingHouseDAO.getAllServiceOfLodgingHouseUnique(roomDAO.getRoomsById(roomId).getLodgingHouse().getLodgingHouseId(), Integer.parseInt(serviceIdRaw[i])));
                    serviceOfRoomAccept.setRoom(roomDAO.getRoomsById(roomId));
                    serviceOfRoomAccept.setPrice(serviceOfLodgingHouseDAO.getAllServiceOfLodgingHouseUnique(roomDAO.getRoomsById(roomId).getLodgingHouse().getLodgingHouseId(), Integer.parseInt(serviceIdRaw[i])).getPrice());
                    serviceOfRoomAccept.setStatusAccept(1);
                    serviceOfRoomAccept.setStatusDelete(0);
                    listServiceOfRoomGlobal.add(serviceOfRoomAccept);
                    serviceOfRoomDAO.insertServiceOfRoom(serviceOfRoomAccept);
                }
                for (ServiceOfRoom serviceOfRoom : listCurrent) {
                    listServiceOfRoomGlobal.add(serviceOfRoom);
                    serviceOfRoomDAO.insertServiceOfRoom(serviceOfRoom);
                }
            } else if (serviceAction.equals("reject")) {
                //listServiceOfRoomGlobal = new ArrayList<>();
                for (int i = 0; i < serviceIdRaw.length; i++) {
                    serviceOfRoomDAO.deleteServiceOfRoomByServiceId(roomId, Integer.parseInt(serviceIdRaw[i]));
                }
                for (ServiceOfRoom serviceOfRoom : listServiceOfRoomGlobal) {
                    serviceOfRoomDAO.insertServiceOfRoom(serviceOfRoom);
                }

            }
        }
        response.sendRedirect("service-in-room-servlet?id=" + roomId + "&roleType=" + acc.getRoleId() + "&statusAccept=" + session.getAttribute("active"));
    }

    private List<Service> getListService(Account account, RoomDAO roomDAO, ServiceOfLodgingHouseDAO serviceOfLodgingHouseDAO, ServiceDAO serviceDAO) {

        int lodgingHouseId = 0;
        List<Service> listService = new ArrayList<>();
        try {
            if (account != null) {
                lodgingHouseId = roomDAO.getRoomByAccountId(account.getAccountID()).getLodgingHouse().getLodgingHouseId();

                List<ServiceOfLodgingHouse> listServiceOfLodgingHouse = serviceOfLodgingHouseDAO.getAllServiceOfLodgingHouseByLodgingHouseId(lodgingHouseId);

                for (ServiceOfLodgingHouse serviceOfLodgingHouse : listServiceOfLodgingHouse) {
                    listService.add(serviceDAO.getServiceById(serviceOfLodgingHouse.getServiceId()));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listService;
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
