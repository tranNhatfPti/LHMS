package controller;

import dal.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.LodgingHouse;
import dal.LodgingHousesDAO;
import model.InformationOfUser;
import dal.InformationOfUserDAO;
import model.Room;
import dal.RoomDAO;
import java.util.List;
import model.Account;
import dal.AccountDAO;
import java.time.LocalDate;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author admin
 */
public class CreateContractServlet extends HttpServlet {

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
            out.println("<title>Servlet CreateContractServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateContractServlet at " + request.getContextPath() + "</h1>");
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
        LodgingHousesDAO lodgingHousesDAO = new LodgingHousesDAO();
        InformationOfUserDAO informationOfUserDAO = new InformationOfUserDAO();
        RoomDAO roomDAO = new RoomDAO();
        AccountDAO accountDAO = new AccountDAO();
        try {
            String lodgingHouseIDRaw = (String) session.getAttribute("lodgingID");
            String roomID = (String) session.getAttribute("roomId");
            int lodgingHouseID = Integer.parseInt(lodgingHouseIDRaw);
            LodgingHouse lodgingHouse = lodgingHousesDAO.getLodgingHouseById(lodgingHouseID);
            InformationOfUser manager = informationOfUserDAO.getManagerInfoByLodgingHouseId(lodgingHouseID);
            Room room = roomDAO.getRoomsById(roomID);
            List<Account> listAccount = accountDAO.getAccountByRole(3);
            for (Account account : listAccount) {
                System.out.println("------------------------------");
                System.out.println(account.getEmail());
            }

            request.setAttribute("listTenant", listAccount);
            request.setAttribute("lodgingHouse", lodgingHouse);
            request.setAttribute("manager", manager);
            request.setAttribute("room", room);
            request.getRequestDispatcher("view/manager/create-contract.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            System.out.println(e);
            System.out.println("dm");
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

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy");
        SimpleDateFormat formatterDate = new SimpleDateFormat("MM-dd-yyyy");
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        LodgingHousesDAO lodgingHousesDAO = new LodgingHousesDAO();
        RoomDAO roomDAO = new RoomDAO();
        AccountDAO accountDAO = new AccountDAO();
        InformationOfUserDAO informationOfUserDAO = new InformationOfUserDAO();
        String check = request.getParameter("check");
        String deposit = request.getParameter("deposit");
        String roomId = request.getParameter("room");
        String managerIdRaw = request.getParameter("managerId");
        String email = request.getParameter("email");
        String dateFromRaw = request.getParameter("dateFrom");
        String dateToRaw = request.getParameter("dateTo");
        LocalDate dateFromParse = LocalDate.parse(dateFromRaw);
        LocalDate dateToParse = LocalDate.parse(dateToRaw);
        String dateFrom = dateFromParse.format(formatter);
        String dateTo = dateToParse.format(formatter);
        if(deposit == null){
            System.out.println("deposit null");
        }
        if(roomId == null){
            System.out.println("roomId null");
        }
        if(managerIdRaw  == null){
            System.out.println("managerIdRaw  null");
        }
        if(email == null){
            System.out.println("email null");
        }
        if(dateFromRaw == null){
            System.out.println("dateFromRaw null");
        }
        if(dateToRaw == null){
            System.out.println("dateToRaw null");
        }
        if(deposit == null){
            System.out.println("deposit null");
        }
        try {
            int deposit2 = Integer.parseInt(deposit);

            Room room = roomDAO.getRoomsById(roomId);
            int lodgingHouseId = room.getLodgingHouse().getLodgingHouseId();
            int managerId = room.getLodgingHouse().getManageId();
            LodgingHouse lodgingHouse = lodgingHousesDAO.getLodgingHouseById(lodgingHouseId);
            Account tenant = accountDAO.getAccountByUserEmail(email);
            InformationOfUser informationOfTenant = informationOfUserDAO.getInformationByAccountID(tenant.getAccountID());
            InformationOfUser informationOfManager = informationOfUserDAO.getInformationByAccountID(managerId);
             System.out.println(managerId);
             System.out.println(roomId);
             System.out.println(lodgingHouseId);
             System.out.println(lodgingHouse.getManageId());
             
            // Tạo nội dung hợp đồng dưới dạng HTML với CSS inline
            StringBuilder contractContent = new StringBuilder();
            contractContent.append("<!DOCTYPE html>");
            contractContent.append("<html>");
            contractContent.append("<head>");
            contractContent.append("<meta charset=\"UTF-8\">");
            contractContent.append("<title>Hợp đồng thuê phòng trọ</title>");
            contractContent.append("<style>");
            contractContent.append("body { font-family: Arial, sans-serif; line-height: 1.6; margin: 20px auto; width: 80%; text-align: justify; }");
            contractContent.append(".header { text-align: center; margin-bottom: 20px; }");
            contractContent.append(".content { text-indent: 20px; }");
            contractContent.append(".left-align { text-align: left; }");
            contractContent.append("</style>");
            contractContent.append("</head>");
            contractContent.append("<body>");
            contractContent.append("<div class=\"header\">");
            contractContent.append("<p><strong>CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM</strong><br>Độc lập – Tự do – Hạnh phúc</p>");
            contractContent.append("<p><strong>HỢP ĐỒNG THUÊ PHÒNG TRỌ</strong></p>");
            contractContent.append("</div>");
            contractContent.append("<div class=\"content\">");
            contractContent.append("<p class=\"left-align\">Hôm nay ngày " + currentDate.getDayOfMonth() + " tháng " + currentDate.getMonthValue() + " năm " + currentDate.getYear() + ";</p>");
            contractContent.append("<p class=\"left-align\">Tại địa chỉ: " + lodgingHouse.getAddressDetail() + " - " + lodgingHouse.getWard() + " - " + lodgingHouse.getDistrict() + " - " + lodgingHouse.getProvince() + "</p>");
            contractContent.append("<p class=\"left-align\">……………………………………………………………………………………………………………………………………</p>");
            contractContent.append("<p class=\"left-align\">Chúng tôi gồm:</p>");
            contractContent.append("<p class=\"left-align\">1. Đại diện bên cho thuê phòng trọ (Bên A):</p>");
            contractContent.append("<table style=\"border-collapse: collapse; width: 100%;\">");
            contractContent.append("<tr><td style=\"border: none; padding: 8px;width: 20%;\">Ông/bà:</td><td style=\"border: none; padding: 8px;\">"
                    + informationOfManager.getFullName() + "</td></tr>");
            contractContent.append("<tr><td style=\"border: none; padding: 8px;\">Sinh ngày:</td><td style=\"border: none; padding: 8px;\">"
                    + formatterDate.format(informationOfManager.getDob()) + "</td></tr>");
            contractContent.append("<tr><td style=\"border: none; padding: 8px;\">Số CMND:</td><td style=\"border: none; padding: 8px;\">"
                    + informationOfManager.getCic() + "</td></tr>");
            contractContent.append("<tr><td style=\"border: none; padding: 8px;\">Số điện thoại:</td><td style=\"border: none; padding: 8px;\">"
                    + informationOfManager.getPhoneNumber() + "</td></tr>");
            contractContent.append("</table>");
            
            contractContent.append("<p class=\"left-align\">2. Bên thuê phòng trọ (Bên B):</p>");
            contractContent.append("<table style=\"border-collapse: collapse; width: 100%;\">");
            contractContent.append("<tr><td style=\"border: none; padding: 8px;width: 20%;\">Ông/bà:</td><td style=\"border: none; padding: 8px;\">"
                    + informationOfTenant.getFullName() + "</td></tr>");
            contractContent.append("<tr><td style=\"border: none; padding: 8px;\">Sinh ngày:</td><td style=\"border: none; padding: 8px;\">"
                    + formatterDate.format(informationOfTenant.getDob()) + "</td></tr>");
            contractContent.append("<tr><td style=\"border: none; padding: 8px;\">Số CMND:</td><td style=\"border: none; padding: 8px;\">"
                    + informationOfTenant.getCic() + "</td></tr>");
            contractContent.append("<tr><td style=\"border: none; padding: 8px;\">Số điện thoại:</td><td style=\"border: none; padding: 8px;\">"
                    + informationOfTenant.getPhoneNumber() + "</td></tr>");
            contractContent.append("</table>");
            
            contractContent.append("<p class=\"left-align\">Sau khi bàn bạc trên tinh thần dân chủ, hai bên cùng có lợi, cùng thống nhất như sau:</p>");
            contractContent.append("<p class=\"left-align\">Bên A đồng ý cho bên B thuê 01 phòng ở tại địa chỉ:</p>");
            contractContent.append("<p class=\"left-align\">" + lodgingHouse.getAddressDetail() + " - " + lodgingHouse.getWard() + " - " + lodgingHouse.getDistrict() + " - " + lodgingHouse.getProvince() + ".</p>");
            contractContent.append("<p class=\"left-align\">Giá thuê: " + numberFormat.format(room.getPrice()) + " VND/tháng<br>");
            contractContent.append("    Tiền cọc: " + numberFormat.format(deposit2) + " VND<br>");
            contractContent.append("………………………………………………………………………………....…<br>");
            contractContent.append("Hợp đồng có giá trị kể từ ngày " + dateFrom + " đến ngày " + dateTo + ".</p>");
            contractContent.append("<p class=\"left-align\"><strong>TRÁCH NHIỆM CỦA CÁC BÊN</strong></p>");
            contractContent.append("<p class=\"left-align\">* Trách nhiệm của bên A:</p>");
            contractContent.append("<p class=\"left-align\">- Tạo mọi điều kiện thuận lợi để bên B thực hiện theo hợp đồng.</p>");
            contractContent.append("<p class=\"left-align\">- Cung cấp nguồn điện, nước, wifi cho bên B sử dụng.</p>");
            contractContent.append("<p class=\"left-align\">* Trách nhiệm của bên B:</p>");
            contractContent.append("<p class=\"left-align\">- Thanh toán đầy đủ các khoản tiền theo đúng thỏa thuận.</p>");
            contractContent.append("<p class=\"left-align\">- Bảo quản các trang thiết bị và cơ sở vật chất của bên A trang bị cho ban đầu (làm hỏng phải sửa, mất phải đền).</p>");
            contractContent.append("<p class=\"left-align\">- Không được tự ý sửa chữa, cải tạo cơ sở vật chất khi chưa được sự đồng ý của bên A.</p>");
            contractContent.append("<p class=\"left-align\">- Giữ gìn vệ sinh trong và ngoài khuôn viên của phòng trọ.</p>");
            contractContent.append("<p class=\"left-align\">- Bên B phải chấp hành mọi quy định của pháp luật Nhà nước và quy định của địa phương.</p>");
            contractContent.append("<p class=\"left-align\">- Nếu bên B cho khách ở qua đêm thì phải báo và được sự đồng ý của chủ nhà đồng thời phải chịu trách nhiệm về các hành vi vi phạm pháp luật của khách trong thời gian ở lại.</p>");
            contractContent.append("<p class=\"left-align\"><strong>TRÁCH NHIỆM CHUNG</strong></p>");
            contractContent.append("<p class=\"left-align\">- Hai bên phải tạo điều kiện cho nhau thực hiện hợp đồng.</p>");
            contractContent.append("<p class=\"left-align\">- Trong thời gian hợp đồng còn hiệu lực nếu bên nào vi phạm các điều khoản đã thỏa thuận thì bên còn lại có quyền đơn phương chấm dứt hợp đồng; nếu sự vi phạm hợp đồng đó gây tổn thất cho bên bị vi phạm hợp đồng thì bên vi phạm hợp đồng phải bồi thường thiệt hại.</p>");
            contractContent.append("<p class=\"left-align\">- Một trong hai bên muốn chấm dứt hợp đồng trước thời hạn thì phải báo trước cho bên kia ít nhất 30 ngày và hai bên phải có sự thống nhất.</p>");
            contractContent.append("<p class=\"left-align\">- Bên A phải trả lại tiền đặt cọc cho bên B.</p>");
            contractContent.append("<p class=\"left-align\">- Bên nào vi phạm điều khoản chung thì phải chịu trách nhiệm trước pháp luật.</p>");
            contractContent.append("<p class=\"left-align\">- Hợp đồng được lập thành 02 bản có giá trị pháp lý như nhau, mỗi bên giữ một bản.</p>");
            contractContent.append("<p class=\"left-align\">ĐẠI DIỆN BÊN B: " + informationOfTenant.getFullName() + "</p>");
            contractContent.append("<p class=\"left-align\">ĐẠI DIỆN BÊN A: " + informationOfManager.getFullName() + "</p>");
            contractContent.append("</div>");
            contractContent.append("</body>");
            contractContent.append("</html>");
            // In ra màn hình
            PrintWriter out = response.getWriter();
            out.println(contractContent);

            out.println("<div class=\"modal-footer\">");
            if (check == null) {
                out.println("<button type=\"button\" class=\"btn btn-secondary\" onclick=\"submitForm()\">Gửi hợp đồng</button>");
            }          
            out.println("</div>");

        } catch (Exception e) {
            System.out.println(e);
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
