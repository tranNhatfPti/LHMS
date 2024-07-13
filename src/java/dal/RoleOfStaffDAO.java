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
import model.RoleOfStaff;

/**
 *
 * @author Admin
 */
public class RoleOfStaffDAO extends DBContext{
    
    public List<RoleOfStaff> getAll() {
        List<RoleOfStaff> listRoleOfStaff = new ArrayList<>();
        String sql = "SELECT * FROM RolesOfStaff WHERE StatusActive = 1";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                RoleOfStaff role = new RoleOfStaff();
                role.setRoleStaffID(rs.getInt(1));
                role.setRoleStaffName(rs.getString(2));
                role.setStatusActive(rs.getInt(3));
                listRoleOfStaff.add(role);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listRoleOfStaff;
    }
    
    public RoleOfStaff getRoleOfStaffByID(int id) {
        String sql = "SELECT * FROM RolesOfStaff WHERE RoleStaffID = ?";
        
        try {
            //roleOfStaffID = Integer.parseInt(id);
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                RoleOfStaff role = new RoleOfStaff();
                role.setRoleStaffID(rs.getInt(1));
                role.setRoleStaffName(rs.getString(2));
                role.setStatusActive(rs.getInt(3));
                return role;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean insertNewRoleOfStaff(String roleNameOfStaff) {
        String sql = "INSERT INTO [dbo].[RolesOfStaff]\n"
                + "     VALUES (?,?)";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, roleNameOfStaff);
            stm.setInt(2, 1);
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return false;
    }
    
    public boolean updateRoleOfStaff(int roleOfStaffID, String roleNameOfStaff) {
        String sql = "UPDATE [dbo].[RolesOfStaff]\n"
                + "   SET [RoleStaffName] = ?\n"
                + " WHERE RoleStaffID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, roleNameOfStaff);
            stm.setInt(2, roleOfStaffID);
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
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
    
    public boolean deleteStaffByRoleID(int roleOfStaffID) {
        String sql = "DELETE FROM Staff WHERE RoleStaffID = ?";
        
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, roleOfStaffID);
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public boolean deleteRoleOfStaff(int roleOfStaffID) {
        String sql1 = "UPDATE Staff SET RoleStaffID = null WHERE RoleStaffID = ?";
        
        try {
            PreparedStatement stm = connection.prepareStatement(sql1);
            stm.setInt(1, roleOfStaffID);
            stm.executeUpdate();
            String sql2 = "UPDATE RolesOfStaff SET StatusActive = 0 WHERE RoleStaffID = ?";
            PreparedStatement stm2 = connection.prepareStatement(sql2);
            stm2.setInt(1, roleOfStaffID);
            stm2.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    
    
    
    
    public static void main(String[] args) {
        RoleOfStaffDAO dao = new RoleOfStaffDAO();
        dao.deleteRoleOfStaff(29);
//        System.out.println(dao.getRoleOfStaffByID(1).getRoleStaffName());
//        //dao.insertNewRoleOfStaff( "Vệ sinh");
//        dao.deleteRoleOfStaff(3);
//        for (RoleOfStaff roleOfStaff : dao.getAll()) {
//            System.out.println(roleOfStaff.getRoleStaffName());
//        }
//        RoleOfStaff a = dao.getRoleOfStaffByID("2");
//        System.out.println(a.getRoleStaffID() + a.getRoleStaffName());
    }
    
}
