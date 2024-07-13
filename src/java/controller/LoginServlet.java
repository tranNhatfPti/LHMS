package controller;

import com.chatapp.services.FileServiceAbstract;
import dal.AccountDAO;
import dal.FeedbackDAO;
import dal.InformationOfUserDAO;
import dal.LodgingHousesDAO;
import dal.RoomDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.ParseException;
import model.Account;
import utils.BCrypt;
import utils.EmailService;
import jakarta.servlet.ServletContext;
import java.io.File;
import java.util.List;
import java.util.UUID;
import model.Feedback;
import model.InformationOfUser;
import model.LodgingHouse;
import model.Room;

/**
 *
 * @author ASUS ZenBook
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

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
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        ServletContext servletContext = getServletContext();
        InformationOfUserDAO id = new InformationOfUserDAO();
        AccountDAO ad = new AccountDAO();

        // dịch vụ muốn làm từ View Page
        String service = request.getParameter("service");

        // đổi url ... 
        if (FileServiceAbstract.rootURL.isEmpty() || FileServiceAbstract.rootURL.contains("localhost")) {
            String url = request.getRequestURL().toString();
            FileServiceAbstract.rootURL = url.replaceAll("LoginServlet", "files/");
            System.out.println(FileServiceAbstract.rootURL);
        }

        if (service == null) {
            request.getRequestDispatcher("/view/login/login.jsp").forward(request, response);
        }

        try {
            // yêu cầu check existence username khi đăng kí được gửi từ JS
            if (service.equals("checkUsernameRegister")) {
                checkUsernameRegister(request, response);
            }

            // yêu cầu check existence email khi đăng kí được gửi từ JS
            if (service.equals("checkEmailRegister")) {
                checkEmailRegister(request, response);
            }

            // kiểm tra chính xác của mật khẩu khi đăng nhập
            if (service.equals("checkCorrectPassword")) {
                checkCorrectPassword(request, response);
            }

            // đăng xuất 
            if (service.equals("logout")) {
                logoutAccount(request, response);
            }

            // xác nhận người dùng đăng kí và lưu thông tin vào db sau khi nhập thông tin đăng kí ở form
            if (service.equals("verifyAccount")) {
                // lấy ra token tương ứng với người đăng kí
                String user_token = request.getParameter("user_token");

                // lấy ra thông tin đã đăng kí trong servletcontext với key là token tương ứng
                Account account = (Account) servletContext.getAttribute(user_token);

                if (account != null) {
                    // sau khi insert thông tin thì xoá đối tượng cần đăng kí trong servletcontext
                    if (insertAccount(request, response, account)) {
                        // thêm thông tin account
                        int accountID = ad.getAccountByUserEmail(account.getEmail()).getAccountID();
                        InformationOfUser informationOfUser = new InformationOfUser(accountID, true, "./Resource/images/avatar.jpg");
                        id.insertInformationOfUser(informationOfUser);

                        // xoá account vừa xác nhận đăng kí 
                        servletContext.removeAttribute(user_token);

                        // tạo phiên đăng nhập
                        session.setAttribute("account", account);

                        // tạo folder riêng cho user mới đăng kí để lưu ảnh, file(tin nhắn) trong folder archive trong server
                        File privateDir = new File(FileServiceAbstract.rootLocation.toString() + "/" + accountID);
                        privateDir.mkdir();

                        // chuyển hướng đến màn hình của guest
                        response.sendRedirect("room?service=showRoomInfor");
                    } else {
                        out.print("Register Failed!");
                    }
                } else {
                    // đã xoá account trong servletcontext khi đăng kí xong
                    // hiển thị thông báo đã đăng kí thành công
                    request.setAttribute("msRegistered", "Bạn đã kích hoạt tài khoản thành công");
                    request.getRequestDispatcher("/view/login/notify-register.jsp").forward(request, response);
                }
            }
        } catch (Exception e) {
            out.print("error");
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
        // gửi dữ liệu dạng post thì sẽ không hiện các param ở url
        PrintWriter out = response.getWriter();
        response.setContentType("text/html;charset=UTF-8");
        AccountDAO ad = new AccountDAO();

        String service = request.getParameter("service");

        try {
            if (service.equals("register")) {
                registerAccount(request, response);
            }

            if (service.equals("login")) {
                loginAccount(request, response);
            }

            if (service.equals("forgot-password")) {
                checkCorrectEmail(request, response);
            }

            if (service.equals("change-forgot-password")) {
                changeForgotPassword(request, response);
            }
        } catch (Exception e) {
            out.print("Error!");
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

    /**
     * Insert an account after entering the registration form
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ParseException
     */
    public void registerAccount(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        ServletContext servletContext = getServletContext();
        BCrypt bcrypt = new BCrypt();
        AccountDAO ad = new AccountDAO();

        // lấy tất cả thông tin đã nhập ở form đăng kí
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        // mã hoá mật khẩu theo Bcrypt
        password = bcrypt.hashpw(password, bcrypt.gensalt());

        // tạo 1 account object để lưu xuống db
        // status: True là hiện tại đang thuê trọ, False là ngược lại
        Account account = new Account(email, username, password, 4, "username");

        // tạo 1 token unique cho người đăng kí
        String uniqueToken = UUID.randomUUID().toString();

        // lưu account được đăng kí vào ServletContext
        servletContext.setAttribute(uniqueToken, account);

        // gửi email xác nhận đăng kí
        EmailService.sendMailToRegister(email, uniqueToken);

        response.sendRedirect("/ManageLodgingHouse/view/login/notify-register.jsp");
    }

    /**
     * Insert an account into the database
     *
     * @param request
     * @param response
     * @param account
     * @return
     * @throws IOException
     */
    public static boolean insertAccount(HttpServletRequest request, HttpServletResponse response, Account account) throws IOException {
        AccountDAO ad = new AccountDAO();
        PrintWriter out = response.getWriter();

        // thêm account mới khi đăng kí
        return ad.insertAccount(account) != 0;
    }

    /**
     * Login
     *
     * @param request servlet request
     * @param response servlet response
     * @throws IOException
     */
    public void loginAccount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        AccountDAO ad = new AccountDAO();
        LodgingHousesDAO lhd = new LodgingHousesDAO();
        RoomDAO roomDao = new RoomDAO();
        FeedbackDAO feedDao = new FeedbackDAO();
        ServletContext servletContext = getServletContext();

        // lấy tài khoản, mật khẩu và nhớ mật khẩu
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("remember-me");

        if (ad.checkUsernameAndPassword(username, password)) {

            // thiết lập nhớ mật khẩu
            if (rememberMe != null && !rememberMe.isBlank()) {
                String uniqueToken = UUID.randomUUID().toString();

                servletContext.setAttribute(uniqueToken, password);

                // tạo cookie
                Cookie cUsername = new Cookie("usernameLHMS", username);
                Cookie cPassword = new Cookie("passwordLHMS", uniqueToken);

                // set tuổi cho cookie 6 tháng(2 key sẽ tồn tại ở browser 6 tháng)
                cUsername.setMaxAge(60 * 60 * 24 * 30 * 6);
                cPassword.setMaxAge(60 * 60 * 24 * 30 * 6);

                // thêm cookie
                response.addCookie(cUsername);
                response.addCookie(cPassword);
            }

            // tạo phiên cho tài khoản
            Account accountLogged = ad.getAccountByUserName(username);
            session.setAttribute("account", accountLogged);

            int accountId = accountLogged.getAccountID();
            int roleId = accountLogged.getRoleId();
            LodgingHousesDAO lodgingHousesDAO = new LodgingHousesDAO();
            switch (roleId) {
                case 2 -> {
                    // chuyển hướng đến homepage của Manager
                    int lodgingID = lhd.getLodgingHouseByManageID(accountLogged.getAccountID());
                    session.setAttribute("lodgingID", lodgingID);
                    response.sendRedirect("/ManageLodgingHouse/view/manager/Home-manager.jsp");
                }
                case 3 -> {
                    // chuyển hướng đến homepage của Landlord
                    response.sendRedirect("/ManageLodgingHouse/view/landlord/Home-page.jsp");
                }
                case 4 -> {
                    // chuyển hướng đến homepage của Tenant
                    List<Room> rooms = roomDao.getRoomsByAccountId(accountId);
                    Room firstRoom = rooms.get(0);
                    LodgingHouse lodging = firstRoom.getLodgingHouse();
                    Feedback feedback = feedDao.getFeedbackByAccountIdAndLodgingId(accountId, lodging.getLodgingHouseId());
                    if (feedback == null || feedback.getFeedbackStatus() == 1) {
                        response.sendRedirect("home-tenant?service=showLodgingInfor");
                    } else {
                        response.sendRedirect("feedback?service=showMandatoryFeedback");
                    }
                }
                default -> {
                    // 
                }
            }
        }
    }

    public static void logoutAccount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        // xoá phiên đăng nhập
        session.removeAttribute("account");
        response.sendRedirect("/ManageLodgingHouse/view/login/login.jsp");
    }

    /**
     * Check the existence of the account's username when registering
     *
     * @param request servlet request
     * @param response servlet response
     * @throws IOException
     */
    public static void checkUsernameRegister(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AccountDAO ad = new AccountDAO();

        String usernameNeedCheck = request.getParameter("usernameNeedCheck");

        // Thiết lập kiểu nội dung phản hồi
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        if (ad.getAccountByUserName(usernameNeedCheck) != null) {
            // Gửi phản hồi về client
            response.getWriter().write("EXISTED");
        } else {
            // Gửi phản hồi về client
            response.getWriter().write("OK");
        }
    }

    /**
     * Check the existence of the account's email when registering
     *
     * @param request servlet request
     * @param response servlet response
     * @throws IOException
     */
    public static void checkEmailRegister(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AccountDAO ad = new AccountDAO();

        String emailNeedCheck = request.getParameter("emailNeedCheck");

        // Thiết lập kiểu nội dung phản hồi
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        if (ad.getAccountByUserEmail(emailNeedCheck) != null) {
            // Gửi phản hồi về client
            response.getWriter().write("EXISTED");
        } else {
            // Gửi phản hồi về client
            response.getWriter().write("OK");
        }
    }

    /**
     * Check username and password when logging in The data is returned from
     * login.jsp page when the user submits the form
     *
     * @param request servlet request
     * @param response servlet response
     * @throws IOException
     */
    public static void checkCorrectPassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AccountDAO ad = new AccountDAO();

        // username và password đã nhập
        String username = request.getParameter("username");
        String passwword = request.getParameter("password");

        // Thiết lập kiểu nội dung phản hồi
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        if (ad.checkUsernameAndPassword(username, passwword)) {
            // Gửi phản hồi về client
            response.getWriter().write("CORRECT");
        } else {
            // Gửi phản hồi về client
            response.getWriter().write("WRONG");
        }
    }

    /**
     * Check the accuracy of the email when the user enters the forgot password
     * form
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException
     * @throws IOException
     */
    public void checkCorrectEmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        AccountDAO ad = new AccountDAO();

        // email đã nhập
        String email = request.getParameter("email");

        // lấy account từ email
        Account account = ad.getAccountByUserEmail(email);

        // null: email không tồn tại hoặc chưa kích hoạt
        if (account == null) {
            request.setAttribute("msForgotEmail", "Email không tồn tại");
            request.getRequestDispatcher("/view/login/forgot.jsp").forward(request, response);
        } // != null: tồn tại 1 tài khoản đã đăng kí bằng email đấy
        // gửi mail xác nhận đổi mật khẩu
        else {
            boolean checkAccountGG = true;

            // xem account này là acc gg hay username.
            if (account.getUsername() == null) {
                checkAccountGG = false;
                request.setAttribute("msEmailGG", "Đây là tài khoản Google, không thể đổi mật khẩu");
                request.getRequestDispatcher("/view/login/forgot.jsp").forward(request, response);
            }

            if (checkAccountGG) {
                // tạo ra 1 unique token cho account muốn đổi mật khẩu
                String uniqueToken = UUID.randomUUID().toString();

                // lưu vào servletcontext(tồn tại mãi trong vòng đời của ứng dụng)
                servletContext.setAttribute(uniqueToken, account);

                // gửi mail
                EmailService.sendMailToForgotPassword(email, uniqueToken);

                request.setAttribute("email", email);
                request.getRequestDispatcher("/view/login/verify-forgot-password.jsp").forward(request, response);
            }
        }
    }

    /**
     * Change the old password when the user clicks on the password change
     * verification link and enters the new password
     *
     * @param request servlet request
     * @param response servlet response
     * @throws IOException
     */
    public void changeForgotPassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletContext servletContext = getServletContext();
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        BCrypt bcrypt = new BCrypt();
        AccountDAO ad = new AccountDAO();

        /**
         * lấy về token đã tạo trước khi gửi mail, mỗi unique token sẽ tương ứng
         * với 1 account muốn đổi mật khẩu, token sẽ đi kèm với link xác nhận,
         * khi người dùng bấm vào link xác nhận sẽ gửi token về và sẽ biết được
         * đổi mật khẩu cho account nào.
         */
        String user_token = request.getParameter("user_token");

        // lấy mật khẩu mới
        String new_password = request.getParameter("new-password");

        // lấy về account tương ứng với token
        Account account = (Account) servletContext.getAttribute(user_token);

        if (account != null) {
            // mã hoá mật khẩu mới
            new_password = bcrypt.hashpw(new_password, bcrypt.gensalt());
            account.setPassword(new_password);

            // update mật khẩu mới cho account
            if (ad.updateAccount(account) != 0) {
                // sau khi update xong sẽ xoá account đấy trong servletcontext
                servletContext.removeAttribute(user_token);

                // chuyển hướng màn hình
                // tạo phiên cho tài khoản
                session.setAttribute("account", account);

                int roleId = account.getRoleId();

                switch (roleId) {
                    case 2 -> {
                        // chuyển hướng đến homepage của Manager
                        response.sendRedirect("/ManageLodgingHouse/view/manager/Home-manager.jsp");
                    }
                    case 3 -> {
                        // chuyển hướng đến homepage của Landlord
                        response.sendRedirect("/ManageLodgingHouse/view/landlord/Home-page.jsp");
                    }
                    case 4 -> {
                        // chuyển hướng đến homepage của Tenant
                        response.sendRedirect("room?service=showRoomInfor");
                    }
                    default -> {
                        // 
                    }
                }
            } else {
                out.print("Update fail");
            }
        }
    }

}
