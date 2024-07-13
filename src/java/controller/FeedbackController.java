/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.oracle.wls.shaded.org.apache.bcel.generic.AALOAD;
import dal.AccountDAO;
import dal.FeedbackDAO;
import dal.InformationOfUserDAO;
import dal.LodgingHousesDAO;
import dal.RoomDAO;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Account;
import model.Feedback;
import model.InformationOfUser;
import model.LodgingHouse;
import model.ManagerFeedback;
import model.Room;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "FeedbackController", urlPatterns = {"/feedback"})
public class FeedbackController extends HttpServlet {

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

        String service = request.getParameter("service");
        if (service == null) {
            service = "showFeedback";
        }
        if (service.equals("showFeedback")) {
            showFeedback(request, response);
        }
        if (service.equals("showFeedbackDetail")) {
            showFeedbackDetail(request, response);
        }

        if (service.equals("showManageFeedback")) {
            showManagerFeedback(request, response);
        }
        if (service.equals("showMandatoryFeedback")) {
            showMadatoryFeedback(request, response);
        }
        if(service.equals("showStatistic")){
            showStatistic(request, response);
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
        String service = request.getParameter("service");
        if (service == null) {
            service = "updateMandatoryFeedback";
        }
        if (service.equals("updateFeedback")) {
            updateFeedback(request, response);
        }
        if (service.equals("updateMandatoryFeedback")) {
            updateMandatoryFeedback(request, response);
        }
        if (service.equals("updateStar")) {
            updateStar(request, response);
        }
    }

    /**
     * Function show form manage feedback.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private static void showManagerFeedback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        //create Daos object.
        AccountDAO accountDao = new AccountDAO();
        InformationOfUserDAO userDao = new InformationOfUserDAO();
        RoomDAO roomDao = new RoomDAO();
        FeedbackDAO feedDao = new FeedbackDAO();
        LodgingHousesDAO lodDao = new LodgingHousesDAO();

        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            request.getRequestDispatcher("view/login/login.jsp").forward(request, response);
        }
        int id = account.getAccountID();
        int checkEmpty=0;
        //Get current date.
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();

        //Get year and month from url.
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        String roomSearch  = request.getParameter("roomSearch");
        if(roomSearch==null){
            roomSearch="";
        }

        //Check null year and month.
        
        if (year == null) {
            year = "" + currentYear;
            checkEmpty++;
        }
        if (month == null) {
            month = "" + currentMonth;
        }
        if(Integer.parseInt(year) == currentYear && Integer.parseInt(month) == currentMonth){
            checkEmpty++;
        }
        
        //Get lodgingId by manageId.
        int lodgingHouseId = lodDao.getLodgingHouseByManageID(id);

        SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");

        //Get list of room have tenant in lodginghouse.
        List<Room> rooms = roomDao.getRoomsByLodgingHouseIdTenant(lodgingHouseId,roomSearch);
        
        //initialize a check to check list feedback of current month is null?
        boolean check = true;

        //Create account and infor to display on header and tag menu.
        Account showAcc = accountDao.getAccountById(id);
        InformationOfUser userInfor = userDao.getInformationByAccountID(id);

        //create ojects for jsp.
        List<ManagerFeedback> listFeedback = new ArrayList<>();
        for (Room r : rooms) {

            //Get room name and tanant name through room.
            String roomName = r.getRoomName();
            InformationOfUser tenantInfor = userDao.getTenantInfoByRoomId(r.getRoomId());
            String name = tenantInfor.getFullName();
            Feedback feedback = feedDao.getFeedbackByAccountIdAndLodgingIdDate(tenantInfor.getAccountID(), lodgingHouseId, year, month);
            String formattedDate;
            if (feedback != null) {
                check = true;
                
                //format date monthYear.
                formattedDate = sdf.format(feedback.getMonthYear());
            } else {
                check = false;
                continue;
            }
            ManagerFeedback f = new ManagerFeedback(roomName, name, formattedDate, feedback.getFeedbackStatus(), tenantInfor.getAccountID());
            listFeedback.add(f);
        }
        List<ManagerFeedback> listFeedbackIndex = new ArrayList<>();
        String indexS = request.getParameter("index");
        if(indexS==null){
            indexS="1";
        }
        int index = Integer.parseInt(indexS);
        int totalRoom=listFeedback.size();
        if(totalRoom==0){
            listFeedbackIndex = listFeedback;
        }else{
            listFeedbackIndex = paginate(listFeedback,index-1);
        }
        int endPage=totalRoom/5;
        if(totalRoom%5!=0){
            endPage++;
        }
        request.setAttribute("tagMenu", "feedback");
        request.setAttribute("check", check);
        request.setAttribute("month", month);
        request.setAttribute("year", year);
        request.setAttribute("endPage", endPage);
        request.setAttribute("totalRoom", listFeedback.size());
        request.setAttribute("index", index);
        request.setAttribute("checkEmpty", checkEmpty);
        request.setAttribute("listFeedback", listFeedbackIndex);
        request.setAttribute("account", showAcc);
        request.setAttribute("userInfor", userInfor);
        request.getRequestDispatcher("view/manager/manager-feedback.jsp").forward(request, response);
    }
    public static List<ManagerFeedback> paginate(List<ManagerFeedback> list, int index) {
        List<List<ManagerFeedback>> paginatedList = new ArrayList<>();
        int pageSize = 5;
        int totalSize = list.size();
        for (int i = 0; i < totalSize; i += pageSize) {
            paginatedList.add(new ArrayList<>(list.subList(i, Math.min(totalSize, i + pageSize))));
        }
        
        if (index < 0 || index >= paginatedList.size()) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        return paginatedList.get(index);
    }
    private static void showFeedback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        //create Daos object.
        AccountDAO accountDao = new AccountDAO();
        InformationOfUserDAO userDao = new InformationOfUserDAO();
        RoomDAO roomDao = new RoomDAO();
        FeedbackDAO feedDao = new FeedbackDAO();

        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            request.getRequestDispatcher("view/login/login.jsp").forward(request, response);
        }
        int id = account.getAccountID();
        List<Room> rooms = roomDao.getRoomsByAccountId(id);
        Account showAcc = accountDao.getAccountById(id);
        InformationOfUser userInfor = userDao.getInformationByAccountID(id);
        int star = feedDao.getStarByAccountId(id);
        if (!rooms.isEmpty()) {
            boolean checkFeedback = true;
            //create ojects for jsp.
            Room firstRoom = rooms.get(0);
            LodgingHouse lodging = firstRoom.getLodgingHouse();
            InformationOfUser manageInfor = userDao.getInformationByAccountID(lodging.getManageId());
            Account managerAccount = accountDao.getAccountById(lodging.getManageId());
            Feedback feedback = feedDao.getFeedbackByAccountIdAndLodgingId(id, lodging.getLodgingHouseId());
            if (feedback == null) {
                checkFeedback = false;
                request.setAttribute("checkFeedback", checkFeedback);
                request.setAttribute("star", star);
                request.setAttribute("firstRoom", firstRoom);
                request.setAttribute("lodging", lodging);
                request.setAttribute("account", showAcc);
                request.setAttribute("manageInfor", manageInfor);
                request.setAttribute("userInfor", userInfor);
                request.getRequestDispatcher("view/tenant/tenant-feedback.jsp").forward(request, response);
            } else {
                Date date = feedback.getMonthYear();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int feedbackYear = calendar.get(Calendar.YEAR);
                int feedbackMonth = calendar.get(Calendar.MONTH);
                if (feedbackMonth == 0) {
                    feedbackMonth = 12;
                    feedbackYear--;
                }
                String feedbackMonthYear = "" + feedbackMonth + "/" + feedbackYear;
                
                request.setAttribute("checkFeedback", checkFeedback);
                request.setAttribute("star", star);
                request.setAttribute("tagMenu", "feedback");
                request.setAttribute("formattedDate", feedbackMonthYear);
                request.setAttribute("feedback", feedback);
                request.setAttribute("firstRoom", firstRoom);
                request.setAttribute("lodging", lodging);
                request.setAttribute("account", showAcc);
                request.setAttribute("managerAccount", managerAccount);
                request.setAttribute("manageInfor", manageInfor);
                request.setAttribute("userInfor", userInfor);
                request.getRequestDispatcher("view/tenant/tenant-feedback.jsp").forward(request, response);
            }

            //format date monthYear.
        }
    }

    private static void showMadatoryFeedback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        //create Daos object.
        AccountDAO accountDao = new AccountDAO();
        InformationOfUserDAO userDao = new InformationOfUserDAO();
        RoomDAO roomDao = new RoomDAO();
        FeedbackDAO feedDao = new FeedbackDAO();

        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            request.getRequestDispatcher("view/login/login.jsp").forward(request, response);
        }
        int id = account.getAccountID();
        List<Room> rooms = roomDao.getRoomsByAccountId(id);
        Account showAcc = accountDao.getAccountById(id);
        InformationOfUser userInfor = userDao.getInformationByAccountID(id);
        if (!rooms.isEmpty()) {

            //create ojects for jsp.
            Room firstRoom = rooms.get(0);
            LodgingHouse lodging = firstRoom.getLodgingHouse();
            InformationOfUser manageInfor = userDao.getInformationByAccountID(lodging.getManageId());
            Feedback feedback = feedDao.getFeedbackByAccountIdAndLodgingId(id, lodging.getLodgingHouseId());

            Date date = feedback.getMonthYear();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int feedbackYear = calendar.get(Calendar.YEAR);
            int feedbackMonth = calendar.get(Calendar.MONTH);
            if (feedbackMonth == 0) {
                feedbackMonth = 12;
                feedbackYear--;
            }
            String feedbackMonthYear = "" + feedbackMonth + "/" + feedbackYear;

            request.setAttribute("tagMenu", "feedback");
            request.setAttribute("formattedDate", feedbackMonthYear);
            request.setAttribute("feedback", feedback);
            request.setAttribute("firstRoom", firstRoom);
            request.setAttribute("lodging", lodging);
            request.setAttribute("account", showAcc);
            request.setAttribute("manageInfor", manageInfor);
            request.setAttribute("userInfor", userInfor);
            request.getRequestDispatcher("view/tenant/mandatory-feedback.jsp").forward(request, response);
        }
    }
    private static void showStatistic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        //create Daos object.
        AccountDAO accountDao = new AccountDAO();
        InformationOfUserDAO userDao = new InformationOfUserDAO();
        RoomDAO roomDao = new RoomDAO();
        FeedbackDAO feedDao = new FeedbackDAO();
        LodgingHousesDAO lodDao = new LodgingHousesDAO();

        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            request.getRequestDispatcher("view/login/login.jsp").forward(request, response);
        }
        int id = account.getAccountID();
        List<Room> rooms = roomDao.getRoomsByAccountId(id);
        Account showAcc = accountDao.getAccountById(id);
        InformationOfUser userInfor = userDao.getInformationByAccountID(id);
        Map<Integer, Integer> m = new HashMap<>();
        int lodgingHouseId = lodDao.getLodgingHouseByManageID(id);
        String year = request.getParameter("year");
        String month = request.getParameter("month");
         m = feedDao.getStar(lodgingHouseId);

        //Get feedback of  cleanService.
        int clean1 = feedDao.getNumberFeedbackClean(month, year, lodgingHouseId, 1);
        int clean2 = feedDao.getNumberFeedbackClean(month, year, lodgingHouseId, 2);
        int clean3 = feedDao.getNumberFeedbackClean(month, year, lodgingHouseId, 3);
        int clean4 = feedDao.getNumberFeedbackClean(month, year, lodgingHouseId, 4);
        
        //Get feedback of electric.
        int electric1 = feedDao.getNumberFeedbackElectric(month, year, lodgingHouseId, 1);
        int electric2 = feedDao.getNumberFeedbackElectric(month, year, lodgingHouseId, 2);
        int electric3 = feedDao.getNumberFeedbackElectric(month, year, lodgingHouseId, 3);
        int electric4 = feedDao.getNumberFeedbackElectric(month, year, lodgingHouseId, 4);
        
        //Get feedback of water.
        int water1 = feedDao.getNumberFeedbackWater(month, year, lodgingHouseId, 1);
        int water2 = feedDao.getNumberFeedbackWater(month, year, lodgingHouseId, 2);
        int water3 = feedDao.getNumberFeedbackWater(month, year, lodgingHouseId, 3);
        int water4 = feedDao.getNumberFeedbackWater(month, year, lodgingHouseId, 4);
        
        //Get feedback of internet.
        int internet1 = feedDao.getNumberFeedbackInternet(month, year, lodgingHouseId, 1);
        int internet2 = feedDao.getNumberFeedbackInternet(month, year, lodgingHouseId, 2);
        int internet3 = feedDao.getNumberFeedbackInternet(month, year, lodgingHouseId, 3);
        int internet4 = feedDao.getNumberFeedbackInternet(month, year, lodgingHouseId, 4);
        
        //Get feedback of price.
        int price1 = feedDao.getNumberFeedbackPrice(month, year, lodgingHouseId, 1);
        int price2 = feedDao.getNumberFeedbackPrice(month, year, lodgingHouseId, 2);
        int price3 = feedDao.getNumberFeedbackPrice(month, year, lodgingHouseId, 3);
        int price4 = feedDao.getNumberFeedbackPrice(month, year, lodgingHouseId, 4);
        
        int star1 = 0;
        int star2 = 0;
        int star3 = 0;
        int star4 = 0;
        int star5 = 0;
        for (Map.Entry<Integer, Integer> entry : m.entrySet()) {
            if(entry.getKey()==1){
                star1 = entry.getValue();
            }
            if(entry.getKey()==2){
                star2 = entry.getValue();
            }
            if(entry.getKey()==3){
                star3 = entry.getValue();
            }
            if(entry.getKey()==4){
                star4 = entry.getValue();
            }
            if(entry.getKey()==5){
                star5 = entry.getValue();
            }
        }
        
        //Get average Star.
        double averageStar = feedDao.getStarByLodgingId(lodgingHouseId);
        //set clean service.
        request.setAttribute("clean1", clean1);
        request.setAttribute("clean2", clean2);
        request.setAttribute("clean3", clean3);
        request.setAttribute("clean4", clean4);
        
        //set electric.
        request.setAttribute("electric1", electric1);
        request.setAttribute("electric2", electric2);
        request.setAttribute("electric3", electric3);
        request.setAttribute("electric4", electric4);
        
        //set water.
        request.setAttribute("water1", water1);
        request.setAttribute("water2", water2);
        request.setAttribute("water3", water3);
        request.setAttribute("water4", water4);
        
        //set internet.
        request.setAttribute("internet1", internet1);
        request.setAttribute("internet2", internet2);
        request.setAttribute("internet3", internet3);
        request.setAttribute("internet4", internet4);
        
        //set price.
        request.setAttribute("price1", price1);
        request.setAttribute("price2", price2);
        request.setAttribute("price3", price3);
        request.setAttribute("price4", price4);
        
        request.setAttribute("star1", star1);
        request.setAttribute("star2", star2);
        request.setAttribute("star3", star3);
        request.setAttribute("star4", star4);
        request.setAttribute("star5", star5);
        
        
        //set average Star.
        request.setAttribute("averageStar", averageStar);
        
        request.setAttribute("account", showAcc);
        request.setAttribute("map", m);
        request.setAttribute("userInfor", userInfor);
        request.setAttribute("year", year);
        request.setAttribute("month", month);
        request.getRequestDispatcher("view/manager/statistic-feedback.jsp").forward(request, response);
        
    }

    private static void updateMandatoryFeedback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        //create Daos object.
        RoomDAO roomDao = new RoomDAO();
        FeedbackDAO feedDao = new FeedbackDAO();

        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            request.getRequestDispatcher("view/login/login.jsp").forward(request, response);
        }
        int id = account.getAccountID();
        List<Room> rooms = roomDao.getRoomsByAccountId(id);
        Room firstRoom = rooms.get(0);
        LodgingHouse lodging = firstRoom.getLodgingHouse();
        Feedback feedback = feedDao.getFeedbackByAccountIdAndLodgingId(id, lodging.getLodgingHouseId());

        //get parameter.
        int clean = Integer.parseInt(request.getParameter("clean"));
        int electric = Integer.parseInt(request.getParameter("electric"));
        int water = Integer.parseInt(request.getParameter("water"));
        int internet = Integer.parseInt(request.getParameter("internet"));
        int price = Integer.parseInt(request.getParameter("price"));
        String otherResponse = request.getParameter("otherResponse");
        Feedback updateFeedback = new Feedback(feedback.getFeedbackId(),
                feedback.getMonthYear(), feedback.getAccountId(),
                feedback.getLodgingId(), feedback.getStar(),
                clean, electric, water, internet, otherResponse,
                1, price);
        if (feedDao.updateFeedback(updateFeedback)) {
            response.sendRedirect("home-tenant?service=showLodgingInfor");
        } else {
            response.sendRedirect("feedback?service=showMandatoryFeedback");
        }
    }

    private static void updateFeedback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        //create Daos object.
        RoomDAO roomDao = new RoomDAO();
        FeedbackDAO feedDao = new FeedbackDAO();

        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            request.getRequestDispatcher("view/login/login.jsp").forward(request, response);
        }
        int id = account.getAccountID();
        List<Room> rooms = roomDao.getRoomsByAccountId(id);
        Room firstRoom = rooms.get(0);
        LodgingHouse lodging = firstRoom.getLodgingHouse();
        Feedback feedback = feedDao.getFeedbackByAccountIdAndLodgingId(id, lodging.getLodgingHouseId());

        //get parameter.
        int clean = Integer.parseInt(request.getParameter("clean"));
        int electric = Integer.parseInt(request.getParameter("electric"));
        int water = Integer.parseInt(request.getParameter("water"));
        int internet = Integer.parseInt(request.getParameter("internet"));
        int price = Integer.parseInt(request.getParameter("price"));
        String otherResponse = request.getParameter("otherResponse");
        Feedback updateFeedback = new Feedback(feedback.getFeedbackId(),
                feedback.getMonthYear(), feedback.getAccountId(),
                feedback.getLodgingId(), feedback.getStar(),
                clean, electric, water, internet, otherResponse,
                1, price);
        if (feedDao.updateFeedback(updateFeedback)) {
            response.sendRedirect("feedback?service=showFeedback");
        }
    }

    private static void showFeedbackDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        //create Daos object.
        AccountDAO accountDao = new AccountDAO();
        InformationOfUserDAO userDao = new InformationOfUserDAO();
        FeedbackDAO feedDao = new FeedbackDAO();
        LodgingHousesDAO lodDao = new LodgingHousesDAO();

        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            request.getRequestDispatcher("view/login/login.jsp").forward(request, response);
        }
        int id = account.getAccountID();

        //Get lodging id.
        int lodgingHouseId = lodDao.getLodgingHouseByManageID(id);
        String monthYear = request.getParameter("monthYear");
        if(!monthYear.startsWith("0")){
            String month = monthYear.substring(0, 2);
        }
        String month = monthYear.substring(1, 2);
        String year = monthYear.substring(3);
        Account showAcc = accountDao.getAccountById(id);
        InformationOfUser userInfor = userDao.getInformationByAccountID(id);

        int tenantId = Integer.parseInt(request.getParameter("tenantId"));
        int star = feedDao.getStarByAccountId(tenantId);
        InformationOfUser tenantInfor = userDao.getInformationByAccountID(tenantId);
        Account tenantAccount = accountDao.getAccountById(tenantId);
        //create ojects for jsp.
        Feedback feedback = feedDao.getFeedbackByAccountIdAndLodgingIdDate(tenantId, lodgingHouseId, year, month);
        Date date = feedback.getMonthYear();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int feedbackYear = calendar.get(Calendar.YEAR);
        int feedbackMonth = calendar.get(Calendar.MONTH);
        if (feedbackMonth == 0) {
            feedbackMonth = 12;
            feedbackYear--;
        }
        String feedbackMonthYear = "" + feedbackMonth + "/" + feedbackYear;
        request.setAttribute("tagMenu", "feedback");
        request.setAttribute("feedbackMonthYear", feedbackMonthYear);
        request.setAttribute("year", year);
        request.setAttribute("month", month);
        request.setAttribute("feedback", feedback);
        request.setAttribute("account", showAcc);
        request.setAttribute("star", star);
        request.setAttribute("tenantInfor", tenantInfor);
        request.setAttribute("tenantAccount", tenantAccount);
        request.setAttribute("userInfor", userInfor);
        request.getRequestDispatcher("view/manager/feedback-detail.jsp").forward(request, response);
    }
    private static void updateStar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        //create Daos object.
        AccountDAO accountDao = new AccountDAO();
        InformationOfUserDAO userDao = new InformationOfUserDAO();
        FeedbackDAO feedDao = new FeedbackDAO();
        LodgingHousesDAO lodDao = new LodgingHousesDAO();

        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            request.getRequestDispatcher("view/login/login.jsp").forward(request, response);
        }
        int id = account.getAccountID();

        int rating = Integer.parseInt(request.getParameter("rating"));
        if(feedDao.updateStar(id, rating)){
            response.sendRedirect("feedback?service=showFeedback");
        }
    }
//    private static void createFeedback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession();
//        //create Daos object.
//        AccountDAO accountDao = new AccountDAO();
//        InformationOfUserDAO userDao = new InformationOfUserDAO();
//        FeedbackDAO feedDao = new FeedbackDAO();
//        LodgingHousesDAO lodDao = new LodgingHousesDAO();
//        RoomDAO roomDao = new RoomDAO();
//
//        Account account = (Account) session.getAttribute("account");
//        if (account == null) {
//            request.getRequestDispatcher("view/login/login.jsp").forward(request, response);
//        }
//        int id = account.getAccountID();
//        
//        //get lodging id by account id of manager.
//        int lodgingHouseId = lodDao.getLodgingHouseByManageID(id);
//
//        //Get list of room have tenant in lodginghouse.
//        List<Room> rooms = roomDao.getRoomsByLodgingHouseIdTenant(lodgingHouseId);
//
//        //Create account and infor to display on header and tag menu.
//        Account showAcc = accountDao.getAccountById(id);
//        InformationOfUser userInfor = userDao.getInformationByAccountID(id);
//        Date currentDate = new Date();
//        int checkInsert = 0;
//        //create ojects for jsp.
//        for (Room r : rooms) {
//            //Get room name and tanant name through room.
//            InformationOfUser tenantInfor = userDao.getTenantInfoByRoomId(r.getRoomId());
//            int star = feedDao.getStarByAccountId(tenantInfor.getAccountID());
//            Feedback feedback = new Feedback(currentDate, tenantInfor.getAccountID(), lodgingHouseId, star, 0);
//            if(feedDao.insertFeedback(feedback)){
//                checkInsert++;
//            }
//        }
//        response.sendRedirect("feedback?service=showManageFeedback");
//    }

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
