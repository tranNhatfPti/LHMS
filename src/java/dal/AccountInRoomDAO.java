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
import model.Account;
import model.AccountInRoom;
import model.Contract;
import model.Room;

/**
 *
 * @author ASUS ZenBook
 */
public class AccountInRoomDAO extends DBContext {

    public void deleteAccountInRoom(AccountInRoom accountInRoom) {
        String sql = """
                    DELETE FROM [dbo].[AccountInRoom]
                          WHERE RoomId = ?""";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, accountInRoom.getRoom().getRoomId());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public void insertAccountInRoom(AccountInRoom accountInRoom) {
        String sql = """
                    INSERT INTO [dbo].[AccountInRoom]
                                ([RoomId]
                                ,[AccountId])
                          VALUES
                                (? ,?)""";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, accountInRoom.getRoom().getRoomId());
            st.setDouble(2, accountInRoom.getAccount().getAccountID());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public AccountInRoom getAccountRoomsById(String roomId, int accountID) {
        String sql = "   SELECT * FROM AccountInRoom WHERE RoomId = ? AND AccountId = ? ";
        try {

            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, roomId);
            st.setInt(2, accountID);
            ResultSet rs = st.executeQuery();

            LodgingHousesDAO lodgingHousesDAO = new LodgingHousesDAO();
            AccountDAO accountDAO = new AccountDAO();
            RoomDAO roomDAO = new RoomDAO();
            if (rs.next()) {
                AccountInRoom accountInRoom = new AccountInRoom(
                        accountDAO.getAccountById(rs.getInt("AccountId")),
                        roomDAO.getRoomsById(rs.getString("RoomId")));
                return accountInRoom;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public int getAccountIdRentingByRoomId(String roomId) {
        int accountId = 0;

        String sql = "SELECT * FROM AccountInRoom where RoomId = ?";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, roomId);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    accountId = rs.getInt("AccountId");
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }

        return accountId;
    }

    public List<String> getRoomIdByAccountIdRenting(int accountId) {
        List<String> list = new ArrayList<>();

        String sql = "SELECT * FROM AccountInRoom where AccountId = ?";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, accountId);
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getString("RoomId"));
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }

        return list;
    }

    public List<String> getRoomIdByAccountIdRented(int accountId) {
        ContractDAO cd = new ContractDAO();

        List<Contract> listContract = cd.getAllContractOfTenant(accountId);
        List<String> list = new ArrayList<>();

        String sql = "SELECT * FROM AccountInRoom where AccountId = ?";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, accountId);
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getString("RoomId"));
                }

                for (Contract contract : listContract) {
                    if (!list.contains(contract.getRoom().getRoomId())) {
                        list.add(contract.getRoom().getRoomId());
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

    public boolean insertRoom(AccountInRoom aIc) {
        String sql = """
                     INSERT INTO [dbo].[AccountInRoom]
                                ([RoomId]
                                ,[AccountId])
                          VALUES
                                (?
                                ,?)""";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, aIc.getRoom().getRoomId());
            st.setInt(2, aIc.getAccount().getAccountID());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public List<AccountInRoom> getAccountInRoomByIdRoom(int lodgingHouseId, String roomId) {
        List<AccountInRoom> list = new ArrayList<>();
        String sql = """
                   SELECT *
                     FROM [dbo].[AccountInRoom]""";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, lodgingHouseId);

            ResultSet rs = st.executeQuery();
            RoomDAO rd = new RoomDAO();
            AccountDAO ad = new AccountDAO();
            while (rs.next()) {
                Room r = rd.getRoomsById(rs.getString("[RoomId]"));
                Account ac = ad.getAccountById(rs.getInt("AccountId"));
                AccountInRoom aIc = new AccountInRoom(ac, r);
                list.add(aIc);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public List<AccountInRoom> getAccountAllInRoomByIdRoom(String roomId) {
        List<AccountInRoom> list = new ArrayList<>();
        String sql = "SELECT * FROM AccountInRoom WHERE RoomId = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, roomId);

            try (ResultSet rs = st.executeQuery()) {
                RoomDAO rd = new RoomDAO();
                AccountDAO ad = new AccountDAO();
                while (rs.next()) {
                    Room r = rd.getRoomsById(rs.getString("RoomId"));
                    Account ac = ad.getAccountById(rs.getInt("AccountId"));
                    AccountInRoom aIc = new AccountInRoom(ac, r);
                    list.add(aIc);
                }
            }
        } catch (SQLException e) {
            // Handle exception appropriately, e.g., logging or rethrowing
            e.printStackTrace();
        }
        return list;
    }

    public int countNumberOfPersonInRoom(String roomID) {
        String sql = "SELECT COUNT(*) AS number FROM AccountInRoom WHERE RoomId = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, roomID);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("number"); // Retrieve the integer value of 'number' column
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception properly
        }
        return 0; // Return 0 if there's an error or no result found
    }

    public static void main(String[] args) {
        AccountInRoomDAO a = new AccountInRoomDAO();
//        System.out.println(a.getAccountIdRentingByRoomId("1"));
//        System.out.println(a.getRoomIdByAccountIdRenting(7).size());
        System.out.println(a.getRoomIdByAccountIdRented(8));
    }
}
