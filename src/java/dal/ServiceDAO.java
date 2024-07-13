package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Service;

/**
 *
 * @author ASUS ZenBook
 */
public class ServiceDAO extends DBContext{
    
    public List<Service> getAllService() {
        List<Service> listOfService = new ArrayList<>();

        String sql = "SELECT * FROM Services";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    listOfService.add(new Service(
                            rs.getInt("ServiceId"),
                            rs.getString("ServiceName")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }
        return listOfService;
    }
    
    public Service getServiceById(int serviceId){
        Service service = null;

        String sql = "SELECT * FROM Services where ServiceId = ?";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, serviceId);
            try (ResultSet rs = pstm.executeQuery()) {             
                if (rs.next()) {
                    service = new Service(rs.getInt("ServiceId"), rs.getString("ServiceName"));
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }
        return service;
    }
    
    public static void main(String[] args) {
        ServiceDAO sd = new ServiceDAO();
        System.out.println(sd.getServiceById(1).getServiceId());
    }
}
