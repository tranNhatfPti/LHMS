package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.LodgingHouse;

/**
 * LodgingHousesDAO class for accessing Account data from the database.
 */
public class LodgingHousesDAO extends DBContext {
        public List<LodgingHouse> getLodgingHouseByManagerEmpty() {
        String sql = "select * from LodgingHouses   where StatusDelete=1 and ManageId is null";
        List<LodgingHouse> list = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                LodgingHouse lodge = new LodgingHouse();
                lodge.setLodgingHouseId(rs.getInt("LodgingHouseId"));
                lodge.setNameLodgingHouse(rs.getString("NameLodgingHouse").toUpperCase());
                lodge.setProvince(rs.getString("Province").toUpperCase());
                lodge.setDistrict(rs.getString("District").toUpperCase());
                lodge.setWard(rs.getString("Ward").toUpperCase());
                lodge.setAddressDetail(rs.getString("AddressDetail"));
                lodge.setNumberOfRoom(rs.getInt("NumberOfRoom"));
                lodge.setImg(rs.getString("Image"));
                lodge.setManageId(rs.getInt("ManageId"));
                if (rs.getInt("Status") == 1) {
                    lodge.setStatus(true);
                } else {
                    lodge.setStatus(false);
                }
                lodge.setOrder_date(rs.getDate("Order_Date"));
                if (rs.getInt("StatusDelete") == 1) {
                    lodge.setStatusDelete(true);
                } else {
                    lodge.setStatusDelete(false);
                }
                list.add(lodge);
            }

            return list;
        } catch (SQLException e) {
            System.out.println(e + "helllo");
        }
        return null;

    }
    public boolean updateManagerLodgingHouse(int updateManagerId, String lodgingHouseId) {
        String sql = """
                 UPDATE [dbo].[LodgingHouses]
                 SET [ManageId] = ?
                 WHERE LodgingHouseId = ?""";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, updateManagerId);
            st.setString(2, lodgingHouseId);

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated > 0; // Return true if rows were updated

        } catch (SQLException e) {
                        return false; // Return false on failure
        }
    }

    public int addLodgingHouse(LodgingHouse lodgingHouse) {
        int rowAffected = 0;
        String sql = "INSERT INTO [dbo].[LodgingHouses]\n"
                + "           ([NameLodgingHouse]\n"
                + "           ,[Province]\n"
                + "           ,[District]\n"
                + "           ,[Ward]\n"
                + "           ,[AddressDetail]\n"
                + "           ,[Image]\n"
                + "           ,[Status]\n"
                + "           ,[NumberOfRoom]\n"
                + "           ,[Order_Date]\n"
                + "           ,[StatusDelete])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?)";
        // Corrected the closing parenthesis and added spaces between placeholders

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, lodgingHouse.getNameLodgingHouse());
            pstm.setString(2, lodgingHouse.getProvince()); // Set the value for the first parameter
            pstm.setString(3, lodgingHouse.getDistrict()); // Set the value for the first parameter
            pstm.setString(4, lodgingHouse.getWard()); // Set the value for the first parameter
            pstm.setString(5, lodgingHouse.getAddressDetail());
            pstm.setString(6, lodgingHouse.getImg());
            pstm.setBoolean(7, lodgingHouse.isStatus());
            pstm.setInt(8, lodgingHouse.getNumberOfRoom());
            java.util.Date utilDate = lodgingHouse.getOrder_date();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            pstm.setDate(9, sqlDate);// Set the value for the first parameter
            pstm.setBoolean(10, true);
            rowAffected = pstm.executeUpdate();

        } catch (SQLException e) {
            System.err.println("SQLaddLodgingHouse: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("addLodgingHouse: " + e.getMessage());
        }
        return rowAffected;
    }

    public List<LodgingHouse> getAllLodgingHouse() {
        String sql = "select *from LodgingHouses";
        List<LodgingHouse> list = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                LodgingHouse lodge = new LodgingHouse();
                lodge.setLodgingHouseId(rs.getInt("LodgingHouseId"));
                lodge.setNameLodgingHouse(rs.getString("NameLodgingHouse").toUpperCase());
                lodge.setProvince(rs.getString("Province").toUpperCase());
                lodge.setDistrict(rs.getString("District").toUpperCase());
                lodge.setWard(rs.getString("Ward").toUpperCase());
                lodge.setAddressDetail(rs.getString("AddressDetail"));
                lodge.setNumberOfRoom(rs.getInt("NumberOfRoom"));
                lodge.setImg(rs.getString("Image"));
                if (rs.getInt("Status") == 1) {
                    lodge.setStatus(true);
                } else {
                    lodge.setStatus(false);
                }
                lodge.setOrder_date(rs.getDate("Order_Date"));
                if (rs.getInt("StatusDelete") == 1) {
                    lodge.setStatusDelete(true);
                } else {
                    lodge.setStatusDelete(false);
                }
                list.add(lodge);
            }

            return list;
        } catch (SQLException e) {
        }
        return null;

    }

    public List<LodgingHouse> searchLodgingHouses(String searchInfo) {
        String sql = "SELECT * FROM [LHMS].[dbo].[LodgingHouses] WHERE "
                + "NameLodgingHouse LIKE ? OR "
                + "Province LIKE ? OR "
                + "District LIKE ? OR "
                + "Ward LIKE ? OR "
                + "AddressDetail LIKE ?";

        List<LodgingHouse> list = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            String searchPattern = "%" + searchInfo + "%";
            st.setString(1, searchPattern);
            st.setString(2, searchPattern);
            st.setString(3, searchPattern);
            st.setString(4, searchPattern);
            st.setString(5, searchPattern);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                LodgingHouse lodge = new LodgingHouse();
                lodge.setLodgingHouseId(rs.getInt("LodgingHouseId"));
                lodge.setNameLodgingHouse(rs.getString("NameLodgingHouse").toUpperCase());
                lodge.setProvince(rs.getString("Province").toUpperCase());
                lodge.setDistrict(rs.getString("District").toUpperCase());
                lodge.setWard(rs.getString("Ward").toUpperCase());
                lodge.setAddressDetail(rs.getString("AddressDetail"));
                lodge.setNumberOfRoom(rs.getInt("NumberOfRoom"));
                lodge.setImg(rs.getString("Image"));
                lodge.setStatus(rs.getInt("Status") == 1);
                lodge.setOrder_date(rs.getDate("Order_Date"));
                lodge.setStatusDelete(rs.getInt("StatusDelete") == 1);
                list.add(lodge);
            }
        } catch (SQLException e) {
            // Consider logging this exception properly

        }
        return list;
    }

    public List<LodgingHouse> searchLodgingHousesByDate(String dateMin, String dateMax) {
        String sql = "SELECT [LodgingHouseId]\n"
                + "      ,[NameLodgingHouse]\n"
                + "      ,[Province]\n"
                + "      ,[District]\n"
                + "      ,[Ward]\n"
                + "      ,[AddressDetail]\n"
                + "      ,[Image]\n"
                + "      ,[Status]\n"
                + "      ,[ManageId]\n"
                + "      ,[NumberOfRoom]\n"
                + "      ,[Order_Date]\n"
                + "      ,[StatusDelete]\n"
                + "  FROM [dbo].[LodgingHouses] where [Order_Date] between ? and ?";

        List<LodgingHouse> list = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, dateMin);
            st.setString(2, dateMax);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                LodgingHouse lodge = new LodgingHouse();
                lodge.setLodgingHouseId(rs.getInt("LodgingHouseId"));
                lodge.setNameLodgingHouse(rs.getString("NameLodgingHouse").toUpperCase());
                lodge.setProvince(rs.getString("Province").toUpperCase());
                lodge.setDistrict(rs.getString("District").toUpperCase());
                lodge.setWard(rs.getString("Ward").toUpperCase());
                lodge.setAddressDetail(rs.getString("AddressDetail"));
                lodge.setNumberOfRoom(rs.getInt("NumberOfRoom"));
                lodge.setImg(rs.getString("Image"));
                lodge.setStatus(rs.getInt("Status") == 1);
                lodge.setOrder_date(rs.getDate("Order_Date"));
                lodge.setStatusDelete(rs.getInt("StatusDelete") == 1);
                list.add(lodge);
            }
        } catch (SQLException e) {
            // Consider logging this exception properly

        }
        return list;
    }

    public LodgingHouse getLodgingHouseById(int id) {
        String sql = "SELECT * FROM LodgingHouses WHERE LodgingHouseId=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                boolean isActive = rs.getInt("Status") == 1;
                LodgingHouse lodge = new LodgingHouse(rs.getInt("LodgingHouseId"),
                        rs.getString("NameLodgingHouse").toUpperCase(),
                        rs.getString("Province").toUpperCase(),
                        rs.getString("District").toUpperCase(),
                        rs.getString("Ward").toUpperCase(),
                        rs.getString("AddressDetail"),
                        "...",
                        isActive,
                        rs.getDate("Order_Date"));
                lodge.setManageId(rs.getInt("ManageId"));
                return lodge;
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public boolean deleteLodgingHouse(int lodgingHouseID) throws SQLException {

        String sql1 = """
                      UPDATE [dbo].[LodgingHouses]
                         SET 
                            [StatusDelete] = ?
                       WHERE LodgingHouseId=?""";

        try (PreparedStatement st1 = connection.prepareStatement(sql1);) {

            st1.setBoolean(1, false);
            st1.setInt(2, lodgingHouseID);
            st1.executeUpdate();

            return true;

        } catch (SQLException e) {
        }

        return false;

    }

    public void UpdateLodgingHouse(LodgingHouse updateLodging) {
        String sql = "UPDATE [dbo].[LodgingHouses]\n"
                + "   SET [NameLodgingHouse] = ?\n"
                + "      ,[Province] = ?\n"
                + "      ,[District] = ?\n"
                + "      ,[Ward] = ?\n"
                + "      ,[AddressDetail] =?\n"
                + "      ,[Image] = ?\n"
                + "      ,[Status] =?\n"
                + "      ,[NumberOfRoom] = ?\n"
                + "      ,[Order_Date] = ?\n"
                + " WHERE LodgingHouseId=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, updateLodging.getNameLodgingHouse());
            st.setString(2, updateLodging.getProvince());
            st.setString(3, updateLodging.getDistrict());
            st.setString(4, updateLodging.getWard());
            st.setString(5, updateLodging.getAddressDetail());
            st.setString(6, updateLodging.getImg());
            st.setBoolean(7, updateLodging.isStatus());
            st.setInt(8, updateLodging.getNumberOfRoom());
            java.util.Date utilDate = updateLodging.getOrder_date();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            st.setDate(9, sqlDate);
            st.setInt(10, updateLodging.getLodgingHouseId());
            st.executeUpdate();
        } catch (SQLException e) {
        }

    }

    public int getLodgingHouseByManageID(int accountID) {
        String sql = "SELECT l.LodgingHouseId FROM LodgingHouses l JOIN  Account a\n "
                + "ON l.ManageId = a.AccountId\n"
                + "WHERE a.accountID = ?";
        int lodgingHouseID = 0;
        try {

            PreparedStatement st = connection.prepareStatement(sql);

            st.setInt(1, accountID);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    lodgingHouseID = rs.getInt("LodgingHouseId");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lodgingHouseID;
    }

    public List<LodgingHouse> pagingLodgingHouse(int index) {
        List<LodgingHouse> list = new ArrayList<>();
        String sql = """
                     select  * from LodgingHouses  where StatusDelete=1 order by LodgingHouseId
                       OFFSET ? ROW FETCH next 3 rows only """;
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, (index - 1) * 3);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                LodgingHouse lodge = new LodgingHouse();
                lodge.setLodgingHouseId(rs.getInt("LodgingHouseId"));
                lodge.setNameLodgingHouse(rs.getString("NameLodgingHouse").toUpperCase());
                lodge.setProvince(rs.getString("Province").toUpperCase());
                lodge.setDistrict(rs.getString("District").toUpperCase());
                lodge.setWard(rs.getString("Ward").toUpperCase());
                lodge.setAddressDetail(rs.getString("AddressDetail"));
                lodge.setNumberOfRoom(rs.getInt("NumberOfRoom"));
                lodge.setImg(rs.getString("Image"));
                lodge.setStatus(rs.getBoolean("Status"));
                lodge.setOrder_date(rs.getDate("Order_Date"));
                lodge.setStatusDelete(rs.getBoolean("StatusDelete"));
                list.add(lodge);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public int getTotalLodgingHouse() {
        String sql = "  select count(*)from LodgingHouses where StatusDelete=1";

        try {
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
        }
        return 0;
    }

    public LodgingHouse getLodgingHouseByIdForTenant(int id) {
        String sql = "SELECT * FROM LodgingHouses WHERE LodgingHouseId=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                boolean isActive = rs.getInt("Status") == 1;
                LodgingHouse lodge = new LodgingHouse(rs.getInt("LodgingHouseId"),
                        rs.getString("NameLodgingHouse"),
                        rs.getString("Province"),
                        rs.getString("District"),
                        rs.getString("Ward"),
                        rs.getString("AddressDetail"),
                        rs.getString("Image"),
                        rs.getInt("NumberOfRoom"),
                        rs.getInt("ManageId"),
                        isActive,
                        rs.getDate("Order_Date"));
                return lodge;
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public static void main(String[] args) throws SQLException {
        LodgingHousesDAO dao = new LodgingHousesDAO();
//        List<LodgingHouse> list = dao.searchLodgingHousesByDate("2024-05-21", "2024-05-23");
//        System.out.println(dao.getLodgingHouseByManageID(28));
//        for (LodgingHouse lodgingHouse : list) {
//            System.out.println(lodgingHouse);
//        }
        System.out.println(dao.getLodgingHouseByManageID(6));
    }
}
