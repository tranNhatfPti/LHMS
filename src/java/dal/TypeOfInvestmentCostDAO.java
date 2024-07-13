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
import model.TypeOfInvestmentCosts;

/**
 *
 * @author admin
 */
public class TypeOfInvestmentCostDAO extends DBContext {

    /**
     * This function to get all type of investment costs
     *
     * @return list all type of investment costs
     */
    public List<TypeOfInvestmentCosts> getAllTypeOfInvestmentCost() {
        List<TypeOfInvestmentCosts> list = new ArrayList<>();
        String sql = "SELECT * FROM TypeOfInvestmentCosts WHERE StatusDelete = 1";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                TypeOfInvestmentCosts typeOfInvestmentCost = new TypeOfInvestmentCosts(rs.getInt("Id"), rs.getString("Name"));
                list.add(typeOfInvestmentCost);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    /**
     * This function to get all type of investment cost by key word
     *
     * @param keyWord is key word want to search
     * @return list of all type of investment cost contain key word
     */
    public List<TypeOfInvestmentCosts> getAllTypeOfInvestmentCostByKeyWord(String keyWord) {
        List<TypeOfInvestmentCosts> list = new ArrayList<>();
        String sql = "SELECT * FROM TypeOfInvestmentCosts WHERE StatusDelete = 1 AND Name LIKE ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, "%" + keyWord + "%");
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                TypeOfInvestmentCosts typeOfInvestmentCost = new TypeOfInvestmentCosts(rs.getInt("Id"), rs.getString("Name"));
                list.add(typeOfInvestmentCost);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        System.out.println(sql);
        return list;
    }

    /**
     * This function use to insert type of investment cost into database
     *
     * @param typeOfInvestmentCosts is type of investment cost want to insert
     */
    public void insertTypeOfInvestmentCost(TypeOfInvestmentCosts typeOfInvestmentCosts) {
        String sql = "INSERT INTO TypeOfInvestmentCosts(Name, StatusDelete) VALUES (?, 1)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, typeOfInvestmentCosts.getName());
            st.executeQuery();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * This function to delete type of investment cost with id from database
     *
     * @param typeOfInvestmentCostId is id type of investment cost want to
     * delete
     */
    public void deleteTypeOfInvestmentCost(int typeOfInvestmentCostId) {
        String sql = "UPDATE TypeOfInvestmentCosts SET StatusDelete = 0 WHERE Id  = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, typeOfInvestmentCostId);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * This function update type of investment cost from database
     *
     * @param typeOfInvestmentCosts is type of investment cost have new
     * information
     */
    public void updateTypeOfInvestmentCost(TypeOfInvestmentCosts typeOfInvestmentCosts) {
        String sql = """
                     UPDATE TypeOfInvestmentCosts
                     SET Name = ?
                     WHERE Id = ?""";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, typeOfInvestmentCosts.getName());
            st.setInt(2, typeOfInvestmentCosts.getId());
            st.executeQuery();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * This function to get investment cost from database by Id
     *
     * @param id is id type of investment cost want to get
     * @return type of investment cost have id input
     */
    public TypeOfInvestmentCosts getTypeOfInvestmentCostByID(int id) {
        String sql = "SELECT * FROM TypeOfInvestmentCosts WHERE Id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                TypeOfInvestmentCosts typeOfInvestmentCost = new TypeOfInvestmentCosts(
                        rs.getInt("Id"),
                        rs.getString("Name"),
                            rs.getInt("StatusDelete"));
                return typeOfInvestmentCost;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    public static  void main(String[] args) {
        TypeOfInvestmentCostDAO da = new TypeOfInvestmentCostDAO();
        List<TypeOfInvestmentCosts> list =  da.getAllTypeOfInvestmentCostByKeyWord("ti?n");
        System.out.println(list.size());
    }
}
