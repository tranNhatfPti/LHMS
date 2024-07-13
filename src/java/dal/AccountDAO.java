package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import utils.BCrypt;

public class AccountDAO extends DBContext {
    
        public List<Account> getAccountByRole(int role) {
        String sql = "SELECT * FROM dbo.Account WHERE [RoleID]=?";
        List<Account> list = new ArrayList<>();
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, role); // Set the value for the first parameter

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    Account account = new Account(
                            rs.getInt("AccountId"),
                            rs.getString("Email"),
                            rs.getString("UserName"),
                            rs.getString("Password"),
                            rs.getInt("RoleID"),
                            rs.getString("TypeOfLogin")
                    );
                    list.add(account);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }
        return list;
    }
    
     public List<Account> getAllAccount() {
        String sql = """
                       SELECT a.*
                             FROM Account a
                             LEFT JOIN AccountInRoom air ON a.AccountId = air.AccountId
                             WHERE air.AccountId IS NULL
                             AND a.RoleID NOT IN (1, 2);
                     """;
        List<Account> list = new ArrayList<>();
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    Account account = new Account(
                            rs.getInt("AccountId"),
                            rs.getString("Email"),
                            rs.getString("UserName"),
                            rs.getString("Password"),
                            rs.getInt("RoleID"),
                            rs.getString("TypeOfLogin")
                    );
                    list.add(account);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }
        return list;
    }

        public List<Account> getInfoAccount() {
        String sql = """
               SELECT 
                       u.[AccountId],
                       u.[FullName],
                       u.[Province],
                       u.[District],
                       u.[Ward],
                       u.[AddressDetail],
                       u.[Avatar],
                       u.[DOB],
                       u.[PhoneNumber],
                       u.[Gender],
                       a.[Email]
                   FROM [InformationOfUser] u
                   INNER JOIN [Account] a ON u.[AccountId] = a.[AccountId]WHERE  a.RoleID NOT IN (1,2);
                     """;
        List<Account> list = new ArrayList<>();
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    /*    public Account(int accountID, String email, String fullName, String province, String district, String ward, String addressDetail, String avatar, Date dob, String phoneNumber, int gender)*/
                    Account account = new Account(
                            rs.getInt("AccountId"),
                            rs.getString("Email"),
                            rs.getString("FullName"),
                            rs.getString("Province"),
                            rs.getString("District"),
                            rs.getString("Ward"),
                            rs.getString("AddressDetail"),
                            rs.getString("Avatar"),
                            rs.getDate("DOB"),
                            rs.getString("PhoneNumber"),
                            rs.getInt("Gender")
                    );
                    list.add(account);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }
        return list;
    }
      public int updateRoleAccount(int accountId) {
        int rowAffected = 0;

        String sql = "UPDATE [dbo].[Account]\n"
                + "SET [RoleID] = ?\n"
                + "WHERE AccountId = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, 2);  // Assuming RoleID 2 is the desired new role ID
            ps.setInt(2, accountId);
            rowAffected = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowAffected;
    }

    public List<Account> getTenantAccount() {
        String sql = """
                       SELECT a.*
                             FROM Account a
                             LEFT JOIN AccountInRoom air ON a.AccountId = air.AccountId
                             WHERE air.AccountId IS NULL
                             AND a.RoleID NOT IN (1,2);
                     """;
        List<Account> list = new ArrayList<>();
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    Account account = new Account(
                            rs.getInt("AccountId"),
                            rs.getString("Email"),
                            rs.getString("UserName"),
                            rs.getString("Password"),
                            rs.getInt("RoleID"),
                            rs.getString("TypeOfLogin")
                    );
                    list.add(account);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }
        return list;
    }
  public List<Account> getAllAccountA() {
        String sql = """
                       SELECT * FROM dbo.Account;
                     """;
        List<Account> list = new ArrayList<>();
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    Account account = new Account(
                            rs.getInt("AccountId"),
                            rs.getString("Email"),
                            rs.getString("UserName"),
                            rs.getString("Password"),
                            rs.getInt("RoleID"),
                            rs.getString("TypeOfLogin")
                    );
                    list.add(account);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }
        return list;
    }
    public Account getAccountById(int id) {
        String sql = "SELECT * FROM Account WHERE AccountId = ?";
        Account account = null;

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, id); // Set the value for the first parameter

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    account = new Account(
                            rs.getInt("AccountId"),
                            rs.getString("Email"),
                            rs.getString("UserName"),
                            rs.getString("Password"),
                            rs.getInt("RoleID"),
                            rs.getString("TypeOfLogin")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }
        return account;
    }

    public Account getAccountByUserName(String userName) {
        String sql = "SELECT * FROM dbo.Account WHERE UserName=?";
        Account account = null;
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, userName); // Set the value for the first parameter

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    account = new Account(
                            rs.getInt("AccountId"),
                            rs.getString("Email"),
                            rs.getString("UserName"),
                            rs.getString("Password"),
                            rs.getInt("RoleID"),
                            rs.getString("TypeOfLogin")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }
        return account;
    }

    public Account getAccountByUserEmail(String email) {
        String sql = "SELECT * FROM dbo.Account WHERE email=?";
        Account account = null;
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, email); // Set the value for the first parameter

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    account = new Account(
                            rs.getInt("AccountId"),
                            rs.getString("Email"),
                            rs.getString("UserName"),
                            rs.getString("Password"),
                            rs.getInt("RoleID"),
                            rs.getString("TypeOfLogin")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }
        return account;
    }

    public boolean checkUsernameAndPassword(String username, String password) {
        BCrypt bcrypt = new BCrypt();

        //khi so sánh chuỗi và dùng setString thì không cần dùng dấu ''
        String sql = "select * from Account where UserName = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String pwHashed = rs.getString("PassWord");
                if (bcrypt.checkpw(password, pwHashed)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

//    public boolean updateProfile(Account account) {
//        String sql = "UPDATE [dbo].[Account]\n"
//                + "   SET [FullName] = ?\n"
//                + "      ,[Province] = ?\n"
//                + "      ,[District] = ?\n"
//                + "      ,[Ward] = ?\n"
//                + "      ,[AddressDetail] = ?\n"
//                + "      ,[Email] = ?\n"
//                + "      ,[PhoneNumber] =?\n"
//                + "      ,[Gender] = ?\n"
//                + "      ,[UserName] = ?\n"
//                + "      ,[Avatar] =?\n"
//                + "      ,[CIC] = ?\n"
//                + "      ,[DOB] =?\n"
//                + " WHERE AccountID = ?\n";
//        try {
//            PreparedStatement stm = connection.prepareStatement(sql);
//
//            stm.setString(1, account.getFullName());
//            stm.setString(2, account.getProvince());
//            stm.setString(3, account.getDistrict());
//            stm.setString(4, account.getWard());
//            stm.setString(5, account.getAddressDetail());
//            stm.setString(6, account.getEmail());
//            stm.setString(7, account.getPhoneNumber());
//            stm.setInt(8, account.getGender());
//            stm.setString(9, account.getUsername());
//            stm.setString(10, account.getAvatar());
//            stm.setString(11, account.getCic());
//            stm.setDate(12, new java.sql.Date(account.getDob().getTime()));
//            stm.setInt(13, account.getAccountID());
//            return stm.executeUpdate() > 0;
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return false;
//    }

    public int insertAccount(Account account) {
        int rowAffected = 0;

        String sql = "INSERT INTO [dbo].[Account]\n"
                + "           ([Email]\n"
                + "           ,[UserName]\n"
                + "           ,[PassWord]\n"
                + "           ,[RoleID]\n"
                + "           ,[TypeOfLogin])\n"
                + "     VALUES  "
                + "(?,?,?,?,?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, account.getEmail());
            ps.setString(2, account.getUsername());
            ps.setString(3, account.getPassword());
            ps.setInt(4, account.getRoleId());
            ps.setString(5, account.getTypeOfLogin());

            // nếu như insert 1 customer giống username thì sẽ lỗi.
            rowAffected = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowAffected;
    }

    public boolean changePassword(String password, int id) {
        String sql = "UPDATE Account\n"
                + "SET PassWord = ?\n"
                + "WHERE AccountID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);

            stm.setString(1, password);
            stm.setInt(2, id);
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public int updateAccount(Account account) {
        int rowAffected = 0;

        String sql = "UPDATE [dbo].[Account]\n"
                + "   SET [Email] = ?\n"
                + "      ,[UserName] = ?\n"
                + "      ,[PassWord] = ?\n"
                + "      ,[RoleID] = ?\n"
                + "      ,[TypeOfLogin] = ?\n"
                + " WHERE AccountId = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, account.getEmail());
            ps.setString(2, account.getUsername());
            ps.setString(3, account.getPassword());
            ps.setInt(4, account.getRoleId());
            ps.setString(5, account.getTypeOfLogin());
            ps.setInt(6, account.getAccountID());

            rowAffected = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowAffected;
    }
    
    public static void main(String[] args) {
        AccountDAO ad = new AccountDAO();
        System.out.println(ad.checkUsernameAndPassword("nhatfpt123s", "trananhnhatlop10B@"));
    }

}
