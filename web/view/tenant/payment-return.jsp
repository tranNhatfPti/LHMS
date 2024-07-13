<%-- 
    Document   : payment-return
    Created on : Jun 15, 2024, 3:57:31 PM
    Author     : ASUS ZenBook
--%>

<%@page import="java.net.URLEncoder"%>
<%@page import="java.nio.charset.StandardCharsets"%>
<%@page import="utils.Config"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="jakarta.servlet.http.HttpSession"%>
<%@page import="dal.BillDAO"%>
<%@page import="dal.RoomDAO"%>
<%@page import="dal.ReceiptDAO"%>
<%@page import="dal.ContractDAO"%>
<%@page import="model.Account"%>
<%@page import="model.Receipt"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <meta name="description" content="">
        <meta name="author" content="">
        <title>KẾT QUẢ THANH TOÁN</title>
        <!-- Bootstrap core CSS -->
        <link href="/vnpay_jsp/assets/bootstrap.min.css" rel="stylesheet"/>
        <!-- Custom styles for this template -->
        <link href="/vnpay_jsp/assets/jumbotron-narrow.css" rel="stylesheet"> 
        <script src="/vnpay_jsp/assets/jquery-1.11.3.min.js"></script>

        <style>
            body {
                background-color: #f1f1f1;
            }

            div{
                display: flex;
                justify-content: center;
            }

            h2{
                font-family: cursive;
            }

            .beautiful-button {
                position: relative;
                display: inline-block;
                background: linear-gradient(to bottom, #1b1c3f, #4a4e91);
                /* Gradient background */
                color: white;
                /* White text color */
                font-family: "Segoe UI", sans-serif;
                /* Stylish and legible font */
                font-weight: bold;
                font-size: 15px;
                /* Large font size */
                border: none;
                /* No border */
                border-radius: 30px;
                /* Rounded corners */
                padding: 10px 20px;
                /* Large padding */
                cursor: pointer;
                /* Cursor on hover */
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
                /* Subtle shadow */
                animation: button-shimmer 2s infinite;
                transition: all 0.3s ease-in-out;
                /* Smooth transition */
            }

            /* Hover animation */
            .beautiful-button:hover {
                background: linear-gradient(to bottom, #2c2f63, #5b67b7);
                animation: button-particles 1s ease-in-out infinite;
                transform: translateY(-2px);
            }

            /* Click animation */
            .beautiful-button:active {
                transform: scale(0.95);
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
            }

            /* Shimmer animation */
            @keyframes button-shimmer {
                0% {
                    background-position: left top;
                }

                100% {
                    background-position: right bottom;
                }
            }

            /* Particle animation */
            @keyframes button-particles {
                0% {
                    background-position: left top;
                }

                100% {
                    background-position: right bottom;
                }
            }
        </style>
    </head>
    <body>
        <%
            BillDAO bd = new BillDAO();
            RoomDAO rd = new RoomDAO();
            ContractDAO cd = new ContractDAO();
            ReceiptDAO red = new ReceiptDAO();
            HttpSession s = request.getSession();
            
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
            
            Account account = (Account) s.getAttribute("account");
            int accountId = account.getAccountID();
            
            //Begin process return from VNPAY
            Map fields = new HashMap();
            for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
                String fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
                String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    fields.put(fieldName, fieldValue);
                }
            }

            String vnp_SecureHash = request.getParameter("vnp_SecureHash");
            if (fields.containsKey("vnp_SecureHashType")) {
                fields.remove("vnp_SecureHashType");
            }
            if (fields.containsKey("vnp_SecureHash")) {
                fields.remove("vnp_SecureHash");
            }
            String signValue = Config.hashAllFields(fields);

        %>
        <!--Begin display -->
        <%
            if (signValue.equals(vnp_SecureHash)) {
                if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
                // success
                    int paid = (int) Double.parseDouble(request.getParameter("vnp_Amount").substring(0, request.getParameter("vnp_Amount").length() - 2));
                    String orderInfo = request.getParameter("vnp_OrderInfo");
                    String[] inforBill = orderInfo.split("&&");
                    
                    String roomId = inforBill[0];
                    String monthYear = inforBill[1];
                    
                    bd.updateBillAfterPay(roomId, monthYear, paid);
                    
                    String description = "Phòng " + rd.getRoomsById(roomId).getRoomName() + " thanh toán tiền phòng và dịch vụ tháng " + monthYear;
                    Date currentDate = new Date();
                    
                    int lodgingHouseId = rd.getRoomsById(roomId).getLodgingHouse().getLodgingHouseId();
                    
                    Receipt receipt = new Receipt(paid, currentDate, description, accountId, lodgingHouseId, true);
                    // insert
                    red.insertReceiptFromBill(receipt);
        %>
        <div>
            <h2 style="color: #00c789;">Thanh Toán Thành Công!</h2>
        </div>  
        <%
                } else {
        %>
        <div>
            <h2 style="color: red">Thanh Toán Thất Bại!</h2>
        </div> 
        <%
                // faild
                }
            } else {
                out.print("invalid signature");
            }
        %>
        <div style="display: ruby-text; margin-top: 10px; font-family: monospace; color: #6b6b6b;">
            <p>Cảm ơn quý khách đã sử dụng hệ thống quản lý phòng trọ LHMS</p><br>
            <p>Mọi thắc mắc xin liên hệ qua Email: <strong style="color: red">lhmsswp391@gmail.com</strong></p>
        </div>
        <div style="margin-top: 10px">
            <button class="beautiful-button" onclick="redirectToHomePage()">
                Trang chủ
            </button>
        </div>

        <script type="text/javascript">
            function redirectToHomePage() {
                window.location.href = '${pageContext.request.contextPath}/room?service=showRoomInfor';
            }
        </script>
    </body>
</html>

