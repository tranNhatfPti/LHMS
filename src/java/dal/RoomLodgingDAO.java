/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.RoomLodging;

/**
 *
 * @author ASUS
 */
public class RoomLodgingDAO extends DBContext {

    public RoomLodging getRoomById(int id) {
        String sql = "SELECT r.RoomId,r.Price,r.MaxOfQuantity,r.Image,r.Description,r.LodgingHouseId,r.AccountId,l.NameLodgingHouse\n"
                + ",l.Province,l.District,l.Ward,l.AddressDetail,l.Image AS ImageLodging,l.NumberOfRoom\n"
                + "FROM dbo.Rooms r INNER JOIN dbo.LodgingHouses l\n"
                + "ON l.LodgingHouseId = r.LodgingHouseId\n"
                + "WHERE r.AccountId = ?";
        RoomLodging roomLodging = null;

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, id); // Set the value for the first parameter

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    roomLodging = new RoomLodging(
                            rs.getInt("RoomId"),
                            rs.getDouble("Price"),
                            rs.getInt("MaxOfQuantity"),
                            rs.getString("Image"),
                            rs.getString("Description"),
                            rs.getInt("LodgingHouseId"),
                            rs.getInt("AccountID"),
                            rs.getString("NameLodgingHouse"),
                            rs.getString("Province"),
                            rs.getString("District"),
                            rs.getString("Ward"),
                            rs.getString("AddressDetail"),
                            rs.getString("ImageLodging"),
                            rs.getInt("NumberOfRoom")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getRoomById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getRoomById: " + e.getMessage());
        }
        return roomLodging;
    }
    
    public static void main(String[] args) {
        RoomLodgingDAO r = new RoomLodgingDAO();
        RoomLodging rd = r.getRoomById(1);
        System.out.println(rd);
    }
}
