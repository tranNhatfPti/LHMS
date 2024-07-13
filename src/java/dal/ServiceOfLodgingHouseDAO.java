package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.ServiceOfLodgingHouse;

/**
 *
 * @author ASUS ZenBook
 */
public class ServiceOfLodgingHouseDAO extends DBContext {
   public ServiceOfLodgingHouse getAllServiceOfLodgingHouseUnique(int lodgingHouseId, int serviceId) {
      

        String sql = "SELECT * FROM ServiceOfLodgingHouse where LodgingHouseId = ? AND ServiceId = ?";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, lodgingHouseId);
            pstm.setInt(2, serviceId);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    ServiceOfLodgingHouse serviceOfLodgingHouse= new ServiceOfLodgingHouse(
                            rs.getInt("ServiceId"),
                            rs.getInt("LodgingHouseId"),
                            rs.getDouble("Price")
                    );
                    return serviceOfLodgingHouse;
                }
                
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }
        return null;
    }
    public List<ServiceOfLodgingHouse> getAllServiceOfLodgingHouse() {
        List<ServiceOfLodgingHouse> list = new ArrayList<>();

        String sql = "SELECT * FROM ServiceOfLodgingHouse";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    list.add(new ServiceOfLodgingHouse(
                            rs.getInt("ServiceId"),
                            rs.getInt("LodgingHouseId"),
                            rs.getDouble("Price")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }
        return list;
    }

    public List<ServiceOfLodgingHouse> getAllServiceOfLodgingHouseByLodgingHouseId(int lodgingHouseId) {
        List<ServiceOfLodgingHouse> list = new ArrayList<>();

        String sql = "SELECT * FROM ServiceOfLodgingHouse where LodgingHouseId = ?";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, lodgingHouseId);
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    list.add(new ServiceOfLodgingHouse(
                            rs.getInt("ServiceId"),
                            rs.getInt("LodgingHouseId"),
                            rs.getDouble("Price")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }
        return list;
    }

    public int insertServiceOfLodgingHouse(ServiceOfLodgingHouse serviceOfLodgingHouse) {
        int rowAffected = 0;

        String sql = "INSERT INTO [dbo].[ServiceOfLodgingHouse]\n"
                + "           ([ServiceId]\n"
                + "           ,[LodgingHouseId]\n"
                + "           ,[Price])\n"
                + "     VALUES  "
                + "(?,?,?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, serviceOfLodgingHouse.getServiceId());
            ps.setInt(2, serviceOfLodgingHouse.getLodgingHouseId());
            ps.setDouble(3, serviceOfLodgingHouse.getPrice());

            // nếu như insert 1 customer giống username thì sẽ lỗi.
            rowAffected = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowAffected;
    }

    public int updateServiceOfLodgingHouse(ServiceOfLodgingHouse serviceOfLodgingHouse) {
        int rowAffected = 0;

        String sql = "UPDATE [dbo].[ServiceOfLodgingHouse]\n"
                + "   SET [Price] = ?\n"
                + " WHERE LodgingHouseId = ? and ServiceId = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDouble(1, serviceOfLodgingHouse.getPrice());
            ps.setInt(2, serviceOfLodgingHouse.getLodgingHouseId());
            ps.setInt(3, serviceOfLodgingHouse.getServiceId());

            // nếu như insert 1 customer giống username thì sẽ lỗi.
            rowAffected = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowAffected;
    }
    
    public int deleteServiceOfLodgingHouse(int lodgingHouseId, int serviceId) {
        int rowAffected = 0;

        String sql = "DELETE FROM ServiceOfLodgingHouse WHERE LodgingHouseId = ? "
                + "and ServiceId = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, lodgingHouseId);
            ps.setInt(2, serviceId);

            // nếu như insert 1 customer giống username thì sẽ lỗi.
            rowAffected = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowAffected;
    }

    public List<String> getNameOfServiceInLodgingHouseByAccountId(int accountId) {
        AccountInRoomDAO ard = new AccountInRoomDAO();
        RoomDAO rd = new RoomDAO();
        String roomId = ard.getRoomIdByAccountIdRenting(accountId).get(0);
        int lodingHouseId = rd.getRoomsById(roomId).getLodgingHouse().getLodgingHouseId();

        List<String> list = new ArrayList<>();

        String sql = "SELECT * FROM ServiceOfLodgingHouse slh inner join Services s on slh.ServiceId = s.ServiceId"
                + " where slh.LodgingHouseId = ?";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, lodingHouseId);
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    if (rs.getInt("ServiceId") != 1 && rs.getInt("ServiceId") != 2) {
                        list.add(rs.getString("ServiceName"));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }

        return list;
    }

    public List<Integer> getServiceIdChosenInLodgingHouse(int lodgingHouseId) {
        List<Integer> list = new ArrayList<>();

        String sql = "select * from ServiceOfLodgingHouse where LodgingHouseId = ?";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, lodgingHouseId);
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getInt("ServiceId"));
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }

        return list;
    }

    public ServiceOfLodgingHouse getServiceByLodgingHouseIdAndServiceId(int lodgingHouseId, int serviceId) {
        ServiceOfLodgingHouse sld = null;

        String sql = "select * from ServiceOfLodgingHouse where LodgingHouseId = ? "
                + "and ServiceId = ?";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, lodgingHouseId);
            pstm.setInt(2, serviceId);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    sld = new ServiceOfLodgingHouse(
                            rs.getInt("ServiceId"),
                            rs.getInt("LodgingHouseId"),
                            rs.getDouble("Price")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }

        return sld;
    }

    public static void main(String[] args) {
        AccountInRoomDAO ard = new AccountInRoomDAO();
        ServiceOfLodgingHouseDAO s = new ServiceOfLodgingHouseDAO();
        List<Integer> list = s.getServiceIdChosenInLodgingHouse(1);

        System.out.println(list.contains(1));
    }
}
