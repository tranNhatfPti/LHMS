/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.Feedback;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Account;
import model.InformationOfUser;
import model.Room;

/**
 *
 * @author ASUS
 */
public class FeedbackDAO extends DBContext {

    public Feedback getFeedbackByAccountIdAndLodgingId(int accountId, int lodgingId) {
        Feedback f;
        String sql = """
                      SELECT *
                        FROM dbo.FeedBack
                        WHERE YEAR(monthYear) = YEAR(GETDATE())
                          AND MONTH(monthYear) = MONTH(GETDATE())
                        AND accountid = ? AND lodgingId = ?""";
        try {

            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, accountId);
            st.setInt(2, lodgingId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                f = new Feedback(rs.getInt("feedbackId"),
                        rs.getDate("monthYear"),
                        rs.getInt("accountId"),
                        rs.getInt("lodgingId"),
                        rs.getInt("star"),
                        rs.getInt("cleaningService"),
                        rs.getInt("electric"),
                        rs.getInt("water"),
                        rs.getInt("internet"),
                        rs.getString("otherResponse"),
                        rs.getInt("feedbackStatus"),
                        rs.getInt("servicePrice"));
                return f;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public int getStarByAccountId(int accountId) {
        int star;
        String sql = """
                      SELECT TOP 1 star FROM dbo.FeedBack WHERE accountId = ?""";
        try {

            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, accountId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                star = rs.getInt("star");
                return star;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
    public int getNumberFeedbackClean(String month, String year, int lodgingId, int feedback) {
        int number;
        String sql = """
                      SELECT COUNT(*)AS number FROM dbo.FeedBack WHERE
                      YEAR(monthYear) = ?
                        AND MONTH(monthYear) = ?
                        AND lodgingId = ? AND cleaningService = ?""";
        try {

            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, year);
            st.setString(2, month);
            st.setInt(3, lodgingId);
            st.setInt(4, feedback);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                number = rs.getInt("number");
                return number;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
    public int getNumberFeedbackElectric(String month, String year, int lodgingId, int feedback) {
        int number;
        String sql = """
                      SELECT COUNT(*)AS number FROM dbo.FeedBack WHERE
                      YEAR(monthYear) = ?
                        AND MONTH(monthYear) = ?
                        AND lodgingId = ? AND electric = ?""";
        try {

            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, year);
            st.setString(2, month);
            st.setInt(3, lodgingId);
            st.setInt(4, feedback);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                number = rs.getInt("number");
                return number;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
    public int getNumberFeedbackWater(String month, String year, int lodgingId,int feedback) {
        int number;
        String sql = """
                      SELECT COUNT(*)AS number FROM dbo.FeedBack WHERE
                      YEAR(monthYear) = ?
                        AND MONTH(monthYear) = ?
                        AND lodgingId = ? AND water = ?""";
        try {

            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, year);
            st.setString(2, month);
            st.setInt(3, lodgingId);
            st.setInt(4, feedback);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                number = rs.getInt("number");
                return number;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
    public int getNumberFeedbackInternet(String month, String year, int lodgingId, int feedback) {
        int number;
        String sql = """
                      SELECT COUNT(*)AS number FROM dbo.FeedBack WHERE
                      YEAR(monthYear) = ?
                        AND MONTH(monthYear) = ?
                        AND lodgingId = ? AND internet = ?""";
        try {

            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, year);
            st.setString(2, month);
            st.setInt(3, lodgingId);
            st.setInt(4, feedback);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                number = rs.getInt("number");
                return number;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
    public int getNumberFeedbackPrice(String month, String year, int lodgingId, int feedback) {
        int number;
        String sql = """
                      SELECT COUNT(*)AS number FROM dbo.FeedBack WHERE
                      YEAR(monthYear) = ?
                        AND MONTH(monthYear) = ?
                        AND lodgingId = ? AND servicePrice = ?""";
        try {

            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, year);
            st.setString(2, month);
            st.setInt(3, lodgingId);
            st.setInt(4, feedback);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                number = rs.getInt("number");
                return number;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
    public Map<Integer, Integer> getStar(int lodgingId) {
        int star;
        int number;
        Map<Integer, Integer> m = new HashMap<>();
        String sql = """
                      SELECT star, COUNT(DISTINCT accountId) AS count
                        FROM FeedBack
                        WHERE lodgingId = ?
                        GROUP BY star
                        ORDER BY star;""";
        try {

            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, lodgingId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                star = rs.getInt("star");
                number = rs.getInt("count");
                m.put(star, number);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return m;
    }
    public double getStarByLodgingId(int id) {
        double star;
        String sql = """
                      SELECT AVG(star * 1.0) AS averageStar
                      FROM (
                          SELECT DISTINCT
                              accountId,
                              star
                          FROM
                              FeedBack
                          WHERE
                              lodgingId = ?
                      ) AS distinctRatings;""";
        try {

            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                star = rs.getDouble("averageStar");
                return star;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public Feedback getFeedbackByAccountIdAndLodgingIdDate(int accountId, int lodgingId, String year, String month) {
        Feedback f;
        String sql = """
                      SELECT *
                        FROM dbo.FeedBack
                        WHERE YEAR(monthYear) = ?
                          AND MONTH(monthYear) = ?
                        AND accountid = ? AND lodgingId = ?""";
        try {

            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(3, accountId);
            st.setInt(4, lodgingId);
            st.setString(1, year);
            st.setString(2, month);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                f = new Feedback(rs.getInt("feedbackId"),
                        rs.getDate("monthYear"),
                        rs.getInt("accountId"),
                        rs.getInt("lodgingId"),
                        rs.getInt("star"),
                        rs.getInt("cleaningService"),
                        rs.getInt("electric"),
                        rs.getInt("water"),
                        rs.getInt("internet"),
                        rs.getString("otherResponse"),
                        rs.getInt("feedbackStatus"),
                        rs.getInt("servicePrice"));
                return f;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Feedback> getFeedbackByLodgingId(int lodgingId) {
        List<Feedback> lf = new ArrayList<>();
        String sql = """
                      SELECT *
                        FROM dbo.FeedBack
                        WHERE YEAR(monthYear) = YEAR(GETDATE())
                          AND MONTH(monthYear) = MONTH(GETDATE())
                        AND lodgingId = ?""";
        try {

            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, lodgingId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Feedback f;
                f = new Feedback(rs.getInt("feedbackId"),
                        rs.getDate("monthYear"),
                        rs.getInt("accountId"),
                        rs.getInt("lodgingId"),
                        rs.getInt("star"),
                        rs.getInt("cleaningService"),
                        rs.getInt("electric"),
                        rs.getInt("water"),
                        rs.getInt("internet"),
                        rs.getString("otherResponse"),
                        rs.getInt("feedbackStatus"),
                        rs.getInt("servicePrice"));
                lf.add(f);
                return lf;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean updateFeedback(Feedback feedback) {
        String sql = "UPDATE [dbo].[FeedBack]\n"
                + "   SET [monthYear] = ?\n"
                + "      ,[accountId] = ?\n"
                + "      ,[lodgingId] = ?\n"
                + "      ,[star] = ?\n"
                + "      ,[cleaningService] = ?\n"
                + "      ,[electric] = ?\n"
                + "      ,[water] = ?\n"
                + "      ,[internet] = ?\n"
                + "      ,[otherResponse] = ?\n"
                + "      ,[feedbackstatus] = ?\n"
                + "      ,[servicePrice] = ?\n"
                + " WHERE feedbackId = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);

            stm.setDate(1, new java.sql.Date(feedback.getMonthYear().getTime()));
            stm.setInt(2, feedback.getAccountId());
            stm.setInt(3, feedback.getLodgingId());
            stm.setInt(4, feedback.getStar());
            stm.setInt(5, feedback.getCleaningService());
            stm.setInt(6, feedback.getElectric());
            stm.setInt(7, feedback.getWater());
            stm.setInt(8, feedback.getInternet());
            stm.setString(9, feedback.getOtherResponse());
            stm.setInt(10, feedback.getFeedbackStatus());
            stm.setInt(11, feedback.getServicePrice());
            stm.setInt(12, feedback.getFeedbackId());
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    public boolean updateStar(int accountId, int star) {
        String sql = "UPDATE [dbo].[FeedBack]\n"
                + "   SET [star] = ?\n"
                + " WHERE accountId = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);

            stm.setInt(1, star);
            stm.setInt(2, accountId);
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean insertFeedback(Feedback feedback) {
        String sql = "INSERT INTO [dbo].[FeedBack]\n"
                + "           ([monthYear]\n"
                + "           ,[accountId]\n"
                + "           ,[lodgingId]\n"
                + "           ,[star]\n"
                + "           ,[feedbackstatus])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?)";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setDate(1, new java.sql.Date(feedback.getMonthYear().getTime()));
            stm.setInt(2, feedback.getAccountId());
            stm.setInt(3, feedback.getLodgingId());
            stm.setInt(4, feedback.getStar());
            stm.setInt(5, feedback.getFeedbackStatus());

            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        AccountDAO accountDao = new AccountDAO();
        InformationOfUserDAO userDao = new InformationOfUserDAO();
        FeedbackDAO feedDao = new FeedbackDAO();
        Map<Integer, Integer>  m = feedDao.getStar(2);
        int water1 = feedDao.getNumberFeedbackWater("5", "2024", 2, 1);
        System.out.println(water1);
        for (Map.Entry<Integer, Integer> entry : m.entrySet()) {
            System.out.println("Star: " + entry.getKey() + ", Count: " + entry.getValue());
        }
    }
}
