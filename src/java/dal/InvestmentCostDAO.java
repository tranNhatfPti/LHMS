/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import java.util.List;
import model.InvestmentCost;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import model.TypeOfInvestmentCosts;

/**
 *
 * @author Nguyễn Công Vinh
 */
public class InvestmentCostDAO extends DBContext {

    /**
     * This function to get list save all investment costs from database by
     * lodging house id
     *
     * @param lodgingHouseId is id of lodging house want to search
     * @return list of all investment costs with lodging house id
     */
    public List<InvestmentCost> getAllInvestmentCostByLodgingHouseId(int lodgingHouseId, int statusAccept) {
        List<InvestmentCost> list = new ArrayList<>();
        String sql = "SELECT * FROM InvestmentCosts WHERE LodgingHouseId = ? AND StatusDelete = 1 AND StatusAccept = ?";
        try {

            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, lodgingHouseId);
            st.setInt(2, statusAccept);
            ResultSet rs = st.executeQuery();
            TypeOfInvestmentCostDAO tyeOfInvestmentCostDAO = new TypeOfInvestmentCostDAO();
            LodgingHousesDAO lodgingHousesDAO = new LodgingHousesDAO();
            AccountDAO accountDAO = new AccountDAO();
            InformationOfUserDAO informationOfUserDAO = new InformationOfUserDAO();
            while (rs.next()) {
                InvestmentCost investmentCost = new InvestmentCost(
                        rs.getInt("InvestmentCostID"),
                        rs.getDouble("Price"),
                        tyeOfInvestmentCostDAO.getTypeOfInvestmentCostByID(rs.getInt("TypeId")),
                        rs.getDate("DateTime"),
                        rs.getString("Description"),
                        accountDAO.getAccountById(rs.getInt("AccountId")),
                        lodgingHousesDAO.getLodgingHouseById(rs.getInt("LodgingHouseId")),
                        rs.getInt("StatusDelete"),
                        rs.getInt("StatusAccept"),
                        rs.getInt("TypeAccept")
                );
                investmentCost.setInformationOfUser(informationOfUserDAO.getInformationByAccountID(rs.getInt("AccountId")));
                list.add(investmentCost);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi ở dao này");
        }
        return list;
    }

    /**
     * This function to get list save all investment costs from database contain
     * key word want to search in search bar and select tag
     *
     * @param searchInfo is info want to search
     * @param searchLodgingHouseId
     * @param statusAccept
     * @return
     */
    public List<InvestmentCost> getAllInvestmentCostByKeyWord(String searchInfo, int searchLodgingHouseId, int statusAccept) {
        System.out.println(searchInfo);
        List<InvestmentCost> list = new ArrayList<>();
        String sql = """
        SELECT I.InvestmentCostID, I.Price, I.TypeId, I.DateTime, I.Description, I.AccountId, I.LodgingHouseId, I.StatusDelete, I.StatusAccept, I.TypeAccept
        FROM investmentcosts I 
        FULL JOIN TypeOfInvestmentCosts T ON I.TypeId = T.Id 
        FULL JOIN LodgingHouses L ON L.LodgingHouseId = I.LodgingHouseId
        FULL JOIN Account A ON A.AccountId = I.AccountId
        FULL JOIN InformationOfUser IU ON IU.AccountId = I.AccountId
        WHERE (I.Price LIKE ? OR T.Name LIKE ? OR I.DateTime LIKE ? OR I.Description LIKE ? OR IU.FullName LIKE ?)
    """;

        if (searchLodgingHouseId != 0) {
            sql += " AND I.LodgingHouseId = ? AND I.StatusDelete = ? AND StatusAccept = ?";
        }

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            String searchPattern = "%" + searchInfo + "%";
            st.setString(1, searchPattern);
            st.setString(2, searchPattern);
            st.setString(3, searchPattern);
            st.setString(4, searchPattern);
            st.setString(5, searchPattern);

            if (searchLodgingHouseId != 0) {
                st.setInt(6, searchLodgingHouseId);
            }
            st.setInt(7, 1);
            st.setInt(8, statusAccept);

            ResultSet rs = st.executeQuery();
            TypeOfInvestmentCostDAO typeOfInvestmentCostDAO = new TypeOfInvestmentCostDAO();
            LodgingHousesDAO lodgingHousesDAO = new LodgingHousesDAO();
            AccountDAO accountDAO = new AccountDAO();
            InformationOfUserDAO informationOfUserDAO = new InformationOfUserDAO();
            while (rs.next()) {
                InvestmentCost investmentCost = new InvestmentCost(
                        rs.getInt("InvestmentCostID"),
                        rs.getDouble("Price"),
                        typeOfInvestmentCostDAO.getTypeOfInvestmentCostByID(rs.getInt("TypeId")),
                        rs.getDate("DateTime"),
                        rs.getString("Description"),
                        accountDAO.getAccountById(rs.getInt("AccountId")),
                        lodgingHousesDAO.getLodgingHouseById(rs.getInt("LodgingHouseId")),
                        rs.getInt("StatusDelete"),
                        rs.getInt("StatusAccept"),
                        rs.getInt("TypeAccept")
                );
                investmentCost.setInformationOfUser(informationOfUserDAO.getInformationByAccountID(rs.getInt("AccountId")));
                list.add(investmentCost);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        System.out.println(sql);
        return list;
    }

    /**
     * This function to get list save all investment costs from database have
     * information about select tag
     *
     * @param searchLodgingHouseId is id of lodging house want to search
     * @param searchTypeOfInvestmentCostId is id type of investment costs want
     * to search
     * @param dateFrom is from date from investment cost
     * @param dateTo if to date from investment cost
     * @param statusAccept
     * @return the investment costs list contain all of attribute want to search
     */
    public List<InvestmentCost> getAllInvestmentCostBySelect(int searchLodgingHouseId, String searchTypeOfInvestmentCostId, String dateFrom, String dateTo, int statusAccept) {
        List<InvestmentCost> list = new ArrayList<>();
        String sql = """
                     SELECT I.InvestmentCostID, I.Price, I.TypeId, I.DateTime, I.Description, I.AccountId, I.LodgingHouseId, I.StatusDelete, I.StatusAccept, I.TypeAccept
                                     from investmentcosts I FULL JOIN TypeOfInvestmentCosts T
                                     \t\t\t\t\tON I.TypeId = T.Id 
                                    \t\t\t\t\tFULL JOIN LodgingHouses L 
                                     \t\t\t\tON L.LodgingHouseId = I.LodgingHouseId
                                   \t\t\t\t\tFULL JOIN Account A
                                    \t\t\t\t\tON A.AccountId = I.AccountId
                                     WHERE I.StatusDelete =1 AND I.StatusAccept = ?""";
        if (searchTypeOfInvestmentCostId != null && !searchTypeOfInvestmentCostId.isEmpty()) {
            sql += " AND I.TypeId = " + searchTypeOfInvestmentCostId;
        }
        if (dateFrom != null && !dateFrom.isEmpty()) {
            sql += " AND I.DateTime >= '" + dateFrom + "'";
        }
        if (dateTo != null && !dateTo.isEmpty()) {
            sql += " AND I.DateTime <= '" + dateTo + "'";
        }
        if (searchLodgingHouseId != 0) {
            sql += " AND I.LodgingHouseId = " + searchLodgingHouseId;
        }
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, statusAccept);
            ResultSet rs = st.executeQuery();
            TypeOfInvestmentCostDAO tyeOfInvestmentCostDAO = new TypeOfInvestmentCostDAO();
            LodgingHousesDAO lodgingHousesDAO = new LodgingHousesDAO();
            AccountDAO accountDAO = new AccountDAO();
            InformationOfUserDAO informationOfUserDAO = new InformationOfUserDAO();
            while (rs.next()) {
                InvestmentCost investmentCost = new InvestmentCost(
                        rs.getInt("InvestmentCostID"),
                        rs.getDouble("Price"),
                        tyeOfInvestmentCostDAO.getTypeOfInvestmentCostByID(rs.getInt("TypeId")),
                        rs.getDate("DateTime"),
                        rs.getString("Description"),
                        accountDAO.getAccountById(rs.getInt("AccountId")),
                        lodgingHousesDAO.getLodgingHouseById(rs.getInt("LodgingHouseId")),
                        rs.getInt("StatusDelete"),
                        rs.getInt("StatusAccept"),
                        rs.getInt("TypeAccept")
                );
                investmentCost.setInformationOfUser(informationOfUserDAO.getInformationByAccountID(rs.getInt("AccountId")));
                list.add(investmentCost);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        System.out.println(sql);
        return list;
    }

    /**
     * This function use to insert investment cost to database
     *
     * @param investmentCost is investment cost want to insert
     */
    public void insertInvestmentCost(InvestmentCost investmentCost) {
        String sql = """
                     INSERT INTO InvestmentCosts (Price, TypeId, [DateTime], [Description], AccountId, LodgingHouseId, StatusDelete, StatusAccept, TypeAccept)
                     VALUES (?, ?, ?, ?, ?, ?, 1, 2, 1);""";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setDouble(1, investmentCost.getPrice());
            st.setInt(2, investmentCost.getTypeOfInvestmentCosts().getId());
            st.setDate(3, new java.sql.Date(investmentCost.getDateTime().getTime()));
            st.setString(4, investmentCost.getDescription());
            st.setInt(5, investmentCost.getAccount().getAccountID());
            st.setInt(6, investmentCost.getLodgingHouse().getLodgingHouseId());
            st.executeQuery();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void insertInvestmentCostConfirm(InvestmentCost investmentCost) {
        String sql = """
                     INSERT INTO InvestmentCosts (Price, TypeId, [DateTime], [Description], AccountId, LodgingHouseId, StatusDelete, StatusAccept, TypeAccept)
                     VALUES (?, ?, ?, ?, ?, ?, 1, 2, ?);""";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setDouble(1, investmentCost.getPrice());
            st.setInt(2, investmentCost.getTypeOfInvestmentCosts().getId());
            st.setDate(3, new java.sql.Date(investmentCost.getDateTime().getTime()));
            st.setString(4, investmentCost.getDescription());
            st.setInt(5, investmentCost.getAccount().getAccountID());
            st.setInt(6, investmentCost.getLodgingHouse().getLodgingHouseId());
            st.setInt(7, investmentCost.getTypeAccept());
            st.executeQuery();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * This function to delete investment cost from database
     *
     * @param investmentCostId is id of investment cost want to delete
     */
    public void deleteInvestmentCost(int investmentCostId) {
        String sql = "UPDATE InvestmentCosts SET StatusDelete = 0 WHERE InvestmentCostID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, investmentCostId);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("lỗi xóa");
        }
    }

    /**
     * This function to update information of investment cost
     *
     * @param investmentCost is investment cost have all data after update
     */
    public void updateInvestmentCost(InvestmentCost investmentCost) {
        String sql = """
                     UPDATE InvestmentCosts
                     SET 
                         Price = ?,
                         TypeId = ?,
                         [DateTime] = ?,  -- Use the appropriate date and time
                         [Description] = ?,
                         AccountId = ?,
                         LodgingHouseId = ?,
                         StatusAccept = ?,
                         TypeAccept = ?
                     WHERE 
                         InvestmentCostID = ?;""";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setDouble(1, investmentCost.getPrice());
            st.setInt(2, investmentCost.getTypeOfInvestmentCosts().getId());
            st.setDate(3, new java.sql.Date(investmentCost.getDateTime().getTime()));
            st.setString(4, investmentCost.getDescription());
            st.setInt(5, investmentCost.getAccount().getAccountID());
            st.setInt(6, investmentCost.getLodgingHouse().getLodgingHouseId());
            st.setInt(7, investmentCost.getStatusAccept());
            st.setInt(8, investmentCost.getTypeAccept());
            st.setInt(9, investmentCost.getInvestmentCostID());
            st.executeQuery();
        } catch (SQLException e) {
            System.out.println("lỗi update");
        }
    }

    /**
     * This function use to get investment cost by id
     *
     * @param id is id of investment cost want to get
     * @return investment cost after searching in database
     */
    public InvestmentCost getInvestmentCostByID(int id) {
        String sql = "SELECT * FROM InvestmentCosts WHERE InvestmentCostID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            TypeOfInvestmentCostDAO tyeOfInvestmentCostDAO = new TypeOfInvestmentCostDAO();
            LodgingHousesDAO lodgingHousesDAO = new LodgingHousesDAO();
            AccountDAO accountDAO = new AccountDAO();

            if (rs.next()) {
                TypeOfInvestmentCosts typeOfInvestmentCosts = tyeOfInvestmentCostDAO.getTypeOfInvestmentCostByID(rs.getInt("TypeId"));
                InvestmentCost investmentCost;
                if (typeOfInvestmentCosts != null) {
                    investmentCost = new InvestmentCost(rs.getInt("InvestmentCostID"),
                            rs.getDouble("Price"),
                            tyeOfInvestmentCostDAO.getTypeOfInvestmentCostByID(rs.getInt("TypeId")),
                            rs.getDate("DateTime"),
                            rs.getString("Description"),
                            accountDAO.getAccountById(rs.getInt("AccountId")),
                            lodgingHousesDAO.getLodgingHouseById(rs.getInt("LodgingHouseId")),
                            rs.getInt("StatusDelete"),
                            rs.getInt("StatusAccept"),
                            rs.getInt("TypeAccept")
                    );
                } else {
                    investmentCost = new InvestmentCost(rs.getInt("InvestmentCostID"),
                            rs.getDouble("Price"),
                            rs.getDate("DateTime"),
                            rs.getString("Description"),
                            accountDAO.getAccountById(rs.getInt("AccountId")),
                            lodgingHousesDAO.getLodgingHouseById(rs.getInt("LodgingHouseId")));
                }
                return investmentCost;

            }
        } catch (SQLException e) {
            System.out.println("lỗi ở đây");
        }
        return null;
    }
    

    public static void main(String[] args) {
        InvestmentCostDAO da = new InvestmentCostDAO();
        List<InvestmentCost> list = da.getAllInvestmentCostByLodgingHouseId(3, 1);
        for (InvestmentCost investmentCost : list) {
            if (investmentCost.getTypeOfInvestmentCosts() != null) {
                System.out.println(investmentCost.getTypeOfInvestmentCosts().getStatusDelete() + "jwn");
            }
        }
    }
}
