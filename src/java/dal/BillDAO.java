package dal;

import controller.BillOfTenantServlet;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import model.Bill;
import java.util.List;
import model.Room;

/**
 *
 * @author ASUS ZenBook
 */
public class BillDAO extends DBContext {
    
    public int createBill(Bill bill){
        int rowAffected = 0;

        String sql = "INSERT INTO [dbo].[Bill]\n"
                + "           ([RoomId]\n"
                + "           ,[MonthYear]\n"
                + "           ,[WaterOld]\n"
                + "           ,[WaterNew]"
                + "           ,[PriceWater]\n"
                + "           ,[ElectronicOld]\n"
                + "           ,[ElectronicNew]\n"
                + "           ,[PriceElectronic]\n"
                + "           ,[Missing]\n"
                + "           ,[Paid]\n"
                + "           ,[Status]\n"
                + "           ,[PriceOtherServices])\n"
                + "     VALUES  "
                + "(?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, bill.getRoomId());
            ps.setString(2, bill.getMonthYear());
            ps.setDouble(3, bill.getWaterOld());
            ps.setDouble(4, bill.getWaterNew());
            ps.setDouble(5, bill.getPriceWater());
            ps.setDouble(6, bill.getElectronicOld());
            ps.setDouble(7, bill.getElectronicNew());
            ps.setDouble(8, bill.getPriceElectronic());
            ps.setDouble(9, bill.getMissing());
            ps.setDouble(10, bill.getPaid());
            ps.setInt(11, bill.getStatus());
            ps.setDouble(12, bill.getPriceOtherServices());

            // nếu như insert 1 customer giống username thì sẽ lỗi.
            rowAffected = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return rowAffected;
    }

    public int createBillForNewMonth(String roomId, String monthYear, double waterOld, double electronicOld, int status) {
        int rowAffected = 0;

        String sql = "INSERT INTO [dbo].[Bill]\n"
                + "           ([RoomId]\n"
                + "           ,[MonthYear]\n"
                + "           ,[WaterOld]\n"
                + "           ,[ElectronicOld]"
                + "           ,[Status])\n"
                + "     VALUES  "
                + "(?,?,?,?,?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, roomId);
            ps.setString(2, monthYear);
            ps.setDouble(3, waterOld);
            ps.setDouble(4, electronicOld);
            ps.setInt(5, status);

            // nếu như insert 1 customer giống username thì sẽ lỗi.
            rowAffected = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowAffected;
    }

    public Bill getBillByRoomIdAndMonthYear(String roomId, String monthYear) {
        String sql = "SELECT * FROM Bill WHERE RoomId = ? and MonthYear = ?";
        Bill bill = null;

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, roomId);
            pstm.setString(2, monthYear);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    bill = new Bill(
                            rs.getString("RoomId"),
                            rs.getString("MonthYear"),
                            rs.getDouble("WaterOld"),
                            rs.getDouble("WaterNew"),
                            rs.getDouble("PriceWater"),
                            rs.getDouble("ElectronicOld"),
                            rs.getDouble("ElectronicNew"),
                            rs.getDouble("PriceElectronic"),
                            rs.getDouble("Missing"),
                            rs.getDouble("Paid"),
                            rs.getInt("Status"),
                            rs.getDouble("PriceOtherServices")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }

        return bill;
    }

    public List<Bill> getAllBillByLodgingHouseID(int lodgingHouseId) {
        String sql = "select * from Bill b inner join Rooms r on b.RoomId = r.RoomId\n"
                + "where r.LodgingHouseId = ?";
        List<Bill> listOfBill = new ArrayList<>();

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, lodgingHouseId);
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    listOfBill.add(new Bill(
                            rs.getString("RoomId"),
                            rs.getString("MonthYear"),
                            rs.getDouble("WaterOld"),
                            rs.getDouble("WaterNew"),
                            rs.getDouble("PriceWater"),
                            rs.getDouble("ElectronicOld"),
                            rs.getDouble("ElectronicNew"),
                            rs.getDouble("PriceElectronic"),
                            rs.getDouble("Missing"),
                            rs.getDouble("Paid"),
                            rs.getInt("Status"),
                            rs.getDouble("PriceOtherServices")
                    )
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }

        return listOfBill;
    }

    public List<Bill> getBillByLodgingHouseIDAndMonthYear(int lodgingHouseId, String monthYear) {
        String sql = "select * from Bill b inner join Rooms r on b.RoomId = r.RoomId\n"
                + "where r.LodgingHouseId = ? and b.MonthYear = ?";
        List<Bill> listOfBill = new ArrayList<>();

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, lodgingHouseId);
            pstm.setString(2, monthYear);
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    listOfBill.add(new Bill(
                            rs.getString("RoomId"),
                            rs.getString("MonthYear"),
                            rs.getDouble("WaterOld"),
                            rs.getDouble("WaterNew"),
                            rs.getDouble("PriceWater"),
                            rs.getDouble("ElectronicOld"),
                            rs.getDouble("ElectronicNew"),
                            rs.getDouble("PriceElectronic"),
                            rs.getDouble("Missing"),
                            rs.getDouble("Paid"),
                            rs.getInt("Status"),
                            rs.getDouble("PriceOtherServices")
                    )
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }

        return listOfBill;
    }

    public List<Bill> getBillByRoomIdAndStartDate(String roomId, String contractStartTime) {
        String sql = "SELECT * FROM Bill WHERE RoomId = ?";
        List<Bill> list = new ArrayList<>();

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, roomId);

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    if (checkTime(contractStartTime, rs.getString("MonthYear"))) {
                        list.add(new Bill(
                                rs.getString("RoomId"),
                                rs.getString("MonthYear"),
                                rs.getDouble("WaterOld"),
                                rs.getDouble("WaterNew"),
                                rs.getDouble("PriceWater"),
                                rs.getDouble("ElectronicOld"),
                                rs.getDouble("ElectronicNew"),
                                rs.getDouble("PriceElectronic"),
                                rs.getDouble("Missing"),
                                rs.getDouble("Paid"),
                                rs.getInt("Status"),
                                rs.getDouble("PriceOtherServices")
                        ));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }

        return list;
    }

    public int updateBill(Bill bill) {
        int rowAffected = 0;

        String sql = "UPDATE [dbo].[Bill]\n"
                + "   SET [WaterOld] = ?\n"
                + "      ,[WaterNew] = ?\n"
                + "      ,[PriceWater] = ?\n"
                + "      ,[ElectronicOld] = ?\n"
                + "      ,[ElectronicNew] = ?\n"
                + "      ,[PriceElectronic] = ?\n"
                + "      ,[Missing] = ?\n"
                + "      ,[Paid] = ?\n"
                + "      ,[Status] = ?\n"
                + "      ,[PriceOtherServices] = ?\n"
                + " WHERE RoomId = ? and MonthYear = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDouble(1, bill.getWaterOld());
            ps.setDouble(2, bill.getWaterNew());
            ps.setDouble(3, bill.getPriceWater());
            ps.setDouble(4, bill.getElectronicOld());
            ps.setDouble(5, bill.getElectronicNew());
            ps.setDouble(6, bill.getPriceElectronic());
            ps.setDouble(7, bill.getMissing());
            ps.setDouble(8, bill.getPaid());
            ps.setInt(9, bill.getStatus());
            ps.setDouble(10, bill.getPriceOtherServices());
            ps.setString(11, bill.getRoomId());
            ps.setString(12, bill.getMonthYear());

            rowAffected = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowAffected;
    }

    public int updateBillAfterPay(String roomId, String monthYear, double paid) {
        int rowAffected = 0;

        String sql = "UPDATE [dbo].[Bill]\n"
                + "   SET [Missing] = ?\n"
                + "      ,[Paid] = ?\n"
                + "      ,[Status] = ?\n"
                + " WHERE RoomId = ? and MonthYear = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDouble(1, calculateTotalAmountOfBill(roomId, monthYear) - paid);
            ps.setDouble(2, paid);
            ps.setInt(3, 2);
            ps.setString(4, roomId);
            ps.setString(5, monthYear);

            rowAffected = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowAffected;
    }

    public static boolean checkTime(String contractStartTime, String timeNeedCheck) {
        YearMonth cst = YearMonth.parse(contractStartTime);
        YearMonth tnc = YearMonth.parse(timeNeedCheck);

        // So sánh thời gian và return
        return tnc.isBefore(cst) ? false : true;
    }

    public double calculateTotalAmountOfBill(String roomId, String monthYear) {
        RoomDAO rd = new RoomDAO();
        Room room = rd.getRoomsById(roomId);
        double totalAmount = 0;

        Bill bill = getBillByRoomIdAndMonthYear(roomId, monthYear);
        totalAmount += (room.getPrice() + bill.getPriceElectronic() + bill.getPriceWater() + bill.getPriceOtherServices());
        //fix lại nếu phòng ko có người thuê thì sẽ ko + tiền phòng
        return totalAmount;
    }

    public List<Bill> searchBillOfManager(String monthYear, String roomId, int status, int lodgingHouseId) {
        List<Bill> list = new ArrayList<>();
        String sql = "select * from Bill b inner join Rooms r on b.RoomId = r.RoomId\n"
                + "where r.LodgingHouseId = " + lodgingHouseId + " and";

        if (status != 0) {
            sql += " b.Status = " + status + " and";
        }

        if (!monthYear.isBlank()) {
            sql += " b.MonthYear = '" + monthYear + "' and";
        }

        if (!roomId.isBlank()) {
            sql += " b.RoomId = '" + roomId + "'";
        }

        if (sql.substring(sql.length() - 3).equals("and")) {
            sql = sql.substring(0, sql.length() - 3);
        }
        System.out.println(sql);
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    list.add(new Bill(
                            rs.getString("RoomId"),
                            rs.getString("MonthYear"),
                            rs.getDouble("WaterOld"),
                            rs.getDouble("WaterNew"),
                            rs.getDouble("PriceWater"),
                            rs.getDouble("ElectronicOld"),
                            rs.getDouble("ElectronicNew"),
                            rs.getDouble("PriceElectronic"),
                            rs.getDouble("Missing"),
                            rs.getDouble("Paid"),
                            rs.getInt("Status"),
                            rs.getDouble("PriceOtherServices")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }

        return list;
    }

    public List<Bill> searchBillOfTenant(String monthYear, String roomId, int status, int tenantId) {
        List<Bill> listBeforeFilter = BillOfTenantServlet.searchBill(tenantId);
        List<Bill> listAfterFilter = new ArrayList<>();

        for (Bill bill : listBeforeFilter) {
            if (status != 0) {
                if (bill.getStatus() != status) {
                    continue;
                }
            }

            if (!monthYear.isBlank()) {
                if (!bill.getMonthYear().equals(monthYear)) {
                    continue;
                }
            }

            if (!roomId.equals("0")) {
                if (!bill.getRoomId().equals(roomId)) {
                    continue;
                }
            }

            listAfterFilter.add(bill);
        }

        return listAfterFilter;
    }

//    public List<Bill> searchBillOfTenant(String monthYear, String roomId, int status, String contractStartTime, int tenantId) {
//        ContractDAO cd = new ContractDAO();
//        AccountInRoomDAO ard = new AccountInRoomDAO();
//        List<Bill> list = new ArrayList<>();
//
//        String sql = "select * from Bill where";
//
//        if (status != 0) {
//            sql += " Status = " + status + " and";
//        }
//
//        if (!monthYear.isBlank()) {
//            sql += " MonthYear = '" + monthYear + "' and";
//        }
//
//        if (!roomId.equals("0")) {
//            sql += " RoomId = '" + roomId + "'";
//        } else {
//            boolean check = true;
//            List<String> listOfRoomId = ard.getRoomIdByAccountIdRenting(tenantId);
//            for (String romId : listOfRoomId) {
//                if(check){
//                    sql += " (RoomId = '" + romId + "' or";
//                    check = false;
//                } else {
//                    sql += " RoomId = '" + romId + "' or";
//                }
//            }
//
//            if (sql.substring(sql.length() - 2).equals("or")) {
//                sql = sql.substring(0, sql.length() - 2) + ")";
//            }
//        }
//
//        if (sql.substring(sql.length() - 3).equals("and")) {
//            sql = sql.substring(0, sql.length() - 3);
//        }
//
//        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
//            System.out.println(sql);
//            try (ResultSet rs = pstm.executeQuery()) {
//                while (rs.next()) {
//                    if (checkTime(contractStartTime, rs.getString("MonthYear"))) {
//                        list.add(new Bill(
//                                rs.getString("RoomId"),
//                                rs.getString("MonthYear"),
//                                rs.getDouble("WaterOld"),
//                                rs.getDouble("WaterNew"),
//                                rs.getDouble("PriceWater"),
//                                rs.getDouble("ElectronicOld"),
//                                rs.getDouble("ElectronicNew"),
//                                rs.getDouble("PriceElectronic"),
//                                rs.getDouble("Missing"),
//                                rs.getDouble("Paid"),
//                                rs.getInt("Status"),
//                                rs.getDouble("PriceOtherServices")
//                        ));
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            System.err.println("SQL getAccountById: " + e.getMessage());
//        } catch (Exception e) {
//            System.err.println("getAccountById: " + e.getMessage());
//        }
//
//        return list;
//    }
    public boolean checkUpdateBill(String roomId, String dateOfCurrentBill) {
        LocalDate nextMonth_date = LocalDate.parse(dateOfCurrentBill + "-01");
        nextMonth_date = nextMonth_date.plusMonths(1);

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM");
        String nextMonth = nextMonth_date.format(format);

        String sql = "SELECT * FROM Bill WHERE RoomId = ? and MonthYear = ?";
        Bill billCurrentMonth = getBillByRoomIdAndMonthYear(roomId, dateOfCurrentBill);
        Bill billNextMonth = null;

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, roomId);
            pstm.setString(2, nextMonth);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    billNextMonth = new Bill(
                            rs.getString("RoomId"),
                            rs.getString("MonthYear"),
                            rs.getDouble("WaterOld"),
                            rs.getDouble("WaterNew"),
                            rs.getDouble("PriceWater"),
                            rs.getDouble("ElectronicOld"),
                            rs.getDouble("ElectronicNew"),
                            rs.getDouble("PriceElectronic"),
                            rs.getDouble("Missing"),
                            rs.getDouble("Paid"),
                            rs.getInt("Status"),
                            rs.getDouble("PriceOtherServices")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }

        // bill muốn update đã được trả tiền
        if (billCurrentMonth.getStatus() != 1) {
            return false;
        } else {
            // chưa có tháng mới thì update
            if (billNextMonth == null) {
                return true;
            } else {
                return (billNextMonth.getWaterNew() == 0 && billNextMonth.getElectronicNew() == 0);
            }
        }
    }
    
    public int deleteBill(String roomId, String monthYear){
        int rowAffected = 0;
        String sql = "DELETE FROM Bill WHERE RoomId = ? and MonthYear = ?";

        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            
            stm.setString(1, roomId);
            stm.setString(2, monthYear);
            
            rowAffected = stm.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return rowAffected;
    }

    public static void main(String[] args) {
        BillDAO bd = new BillDAO();
//        System.out.println(checkTime("2024-06", "2024-05"));
        Bill bill = new Bill("1", "2024-07", 23.0, 23.0, 0.0, 23.0, 23.0, 0.0, 2050000.0, 1000000.0, 3, 50000.0);
        bd.createBill(bill);
//        System.out.println(bd.searchBillOfTenant("2024-05", "0", 0, "2024-06", 8).get(0).getMonthYear());
    }
}
