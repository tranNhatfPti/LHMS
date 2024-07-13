/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Staff;

/**
 *
 * @author Admin
 */
public class StaffDAO extends DBContext {

    public List<Staff> getAllStaff() {
        List<Staff> listRoleOfStaff = new ArrayList<>();
        RoleOfStaffDAO roleOfStaff = new RoleOfStaffDAO();
        LodgingHousesDAO lodgingHouse = new LodgingHousesDAO();
        String sql = "SELECT * FROM Staff";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Staff staff = new Staff();
                staff.setStaffID(rs.getInt(1));
                staff.setNameStaff(rs.getString(2));
                staff.setRoleStaffID(roleOfStaff.getRoleOfStaffByID(rs.getInt(3)));
                staff.setPhoneNumber(rs.getString(4));
                staff.setEmail(rs.getString(5));
                staff.setProvince(rs.getString(6));
                staff.setDistrict(rs.getString(7));
                staff.setWard(rs.getString(8));
                staff.setAddressDetail(rs.getString(9));
                staff.setSalary(rs.getDouble(10));
                staff.setLodgingHouseID(lodgingHouse.getLodgingHouseById(rs.getInt(11)));
                listRoleOfStaff.add(staff);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listRoleOfStaff;
    }
    
    public List<Staff> getAllStaffByLodgingHouseID(int lodgingHouseID) {
        List<Staff> listRoleOfStaff = new ArrayList<>();
        RoleOfStaffDAO roleOfStaff = new RoleOfStaffDAO();
        LodgingHousesDAO lodgingHouse = new LodgingHousesDAO();
        String sql = "SELECT s.StaffId,s.NameStaff,r.RoleStaffID,s.PhoneNumber,s.Email,\n"
                + "s.Province,s.District,s.Ward,s.AddressDetail,s.Salary,s.LodgingHouseId\n"
                + "FROM Staff s LEFT JOIN RolesOfStaff r\n"
                + "ON r.RoleStaffID = s.RoleStaffID\n"
                + "WHERE LodgingHouseId = ? "
                + "ORDER BY s.StaffId DESC";
        try {

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, lodgingHouseID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Staff staff = new Staff();
                staff.setStaffID(rs.getInt(1));
                staff.setNameStaff(rs.getString(2));
                staff.setRoleStaffID(roleOfStaff.getRoleOfStaffByID(rs.getInt(3)));
                staff.setPhoneNumber(rs.getString(4));
                staff.setEmail(rs.getString(5));
                staff.setProvince(rs.getString(6));
                staff.setDistrict(rs.getString(7));
                staff.setWard(rs.getString(8));
                staff.setAddressDetail(rs.getString(9));
                staff.setSalary(rs.getDouble(10));
                staff.setLodgingHouseID(lodgingHouse.getLodgingHouseById(rs.getInt(11)));
                listRoleOfStaff.add(staff);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listRoleOfStaff;
    }

    public boolean checkExistPhoneNumber(int staffID, String phoneNumber) {
        String currentPhoneNumber = null;
        String sql = "SELECT phoneNumber FROM Staff s JOIN RolesOfStaff r\n"
                + "ON s.RoleStaffID = r.RoleStaffID\n"
                + "WHERE s.staffID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, staffID);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    currentPhoneNumber = rs.getString("phoneNumber");
                }
            }
            if (!phoneNumber.equals(currentPhoneNumber)) {
                String checkQuery = "SELECT COUNT(*) FROM Staff WHERE phoneNumber = ? AND staffID != ?";
                PreparedStatement stm1 = connection.prepareStatement(checkQuery);
                stm1.setString(1, phoneNumber);
                stm1.setInt(2, staffID);
                //stm1.setInt(3, lodgingHouseID);
                try (ResultSet rs = stm1.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        // Số điện thoại đã tồn tại và không thuộc về nhân viên hiện tại
                        return true;
                    }
                }
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkExistEmail(int staffID, String email) {

        String currentEmail = null;
        String sql = "SELECT email FROM Staff s JOIN RolesOfStaff r\n"
                + "ON s.RoleStaffID = r.RoleStaffID\n"
                + "WHERE s.staffID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, staffID);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    currentEmail = rs.getString("email");
                }
            }
            if (!email.equals(currentEmail)) {
                String checkQuery = "SELECT COUNT(*) FROM Staff WHERE email = ? AND staffID != ? ";
                PreparedStatement stm1 = connection.prepareStatement(checkQuery);
                stm1.setString(1, currentEmail);
                stm1.setInt(2, staffID);
                //stm1.setInt(3, lodgingHouseID);
                try (ResultSet rs = stm1.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        // Số điện thoại hoặc email đã tồn tại
                        return true;
                    }
                }
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public int getTotalStaff() {
        int newStaffId = 0;
        String sql = "SELECT TOP 1 StaffID From staff ORDER BY StaffId DESC";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if(rs.next()) {
                newStaffId = rs.getInt("StaffID");
            }
        } catch (Exception e) {
        }
        return newStaffId;
    }

    public boolean updateStaff(int staffID, String nameStaff,
            int roleStaffID, String phoneNumber, String email,
            String province, String district, String ward, String addressDetail,
            double salary, int lodgingHouseId) {
        try {

            String updateQuery = "UPDATE Staff SET NameStaff = ?, RoleStaffID = ?,"
                    + " PhoneNumber = ?, Email = ?, Province = ?, "
                    + "District = ?, Ward = ?, AddressDetail = ?, Salary = ?,"
                    + " LodgingHouseId = ? WHERE StaffId = ?";
            PreparedStatement stmUpdate = connection.prepareStatement(updateQuery);

            stmUpdate.setString(1, nameStaff);
            stmUpdate.setInt(2, roleStaffID);
            stmUpdate.setString(3, phoneNumber);
            stmUpdate.setString(4, email);
            stmUpdate.setString(5, province);
            stmUpdate.setString(6, district);
            stmUpdate.setString(7, ward);
            stmUpdate.setString(8, addressDetail);
            stmUpdate.setDouble(9, salary);
            stmUpdate.setInt(10, lodgingHouseId);
            stmUpdate.setInt(11, staffID);
            return stmUpdate.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertStaff(String nameStaff, int roleStaffID, String phoneNumber, String email,
            String province, String district, String ward, String addressDetail,
            double salary, int lodgingHouseId) {
        String sql = "INSERT INTO Staff "
                + "(NameStaff, RoleStaffID, PhoneNumber, "
                + "Email, Province, District,Ward, AddressDetail, Salary, "
                + "LodgingHouseId)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);

            stm.setString(1, nameStaff);
            stm.setInt(2, roleStaffID);
            stm.setString(3, phoneNumber);
            stm.setString(4, email);
            stm.setString(5, province);
            stm.setString(6, district);
            stm.setString(7, ward);
            stm.setString(8, addressDetail);
            stm.setDouble(9, salary);
            stm.setInt(10, lodgingHouseId);
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Staff getStaffByID(int staffID) {
        String sql = "SELECT * FROM Staff WHERE StaffID = ?";
        int roleOfStaffID;
        RoleOfStaffDAO roleOfStaff = new RoleOfStaffDAO();
        Staff staff = new Staff();
        LodgingHousesDAO lodgingHouse = new LodgingHousesDAO();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, staffID);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                staff.setStaffID(rs.getInt(1));
                staff.setNameStaff(rs.getString(2));
                staff.setRoleStaffID(roleOfStaff.getRoleOfStaffByID(rs.getInt(3)));
                staff.setPhoneNumber(rs.getString(4));
                staff.setEmail(rs.getString(5));
                staff.setProvince(rs.getString(6));
                staff.setDistrict(rs.getString(7));
                staff.setWard(rs.getString(8));
                staff.setAddressDetail(rs.getString(9));
                staff.setSalary(rs.getDouble(10));
                staff.setLodgingHouseID(lodgingHouse.getLodgingHouseById(rs.getInt(11)));
                return staff;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteStaffByID(int staffID) {
        String sql = "DELETE FROM Staff WHERE StaffId = ?";

        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, staffID);
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public List<Staff> getListStaffBySearch(String keyword, int lodgingHouseID) {
        List<Staff> listStaff = new ArrayList<>();

        String sql = "SELECT s.NameStaff, s.Province, s.District,s.Ward,"
                + "s.AddressDetail,s.Email,s.phoneNumber,r.RoleStaffID,"
                + "s.LodgingHouseId,s.staffID,s.salary\n"
                + "FROM Staff s FULL JOIN RolesOfStaff r\n"
                + "ON s.RoleStaffID = r.RoleStaffID\n"
                + "WHERE (s.NameStaff LIKE ? OR s.Email like ?\n"
                + "OR s.District like ? OR s.phoneNumber LIKE ?\n"
                + "OR s.AddressDetail LIKE ? OR s.Province LIKE ?\n"
                + "OR s.Ward LIKE ? OR r.RoleStaffName LIKE ?) ";
        try {
            if (lodgingHouseID != 0) {
                sql += "AND LodgingHouseId = ?";
            }
            String searchPattern = "%" + keyword + "%";
            PreparedStatement st = connection.prepareStatement(sql);

            st.setString(1, searchPattern);
            st.setString(2, searchPattern);
            st.setString(3, searchPattern);
            st.setString(4, searchPattern);
            st.setString(5, searchPattern);
            st.setString(6, searchPattern);
            st.setString(7, searchPattern);
            st.setString(8, searchPattern);
            st.setInt(9, lodgingHouseID);

            ResultSet rs = st.executeQuery();

            RoleOfStaffDAO roleDAO = new RoleOfStaffDAO();
            LodgingHousesDAO lodgeDAO = new LodgingHousesDAO();
            while (rs.next()) {
                Staff staff = new Staff();
                staff.setNameStaff(rs.getString(1));
                staff.setProvince(rs.getString(2));
                staff.setDistrict(rs.getString(3));
                staff.setWard(rs.getString(4));
                staff.setAddressDetail(rs.getString(5));
                staff.setEmail(rs.getString(6));
                staff.setPhoneNumber(rs.getString(7));
                staff.setRoleStaffID(roleDAO.getRoleOfStaffByID(rs.getInt(8)));
                staff.setLodgingHouseID(lodgeDAO.getLodgingHouseById(rs.getInt(9)));
                staff.setStaffID(rs.getInt(10));
                staff.setSalary(rs.getDouble(11));
                listStaff.add(staff);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listStaff;
    }

    public boolean getExistStaffByEmail(String email) {
        String sql = "SELECT * FROM Staff s JOIN RolesOfStaff r\n"
                + "ON s.RoleStaffID = r.RoleStaffID\n"
                + "WHERE s.Email = ? ";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            //st.setInt(2, lodgingHouseID);
            ResultSet rs = st.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean getExistStaffByPhoneNumber(String phoneNumber) {
        String sql = "SELECT * FROM Staff s JOIN RolesOfStaff r\n"
                + "ON s.RoleStaffID = r.RoleStaffID\n"
                + "WHERE s.PhoneNumber = ? ";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, phoneNumber);
            //st.setInt(2, lodgingHouseID);
            ResultSet rs = st.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        StaffDAO dao = new StaffDAO();
//        for (Staff i : dao.getListStaffBySearch("vanduy")) {
//            System.out.println(i);
//        }
//        boolean result = dao.updateStaff(82, "John Doe", 1, "0378658134", "john.doe@example.com",
//                "Province", "District", "Ward", "123 Street", 5000.0, 4);
//        System.out.println(result);

        //System.out.println(dao.checkExistPhoneNumber(82, "vanduymely@gmail.com"));
//        if(dao.checkExistEmail(82, "vanduymely@gmail.com")) {
//            System.out.println("exist");
//        } else {
//            System.out.println("deo exist");
//        }
//        try {
//            boolean result = dao.insertStaff("Hoang Van Duy", 2, "0111111111", "duyanh@gmail.com",
//                "Hanoi", "Hoang Mike", "Thanh Dam", "456 Street",
//                134343, 2);
//        System.out.println("Insert successful: " + result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println(dao.getStaffByID(1));
//        for (Staff staff : dao.getAllStaff()) {
//            System.out.println(staff.getNameStaff());
//        }
//        for (Staff a : dao.getAllStaffByLodgingHouseID(9)) {
//            System.out.println(a.toString());
//        }
        System.out.println(dao.getTotalStaff());
        
    }
}
