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
import model.ServiceOfLodgingHouse;
import model.ServiceOfRoom;

/**
 *
 * @author Admin
 */
public class ServiceOfRoomDAO extends DBContext {

    public List<ServiceOfRoom> getServiceOfRoom(int lodgingHouseId, String roomId) {
        List<ServiceOfRoom> list = new ArrayList<>();
        String sql = "select * from ServiceOfRoom WHERE RoomId = ?";
        try {

            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, roomId);
            ResultSet rs = st.executeQuery();
            ServiceDAO serviceDAO = new ServiceDAO();
            RoomDAO roomDAO = new RoomDAO();
            ServiceOfLodgingHouseDAO serviceOfLodgingHouseDAO = new ServiceOfLodgingHouseDAO();
            while (rs.next()) {
                ServiceOfLodgingHouse serviceOfLodgingHouse = serviceOfLodgingHouseDAO.getAllServiceOfLodgingHouseUnique(lodgingHouseId, rs.getInt("ServiceId"));
                Room room = roomDAO.getRoomsById(rs.getString("RoomId"));
                ServiceOfRoom serviceOfRoom = new ServiceOfRoom(serviceOfLodgingHouse, room);
                list.add(serviceOfRoom);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public ServiceOfRoom getServiceOfRoomByRoomIdAndServiceId(String roomId, int serviceId) {
        ServiceOfRoom serviceOfRoom = null;
        ServiceDAO serviceDAO = new ServiceDAO();
            RoomDAO roomDAO = new RoomDAO();
            ServiceOfLodgingHouseDAO serviceOfLodgingHouseDAO = new ServiceOfLodgingHouseDAO();
        String sql = "SELECT * FROM ServiceOfRoom where RoomId = ? and ServiceId = ?";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, roomId);
            pstm.setInt(2, serviceId);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    //serviceOfRoom = new ServiceOfRoom(rs.getInt("ServiceId"), rs.getInt("RoomId"), rs.getDouble("Price"));
                    Room room = roomDAO.getRoomsById(rs.getString("RoomId"));
                    ServiceOfLodgingHouse serviceOfLodgingHouse = serviceOfLodgingHouseDAO.getAllServiceOfLodgingHouseUnique(room.getLodgingHouse().getLodgingHouseId(), rs.getInt("ServiceId"));
                    
                    serviceOfRoom.setPrice(serviceOfLodgingHouse.getPrice());
                    serviceOfRoom.setStatusAccept(rs.getInt(4));
                    serviceOfRoom.setStatusDelete(rs.getInt(5));
                    serviceOfRoom.setServiceOfLodgingHouse(serviceOfLodgingHouse);
                    serviceOfRoom.setRoom(room);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }
        return serviceOfRoom;
    }

    public List<ServiceOfRoom> getAllServiceOfRoomByRoomId(String roomId) {
        List<ServiceOfRoom> listOfSR = new ArrayList<>();
        ServiceDAO serviceDAO = new ServiceDAO();
            RoomDAO roomDAO = new RoomDAO();
            ServiceOfLodgingHouseDAO serviceOfLodgingHouseDAO = new ServiceOfLodgingHouseDAO();
        String sql = "SELECT * FROM ServiceOfRoom where RoomId = ?";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, roomId);
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    ServiceOfRoom serviceOfRoom = new ServiceOfRoom();
                    Room room = roomDAO.getRoomsById(rs.getString("RoomId"));
                    ServiceOfLodgingHouse serviceOfLodgingHouse = serviceOfLodgingHouseDAO.getAllServiceOfLodgingHouseUnique(room.getLodgingHouse().getLodgingHouseId(), rs.getInt("ServiceId"));
                    
                    serviceOfRoom.setPrice(serviceOfLodgingHouse.getPrice());
                    serviceOfRoom.setStatusAccept(rs.getInt(4));
                    serviceOfRoom.setStatusDelete(rs.getInt(5));
                    serviceOfRoom.setServiceOfLodgingHouse(serviceOfLodgingHouse);
                    serviceOfRoom.setRoom(room);
                    listOfSR.add(serviceOfRoom);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }

        return listOfSR;
    }

    public boolean checkServiceBeingUsedAnyRoom(int lodgingHouseId, int serviceId) {
        String sql = "SELECT * FROM ServiceOfRoom sr inner join Rooms r on r.RoomId = sr.RoomId "
                + "where r.LodgingHouseId = ? and sr.ServiceId = ?";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, lodgingHouseId);
            pstm.setInt(2, serviceId);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }

        return false;
    }

    //-------------------------------------------------------------------------
    public void insertServiceOfRoom(ServiceOfRoom serviceOfRoom) {
        String sql = "INSERT INTO ServiceOfRoom (ServiceId, RoomId, Price, StatusAccept, StatusDelete) VALUES  (?,?,?,?,?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, serviceOfRoom.getServiceOfLodgingHouse().getServiceId());
            st.setString(2, serviceOfRoom.getRoom().getRoomId());
            st.setDouble(3, serviceOfRoom.getServiceOfLodgingHouse().getPrice());
            st.setInt(4, serviceOfRoom.getStatusAccept());
            st.setInt(5, serviceOfRoom.getStatusDelete());

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<ServiceOfRoom> getServiceOfRoom(int lodgingHouseId, String roomId, int statusAccept) {
        List<ServiceOfRoom> list = new ArrayList<>();
        String sql = "select * from ServiceOfRoom WHERE RoomId = ? AND StatusAccept = ? AND StatusDelete = 0";
        try {

            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, roomId);
            st.setInt(3, statusAccept);
            ResultSet rs = st.executeQuery();
            ServiceDAO serviceDAO = new ServiceDAO();
            RoomDAO roomDAO = new RoomDAO();
            ServiceOfLodgingHouseDAO serviceOfLodgingHouseDAO = new ServiceOfLodgingHouseDAO();
            while (rs.next()) {
                ServiceOfRoom serviceOfRoom = new ServiceOfRoom();
                ServiceOfLodgingHouse serviceOfLodgingHouse = serviceOfLodgingHouseDAO.getAllServiceOfLodgingHouseUnique(lodgingHouseId, rs.getInt("ServiceId"));
                Room room = roomDAO.getRoomsById(rs.getString("RoomId"));
                serviceOfRoom.setPrice(serviceOfLodgingHouse.getPrice());
                serviceOfRoom.setStatusAccept(rs.getInt(4));
                serviceOfRoom.setStatusDelete(rs.getInt(5));
                serviceOfRoom.setServiceOfLodgingHouse(serviceOfLodgingHouse);
                serviceOfRoom.setRoom(room);
                list.add(serviceOfRoom);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<ServiceOfRoom> getServiceOfRoomRequesPending(int lodgingHouseId, String roomId) {
        List<ServiceOfRoom> list = new ArrayList<>();
        String sql = "select * from ServiceOfRoom WHERE RoomId = ? AND StatusAccept = 0 ";
        try {

            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, roomId);

            ResultSet rs = st.executeQuery();
            ServiceDAO serviceDAO = new ServiceDAO();
            RoomDAO roomDAO = new RoomDAO();
            ServiceOfLodgingHouseDAO serviceOfLodgingHouseDAO = new ServiceOfLodgingHouseDAO();
            while (rs.next()) {
                ServiceOfRoom serviceOfRoom = new ServiceOfRoom();
                ServiceOfLodgingHouse serviceOfLodgingHouse = serviceOfLodgingHouseDAO.getAllServiceOfLodgingHouseUnique(lodgingHouseId, rs.getInt("ServiceId"));
                Room room = roomDAO.getRoomsById(rs.getString("RoomId"));
                serviceOfRoom.setPrice(serviceOfLodgingHouse.getPrice());
                serviceOfRoom.setStatusAccept(rs.getInt(4));
                serviceOfRoom.setStatusDelete(rs.getInt(5));
                serviceOfRoom.setServiceOfLodgingHouse(serviceOfLodgingHouse);
                serviceOfRoom.setRoom(room);
                list.add(serviceOfRoom);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<ServiceOfRoom> getServiceOfRoomAccpeted(int lodgingHouseId, String roomId) {
        List<ServiceOfRoom> list = new ArrayList<>();
        String sql = "select * from ServiceOfRoom WHERE RoomId = ? AND StatusAccept = 1 ";
        try {

            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, roomId);

            ResultSet rs = st.executeQuery();
            ServiceDAO serviceDAO = new ServiceDAO();
            RoomDAO roomDAO = new RoomDAO();
            ServiceOfLodgingHouseDAO serviceOfLodgingHouseDAO = new ServiceOfLodgingHouseDAO();
            while (rs.next()) {
                ServiceOfRoom serviceOfRoom = new ServiceOfRoom();
                ServiceOfLodgingHouse serviceOfLodgingHouse = serviceOfLodgingHouseDAO.getAllServiceOfLodgingHouseUnique(lodgingHouseId, rs.getInt("ServiceId"));
                Room room = roomDAO.getRoomsById(rs.getString("RoomId"));
                serviceOfRoom.setPrice(serviceOfLodgingHouse.getPrice());
                serviceOfRoom.setStatusAccept(rs.getInt(4));
                serviceOfRoom.setStatusDelete(rs.getInt(5));
                serviceOfRoom.setServiceOfLodgingHouse(serviceOfLodgingHouse);
                serviceOfRoom.setRoom(room);
                list.add(serviceOfRoom);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<ServiceOfRoom> getServiceOfRoomDeactive(int lodgingHouseId, String roomId) {
        List<ServiceOfRoom> list = new ArrayList<>();
        String sql = "select * from ServiceOfRoom WHERE RoomId = ? AND StatusAccept = 1  AND StatusDelete = 1";
        try {

            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, roomId);

            ResultSet rs = st.executeQuery();
            ServiceDAO serviceDAO = new ServiceDAO();
            RoomDAO roomDAO = new RoomDAO();
            ServiceOfLodgingHouseDAO serviceOfLodgingHouseDAO = new ServiceOfLodgingHouseDAO();
            while (rs.next()) {
                ServiceOfRoom serviceOfRoom = new ServiceOfRoom();
                ServiceOfLodgingHouse serviceOfLodgingHouse = serviceOfLodgingHouseDAO.getAllServiceOfLodgingHouseUnique(lodgingHouseId, rs.getInt("ServiceId"));
                Room room = roomDAO.getRoomsById(rs.getString("RoomId"));
                serviceOfRoom.setPrice(serviceOfLodgingHouse.getPrice());
                serviceOfRoom.setStatusAccept(rs.getInt(4));
                serviceOfRoom.setStatusDelete(rs.getInt(5));
                serviceOfRoom.setServiceOfLodgingHouse(serviceOfLodgingHouse);
                serviceOfRoom.setRoom(room);
                list.add(serviceOfRoom);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

//    public void acceptServiceAll(String roomId, int serviceId) {
//        String sql = "Update ServiceOfRoom  SET StatusDelete = 0  WHERE RoomId = ? AND  StatusDelete = 1 AND ServiceId = ?";
//        try {
//            PreparedStatement st = connection.prepareStatement(sql);
//            
//            st.setString(1, roomId);
//            st.setInt(2, serviceId);
//            st.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
//    }
    public void acceptService(String roomId, int serviceId) {
        String sql = "Update ServiceOfRoom  SET StatusDelete = 0  WHERE RoomId = ? AND  StatusAccept = 1 AND ServiceId = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            st.setString(1, roomId);
            st.setInt(2, serviceId);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void deactiveService(String roomId, int serviceId) {
        String sql = "Update ServiceOfRoom  SET StatusDelete = 1  WHERE RoomId = ? AND serviceId = ? ";
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            st.setString(1, roomId);
            st.setInt(2, serviceId);
            //st.setInt(2, serviceId);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void deleteServiceOfRoomPendingByServiceId(String roomId) {
        String sql = "DELETE FROM ServiceOfRoom WHERE RoomId = ? AND StatusAccept = 0";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, roomId);
            //st.setInt(2, serviceId);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteServiceOfRoomByServiceId(String roomId, int serviceId) {
        String sql = "DELETE FROM ServiceOfRoom WHERE RoomId = ? AND ServiceId = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, roomId);
            st.setInt(2, serviceId);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void resetServiceInRoom(String roomId) {
        String sql = "DELETE FROM ServiceOfRoom WHERE RoomId = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, roomId);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        ServiceOfRoomDAO serviceOfRoomDAO = new ServiceOfRoomDAO();
        RoomDAO roomDAO = new RoomDAO();
        for (ServiceOfRoom s : serviceOfRoomDAO.getServiceOfRoomAccpeted(9, "Room101")) {
            System.out.println(s.getServiceOfLodgingHouse().getServiceId());
        }
//        for (ServiceOfRoom a : serviceOfRoomDAO.getServiceOfRoomRequesPending(9, "Room101")) {
//            System.out.println(a.getServiceOfLodgingHouse().getServiceId());
//        }
        //System.out.println(.get(0).toString());

        //serviceOfRoomDAO.resetServiceInRoom("3_1");
    }

}
