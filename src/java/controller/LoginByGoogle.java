package controller;

import utils.Constants;
import utils.EmailService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dal.AccountDAO;
import dal.ContractDAO;
import dal.LodgingHousesDAO;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import model.Account;
import model.Contract;
import model.UserGoogleDTO;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

/**
 *
 * @author ASUS ZenBook
 */
@WebServlet(name = "LoginByGoogle", urlPatterns = {"/LoginByGoogle"})
public class LoginByGoogle extends HttpServlet {

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
            out.println("<title>Servlet LoginByGoogle</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginByGoogle at " + request.getContextPath() + "</h1>");
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
        ServletContext servletContext = getServletContext();
        response.setContentType("text/html;charset=UTF-8");
        AccountDAO ad = new AccountDAO();
        LodgingHousesDAO lhd = new LodgingHousesDAO();

        // sau khi người dùng gửi yêu cầu đăng nhập lên Google, gg sẽ trả về tham số code(refresh token)
        String code = request.getParameter("code");

        // gửi refresh token lên gg và gg sẽ trả về 1 authenication token
        String authenication_token = getToken(code);

        // sau khi có authenication token, sẽ gửi authenication token lên gg, gg sẽ trả về thông tin người dùng
        UserGoogleDTO userInf = getUserInfo(authenication_token);

        // lấy thông tin email, name được trả về từ gg
        String email = userInf.getEmail();
        String fullname = userInf.getName();

        // kiểm tra email này đã đăng kí bằng username hoặc gg trước đó hay chưa
        if (ad.getAccountByUserEmail(email) != null) {
            Account accountGoogle = ad.getAccountByUserEmail(email);

            // kiểm tra xem account GG có được đăng kí bằng 1 tài khoản dùng username trước đó không
            if (accountGoogle.getUsername() != null) {
                // gửi thông báo tài khoản GG này đã được đăng kí vào 1 tài khoản trong hệ thống
                request.setAttribute("msLogin", "Tài khoản Google này đã được đăng kí cho 1 tài khoản khác trong hệ thống."
                        + "Vui lòng sử dụng tài khoản Google khác.");
                request.getRequestDispatcher("/view/login/notify-login.jsp").forward(request, response);
            } else {
                // tạo phiên cho tài khoản
                session.setAttribute("account", accountGoogle);

                int roleId = accountGoogle.getRoleId();

                switch (roleId) {
                    case 2 -> {
                        // chuyển hướng đến homepage của Manager
                        int lodgingID = lhd.getLodgingHouseByManageID(accountGoogle.getAccountID());
                        System.out.println("ID=====" + accountGoogle.getAccountID());
                        session.setAttribute("lodgingID", lodgingID);
                        System.out.println("Hello :" + lodgingID);
                        response.sendRedirect("/ManageLodgingHouse/home-manager?LodgingHouseID=" + lodgingID);
                    }
                    case 1 -> {
                        // chuyển hướng đến homepage của Landlord
                        response.sendRedirect("/ManageLodgingHouse/home-controller");
                    }
                    case 3 -> {
                        // chuyển hướng đến homepage của Tenant
                        Date current = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                        // Format the current date
                        String formattedDate = sdf.format(current);
                        ContractDAO cd = new ContractDAO();
                        Contract c = cd.getContractBy(accountGoogle.getAccountID(), formattedDate);
                        System.out.println(c);
                        if(null!=c){  response.sendRedirect("room-detail-for-tenant?id=" + c.getRoom().getRoomId());}
                        else {
                            response.sendRedirect("notification-waiting-room");
                        }
                    }
                    default -> {
                        // 
                    }
                }
            }
        } else {
            // tạo account object sau khi lấy thông tin từ gg
            Account accountGoogle = new Account(email, 3, "google");

            // tạo 1 token unique cho người đăng kí
            String uniqueToken = UUID.randomUUID().toString();

            // lưu account được đăng kí vào ServletContext
            servletContext.setAttribute(uniqueToken, accountGoogle);

            // gửi email xác nhận đăng kí
            // verify ở LoginServlet
            EmailService.sendMailToRegister(email, uniqueToken);

            response.sendRedirect("/ManageLodgingHouse/view/login/notify-register.jsp");
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
        processRequest(request, response);
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

    /**
     *
     * @param code refresh token is returned when requesting to Login to Google
     * @return access token(authentication token) is returned when requesting to
     * Google to take access token
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String getToken(String code) throws ClientProtocolException, IOException {
        // call api to get token
        String response = Request.Post(Constants.GOOGLE_LINK_GET_TOKEN)
                .bodyForm(Form.form().add("client_id", Constants.GOOGLE_CLIENT_ID)
                        .add("client_secret", Constants.GOOGLE_CLIENT_SECRET)
                        .add("redirect_uri", Constants.GOOGLE_REDIRECT_URI).add("code", code)
                        .add("grant_type", Constants.GOOGLE_GRANT_TYPE).build())
                .execute().returnContent().asString();

        // return json object
        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");
        return accessToken;
    }

    /**
     *
     * @param accessToken authentication token
     * @return User information is returned from Google
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static UserGoogleDTO getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
        String link = Constants.GOOGLE_LINK_GET_USER_INFO + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();

        UserGoogleDTO googlePojo = new Gson().fromJson(response, UserGoogleDTO.class);

        return googlePojo;
    }

}
