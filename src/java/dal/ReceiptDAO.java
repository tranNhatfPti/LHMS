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
import model.Receipt;
import java.sql.Timestamp;

/**
 *
 * @author admin
 */
public class ReceiptDAO extends DBContext {
    public List<Receipt> getReceiptByDate(String dateFrom,String dateTo, int lodID) {
        String sql = """
                     SELECT [ReceiptId]
                           ,[Price]
                           ,[DateTime]
                           ,[Description]
                           ,[AccountId]
                           ,[LodgingHouseId] ,[StatusPayMent]
                       FROM [dbo].[Receipt] where (DateTime between ? and ?) and [LodgingHouseId]=?""";
        List<Receipt> list = new ArrayList<>();
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, dateFrom); // Set the value for the first parameter
              pstm.setString(2, dateTo); 
            pstm.setInt(3, lodID);
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    Receipt rc = new Receipt(rs.getInt("ReceiptId"), rs.getInt("Price"),
                            rs.getDate("DateTime"), rs.getString("Description"),
                            rs.getInt("AccountId"), rs.getInt("LodgingHouseId"), rs.getBoolean("StatusPayMent"));
                    list.add(rc);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }
        return list;
    }
    public Receipt getReceiptByAccountId(int id) {
        String sql = """
                     SELECT [ReceiptId]
                           ,[Price]
                           ,[DateTime]
                           ,[Description]
                           ,[AccountId]
                           ,[LodgingHouseId] ,[StatusPayMent]
                       FROM [dbo].[Receipt] where AccountId=?""";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, id); // Set the value for the first parameter

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    Receipt rc = new Receipt(rs.getInt("ReceiptId"), rs.getInt("Price"),
                            rs.getDate("DateTime"), rs.getString("Description"),
                            rs.getInt("AccountId"), rs.getInt("LodgingHouseId"), rs.getBoolean("StatusPayMent"));
                    return rc;
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }
        return null;
    }

    public Receipt getReceiptByReceiptId(int id) {
        String sql = """
                     SELECT [ReceiptId]
                           ,[Price]
                           ,[DateTime]
                           ,[Description]
                           ,[AccountId]
                           ,[LodgingHouseId] ,[StatusPayMent]
                       FROM [dbo].[Receipt] where [ReceiptId]=?""";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, id); // Set the value for the first parameter

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    Receipt rc = new Receipt(rs.getInt("ReceiptId"), rs.getInt("Price"),
                            rs.getDate("DateTime"), rs.getString("Description"),
                            rs.getInt("AccountId"), rs.getInt("LodgingHouseId"), rs.getBoolean("StatusPayMent"));
                    return rc;
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }
        return null;
    }

    public List<Receipt> getReceiptByDate(String date, int lodID) {
        String sql = """
                     SELECT [ReceiptId]
                           ,[Price]
                           ,[DateTime]
                           ,[Description]
                           ,[AccountId]
                           ,[LodgingHouseId] ,[StatusPayMent]
                       FROM [dbo].[Receipt] where DateTime = ? and [LodgingHouseId]=?""";
        List<Receipt> list = new ArrayList<>();
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, date); // Set the value for the first parameter
            pstm.setInt(2, lodID);
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    Receipt rc = new Receipt(rs.getInt("ReceiptId"), rs.getInt("Price"),
                            rs.getDate("DateTime"), rs.getString("Description"),
                            rs.getInt("AccountId"), rs.getInt("LodgingHouseId"), rs.getBoolean("StatusPayMent"));
                    list.add(rc);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }
        return list;
    }

    public List<Receipt> getReceiptByPrice(String price, int id) {
        String sql = """
                     SELECT [ReceiptId]
                           ,[Price]
                           ,[DateTime]
                           ,[Description]
                           ,[AccountId]
                           ,[LodgingHouseId] ,[StatusPayMent]
                       FROM [dbo].[Receipt] where Price=? and [LodgingHouseId]=?""";
        List<Receipt> list = new ArrayList<>();
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, price); // Set the value for the first parameter
            pstm.setInt(2, id);
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    Receipt rc = new Receipt(rs.getInt("ReceiptId"), rs.getInt("Price"),
                            rs.getDate("DateTime"), rs.getString("Description"),
                            rs.getInt("AccountId"), rs.getInt("LodgingHouseId"), rs.getBoolean("StatusPayMent"));
                    list.add(rc);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }
        return list;
    }

    public List<Receipt> getAllReceiptById(int id) {
        String sql = """
                     SELECT [ReceiptId]
                           ,[Price]
                           ,[DateTime]
                           ,[Description]
                           ,[AccountId]
                           ,[LodgingHouseId] ,[StatusPayMent]
                       FROM [dbo].[Receipt] where [LodgingHouseId]=?""";
        List<Receipt> list = new ArrayList();
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, id); // Set the value for the first parameter

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    Receipt rc = new Receipt(rs.getInt("ReceiptId"), rs.getInt("Price"),
                            rs.getDate("DateTime"), rs.getString("Description"),
                            rs.getInt("AccountId"), rs.getInt("LodgingHouseId"), rs.getBoolean("StatusPayMent"));
                    list.add(rc);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }
        return list;
    }

    public List<Receipt> getAllReceiptPriceDes(int id) {
        String sql = """
                     SELECT [ReceiptId]
                           ,[Price]
                           ,[DateTime]
                           ,[Description]
                           ,[AccountId]
                           ,[LodgingHouseId] ,[StatusPayMent]
                       FROM [dbo].[Receipt] where [LodgingHouseId]=?  order by  [Price] desc""";
        List<Receipt> list = new ArrayList();
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, id); // Set the value for the first parameter

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    Receipt rc = new Receipt(rs.getInt("ReceiptId"), rs.getInt("Price"),
                            rs.getDate("DateTime"), rs.getString("Description"),
                            rs.getInt("AccountId"), rs.getInt("LodgingHouseId"), rs.getBoolean("StatusPayMent"));
                    list.add(rc);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }
        return list;
    }

    public List<Receipt> getAllReceiptDateDes(int id) {
        String sql = """
                     SELECT [ReceiptId]
                           ,[Price]
                           ,[DateTime]
                           ,[Description]
                           ,[AccountId]
                           ,[LodgingHouseId] ,[StatusPayMent]
                       FROM [dbo].[Receipt] where [LodgingHouseId]=?  order by  [DateTime] desc""";
        List<Receipt> list = new ArrayList();
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, id); // Set the value for the first parameter

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    Receipt rc = new Receipt(rs.getInt("ReceiptId"), rs.getInt("Price"),
                            rs.getDate("DateTime"), rs.getString("Description"),
                            rs.getInt("AccountId"), rs.getInt("LodgingHouseId"), rs.getBoolean("StatusPayMent"));
                    list.add(rc);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }
        return list;
    }

    public List<Receipt> getAllReceiptDateAsc(int id) {
        String sql = """
                     SELECT [ReceiptId]
                           ,[Price]
                           ,[DateTime]
                           ,[Description]
                           ,[AccountId]
                           ,[LodgingHouseId] ,[StatusPayMent]
                       FROM [dbo].[Receipt] where [LodgingHouseId]=?  order by  [DateTime] asc""";
        List<Receipt> list = new ArrayList();
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, id); // Set the value for the first parameter

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    Receipt rc = new Receipt(rs.getInt("ReceiptId"), rs.getInt("Price"),
                            rs.getDate("DateTime"), rs.getString("Description"),
                            rs.getInt("AccountId"), rs.getInt("LodgingHouseId"), rs.getBoolean("StatusPayMent"));
                    list.add(rc);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }
        return list;
    }

    public List<Receipt> getAllReceiptPriceAsc(int id) {
        String sql = """
                     SELECT [ReceiptId]
                           ,[Price]
                           ,[DateTime]
                           ,[Description]
                           ,[AccountId]
                           ,[LodgingHouseId] ,[StatusPayMent]
                       FROM [dbo].[Receipt] where [LodgingHouseId]=?  order by  [Price] asc""";
        List<Receipt> list = new ArrayList();
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, id); // Set the value for the first parameter

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    Receipt rc = new Receipt(rs.getInt("ReceiptId"), rs.getInt("Price"),
                            rs.getDate("DateTime"), rs.getString("Description"),
                            rs.getInt("AccountId"), rs.getInt("LodgingHouseId"), rs.getBoolean("StatusPayMent"));
                    list.add(rc);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }
        return list;
    }

    public void insertReceipt(Receipt receipt) {
        String sql = """
          INSERT INTO [dbo].[Receipt]
                        ([Price]
                        ,[DateTime]
                        ,[Description]
                        ,[AccountId]
                        ,[LodgingHouseId] ,[StatusPayMent])
                  VALUES
                        (?
                        ,?
                        ,?
                        ,?
                        ,?,?)""";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, receipt.getPrice());

            java.util.Date utilDate = receipt.getDateTime();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            pstm.setDate(2, sqlDate);

            pstm.setString(3, receipt.getDescription());
            pstm.setInt(4, receipt.getAccountId());
            pstm.setInt(5, receipt.getLodgingHouseId());
            pstm.setBoolean(6, false);

            // Execute the update
            pstm.executeUpdate();

            System.out.println("Receipt inserted successfully");
        } catch (Exception e) {
            System.err.println("insertReceipt: " + e.getMessage());
        }
    }
    public void insertReceiptWithDeposit(Receipt receipt) {
        String sql = """
          INSERT INTO [dbo].[Receipt]
                        ([Price]
                        ,[DateTime]
                        ,[Description]
                        ,[StatusPayment]        
                        ,[LodgingHouseId])
                  VALUES
                        (?
                        ,?
                        ,?
                        ,?
                        ,?)""";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, receipt.getPrice());
            java.util.Date utilDate = receipt.getDateTime();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            pstm.setDate(2, sqlDate);
            pstm.setString(3, receipt.getDescription());
            pstm.setBoolean(4, true);
            pstm.setInt(5, receipt.getLodgingHouseId());
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void updateStatus(int id, int status) {
        String sql = """
          UPDATE dbo.Receipt
          SET	 StatusPayMent = ? WHERE ReceiptId = ?""";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(2, id);
            pstm.setInt(1,status);

            pstm.executeUpdate();

            System.out.println("Receipt inserted successfully");
        } catch (Exception e) {
            System.err.println("insertReceipt: " + e.getMessage());
        }
    }


    public void insertReceiptFromBill(Receipt receipt) {
        String sql = """
          INSERT INTO [dbo].[Receipt]
                        ([Price]
                        ,[DateTime]
                        ,[Description]
                        ,[AccountId]
                        ,[LodgingHouseId] ,[StatusPayMent])
                  VALUES
                        (?
                        ,?
                        ,?
                        ,?
                        ,?,?)""";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setDouble(1, receipt.getPrice());

            Timestamp sqlDate = new Timestamp(receipt.getDateTime().getTime());
            pstm.setTimestamp(2, sqlDate);

            pstm.setString(3, receipt.getDescription());
            if (receipt.getAccountId() != 0) {
                pstm.setInt(4, receipt.getAccountId());
            } else {
                pstm.setNull(4, java.sql.Types.INTEGER);
            }
            pstm.setInt(5, receipt.getLodgingHouseId());
            pstm.setBoolean(6, receipt.isStatus());

            // Execute the update
            pstm.executeUpdate();

            System.out.println("Receipt inserted successfully");
        } catch (Exception e) {
            System.err.println("insertReceipt: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ReceiptDAO d = new ReceiptDAO();
        System.out.println(d.getReceiptByAccountId(1));
    }

    public List<Receipt> getReceiptByAccount(String info) {
        String sql = """
                   SELECT 
                         [ReceiptId],
                         [Price],
                         [DateTime],
                         [Description],
                         [AccountId],
                         [LodgingHouseId],
                         [StatusPayMent]
                     FROM [LHMS1].[dbo].[Receipt] r
                     WHERE r.[AccountId] IN (
                         SELECT [AccountId]
                         FROM .[dbo].[Account]
                         WHERE [Email] like ? or  r.Description like ?
                     );""";
        List<Receipt> list = new ArrayList<>();
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, "%" + info + "%"); // Set the value for the first parameter
            pstm.setString(2, "%" + info + "%");
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    Receipt rc = new Receipt(rs.getInt("ReceiptId"), rs.getInt("Price"),
                            rs.getDate("DateTime"), rs.getString("Description"),
                            rs.getInt("AccountId"), rs.getInt("LodgingHouseId"), rs.getBoolean("StatusPayMent"));
                    list.add(rc);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }
        return list;
    }

    public List<Receipt> getReceiptByDescription(String info, int id) {
        String sql = """
                   SELECT 
                         [ReceiptId],
                         [Price],
                         [DateTime],
                         [Description],
                         [AccountId],
                         [LodgingHouseId],
                         [StatusPayMent]
                     FROM [dbo].[Receipt] r
                     WHERE r.Description like ? and [LodgingHouseId] =?
                     """;
        List<Receipt> list = new ArrayList<>();
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, "%" + info + "%"); // Set the value for the first parameter
            pstm.setInt(2, id);
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    Receipt rc = new Receipt(rs.getInt("ReceiptId"), rs.getInt("Price"),
                            rs.getDate("DateTime"), rs.getString("Description"),
                            rs.getInt("AccountId"), rs.getInt("LodgingHouseId"), rs.getBoolean("StatusPayMent"));
                    list.add(rc);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }
        return list;
    }

    public int getTotalReceiptByAccountId(int id, int status1, int status2) {
        String sql = """
                 SELECT COUNT(*) FROM dbo.Receipt
                 WHERE AccountId = ? AND (StatusPayMent = ? or StatusPayMent = ?)""";
        int total = 0;
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, id); // Set the value for the first parameter
            pstm.setInt(2, status1); // Set the value for the first parameter
            pstm.setInt(3, status2); // Set the value for the first parameter
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    total = rs.getInt(1); // Fetch the count from the ResultSet
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAllReceiptByAccountId: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAllReceiptByAccountId: " + e.getMessage());
        }
        return total;
    }

    public List<Receipt> getAllReceiptByAccountId(int id, int index, int status1, int status2) {
        String sql = """
                     SELECT * FROM dbo.Receipt
                       WHERE AccountId = ? AND (StatusPayMent = ? or StatusPayMent = ?)
                       ORDER BY DateTime DESC
                       OFFSET ? ROW FETCH NEXT 5 ROWS ONLY""";
        List<Receipt> list = new ArrayList<>();
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, id); // Set the value for the first parameter
            pstm.setInt(2, status1); // Set the value for the first parameter
            pstm.setInt(3, status2); // Set the value for the first parameter
            pstm.setInt(4, index); // Set the value for the first parameter
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    Receipt rc = new Receipt(rs.getInt("ReceiptId"), rs.getInt("Price"),
                            rs.getDate("DateTime"), rs.getString("Description"),
                            rs.getInt("AccountId"), rs.getInt("LodgingHouseId"), rs.getBoolean("StatusPayMent"));
                    list.add(rc);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL getAccountById: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("getAccountById: " + e.getMessage());
        }
        return list;
    }

}
