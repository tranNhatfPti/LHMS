/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.InformationOfUser;

/**
 *
 * @author ASUS ZenBook
 */
public class InformationOfUserDAO extends DBContext {
     public InformationOfUser getManagerInfoByLodgingHouseId(int id) {
        String sql = """
                     SELECT IU.*  FROM LodgingHouses L FULL JOIN InformationOfUser IU
                     ON L.ManageId = IU.AccountId
                     WHERE LodgingHouseId=?""";
        InformationOfUser informationOfUser = null;
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                informationOfUser = new InformationOfUser(
                        rs.getInt("AccountId"),
                        rs.getString("FullName"),
                        rs.getString("Province"),
                        rs.getString("District"),
                        rs.getString("Ward"),
                        rs.getString("AddressDetail"),
                        rs.getBoolean("Status"),
                        rs.getString("Avatar"),
                        rs.getString("CIC"),
                        rs.getDate("DOB"),
                        rs.getString("PhoneNumber"),
                        rs.getInt("Gender")
                );
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return informationOfUser;
    }

    public int insertInformationOfUser(InformationOfUser informationOfUser) {
        int rowAffected = 0;

        String sql = "INSERT INTO [dbo].[InformationOfUser]\n"
                + "           ([AccountId]\n"
                + "           ,[FullName]\n"
                + "           ,[Province]\n"
                + "           ,[District]\n"
                + "           ,[Ward]\n"
                + "           ,[AddressDetail]\n"
                + "           ,[Status]\n"
                + "           ,[Avatar]\n"
                + "           ,[CIC]\n"
                + "           ,[DOB])\n"
                + "           ,[PhoneNumber]\n"
                + "           ,[Gender])\n"
                + "     VALUES  "
                + "(?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, informationOfUser.getAccountID());
            ps.setString(2, informationOfUser.getFullName());
            ps.setString(3, informationOfUser.getProvince());
            ps.setString(4, informationOfUser.getDistrict());
            ps.setString(5, informationOfUser.getWard());
            ps.setString(6, informationOfUser.getAddressDetail());
            ps.setBoolean(7, informationOfUser.isStatus());
            ps.setString(8, informationOfUser.getAvatar());
            ps.setString(9, informationOfUser.getCic());
            if (informationOfUser.getDob() != null) {
                ps.setDate(10, new java.sql.Date(informationOfUser.getDob().getTime()));
            } else {
                ps.setNull(10, java.sql.Types.DATE);
            }
            ps.setString(11, informationOfUser.getPhoneNumber());
            if (informationOfUser.getGender() != 0) {
                ps.setInt(12, informationOfUser.getGender());
            } else {
                ps.setNull(12, java.sql.Types.INTEGER);
            }

            // nếu như insert 1 customer giống username thì sẽ lỗi.
            rowAffected = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowAffected;
    }

    public InformationOfUser getInformationByAccountID(int accountID) {
        String sql = "SELECT * FROM InformationOfUser WHERE AccountId = ?";
        InformationOfUser informationOfUser = null;

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, accountID); // Set the value for the first parameter

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    informationOfUser = new InformationOfUser(
                            rs.getInt("AccountId"),
                            rs.getString("FullName"),
                            rs.getString("Province"),
                            rs.getString("District"),
                            rs.getString("Ward"),
                            rs.getString("AddressDetail"),
                            rs.getBoolean("Status"),
                            rs.getString("Avatar"),
                            rs.getString("CIC"),
                            rs.getDate("DOB"),
                            rs.getString("PhoneNumber"),
                            rs.getInt("Gender")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }

        return informationOfUser;
    }

    public InformationOfUser getTenantInfoByRoomId(String id) {
        String sql = """
                     SELECT iu.*
                     FROM Account a
                     JOIN dbo.InformationOfUser iu ON iu.AccountId = a.AccountId
                     JOIN AccountInRoom air ON a.AccountId = air.AccountId
                     JOIN Rooms r ON air.RoomId = r.RoomId
                     WHERE r.RoomId=?""";
        InformationOfUser informationOfUser = null;
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                informationOfUser = new InformationOfUser(
                        rs.getInt("AccountId"),
                        rs.getString("FullName"),
                        rs.getString("Province"),
                        rs.getString("District"),
                        rs.getString("Ward"),
                        rs.getString("AddressDetail"),
                        rs.getBoolean("Status"),
                        rs.getString("Avatar"),
                        rs.getString("CIC"),
                        rs.getDate("DOB"),
                        rs.getString("PhoneNumber"),
                        rs.getInt("Gender")
                );
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return informationOfUser;
    }

    public boolean updateProfile(InformationOfUser account) {
        String sql = "UPDATE [dbo].[InformationOfUser]\n"
                + "   SET \n"
                + "      [FullName] = ?\n"
                + "      ,[Province] = ?\n"
                + "      ,[District] = ?\n"
                + "      ,[Ward] = ?\n"
                + "      ,[AddressDetail] = ?\n"
                + "      ,[Avatar] = ?\n"
                + "      ,[CIC] = ?\n"
                + "      ,[DOB] = ?\n"
                + "      ,[PhoneNumber] = ?\n"
                + "      ,[Gender] = ?\n"
                + " WHERE [AccountId] = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);

            stm.setString(1, account.getFullName());
            stm.setString(2, account.getProvince());
            stm.setString(3, account.getDistrict());
            stm.setString(4, account.getWard());
            stm.setString(5, account.getAddressDetail());
            stm.setString(6, account.getAvatar());
            stm.setString(7, account.getCic());
            stm.setDate(8, new java.sql.Date(account.getDob().getTime()));
            stm.setString(9, account.getPhoneNumber());
            stm.setInt(10, account.getGender());
            stm.setInt(11, account.getAccountID());
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        InformationOfUserDAO id = new InformationOfUserDAO();
        System.out.println(id.getInformationByAccountID(6).getAddressDetail());
    }
}
