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
import model.Room;
import model.RoomLodging;

/**
 *
 * @author ASUS
 */
public class RoomDAO extends DBContext {

    public RoomLodging getRoomById(int id) {
        String sql = "SELECT r.RoomId,r.Price,r.MaxOfQuantity,r.Image,r.Description,r.LodgingHouseId,a.AccountId,l.NameLodgingHouse\n"
                + ",l.Province,l.District,l.Ward,l.AddressDetail,l.Image AS ImageLodging,l.NumberOfRoom FROM dbo.Rooms r JOIN dbo.AccountInRoom a ON a.RoomId = r.RoomId\n"
                + "JOIN dbo.LodgingHouses l ON l.LodgingHouseId = r.LodgingHouseId \n"
                + "WHERE a.AccountId = ?";
        RoomLodging roomLodging = null;

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, id); // Set the value for the first parameter

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    roomLodging = new RoomLodging(
                            rs.getInt("RoomId"),
                            rs.getInt("Price"),
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
    
    
     public Room getRoomsDeleted(int lodgingHouseId, String roomName) {
        String sql = "SELECT * FROM Rooms WHERE LodgingHouseId = ? AND RoomName = ? AND StatusDelete = 0";
        try {

            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, lodgingHouseId);
            st.setString(2, roomName);

            ResultSet rs = st.executeQuery();

            LodgingHousesDAO lodgingHousesDAO = new LodgingHousesDAO();

            if (rs.next()) {
                Room room = new Room(rs.getString("RoomId"),
                        rs.getInt("Price"),
                        rs.getInt("MaxOfQuantity"),
                        rs.getInt("Status"),
                        rs.getString("Image"),
                        rs.getString("Description"),
                        lodgingHousesDAO.getLodgingHouseById(rs.getInt("LodgingHouseId")),
                        rs.getInt("StatusDelete"),
                        rs.getString("RoomName"));
                return room;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public Room getRoomsById(String roomId) {
        String sql = "SELECT * FROM Rooms WHERE RoomId = ?";
        try {

            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, roomId);

            ResultSet rs = st.executeQuery();

            LodgingHousesDAO lodgingHousesDAO = new LodgingHousesDAO();

            if (rs.next()) {
                Room room = new Room(rs.getString("RoomId"),
                        rs.getInt("Price"),
                        rs.getInt("MaxOfQuantity"),
                        rs.getInt("Status"),
                        rs.getString("Image"),
                        rs.getString("Description"),
                        lodgingHousesDAO.getLodgingHouseById(rs.getInt("LodgingHouseId")),
                        rs.getInt("StatusDelete"),
                        rs.getString("RoomName"));
                return room;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public List<Room> getRoomsByLodgingHouseId(int lodgingHouseId) {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT * FROM Rooms WHERE LodgingHouseId = ? AND StatusDelete = 1";
        try {

            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, lodgingHouseId);

            ResultSet rs = st.executeQuery();

            LodgingHousesDAO lodgingHousesDAO = new LodgingHousesDAO();

            while (rs.next()) {
                Room room = new Room(rs.getString("RoomId"),
                        rs.getInt("Price"),
                        rs.getInt("MaxOfQuantity"),
                        rs.getInt("Status"),
                        rs.getString("Image"),
                        rs.getString("Description"),
                        lodgingHousesDAO.getLodgingHouseById(rs.getInt("LodgingHouseId")),
                        rs.getInt("StatusDelete"),
                        rs.getString("RoomName"));

                list.add(room);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    
    public List<Room> getAllRoomsByLodgingHouseId(int lodgingHouseId) {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT * FROM Rooms WHERE LodgingHouseId = ?";
        try {

            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, lodgingHouseId);

            ResultSet rs = st.executeQuery();

            LodgingHousesDAO lodgingHousesDAO = new LodgingHousesDAO();

            while (rs.next()) {
                Room room = new Room(rs.getString("RoomId"),
                        rs.getInt("Price"),
                        rs.getInt("MaxOfQuantity"),
                        rs.getInt("Status"),
                        rs.getString("Image"),
                        rs.getString("Description"),
                        lodgingHousesDAO.getLodgingHouseById(rs.getInt("LodgingHouseId")),
                        rs.getInt("StatusDelete"),
                        rs.getString("RoomName"));

                list.add(room);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }
    
    public void insertRoom(Room room) {
        String sql = """
                     INSERT INTO Rooms (RoomId, Price, MaxOfQuantity, Status, Image, Description, LodgingHouseId, StatusDelete, RoomName)
                     VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);""";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, room.getRoomId());
            st.setDouble(2, room.getPrice());
            st.setInt(3, room.getMaxOfQuantity());
            st.setInt(4, room.getStatus());
            st.setString(5, room.getImage());
            st.setString(6, room.getDescription());
            st.setInt(7, room.getLodgingHouse().getLodgingHouseId());
            st.setInt(8, room.getStatusDelete());
            st.setString(9, room.getRoomName());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public int getNumberRoomInLodgingHouse(int lodgingHouseId) {
        String sql = "SELECT count(*) as num from Rooms where LodgingHouseId = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, lodgingHouseId);
            st.executeQuery();
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("num");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    public void updateRoom(Room room) {
        String sql = """
                     UPDATE [dbo].[Rooms]
                        SET 
                           [Price] = ?
                           ,[MaxOfQuantity] = ?
                           ,[Status] = ?
                           ,[Image] = ?
                           ,[Description] = ?
                           ,[LodgingHouseId] = ?
                           ,[StatusDelete] = ?
                           ,[RoomName] = ?
                     where roomID = ?
                     """;

        try {
            PreparedStatement st = connection.prepareStatement(sql);

            st.setDouble(1, room.getPrice());
            st.setInt(2, room.getMaxOfQuantity());
            st.setInt(3, room.getStatus());
            st.setString(4, room.getImage());
            st.setString(5, room.getDescription());
            st.setInt(6, room.getLodgingHouse().getLodgingHouseId());
            st.setInt(7, room.getStatusDelete());
            st.setString(8, room.getRoomName());
            st.setString(9, room.getRoomId());
            st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Room> getRoomsByTenantID(int tenantID) {
        List<Room> list = new ArrayList<>();
        String sql = """
                     SELECT R.* FROM Rooms R FULL JOIN Contract C ON R.RoomID = C.RoomId
                     \t\t\t\t\t\t\t\tWHERE C.TenantId = ? AND 
                     \t\t\t\t\t\t\t\tC.StatusDelete = 1 AND 
                     \t\t\t\t\t\t\t\tC.statusAccept = 2 AND
                     \t\t\t\t\t\t\t\tC.TypeAccept = 1""";
        try {

            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, tenantID);

            ResultSet rs = st.executeQuery();

            LodgingHousesDAO lodgingHousesDAO = new LodgingHousesDAO();

            while (rs.next()) {
                Room room = new Room(rs.getString("RoomId"),
                        rs.getInt("Price"),
                        rs.getInt("MaxOfQuantity"),
                        rs.getInt("Status"),
                        rs.getString("Image"),
                        rs.getString("Description"),
                        lodgingHousesDAO.getLodgingHouseById(rs.getInt("LodgingHouseId")),
                        rs.getInt("StatusDelete"),
                        rs.getString("RoomName"));

                list.add(room);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Room> getRoomsByTenantIDAndKeyword(int tenantID, String keyWord) {
        List<Room> list = new ArrayList<>();
        String sql = """
                    SELECT R.* FROM Rooms R FULL JOIN Contract C ON R.RoomID = C.RoomId
                     								WHERE C.TenantId = ? AND 
                     								C.StatusDelete = 1 AND 
                     								C.statusAccept = 2 AND
                     								C.TypeAccept = 1 AND
                     								(RoomName LIKE ? OR Description LIKE ? OR Price LIKE ? OR MaxOfQuantity LIKE ? )""";
        try {

            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, tenantID);
            String searchPattern = "%" + keyWord + "%";
            st.setString(2, searchPattern);
            st.setString(3, searchPattern);
            st.setString(4, searchPattern);
            st.setString(5, searchPattern);
            ResultSet rs = st.executeQuery();

            LodgingHousesDAO lodgingHousesDAO = new LodgingHousesDAO();

            while (rs.next()) {
                Room room = new Room(rs.getString("RoomId"),
                        rs.getInt("Price"),
                        rs.getInt("MaxOfQuantity"),
                        rs.getInt("Status"),
                        rs.getString("Image"),
                        rs.getString("Description"),
                        lodgingHousesDAO.getLodgingHouseById(rs.getInt("LodgingHouseId")),
                        rs.getInt("StatusDelete"),
                        rs.getString("RoomName"));

                list.add(room);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Room> getRoomsByKeyword(int lodgingHouseID, String keyWord) {
        List<Room> list = new ArrayList<>();
        String sql = """
                     SELECT * FROM Rooms WHERE LodgingHouseId = ?
                                                AND StatusDelete = 1
                                                AND (RoomName LIKE ? OR Description LIKE ? OR Price LIKE ? OR MaxOfQuantity LIKE ? )
                    """;
        try {

            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, lodgingHouseID);
            String searchPattern = "%" + keyWord + "%";
            st.setString(2, searchPattern);
            st.setString(3, searchPattern);
            st.setString(4, searchPattern);
            st.setString(5, searchPattern);
            ResultSet rs = st.executeQuery();

            LodgingHousesDAO lodgingHousesDAO = new LodgingHousesDAO();

            while (rs.next()) {
                Room room = new Room(rs.getString("RoomId"),
                        rs.getInt("Price"),
                        rs.getInt("MaxOfQuantity"),
                        rs.getInt("Status"),
                        rs.getString("Image"),
                        rs.getString("Description"),
                        lodgingHousesDAO.getLodgingHouseById(rs.getInt("LodgingHouseId")),
                        rs.getInt("StatusDelete"),
                        rs.getString("RoomName"));

                list.add(room);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Room> getRoomsBySelect(int lodgingHouseID, String minPrice, String maxPrice, String status) {
        System.out.println("status sql" + status);
        List<Room> list = new ArrayList<>();
        String sql = "SELECT * FROM Rooms WHERE  LodgingHouseId = ?  AND StatusDelete = 1";

        if (minPrice != null && !minPrice.isEmpty()) {
            sql += "AND Price >= " + minPrice;
        }
        if (maxPrice != null && !maxPrice.isEmpty()) {
            sql += " AND Price <=  " + maxPrice;
        }
        if (status != null && !status.isEmpty() && !status.equals("3")) {
            sql += "AND Status  = " + status;
        }
        try {

            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, lodgingHouseID);
            ResultSet rs = st.executeQuery();

            LodgingHousesDAO lodgingHousesDAO = new LodgingHousesDAO();

            while (rs.next()) {
                Room room = new Room(rs.getString("RoomId"),
                        rs.getInt("Price"),
                        rs.getInt("MaxOfQuantity"),
                        rs.getInt("Status"),
                        rs.getString("Image"),
                        rs.getString("Description"),
                        lodgingHousesDAO.getLodgingHouseById(rs.getInt("LodgingHouseId")),
                        rs.getInt("StatusDelete"),
                        rs.getString("RoomName"));

                list.add(room);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        System.out.println(sql);
        return list;
    }
public List<Room> getRoomsByAccountId(int id) {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT r.*\n"
                + "FROM Rooms r\n"
                + "JOIN AccountInRoom air ON r.RoomId = air.RoomId\n"
                + "WHERE air.AccountId = ?;";
        try {

            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            LodgingHousesDAO lodgingHousesDAO = new LodgingHousesDAO();

            while (rs.next()) {
                Room room = new Room(rs.getString("RoomId"),
                        rs.getDouble("Price"),
                        rs.getInt("MaxOfQuantity"),
                        rs.getInt("Status"),
                        rs.getString("Image"),
                        rs.getString("Description"),
                        lodgingHousesDAO.getLodgingHouseByIdForTenant(rs.getInt("LodgingHouseId")),
                        rs.getInt("StatusDelete"),
                        rs.getString("RoomName"));

                list.add(room);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Room> getRoomsByLodgingHouseIdTenant(int lodgingHouseId,String roomName) {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT DISTINCT r.*\n"
                + "FROM Rooms r\n"
                + "JOIN AccountInRoom air ON r.RoomId = air.RoomId\n"
                + "WHERE r.LodgingHouseId = ? AND r.RoomName LIKE '%"+roomName+"%'";
        try {

            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, lodgingHouseId);

            ResultSet rs = st.executeQuery();

            LodgingHousesDAO lodgingHousesDAO = new LodgingHousesDAO();

            while (rs.next()) {
                Room room = new Room(rs.getString("RoomId"),
                        rs.getDouble("Price"),
                        rs.getInt("MaxOfQuantity"),
                        rs.getInt("Status"),
                        rs.getString("Image"),
                        rs.getString("Description"),
                        lodgingHousesDAO.getLodgingHouseById(rs.getInt("LodgingHouseId")),
                        rs.getInt("StatusDelete"),
                        rs.getString("RoomName"));

                list.add(room);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }
    public static void main(String[] args) {
        RoomDAO r = new RoomDAO();
        System.out.println(r.getRoomsBySelect(3, "10", "100000000", "0").size());
    }
}
