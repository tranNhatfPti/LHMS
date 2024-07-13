package controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import dal.InformationOfUserDAO;
import dal.LodgingHousesDAO;
import dal.RoomDAO;
import dal.TenancyDepositDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import model.Account;

/**
 *
 * @author Admin
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50)
public class AddTenancyDeposit extends HttpServlet {

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
            out.println("<title>Servlet AddTenancyDeposit</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddTenancyDeposit at " + request.getContextPath() + "</h1>");
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
        String roomID = request.getParameter("roomId");
        LodgingHousesDAO lodgingHouseDAO = new LodgingHousesDAO();
        InformationOfUserDAO informationOfUserDAO = new InformationOfUserDAO();
        Account acc = (Account) session.getAttribute("account");
        RoomDAO roomDAO = new RoomDAO();
        int lodgingHouseID = 0;
        String lodgingHouseIDRaw = (String) session.getAttribute("lodgingID");
        if (lodgingHouseIDRaw != null) {
            lodgingHouseID = Integer.parseInt(lodgingHouseIDRaw);
        }
        request.setAttribute("accountManager", acc);
        request.setAttribute("inforOfManager", informationOfUserDAO.getInformationByAccountID(acc.getAccountID()));
        request.setAttribute("lodgingHouseById", lodgingHouseDAO.getLodgingHouseById(lodgingHouseID));
        //session.setAttribute("roomId", roomID);
        request.setAttribute("roomByID", roomDAO.getRoomsById(roomID));
        request.getRequestDispatcher("view/manager/add-tenancy-deposit.jsp").forward(request, response);
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
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession sesseion = request.getSession();
        TenancyDepositDAO tenancyDepositDAO = new TenancyDepositDAO();
        
        LocalDate currentDate = LocalDate.now();
        Date bookingDateRaw = java.sql.Date.valueOf(currentDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String bookingDate = dateFormat.format(bookingDateRaw);
        
        LocalDate futureDate = currentDate.plusDays(15);
        Date arriveDateRaww = java.sql.Date.valueOf(futureDate);
        String arriveDate = dateFormat.format(arriveDateRaww);
        
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String depositMoneyRaw = request.getParameter("depositMoney");
        String dateOfBirthRaw = request.getParameter("dateOfBirth");
        String cic = request.getParameter("cic");
        String description = request.getParameter("description");
        //String roomIdRaw = (String) sesseion.getAttribute("roomId");
        String roomIdRaw = request.getParameter("roomId");
        double depositMoney = 0;
        
        try {
            if(roomIdRaw != null && depositMoneyRaw != null && dateOfBirthRaw != null) {
                depositMoney = Double.parseDouble(depositMoneyRaw);
                boolean result = tenancyDepositDAO.insertTenancyDeposit(name, email, 
                        phoneNumber, dateOfBirthRaw, bookingDate,
                        arriveDate, depositMoney, roomIdRaw,
                        description, 0,0, cic);
                if(result) {
                    response.sendRedirect("view-tenancy-deposit?roomId=" + sesseion.getAttribute("roomId"));
                } else {
                    request.setAttribute("notice", "Thêm không thành công !");
                    request.getRequestDispatcher("view/manager/add-tenancy-deposit.jsp").forward(request, response);
                } 
            } else {
                request.setAttribute("notice", "Thêm không thành công !");
                request.getRequestDispatcher("view/manager/add-tenancy-deposit.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //
        
        //try {           
//            Part part = request.getPart("file");
//            String realPath = request.getServletContext().getRealPath("/uploads");
//            String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
//            if (!Files.exists(Paths.get(realPath))) {
//                Files.createDirectories(Paths.get(realPath));
//            }
//            part.write(realPath + "/" + fileName);
//            Path filePath = Paths.get(realPath, fileName);
//            try (InputStream fileContent = part.getInputStream()) {
//                //System.out.println(fileContent);
//                Files.copy(fileContent, filePath, StandardCopyOption.REPLACE_EXISTING);
//            }
//
//            // Ghi nội dung vào file
//            String templateContent;
//            try (InputStream templateInputStream = new FileInputStream(filePath.toFile())) {
//                
//                templateContent = new String(templateInputStream.readAllBytes(), StandardCharsets.UTF_8);
//            }
//            
//            // Thay thế các placeholder bằng dữ liệu người dùng
//            templateContent = templateContent.replace("<<fullNameA>>", name)
//                    .replace("<<emailA>>", email)
//                    .replace("<<phoneNumberA>>", phoneNumber)
//                    .replace("<<depositMoney>>", depositMoney)
//                    .replace("<<dateOfBirthA>>", dateOfBirth)
//                    .replace("<<cicA>>", cic)
//                    .replace("<<arriveDate>>", arriveDate);
//            System.out.println("4444444444444444444444444444444444444444444444444444444444444444444444444444444444");
//            System.out.println(templateContent);
//            try (OutputStream outputStream = new FileOutputStream(filePath.toFile())) {
//                byte[] bytes = templateContent.getBytes(StandardCharsets.UTF_8);
//                System.out.println("4444444444444444444444444444444444444444444444444444444444444444444444444444444444");
//                outputStream.write(bytes);
//            }
//            //System.out.println(templateContent);
//            // Phản hồi file về cho người dùng
//            System.out.println("Gửi file về cho người dùng...");
//            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
//            response.setHeader("Content-Disposition", "attachment; filename=filled111_document.doc");
//            try (InputStream fileInputStream = new FileInputStream(fileName)) {
//                byte[] buffer = new byte[4096];
//                int bytesRead;
//                System.out.println("");
//                 System.out.println("1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
//                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
//                    response.getOutputStream().write(buffer, 0, bytesRead);
//                    System.out.println("");
//                    System.out.println("1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            System.out.println("1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
//            System.out.println("File đã được gửi xong.");
//            // Xóa tệp sau khi đã gửi cho người dùng
//            //Files.deleteIfExists(filePath);
//            //Files.deleteIfExists(filledFilePath);
//            Files.deleteIfExists(filePath);
//
//            // Phản hồi tin nhắn thành công cho người dùng
//            request.setAttribute("message", "File has been processed and downloaded successfully.");
//            request.getRequestDispatcher("view/manager/add-tenancy-deposit.jsp").forward(request, response);
//        } catch (Exception e) {
//            request.setAttribute("message", "There was an error processing the file.");
//            request.getRequestDispatcher("view/manager/add-tenancy-deposit.jsp").forward(request, response);
////        }
//
//        }

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
