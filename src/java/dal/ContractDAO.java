/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Account;
import model.Contract;

import model.Room;

public class ContractDAO extends DBContext {

    public Contract getContractByDateAndTenant(String roomId, String date) {
        String sql = """
                     SELECT * FROM Contract WHERE RoomId = ?  AND DateFrom <= ? and DateTo >= ?
                     \t\t\t\t\t\t\tAND statusAccept != 3 AND StatusDelete = 1 AND TypeAccept = 1""";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, roomId);
            st.setString(2, date);
            st.setString(3, date);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                AccountDAO ad = new AccountDAO();
                RoomDAO rd = new RoomDAO();
                Room room = rd.getRoomsById(rs.getString("RoomId"));
                Account tenant = ad.getAccountById(rs.getInt("TenantId"));
                Account manager = ad.getAccountById(rs.getInt("ManageId"));
                Contract c = new Contract(room, tenant, manager,
                        rs.getDate("DateFrom"),
                        rs.getDate("DateTo"),
                        rs.getInt("StatusDelete"),
                        rs.getInt("StatusAccept"),
                        rs.getLong("ContractDeposit"),
                        rs.getInt("TypeAccept"),
                        rs.getInt("RoleCreateContract"));
                return c;
            }
        } catch (SQLException e) {
            System.out.println(e + "loi o contract");
        }
        return null;
    }

    public List<Contract> getContractExpired() {
        String sql = "SELECT *FROM [LHMS].[dbo].[Contract] WHERE DateTo <= GETDATE()";

        List<Contract> c1 = new ArrayList<>();

        try {
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                AccountDAO ad = new AccountDAO();
                RoomDAO rd = new RoomDAO();

                Room room = rd.getRoomsById(rs.getString("RoomId"));
                Account tenant = ad.getAccountById(rs.getInt("TenantId"));
                Account manager = ad.getAccountById(rs.getInt("ManageId"));
                Contract c = new Contract(room, tenant, manager,
                        rs.getDate("DateFrom"),
                        rs.getDate("DateTo"),
                        rs.getInt("StatusDelete"),
                        rs.getInt("StatusAccept"),
                        rs.getLong("ContractDeposit"),
                        rs.getInt("TypeAccept"),
                        rs.getInt("RoleCreateContract"));
                c1.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e + "loi o contract");
        }
        return c1;
    }

    public List<Contract> getOldContractForTenantByKeyword(String keyWord, int tenantID) {
        String sql = """
                    SELECT C.* FROM Contract C full join Rooms R 
                                            ON C.RoomId = R.RoomId
                                               	full join Account A 
                        		    ON A.AccountId = C.TenantId
                        			full join InformationOfUser I 
                        		    ON I.AccountId = C.TenantId
                        		WHERE 
                                            C.statusAccept= 2                              
                                            AND C.TenantId = ?  
                                            AND C.TypeAccept = 1
                        		    AND (R.RoomName LIKE ? OR
                        		    R.Price LIKE ? OR
                                            R.Description LIKE ? OR
                                            C.DateFrom LIKE ? OR
                                            C.DateTo LIKE ? OR
                                            A.Email LIKE ? OR
                                            C.ContractDeposit LIKE ?) 
                     """;
        List<Contract> c1 = new ArrayList<>();

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, tenantID);
            String searchPattern = "%" + keyWord + "%";
            st.setString(2, searchPattern);
            st.setString(3, searchPattern);
            st.setString(4, searchPattern);
            st.setString(5, searchPattern);
            st.setString(6, searchPattern);
            st.setString(7, searchPattern);
            st.setString(8, searchPattern);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                AccountDAO ad = new AccountDAO();
                RoomDAO rd = new RoomDAO();

                Room room = rd.getRoomsById(rs.getString("RoomId"));
                Account tenant = ad.getAccountById(rs.getInt("TenantId"));
                Account manager = ad.getAccountById(rs.getInt("ManageId"));
                Contract c = new Contract(room, tenant, manager,
                        rs.getDate("DateFrom"),
                        rs.getDate("DateTo"),
                        rs.getInt("StatusDelete"),
                        rs.getInt("StatusAccept"),
                        rs.getLong("ContractDeposit"),
                        rs.getInt("TypeAccept"),
                        rs.getInt("RoleCreateContract"));
                c1.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e + "loi o contract search");
        }
        return c1;
    }
    
    public Contract getContractByRoomId(String roomId) {
        String sql = """
                     SELECT *
                       FROM [LHMS].[dbo].[Contract] WHERE ROOMID = ? AND StatusAccept = ? AND StatusDelete = ? AND TypeAccept = ?
                     """;
        Contract c = new Contract();

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, roomId);
            st.setInt(2, 2);
            st.setInt(3, 1);
            st.setInt(4, 1);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                AccountDAO ad = new AccountDAO();
                RoomDAO rd = new RoomDAO();

                Room room = rd.getRoomsById(rs.getString("RoomId"));
                Account tenant = ad.getAccountById(rs.getInt("TenantId"));
                Account manager = ad.getAccountById(rs.getInt("ManageId"));
                c.setContractDeposit(rs.getLong("ContractDeposit"));
                c.setManager(manager);
                c.setDateFrom(rs.getDate("DateFrom"));
                c.setDateTo(rs.getDate("DateTo"));
                c.setRoleCreateContract(rs.getInt("RoleCreateContract"));
                c.setRoom(room);
                c.setStatusDelete(rs.getInt("StatusDelete"));
                c.setStatusAccept(rs.getInt("StatusAccept"));
                c.setTypeOfAccept(rs.getInt("TypeAccept"));
                c.setTenantId(tenant);
                return c;
            }
        } catch (SQLException e) {
            System.out.println(e + "loi o contract");
        }
        return null;
    }

    public List<Contract> getOldContractForTenant(int tenantID) {
        String sql = """
                     SELECT * FROM Contract 
                                WHERE TenantId = ? 
                                AND TypeAccept = 1
                                AND StatusAccept = 2;;
                     """;
        List<Contract> c1 = new ArrayList<>();

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, tenantID);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                AccountDAO ad = new AccountDAO();
                RoomDAO rd = new RoomDAO();

                Room room = rd.getRoomsById(rs.getString("RoomId"));
                Account tenant = ad.getAccountById(rs.getInt("TenantId"));
                Account manager = ad.getAccountById(rs.getInt("ManageId"));
                Contract c = new Contract(room, tenant, manager,
                        rs.getDate("DateFrom"),
                        rs.getDate("DateTo"),
                        rs.getInt("StatusDelete"),
                        rs.getInt("StatusAccept"),
                        rs.getLong("ContractDeposit"),
                        rs.getInt("TypeAccept"),
                        rs.getInt("RoleCreateContract"));
                c1.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e + "loi o contract");
        }
        return c1;
    }

    public List<Contract> getContractForTenantByKeyword(int statusAccept, String keyWord, int tenantID) {
        String sql = """
                    SELECT C.* FROM Contract C full join Rooms R 
                                            ON C.RoomId = R.RoomId
                                               	full join Account A 
                        		    ON A.AccountId = C.TenantId
                        			full join InformationOfUser I 
                        		    ON I.AccountId = C.TenantId
                        		WHERE C.StatusDelete = 1					
                                            AND C.statusAccept = ?
                        		    AND (R.RoomName LIKE ? OR
                        		    R.Price LIKE ? OR
                                            R.Description LIKE ? OR
                                            C.DateFrom LIKE ? OR
                                            C.DateTo LIKE ? OR
                                            A.Email LIKE ? OR
                                            C.ContractDeposit LIKE ?) 
                                            AND C.TenantId = ?
                                            AND DateTo >= GETDATE() 
                     """;
        List<Contract> c1 = new ArrayList<>();

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, statusAccept);
            String searchPattern = "%" + keyWord + "%";
            st.setString(2, searchPattern);
            st.setString(3, searchPattern);
            st.setString(4, searchPattern);
            st.setString(5, searchPattern);
            st.setString(6, searchPattern);
            st.setString(7, searchPattern);
            st.setString(8, searchPattern);
            st.setInt(9, tenantID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                AccountDAO ad = new AccountDAO();
                RoomDAO rd = new RoomDAO();

                Room room = rd.getRoomsById(rs.getString("RoomId"));
                Account tenant = ad.getAccountById(rs.getInt("TenantId"));
                Account manager = ad.getAccountById(rs.getInt("ManageId"));
                Contract c = new Contract(room, tenant, manager,
                        rs.getDate("DateFrom"),
                        rs.getDate("DateTo"),
                        rs.getInt("StatusDelete"),
                        rs.getInt("StatusAccept"),
                        rs.getLong("ContractDeposit"),
                        rs.getInt("TypeAccept"),
                        rs.getInt("RoleCreateContract"));
                c1.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e + "loi o contract search");
        }
        return c1;
    }

    public Contract getContractByDateAndTenant(String roomId, int month, int year) {
        String sql = "SELECT * FROM Contract WHERE RoomId = ?  AND MONTH(DateFrom) = ? AND YEAR(DateFrom) = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, roomId);
            st.setInt(2, month);
            st.setInt(3, year);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                AccountDAO ad = new AccountDAO();
                RoomDAO rd = new RoomDAO();
                Room room = rd.getRoomsById(rs.getString("RoomId"));
                Account tenant = ad.getAccountById(rs.getInt("TenantId"));
                Account manager = ad.getAccountById(rs.getInt("ManageId"));
                Contract c = new Contract(room, tenant, manager,
                        rs.getDate("DateFrom"),
                        rs.getDate("DateTo"),
                        rs.getInt("StatusDelete"),
                        rs.getInt("StatusAccept"),
                        rs.getLong("ContractDeposit"),
                        rs.getInt("TypeAccept"),
                        rs.getInt("RoleCreateContract"));
                return c;
            }
        } catch (SQLException e) {
            System.out.println(e + "loi o contract");
        }
        return null;
    }

    public void insertContract(Contract contract) {
        String sql = """
                     INSERT INTO [dbo].[Contract] ([RoomId], [TenantId], [ManageId], [DateFrom], [DateTo], [StatusDelete], [StatusAccept], ContractDeposit, TypeAccept, RoleCreateContract)
                     VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);""";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, contract.getRoom().getRoomId());
            st.setDouble(2, contract.getTenantId().getAccountID());
            st.setInt(3, contract.getManager().getAccountID());
            st.setDate(4, new java.sql.Date(contract.getDateFrom().getTime()));
            st.setDate(5, new java.sql.Date(contract.getDateTo().getTime()));
            st.setInt(6, contract.getStatusDelete());
            st.setInt(7, contract.getStatusAccept());
            st.setDouble(8, contract.getContractDeposit());
            st.setDouble(9, contract.getTypeOfAccept());
            st.setInt(10, contract.getRoleCreateContract());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public Contract getContractBy(int id, String date) {
        String sql = """
                    SELECT  [RoomId]
                           ,[TenantId]
                           ,[ManageId]
                           ,[DateFrom]
                           ,[DateTo]
                           ,[StatusDelete]
                           ,[StatusAccept]
                       FROM [LHMS1].[dbo].[Contract]  where [TenantId]=? and StatusAccept=1   and ? <=[DateTo]""";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            st.setString(2, date);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                AccountDAO ad = new AccountDAO();
                RoomDAO rd = new RoomDAO();
                Room room = rd.getRoomsById(rs.getString("RoomId"));
                Account tenant = ad.getAccountById(rs.getInt("TenantId"));
                Account manager = ad.getAccountById(rs.getInt("ManageId"));
                Contract c = new Contract(room, tenant, manager,
                        rs.getDate("DateFrom"),
                        rs.getDate("DateTo"),
                        rs.getInt("StatusDelete"),
                        rs.getInt("StatusAccept"));
                return c;
            }
        } catch (SQLException e) {
            System.out.println(e + "loi o contract");
        }
        return null;
    }

    public List<Contract> getContract() {
        String sql = """
                     SELECT *
                       FROM [LHMS1].[dbo].[Contract]  
                     """;
        List<Contract> c1 = new ArrayList<>();

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                AccountDAO ad = new AccountDAO();
                RoomDAO rd = new RoomDAO();

                Room room = rd.getRoomsById(rs.getString("RoomId"));
                Account tenant = ad.getAccountById(rs.getInt("TenantId"));
                Account manager = ad.getAccountById(rs.getInt("ManageId"));
                Contract c = new Contract(room, tenant, manager,
                        rs.getDate("DateFrom"),
                        rs.getDate("DateTo"),
                        rs.getInt("StatusDelete"),
                        rs.getInt("StatusAccept"));
                c1.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e + "loi o contract");
        }
        return c1;
    }

    public Contract getContractByRoomId(int id) {
        String sql = """
                    SELECT  [RoomId]
                           ,[TenantId]
                           ,[ManageId]
                           ,[DateFrom]
                           ,[DateTo]
                           ,[StatusDelete]
                           ,[StatusAccept]
                       FROM [dbo].[Contract]  where [RoomId]=? and StatusAccept=1""";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                AccountDAO ad = new AccountDAO();
                RoomDAO rd = new RoomDAO();
                Room room = rd.getRoomsById(rs.getString("RoomId"));
                Account tenant = ad.getAccountById(rs.getInt("TenantId"));
                Account manager = ad.getAccountById(rs.getInt("ManageId"));
                Contract c = new Contract(room, tenant, manager,
                        rs.getDate("DateFrom"),
                        rs.getDate("DateTo"),
                        rs.getInt("StatusDelete"),
                        rs.getInt("StatusAccept"));
                return c;
            }
        } catch (SQLException e) {
            System.out.println(e + "loi o contract");
        }
        return null;
    }

    public List<Contract> getContractByRoomId(String roomId, int statusAccept, int statusDelete) {
        String sql = """
                      SELECT *
                       FROM [LHMS].[dbo].[Contract] WHERE ROOMID = ? AND StatusAccept = ? AND StatusDelete = ?
                     """;
        List<Contract> c1 = new ArrayList<>();

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, roomId);
            st.setInt(2, statusAccept);
            st.setInt(3, statusDelete);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                AccountDAO ad = new AccountDAO();
                RoomDAO rd = new RoomDAO();

                Room room = rd.getRoomsById(rs.getString("RoomId"));
                Account tenant = ad.getAccountById(rs.getInt("TenantId"));
                Account manager = ad.getAccountById(rs.getInt("ManageId"));
                Contract c = new Contract(room, tenant, manager,
                        rs.getDate("DateFrom"),
                        rs.getDate("DateTo"),
                        rs.getInt("StatusDelete"),
                        rs.getInt("StatusAccept"),
                        rs.getLong("ContractDeposit"),
                        rs.getInt("TypeAccept"),
                        rs.getInt("RoleCreateContract"));
                c1.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e + "loi o contract");
        }
        return c1;
    }

    public List<Contract> getContractForTenant(int tenantID, int statusAccept) {
        String sql = """
                     SELECT * FROM Contract 
                     WHERE TenantId = ? 
                       AND StatusDelete = 1 
                       AND DateTo >= GETDATE()  AND StatusAccept = ?;
                     """;
        List<Contract> c1 = new ArrayList<>();

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, tenantID);
            st.setInt(2, statusAccept);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                AccountDAO ad = new AccountDAO();
                RoomDAO rd = new RoomDAO();

                Room room = rd.getRoomsById(rs.getString("RoomId"));
                Account tenant = ad.getAccountById(rs.getInt("TenantId"));
                Account manager = ad.getAccountById(rs.getInt("ManageId"));
                Contract c = new Contract(room, tenant, manager,
                        rs.getDate("DateFrom"),
                        rs.getDate("DateTo"),
                        rs.getInt("StatusDelete"),
                        rs.getInt("StatusAccept"),
                        rs.getLong("ContractDeposit"),
                        rs.getInt("TypeAccept"),
                        rs.getInt("RoleCreateContract"));
                c1.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e + "loi o contract");
        }
        return c1;
    }

    public List<Contract> getContractForTenantByAccept(int tenantID, int statusAccept) {
        String sql = """
                     SELECT * FROM Contract 
                     WHERE TenantId = ? 
                     AND StatusDelete = 1 
                     AND StatusAccept = ?;
                     """;
        List<Contract> c1 = new ArrayList<>();

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, tenantID);
            st.setInt(2, statusAccept);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                AccountDAO ad = new AccountDAO();
                RoomDAO rd = new RoomDAO();

                Room room = rd.getRoomsById(rs.getString("RoomId"));
                Account tenant = ad.getAccountById(rs.getInt("TenantId"));
                Account manager = ad.getAccountById(rs.getInt("ManageId"));
                Contract c = new Contract(room, tenant, manager,
                        rs.getDate("DateFrom"),
                        rs.getDate("DateTo"),
                        rs.getInt("StatusDelete"),
                        rs.getInt("StatusAccept"),
                        rs.getLong("ContractDeposit"),
                        rs.getInt("TypeAccept"),
                        rs.getInt("RoleCreateContract"));
                c1.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e + "loi o contract");
        }
        return c1;
    }

    public Contract getContractByPrimaryKey(String roomId, int tenantId, String dateFrom, String dateTo, int typeAccept, int statusAccept) {
        String sql = """
                    SELECT * FROM Contract WHERE 
                     RoomId = ? AND
                     TenantId = ? AND
                     DateFrom = ? AND
                     DateTo = ? AND 
                     TypeAccept = ? AND
                     StatusAccept = ? """;

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, roomId);
            st.setInt(2, tenantId);
            st.setString(3, dateFrom);
            st.setString(4, dateTo);
            st.setInt(5, typeAccept);
            st.setInt(6, statusAccept);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                AccountDAO ad = new AccountDAO();
                RoomDAO rd = new RoomDAO();
                Room room = rd.getRoomsById(rs.getString("RoomId"));
                Account tenant = ad.getAccountById(rs.getInt("TenantId"));
                Account manager = ad.getAccountById(rs.getInt("ManageId"));
                Contract c = new Contract(room, tenant, manager,
                        rs.getDate("DateFrom"),
                        rs.getDate("DateTo"),
                        rs.getInt("StatusDelete"),
                        rs.getInt("StatusAccept"),
                        rs.getLong("ContractDeposit"),
                        rs.getInt("TypeAccept"),
                        rs.getInt("RoleCreateContract"));
                return c;
            }
        } catch (SQLException e) {
            System.out.println(e + "loi o contract primary");
        }
        return null;
    }

    public Contract getContractAccept(String roomId, int tenantId, String dateFrom, String dateTo) {
        String sql = """
                    SELECT * FROM Contract WHERE 
                     RoomId = ? AND
                     TenantId = ? AND
                     DateFrom = ? AND
                     DateTo = ? """;

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, roomId);
            st.setInt(2, tenantId);
            st.setString(3, dateFrom);
            st.setString(4, dateTo);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                AccountDAO ad = new AccountDAO();
                RoomDAO rd = new RoomDAO();
                Room room = rd.getRoomsById(rs.getString("RoomId"));
                Account tenant = ad.getAccountById(rs.getInt("TenantId"));
                Account manager = ad.getAccountById(rs.getInt("ManageId"));
                Contract c = new Contract(room, tenant, manager,
                        rs.getDate("DateFrom"),
                        rs.getDate("DateTo"),
                        rs.getInt("StatusDelete"),
                        rs.getInt("StatusAccept"),
                        rs.getLong("ContractDeposit"),
                        rs.getInt("TypeAccept"),
                        rs.getInt("RoleCreateContract"));
                return c;
            }
        } catch (SQLException e) {
            System.out.println(e + "loi o contract primary");
        }
        return null;
    }

    public void updateContract(Contract contract) {
        String sql = """
                     UPDATE Contract
                     SET 
                         RoomId = ?,
                         TenantId = ?,
                         ManageId = ?, 
                         DateFrom = ?,
                         DateTo = ?,
                         StatusDelete = ?,
                         StatusAccept = ?,
                         ContractDeposit = ?,
                         TypeAccept = ?,
                         RoleCreateContract = ?
                     WHERE 
                        RoomId = ? AND
                        TenantId = ? AND
                        DateFrom = ? AND
                        DateTo = ? AND 
                        TypeAccept = ?;""";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, contract.getRoom().getRoomId());
            st.setDouble(2, contract.getTenantId().getAccountID());
            st.setInt(3, contract.getManager().getAccountID());
            st.setDate(4, new java.sql.Date(contract.getDateFrom().getTime()));
            st.setDate(5, new java.sql.Date(contract.getDateTo().getTime()));
            st.setInt(6, contract.getStatusDelete());
            st.setInt(7, contract.getStatusAccept());
            st.setDouble(8, contract.getContractDeposit());
            st.setInt(9, contract.getTypeOfAccept());
            System.out.println("dao" + contract.getStatusAccept());
            st.setInt(10, contract.getRoleCreateContract());
            st.setString(11, contract.getRoom().getRoomId());
            st.setDouble(12, contract.getTenantId().getAccountID());
            st.setDate(13, new java.sql.Date(contract.getDateFrom().getTime()));
            st.setDate(14, new java.sql.Date(contract.getDateTo().getTime()));
            st.setInt(15, contract.getTypeOfAccept());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("lỗi update");
            System.out.println(e);
        }
    }

    public Contract getContractToHandle(String roomId, int tenantId, String dateFrom, String dateTo, int TypeAccept) {
        String sql = """
                     SELECT * FROM Contract WHERE RoomId = ? AND TenantId = ? AND DateFrom = ? 
                       AND DateTo = ? AND TypeAccept = ?   
                     """;
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, roomId);
            st.setInt(2, tenantId);
            st.setString(3, dateFrom);
            st.setString(4, dateTo);
            st.setInt(5, TypeAccept);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                AccountDAO ad = new AccountDAO();
                RoomDAO rd = new RoomDAO();

                Room room = rd.getRoomsById(rs.getString("RoomId"));
                Account tenant = ad.getAccountById(rs.getInt("TenantId"));
                Account manager = ad.getAccountById(rs.getInt("ManageId"));
                Contract c = new Contract(room, tenant, manager,
                        rs.getDate("DateFrom"),
                        rs.getDate("DateTo"),
                        rs.getInt("StatusDelete"),
                        rs.getInt("StatusAccept"),
                        rs.getLong("ContractDeposit"),
                        rs.getInt("TypeAccept"),
                        rs.getInt("RoleCreateContract"));
                return c;
            }
        } catch (SQLException e) {
            System.out.println(e + "loi o contract");
        }
        return null;
    }

    public Contract getContractByDateAndRoomID(String roomId, Date monthYear) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        RoomDAO rd = new RoomDAO();
        AccountDAO ad = new AccountDAO();

        Contract contract = null;
        List<Contract> list = new ArrayList<>();
        String sql = "select * from Contract where RoomId = ? and statusAccept = 1";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, roomId);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                contract = new Contract(
                        rd.getRoomsById(rs.getString("RoomId")),
                        ad.getAccountById(rs.getInt("TenantId")),
                        ad.getAccountById(rs.getInt("ManageId")),
                        rs.getDate("DateFrom"),
                        rs.getDate("DateTo"),
                        rs.getInt("StatusDelete"),
                        rs.getInt("StatusAccept"),
                        rs.getLong("ContractDeposit"),
                        rs.getInt("TypeAccept"),
                        rs.getInt("RoleCreateContract"));
                list.add(contract);
            }
        } catch (SQLException e) {
            System.out.println(e + "loi o contract");
        }

        // có thể có 2 hợp đồng: hđ1 có DateTo cùng tháng với DateFrom của hđ2
        List<Contract> contractChosen = new ArrayList<>();

        // lấy ra tất cả hđ từ DateTo -> DateFrom chứa monthYear(chỉ tháng năm)
        for (Contract c : list) {
            String dateFrom = formatter.format(c.getDateFrom());
            String dateTo = formatter.format(c.getDateTo());
            String needCheck = formatter.format(monthYear);
            if (checkTime(dateFrom, dateTo, needCheck)) {
                contractChosen.add(c);
            }
        }

        if (contractChosen.size() == 0) {
            return null;
        } else if (contractChosen.size() == 1) {
            return contractChosen.get(0);
        } else {
            // nếu như có 2 hđ trở lên thì lấy hợp đồng có DateTo lớn hơn
            contract = contractChosen.get(0);
            for (int i = 1; i <= contractChosen.size() - 1; i++) {
                if (contractChosen.get(i).getDateTo().compareTo(contract.getDateTo()) > 0) {
                    contract = contractChosen.get(i);
                }
            }
            return contract;
        }
    }

    public Contract getCurrentContractOfTenant(String roomId) {
        RoomDAO rd = new RoomDAO();
        AccountDAO ad = new AccountDAO();

        Contract contract = null;
        String sql = "select * from Contract where RoomId = ? and StatusDelete = ? "
                + "and DateFrom <= ? and DateTo >= ? and statusAccept = 1";

        Date currentDate = new Date();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, roomId);
            st.setBoolean(2, true);
            st.setDate(3, new java.sql.Date(currentDate.getTime()));
            st.setDate(4, new java.sql.Date(currentDate.getTime()));

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                contract = new Contract(
                        rd.getRoomsById(rs.getString("RoomId")),
                        ad.getAccountById(rs.getInt("TenantId")),
                        ad.getAccountById(rs.getInt("ManageId")),
                        rs.getDate("DateFrom"),
                        rs.getDate("DateTo"),
                        rs.getInt("StatusDelete"),
                        rs.getInt("StatusAccept"),
                        rs.getLong("ContractDeposit"),
                        rs.getInt("TypeAccept"),
                        rs.getInt("RoleCreateContract"));
            }
        } catch (SQLException e) {
            System.out.println(e + "loi o contract");
        }

        return contract;
    }

    public List<Contract> getAllContractOfTenant(int tenantId) {
        RoomDAO rd = new RoomDAO();
        AccountDAO ad = new AccountDAO();

        List<Contract> list = new ArrayList<>();
        Contract contract;
        String sql = "select * from Contract where TenantId = ? and statusAccept = 1 ";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, tenantId);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                contract = new Contract(
                        rd.getRoomsById(rs.getString("RoomId")),
                        ad.getAccountById(rs.getInt("TenantId")),
                        ad.getAccountById(rs.getInt("ManageId")),
                        rs.getDate("DateFrom"),
                        rs.getDate("DateTo"),
                        rs.getInt("StatusDelete"),
                        rs.getInt("StatusAccept"),
                        rs.getLong("ContractDeposit"),
                        rs.getInt("TypeAccept"),
                        rs.getInt("RoleCreateContract"));
                list.add(contract);
            }
        } catch (SQLException e) {
            System.out.println(e + "loi o contract");
        }

        return list;
    }

    public static boolean checkTime(String dateFrom, String dateTo, String timeNeedCheck) {
        YearMonth from = YearMonth.parse(dateFrom);
        YearMonth to = YearMonth.parse(dateTo);
        YearMonth needCheck = YearMonth.parse(timeNeedCheck);

        // So sánh thời gian và return
        if (!from.isAfter(needCheck) && !to.isBefore(needCheck)) {
            return true;
        }

        return false;
    }

    public List<Contract> getContractInRoomByKeyword(String roomId, int statusAccept, String keyWord) {
        String sql = """
                    SELECT C.* FROM Contract C full join Rooms R 
                                            ON C.RoomId = R.RoomId
                                               	full join Account A 
                        		    ON A.AccountId = C.TenantId
                        			full join InformationOfUser I 
                        		    ON I.AccountId = C.TenantId
                        		WHERE R.RoomId = ?
                                            AND C.StatusDelete = 1					
                                            AND C.statusAccept = ?
                        		    AND (R.RoomName LIKE ? OR
                        		    R.Price LIKE ? OR
                                            R.Description LIKE ? OR
                                            C.DateFrom LIKE ? OR
                                            C.DateTo LIKE ? OR
                                            A.Email LIKE ?  OR
                                            C.ContractDeposit LIKE ?) 
                     """;
        List<Contract> c1 = new ArrayList<>();

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, roomId);
            st.setInt(2, statusAccept);
            String searchPattern = "%" + keyWord + "%";
            st.setString(3, searchPattern);
            st.setString(4, searchPattern);
            st.setString(5, searchPattern);
            st.setString(6, searchPattern);
            st.setString(7, searchPattern);
            st.setString(8, searchPattern);
            st.setString(9, searchPattern);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                AccountDAO ad = new AccountDAO();
                RoomDAO rd = new RoomDAO();

                Room room = rd.getRoomsById(rs.getString("RoomId"));
                Account tenant = ad.getAccountById(rs.getInt("TenantId"));
                Account manager = ad.getAccountById(rs.getInt("ManageId"));
                Contract c = new Contract(room, tenant, manager,
                        rs.getDate("DateFrom"),
                        rs.getDate("DateTo"),
                        rs.getInt("StatusDelete"),
                        rs.getInt("StatusAccept"),
                        rs.getLong("ContractDeposit"),
                        rs.getInt("TypeAccept"),
                        rs.getInt("RoleCreateContract"));
                c1.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e + "loi o contract search");
        }
        return c1;
    }

    public static void main(String[] args) throws java.text.ParseException {
        ContractDAO cd = new ContractDAO();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
//        System.out.println(checkTime("2024-05", "2024-09", "2024-04"));
        Date date = formatter.parse("2024-06");
        System.out.println(cd.getContractByDateAndRoomID("1", date).getRoom().getRoomId());
    }

}
