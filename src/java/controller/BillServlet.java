/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountDAO;
import dal.AccountInRoomDAO;
import dal.BillDAO;
import dal.ContractDAO;
import dal.InformationOfUserDAO;
import dal.ReceiptDAO;
import dal.RoomDAO;
import dal.ServiceOfRoomDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import model.Account;
import model.Bill;
import model.Contract;
import model.InformationOfUser;
import model.Receipt;
import model.Room;
import model.ServiceOfRoom;
import utils.EmailService;
import utils.WebSocket;

/**
 *
 * @author ASUS ZenBook
 */
@WebServlet(name = "BillServlet", urlPatterns = {"/bill"})
public class BillServlet extends HttpServlet {

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
            out.println("<title>Servlet BillServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BillServlet at " + request.getContextPath() + "</h1>");
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
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        AccountDAO ad = new AccountDAO();
        InformationOfUserDAO iud = new InformationOfUserDAO();
        ServiceOfRoomDAO srd = new ServiceOfRoomDAO();
        BillDAO bd = new BillDAO();
        RoomDAO rd = new RoomDAO();

        String service = request.getParameter("service");
        int lodgingHouseId = (int) session.getAttribute("lodgingID");

        if (service == null) {
            Calendar currentDate = Calendar.getInstance();
            String monthYear = formatter.format(currentDate.getTime());

            redirectToSlhManage(lodgingHouseId, monthYear, request, response);
        } else {
            if (service.equals("viewInforOfTenant")) {
                int tenantId = Integer.parseInt(request.getParameter("tenantId"));

                Account accountOfTenant = ad.getAccountById(tenantId);
                InformationOfUser inforOfTenant = iud.getInformationByAccountID(tenantId);

                request.setAttribute("accountOfTenant", accountOfTenant);
                request.setAttribute("inforOfTenant", inforOfTenant);
                request.getRequestDispatcher("/view/manager/Information-of-tenant.jsp").forward(request, response);
            }

            if (service.equals("deleteBill")) {
                String roomId = request.getParameter("roomId");
                String monthYear = request.getParameter("monthYear");

                // Thiết lập kiểu nội dung phản hồi
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");

                if (bd.deleteBill(roomId, monthYear) != 0) {
                    // Gửi phản hồi về client
                    out.write("OK");
                } else {
                    // Gửi phản hồi về client
                    out.write("ERROR");
                }
            }

            if (service.equals("insertBill")) {
                String roomId = request.getParameter("roomId");
                String monthYear = request.getParameter("monthYear");
                String oldWater_string = request.getParameter("oldWater");
                String oldElectronic_string = request.getParameter("oldElectronic");
                String newWater_string = request.getParameter("newWater");
                String newElectronic_string = request.getParameter("newElectronic");
                String status_string = request.getParameter("status");
                String paid_string = request.getParameter("paid");

                double oldWater = Double.parseDouble(oldWater_string);
                double oldElectronic = Double.parseDouble(oldElectronic_string);
                double newWater = Double.parseDouble(newWater_string);
                double newElectronic = Double.parseDouble(newElectronic_string);
                int status = Integer.parseInt(status_string);
                double paid;
                if (paid_string.isBlank()) {
                    paid_string = "0";
                }
                paid = Double.parseDouble(paid_string);

                double priceWater = 0;
                double priceElectronic = 0;
                double priceOtherServices = 0;

                List<ServiceOfRoom> listOfSR = srd.getAllServiceOfRoomByRoomId(roomId);
                for (ServiceOfRoom serviceOfRoom : listOfSR) {
                    if (serviceOfRoom.getServiceId() == 1) {
                        priceElectronic = serviceOfRoom.getPrice() * (newElectronic - oldElectronic);
                    }

                    if (serviceOfRoom.getServiceId() == 2) {
                        priceWater = serviceOfRoom.getPrice() * (newWater - oldWater);
                    }

                    if (serviceOfRoom.getServiceId() == 3 || serviceOfRoom.getServiceId() == 4 || serviceOfRoom.getServiceId() == 5) {
                        priceOtherServices += serviceOfRoom.getPrice();
                    }
                }

                Room room = rd.getRoomsById(roomId);
                double totalAmount = (room.getPrice() + priceWater + priceElectronic + priceOtherServices);
                // trường hợp nhập paid lớn hơn totalAmount
                if (paid > totalAmount) {
                    paid = totalAmount;
                }
                double missing = totalAmount - paid;

                Bill bill = new Bill(roomId, monthYear, oldWater, newWater, priceWater, oldElectronic,
                        newElectronic, priceElectronic, missing, paid, status, priceOtherServices);

                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                if (bd.getBillByRoomIdAndMonthYear(roomId, monthYear) != null) {
                    out.write("EXISTING");
                } else {
                    if (bd.createBill(bill) != 0) {
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
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        AccountDAO ad = new AccountDAO();
        InformationOfUserDAO iu = new InformationOfUserDAO();
        BillDAO bd = new BillDAO();
        RoomDAO rd = new RoomDAO();
        ContractDAO cd = new ContractDAO();
        ReceiptDAO red = new ReceiptDAO();
        ServiceOfRoomDAO srd = new ServiceOfRoomDAO();
        AccountInRoomDAO ard = new AccountInRoomDAO();

        String service = request.getParameter("service");
        int lodgingHouseId = (int) session.getAttribute("lodgingID");

        try {
            if (service.equals("searchBill")) {
                String monthYear = request.getParameter("searchDate");
                String roomId = request.getParameter("roomId");
                String status_string = request.getParameter("status");
                int status = Integer.parseInt(status_string);

//                out.print(monthYear + "" + roomId + "" + status_string);
                List<Bill> listOfBill = bd.searchBillOfManager(monthYear, roomId, status, lodgingHouseId);

                request.setAttribute("monthYear", monthYear);
                request.setAttribute("roomId", roomId);
                request.setAttribute("status", status);
                request.setAttribute("listOfBill", listOfBill);
                request.getRequestDispatcher("/view/manager/bill-manage.jsp").forward(request, response);
            }

            if (service.equals("updateBill")) {
                String roomId = request.getParameter("roomId");
                String monthYear = request.getParameter("monthYear");
                String newWater_string = request.getParameter("newWater");
                String newElectronic_string = request.getParameter("newElectronic");
                String status_string = request.getParameter("status");
                String paid_string = request.getParameter("paid");
                String statusCreate = request.getParameter("statusCreate");

                int status;
                if (status_string == null) {
                    status_string = "1";
                }
                if (statusCreate == null) {
                    statusCreate = "";
                }

                status = Integer.parseInt(status_string);

                Bill billBeforeCalculate = bd.getBillByRoomIdAndMonthYear(roomId, monthYear);

                double newWater;
                double newElectronic;
                // null khi bị disabled
                if (newWater_string == null && newElectronic_string == null) {
                    newWater = billBeforeCalculate.getWaterNew();
                    newElectronic = billBeforeCalculate.getElectronicNew();
                } else {
                    newWater = Double.parseDouble(newWater_string);
                    newElectronic = Double.parseDouble(newElectronic_string);
                }

                double oldWater = billBeforeCalculate.getWaterOld();
                double oldElectronic = billBeforeCalculate.getElectronicOld();

                double priceWater = 0;
                double priceElectronic = 0;
                double priceOtherServices = 0;

                List<ServiceOfRoom> listOfSR = srd.getAllServiceOfRoomByRoomId(roomId);
                for (ServiceOfRoom serviceOfRoom : listOfSR) {
                    if (serviceOfRoom.getServiceId() == 1) {
                        priceElectronic = serviceOfRoom.getPrice() * (newElectronic - oldElectronic);
                    }

                    if (serviceOfRoom.getServiceId() == 2) {
                        priceWater = serviceOfRoom.getPrice() * (newWater - oldWater);
                    }

                    if (serviceOfRoom.getServiceId() == 3 || serviceOfRoom.getServiceId() == 4 || serviceOfRoom.getServiceId() == 5) {
                        priceOtherServices += serviceOfRoom.getPrice();
                    }
                }

                // sau khi tính toán thì cập nhật lại
                billBeforeCalculate.setWaterNew(newWater);
                billBeforeCalculate.setElectronicNew(newElectronic);
                billBeforeCalculate.setPriceWater(priceWater);
                billBeforeCalculate.setPriceElectronic(priceElectronic);
                billBeforeCalculate.setPriceOtherServices(priceOtherServices);

                // chưa thanh toán
                Room room = rd.getRoomsById(roomId);
                double totalAmount = (room.getPrice() + priceWater + priceElectronic + priceOtherServices);
                if (billBeforeCalculate.getPaid() == 0) {
                    billBeforeCalculate.setMissing(totalAmount);

                    // khi update số điện và số nước NULL hoặc update lại
                    // chỉ gửi mail thông báo khi không chọn đã thanh toán hoặc thành toán 1 phần
                    if (status == 1) {
                        Date date = formatter.parse(monthYear);
                        Contract currentContract = cd.getContractByDateAndRoomID(roomId, date);

                        // nếu tồn tại hợp đồng của phòng này thì mới gửi được mail
                        if (currentContract != null) {
                            int tenantId = currentContract.getTenantId().getAccountID();
                            Account accountSendMail = ad.getAccountById(tenantId);
                            InformationOfUser inforAccountSendMail = iu.getInformationByAccountID(tenantId);

                            String mailTo = accountSendMail.getEmail();
                            String fullName = inforAccountSendMail.getFullName();

                            // gửi mail
                            EmailService.sendMailToNotifyPayment(mailTo, monthYear, fullName, totalAmount);
                            
                            // gửi tbao sang UI of Tenant
                            WebSocket.broadcast(tenantId, roomId, monthYear);
                        }

                        // tạo mới bill tháng sau
                        if (statusCreate.equals("createBillForNewMonth")) {
                            // kiểm tra xem monthYear có đúng định dạng yyyy-MM không
                            LocalDate nextMonth_date;
                            if (isValidYearMonth(monthYear)) {
                                nextMonth_date = LocalDate.parse(monthYear + "-01");
                            } else {
                                nextMonth_date = LocalDate.parse(monthYear);
                            }
                            nextMonth_date = nextMonth_date.plusMonths(1);

                            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM");
                            String nextMonth = nextMonth_date.format(format);

                            // xét th update lại số liệu điện và nước mới
                            Bill billOfNextMonth = bd.getBillByRoomIdAndMonthYear(roomId, nextMonth);
                            if (billOfNextMonth != null) {
                                // != null: đã có 1 bill của tháng sau
                                // update lại số điện và nước mới của tháng sau
                                billOfNextMonth.setElectronicOld(newElectronic);
                                billOfNextMonth.setWaterOld(newWater);

                                bd.updateBill(billOfNextMonth);
                            } else {
                                // insert bill
                                bd.createBillForNewMonth(roomId, nextMonth, newWater, newElectronic, 1);
                            }
                        }
//                        // tạo bill mới cho tháng hiện tại
//                        else {
//                            // lấy thời gian hiện tại
//                            String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//                            // tạo bill trong tháng
//                            bd.createBillForNewMonth(roomId, currentDate, newWater, newElectronic, 1);
//                        }
                    }
                }

                // đã thanh toán
                if (status == 2) {
                    // khi set đã thanh toán, phải tính được số tiền còn lại phải trả trước khi đã thanh toán để lưu vào receipt
                    int paid = (int) billBeforeCalculate.getMissing();
                    String description = "Phòng " + rd.getRoomsById(roomId).getRoomName() + " thanh toán tiền phòng và dịch vụ tháng " + monthYear;
                    Date currentDate = new Date();

                    int accountId = 0;
                    Date date = formatter.parse(monthYear);
                    Contract contract = cd.getContractByDateAndRoomID(roomId, date);
                    if (contract != null) {
                        accountId = contract.getTenantId().getAccountID();
                    }

                    Receipt receipt = new Receipt(paid, currentDate, description, accountId, lodgingHouseId, true);
                    // insert
                    red.insertReceiptFromBill(receipt);

                    // set lại thông tin bill sau khi đổi status
                    billBeforeCalculate.setMissing(0);
                    billBeforeCalculate.setPaid(totalAmount);
                    billBeforeCalculate.setStatus(2);
                }

                // đã thanh toán 1 phần
                if (status == 3) {
                    int paid;

                    if (paid_string == null) {
                        paid_string = "0";
                    }
                    paid = (int) Double.parseDouble(paid_string);

                    // lưu vào receipt
                    String description = "Phòng " + rd.getRoomsById(roomId).getRoomName() + " thanh toán tiền phòng và dịch vụ tháng " + monthYear;
                    Date currentDate = new Date();

                    int accountId = 0;
                    Date date = formatter.parse(monthYear);
                    Contract contract = cd.getContractByDateAndRoomID(roomId, date);
                    if (contract != null) {
                        accountId = contract.getTenantId().getAccountID();
                    }

                    Receipt receipt = new Receipt(paid, currentDate, description, accountId, lodgingHouseId, true);
                    // insert
                    red.insertReceiptFromBill(receipt);

                    // cộng thêm tiền đã trả
                    billBeforeCalculate.setPaid(billBeforeCalculate.getPaid() + paid);
                    // trừ đi tiền còn thiếu
                    billBeforeCalculate.setMissing(billBeforeCalculate.getMissing() - paid);

                    if (billBeforeCalculate.getMissing() == 0) {
                        billBeforeCalculate.setStatus(2);
                    } else {
                        billBeforeCalculate.setStatus(3);
                    }
                }

                bd.updateBill(billBeforeCalculate);
                redirectToSlhManage(lodgingHouseId, monthYear, request, response);
            }

        } catch (Exception e) {
            out.print(e.getMessage());
            e.printStackTrace();
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

    public void redirectToSlhManage(int lodgingHouseId, String monthYear, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BillDAO bd = new BillDAO();

        List<Bill> listOfBill = bd.getBillByLodgingHouseIDAndMonthYear(lodgingHouseId, monthYear);

        request.setAttribute("monthYear", monthYear);
        request.setAttribute("listOfBill", listOfBill);
        request.getRequestDispatcher("/view/manager/bill-manage.jsp").forward(request, response);
    }

    public static boolean isValidYearMonth(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        try {
            YearMonth.parse(dateString, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println(isValidYearMonth("2024-06"));
    }

}
