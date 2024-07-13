/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Account;
import model.LodgingHouse;
import model.TenancyDeposit;

/**
 *
 * @author Admin
 */
public class TenancyDepositDAO extends DBContext {
    
    public List<TenancyDeposit> getAllTenancyDepositByRoomID(String roomID, int statusDelete) {
        List<TenancyDeposit> listRoleOfStaff = new ArrayList<>();
        RoomDAO room = new RoomDAO();
        String sql = "SELECT * FROM TenancyDeposit WHERE RoomId =  ? AND StatusDelete = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, roomID);
            stm.setInt(2, statusDelete);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                TenancyDeposit tenancyDeposit = new TenancyDeposit();
                tenancyDeposit.setTenancyDepositID(rs.getInt(1));
                tenancyDeposit.setPhoneNumber(rs.getString(2));
                tenancyDeposit.setDepositMoney(rs.getDouble(3));
                tenancyDeposit.setBookingDate(rs.getString(4));
                tenancyDeposit.setArriveDate(rs.getString(5));
                tenancyDeposit.setDescription(rs.getString(6));
                tenancyDeposit.setRoomID(room.getRoomsById(rs.getString(7)));
                tenancyDeposit.setFullName(rs.getString(8));
                tenancyDeposit.setEmail(rs.getString(9));
                tenancyDeposit.setDateOfBirth(rs.getString(10));
                tenancyDeposit.setStatusDeposit(rs.getInt(11));
                tenancyDeposit.setCIC(rs.getString(12));
                tenancyDeposit.setStatusDelete(rs.getInt(13));
                listRoleOfStaff.add(tenancyDeposit);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listRoleOfStaff;
    }
    
    public TenancyDeposit getTenancyDepositByRoomId(String roomId) {
        String sql = "SELECT * FROM TenancyDeposit WHERE RoomID = ? AND StatusDelete = 0";
        TenancyDeposit tenancyDeposit = new TenancyDeposit();
        RoomDAO romAO = new RoomDAO();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, roomId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                tenancyDeposit.setTenancyDepositID(rs.getInt(1));
                tenancyDeposit.setPhoneNumber(rs.getString(2));
                tenancyDeposit.setDepositMoney(rs.getDouble(3));
                tenancyDeposit.setBookingDate(rs.getString(4));
                tenancyDeposit.setArriveDate(rs.getString(5));
                tenancyDeposit.setDescription(rs.getString(6));
                tenancyDeposit.setRoomID(romAO.getRoomsById(rs.getString(7)));
                tenancyDeposit.setFullName(rs.getString(8));
                tenancyDeposit.setEmail(rs.getString(9));
                tenancyDeposit.setDateOfBirth(rs.getString(10));
                tenancyDeposit.setStatusDeposit(rs.getInt(11));
                tenancyDeposit.setCIC(rs.getString(12));
                tenancyDeposit.setStatusDelete(rs.getInt(13));
                return tenancyDeposit;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public boolean deleteTenancyDepositByID(int id) {
        String sql = "UPDATE TenancyDeposit SET StatusDelete = ?  WHERE tenancyDepositID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, 1);
            stm.setInt(2, id);
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean updateAcceptStatusDepositByID(int id) {
        String sql = "UPDATE TenancyDeposit SET "
                + "StatusDeposit = ? "
                + "WHERE TenancyDepositID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);

            stm.setInt(1, 1);
            stm.setInt(2, id);
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public boolean updateConfirmStatusDepositByID(int id) {
        String sql = "UPDATE TenancyDeposit SET "
                + "StatusDelete = ? "
                + "WHERE TenancyDepositID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);

            stm.setInt(1, 2);
            stm.setInt(2, id);
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean updateCancelStatusDepositByID(int id) {
        String sql = "UPDATE TenancyDeposit SET "
                + "StatusDeposit = ?,  StatusDelete = 1 "
                + "WHERE TenancyDepositID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);

            stm.setInt(1, 2);
            stm.setInt(2, id);
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean updateTenancyDeposit(int id, String fullName, String email,
            String phoneNumber, String dateOfBirth, String bookingDate,
            String arriveDate, double depositMoney, String roomID,
            String description, int statusDeposit, String CIC) {
        String sql = "UPDATE TenancyDeposit SET "
                + "FullName = ?, "
                + "Email = ?, "
                + "PhoneNumber = ?, "
                + "DateOfBirth = ?, "
                + "BookingDate = ?, "
                + "ArriveDay = ?, "
                + "Deposit = ?, "
                + "RoomId = ?, "
                + "[Description] = ?, "
                + "CIC = ?, "
                + "StatusDeposit = ?, "
                + "StatusDelete = ? "
                + "WHERE TenancyDepositID = ?";
        java.sql.Date sqlDateOfBirth = java.sql.Date.valueOf(dateOfBirth);
        java.sql.Date sqlBookingDate = java.sql.Date.valueOf(bookingDate);
        java.sql.Date sqlArriveDate = java.sql.Date.valueOf(arriveDate);
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, fullName);
            stm.setString(2, email);
            stm.setString(3, phoneNumber);
            stm.setDate(4, sqlDateOfBirth);
            stm.setDate(5, sqlBookingDate);
            stm.setDate(6, sqlArriveDate);
            stm.setDouble(7, depositMoney);
            stm.setString(8, roomID);
            stm.setString(9, description);
            stm.setString(10, CIC);
            stm.setInt(11, 0);
            stm.setInt(12, 0);
            stm.setInt(13, id);
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public TenancyDeposit getTenancyDepositId(int id) {
        String sql = "SELECT * FROM TenancyDeposit WHERE TenancyDepositID = ?";
        TenancyDeposit tenancyDeposit = new TenancyDeposit();
        RoomDAO romAO = new RoomDAO();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                tenancyDeposit.setTenancyDepositID(rs.getInt(1));
                tenancyDeposit.setPhoneNumber(rs.getString(2));
                tenancyDeposit.setDepositMoney(rs.getDouble(3));
                tenancyDeposit.setBookingDate(rs.getString(4));
                tenancyDeposit.setArriveDate(rs.getString(5));
                tenancyDeposit.setDescription(rs.getString(6));
                tenancyDeposit.setRoomID(romAO.getRoomsById(rs.getString(7)));
                tenancyDeposit.setFullName(rs.getString(8));
                tenancyDeposit.setEmail(rs.getString(9));
                tenancyDeposit.setDateOfBirth(rs.getString(10));
                tenancyDeposit.setStatusDeposit(rs.getInt(11));
                tenancyDeposit.setCIC(rs.getString(12));
                tenancyDeposit.setStatusDelete(rs.getInt(13));
                return tenancyDeposit;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Account getAccountManagerByLodgingHouseID(int lodgingHouseID) {
        String sql = "SELECT a.AccountId,a.Email,a.UserName,a.PassWord,a.RoleID,a.TypeOfLogin"
                + " FROM Account a JOIN  LodgingHouses l \n"
                + "ON a.AccountId = l.ManageId WHERE l.LodgingHouseId = ?";
        Account account = new Account();
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, lodgingHouseID);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {

                account.setAccountID(rs.getInt(1));
                account.setEmail(rs.getString(2));
                account.setUsername(rs.getString(3));
                account.setPassword(rs.getString(4));
                account.setRoleId(rs.getInt(5));
                account.setTypeOfLogin(rs.getString(6));
                return account;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertTenancyDeposit(String fullName, String email,
            String phoneNumber, String dateOfBirth, String bookingDate,
            String arriveDate, double depositMoney, String roomID,
            String description, int statusDeposit, int statusDelete, String CIC) {
        String sql = "INSERT INTO TenancyDeposit (\n"
                + "    FullName,\n"
                + "    Email,\n"
                + "    PhoneNumber,\n"
                + "    DateOfBirth,\n"
                + "    BookingDate,\n"
                + "    ArriveDay,\n"
                + "    Deposit,\n"
                + "    RoomId,\n"
                + "    [Description],\n"
                + "    StatusDeposit,\n"
                + "    CIC, "
                + "    StatusDelete "
                + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?);";
        java.sql.Date sqlDateOfBirth = java.sql.Date.valueOf(dateOfBirth);
        java.sql.Date sqlBookingDate = java.sql.Date.valueOf(bookingDate);
        java.sql.Date sqlArriveDate = java.sql.Date.valueOf(arriveDate);
        try {
            PreparedStatement stm = connection.prepareStatement(sql);

            stm.setString(1, fullName);
            stm.setString(2, email);
            stm.setString(3, phoneNumber);
            stm.setDate(4, sqlDateOfBirth);
            stm.setDate(5, sqlBookingDate);
            stm.setDate(6, sqlArriveDate);
            stm.setDouble(7, depositMoney);
            stm.setString(8, roomID);
            stm.setString(9, description);
            stm.setInt(10, 0);
            stm.setString(11, CIC);
            stm.setInt(12, statusDelete);
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) throws ParseException {
        TenancyDepositDAO ten = new TenancyDepositDAO();
        System.out.println(ten.getAllTenancyDepositByRoomID("Room101", 2).size());
//        for (TenancyDeposit t : ten.getAllTenancyDepositByRoomID("Room101", 2)) {
//            System.out.println(t.toString());
//        }
//        if(ten.insertTenancyDeposit("Nguyễn Văn Duy", "duynvhe2111@gmaill.com", "0378658122", "2003-04-09",
//                "2024-06-21", "2024-06-24", 100066, "Room101", "Ngueyenx adasd",0, 0,"034201019243")) {
//            System.out.println("ưewewewesa");
//        }



//        LocalDate date01 = LocalDate.parse("2024-06-21");
//        LocalDate date02 = LocalDate.parse("2024-06-24");
//
//        LocalDate currentDate = LocalDate.now();
//        Date bookingDateRaw = java.sql.Date.valueOf(currentDate);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//        String bookingDate = dateFormat.format(bookingDateRaw);
//        LocalDate arriveDate = null;
//        String arriveDateRaw = null;
//        try {
//            if (ten.getTenancyDepositByRoomId("Room101") != null) {
//                //arriveDate = dateFormat.parse(tenancyDepositDAO.getTenancyDepositByRoomId(roomId).getArriveDate());
//                arriveDateRaw = ten.getTenancyDepositByRoomId("Room101").getArriveDate();
//                arriveDate = LocalDate.parse(arriveDateRaw);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        long daysBetween = ChronoUnit.DAYS.between(currentDate, arriveDate);
//        System.out.println(daysBetween);


//        LocalDate currentDate = LocalDate.now();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//        Date arriveDate = dateFormat.parse("2024-06-24");
        //LocalDate arriveLocalDate = arriveDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        //long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(arriveDate.toInstant(),currentDate);
        //System.out.println(daysBetween);
//        for (TenancyDeposit tenancyDeposit : ten.getAllTenancyDepositByRoomID("Room101")) {
//            System.out.println(tenancyDeposit.toString());
//        }
//        try {
//             if(ten.updateCancelStatusDepositByID(6)) {
//                 System.out.println("ttrtrtr");
//             } else {
//                 System.out.println("fdfsdfsdf");
//             }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } 
        //System.out.println(ten.getAccountManagerByLodgingHouseID(9).getAccountID());
//        LocalDate currentDate = LocalDate.now();
//
//        // Định dạng theo dd-MM-yyyy
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        String formattedDate = currentDate.format(formatter);
        // Chuyển đổi từ String sang Date (lưu ý: Date không hỗ trợ định dạng ngày tháng, nó chỉ lưu trữ thời điểm)
//        try {
//            Date date = new SimpleDateFormat("dd-MM-yyyy").parse(formattedDate);
//            System.out.println("Converted Date: " + date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy");
//        
       
    }
}
