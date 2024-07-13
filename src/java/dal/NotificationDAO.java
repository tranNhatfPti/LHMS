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
import model.Notification;

/**
 *
 * @author admin
 */
public class NotificationDAO extends DBContext {
    
    public List<Notification> getNotificationByReveiverId(int accountId) {
        List<Notification> listN = new ArrayList<>();
        String sql = """
                      SELECT  [NotificationId]
                            ,[NotificationMessage]
                            ,[NotificationDateTime]                   
                            ,[ReceiverId]
                            ,[SenderId]
                            ,[ConfirmationStatus],[TypeOfNotification]
                        FROM [dbo].[Notification] Where [ReceiverId]=? order by [NotificationId] desc """;
        try {
            
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, accountId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Notification noti = new Notification(rs.getInt("NotificationId"),
                        rs.getString("NotificationMessage"),
                        rs.getDate("NotificationDateTime"),
                        rs.getInt("receiverId"),
                        rs.getInt("senderId"),
                        rs.getInt("confirmationStatus"),
                        rs.getInt("TypeOfNotification"));
                listN.add(noti);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi ở dao này");
        }
        return listN;
    }
    
    public List<Notification> getAllNotification() {
        List<Notification> listN = new ArrayList<>();
        String sql = """
                      SELECT  [NotificationId]
                            ,[NotificationMessage]
                            ,[NotificationDateTime]                   
                            ,[ReceiverId]
                            ,[SenderId]
                            ,[ConfirmationStatus],[TypeOfNotification]
                        FROM [dbo].[Notification]  order by [NotificationId] desc""";
        try {
            
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Notification noti = new Notification(rs.getInt("NotificationId"),
                        rs.getString("NotificationMessage"),
                        rs.getDate("NotificationDateTime"),
                        rs.getInt("receiverId"),
                        rs.getInt("senderId"),
                        rs.getInt("confirmationStatus"), rs.getInt("TypeOfNotification"));
                listN.add(noti);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi ở dao này");
        }
        return listN;
    }
    //DELETE FROM [dbo].[Notification]
    // WHERE NotificationId=?

    public void DeleteNotification(String id) {
        
        String sql = """
                   DELETE FROM [dbo].[Notification]
                                          WHERE NotificationId=? """;
        try {
            
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
            }
        } catch (SQLException e) {
            System.out.println("Lỗi ở dao này");
        }
    }
    
    public void RejectNotification(Notification newNotificaiton) {
        
        String sql = """
                    UPDATE [dbo].[Notification]
                                      SET
                                         [ConfirmationStatus] = ?
                                    WHERE NotificationId =?  """;
        try {
            
            PreparedStatement st = connection.prepareStatement(sql);
            
            st.setInt(1, 1);
            st.setInt(2, newNotificaiton.getNotificationId());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                
            }
        } catch (SQLException e) {
            System.out.println("Lỗi ở dao này");
        }
    }
    
    public List<Notification> getNotificationBySenderId(int accountId) {
        List<Notification> listN = new ArrayList<>();
        String sql = """
                      SELECT  [NotificationId]
                            ,[NotificationMessage]
                            ,[NotificationDateTime]                   
                            ,[ReceiverId]
                            ,[SenderId]
                            ,[ConfirmationStatus],[TypeOfNotification]
                        FROM [dbo].[Notification] Where [SenderId]=?  order by [NotificationId] desc""";
        try {
            
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, accountId);
    
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Notification noti = new Notification(rs.getInt("NotificationId"),
                        rs.getString("NotificationMessage"),
                        rs.getDate("NotificationDateTime"),
                        rs.getInt("receiverId"),
                        rs.getInt("senderId"),
                        rs.getInt("confirmationStatus"), rs.getInt("TypeOfNotification"));
                listN.add(noti);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi ở dao này");
        }
        return listN;
    }
    
    public Notification getNotificationById(String id) {
        
        String sql = """
                      SELECT  [NotificationId]
                            ,[NotificationMessage]
                            ,[NotificationDateTime]
                            ,[ReceiverId]
                            ,[SenderId]
                            ,[ConfirmationStatus],[TypeOfNotification]
                        FROM [dbo].[Notification] Where [NotificationId]=?   order by [NotificationId] desc""";
        try {
            
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Notification noti = new Notification(rs.getInt("NotificationId"),
                        rs.getString("NotificationMessage"),
                        rs.getDate("NotificationDateTime"),
                        rs.getInt("receiverId"),
                        rs.getInt("senderId"),
                        rs.getInt("confirmationStatus"), rs.getInt("TypeOfNotification"));
                return noti;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi ở dao này");
        }
        return null;
    }

    public List<Notification> searchNotificationBySender(String search, int senderID) {
        List<Notification> list = new ArrayList<>();
        String sql = """
                 SELECT * FROM Notification 
                 WHERE( NotificationMessage LIKE ? OR NotificationDateTime LIKE ? ) and [SenderId]=?  order by [NotificationId] desc""";
        
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, "%" + search + "%");
            st.setString(2, "%" + search + "%");
            st.setInt(3, senderID);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Notification noti = new Notification(
                            rs.getInt("NotificationId"),
                            rs.getString("NotificationMessage"),
                            rs.getDate("NotificationDateTime"),
                            rs.getInt("ReceiverId"),
                            rs.getInt("SenderId"),
                            rs.getInt("ConfirmationStatus"), rs.getInt("TypeOfNotification"));
                    list.add(noti);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi ở dao này: " + e.getMessage());
        }
        return list;
    }

    public List<Notification> searchNotification(String search) {
        List<Notification> list = new ArrayList<>();
        String sql = """
                 SELECT * FROM Notification 
                 WHERE NotificationMessage LIKE ? OR NotificationDateTime LIKE ?   order by [NotificationId] desc""";
        
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, "%" + search + "%");
            st.setString(2, "%" + search + "%");
            
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Notification noti = new Notification(
                            rs.getInt("NotificationId"),
                            rs.getString("NotificationMessage"),
                            rs.getDate("NotificationDateTime"),
                            rs.getInt("ReceiverId"),
                            rs.getInt("SenderId"),
                            rs.getInt("ConfirmationStatus"), rs.getInt("TypeOfNotification"));
                    list.add(noti);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi ở dao này: " + e.getMessage());
        }
        return list;
    }
    
    public List<Notification> searchNotificationForTenant(String search, int receiverId) {
        List<Notification> list = new ArrayList<>();
        String sql = """
                 SELECT * FROM Notification 
                 WHERE (NotificationMessage LIKE ? OR NotificationDateTime LIKE ?) AND [ReceiverId]=?  order by [NotificationId] desc""";
        
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, "%" + search + "%");
            st.setString(2, "%" + search + "%");
            st.setInt(3, receiverId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Notification noti = new Notification(
                            rs.getInt("NotificationId"),
                            rs.getString("NotificationMessage"),
                            rs.getDate("NotificationDateTime"),
                            rs.getInt("ReceiverId"),
                            rs.getInt("SenderId"),
                            rs.getInt("ConfirmationStatus"), rs.getInt("TypeOfNotification"));
                    list.add(noti);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi ở dao này: " + e.getMessage());
        }
        return list;
    }
    
    public void insertNotification(Notification newNotification) {
        String sql = """
                INSERT INTO [dbo].[Notification]
                          ([NotificationMessage]
                          ,[NotificationDateTime]
                          ,[ReceiverId]
                          ,[SenderId]
                          ,[ConfirmationStatus]
                     ,[TypeOfNotification])
                VALUES
                          (?,?,?,?,?,?)""";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, newNotification.getNotificationMessage());
            java.util.Date utilDate = newNotification.getNotificationDateTime();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            st.setDate(2, sqlDate);
            st.setInt(3, newNotification.getReceiverId());
            st.setInt(4, newNotification.getSenderId());
            st.setInt(5, newNotification.isConfirmationStatus());
            st.setInt(6, newNotification.getTypeOfNotification());
            
            int rowsInserted = st.executeUpdate(); // Thực thi câu lệnh INSERT

            if (rowsInserted > 0) {
                System.out.println("Thông báo đã được thêm vào cơ sở dữ liệu.");
            } else {
                System.out.println("Không có thông báo nào được thêm vào cơ sở dữ liệu.");
            }
            
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm thông báo vào cơ sở dữ liệu: " + e.getMessage());
        }
    }
    
    public void updateNotification(Notification newNotification) {
        String sql = """
                UPDATE [dbo].[Notification]
                   SET [NotificationMessage] = ?
                      ,[NotificationDateTime] = ?
                      ,[ReceiverId] = ?
                      ,[SenderId] = ?      ,
                	  [ConfirmationStatus] = ?
                      ,[TypeOfNotification] = ?
                	  WHERE [NotificationId]=?""";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, newNotification.getNotificationMessage());
            java.util.Date utilDate = newNotification.getNotificationDateTime();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            st.setDate(2, sqlDate);
            st.setInt(3, newNotification.getReceiverId());
            st.setInt(4, newNotification.getSenderId());
            st.setInt(5, 0);
            st.setInt(6, newNotification.getTypeOfNotification());
            st.setInt(7, newNotification.getNotificationId());
            int rowsInserted = st.executeUpdate();            
            
            if (rowsInserted > 0) {
                System.out.println("Thông báo đã được thêm vào cơ sở dữ liệu.");
            } else {
                System.out.println("Không có thông báo nào được thêm vào cơ sở dữ liệu.");
            }
            
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm thông báo vào cơ sở dữ liệu: " + e.getMessage());
        }
    }
    
    public void confirmNotification(Notification newNotificaiton) {
        
        String sql = """
                    UPDATE [dbo].[Notification]
                                      SET 
                                         [ConfirmationStatus] = ?
                                    WHERE NotificationId =?""";
        try {
            
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, 2);
            st.setInt(2, newNotificaiton.getNotificationId());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                
            }
        } catch (SQLException e) {
            System.out.println("Lỗi ở dao này");
        }
    }
    
    public static void main(String[] args) {
        NotificationDAO no = new NotificationDAO();
        List<Notification> listN = no.getNotificationBySenderId(1);
        for (Notification notification : listN) {
            System.out.println(notification);
        }
    }
}
