/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import java.util.ArrayList;
import Model.Feedback;

/**
 *
 * @author Trang
 */
public class FeedbackDAO extends MyDAO {
        public ArrayList<Feedback> getAllFeedbacks() {
        String sql = "SELECT * FROM Feedback";
        try {
            ArrayList<Feedback> lsFeedback = new ArrayList<>();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Feedback f = new Feedback(
                        rs.getInt("ID"),
                        rs.getInt("UserID"),
                        rs.getInt("ProductID"),
                        rs.getInt("Star"),
                        rs.getString("FeedbackDetail")
                );
                lsFeedback.add(f);
            }
            return lsFeedback;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
        
            public Feedback getFeedbacksById(int id) {
        String query = "SELECT * FROM Feedback WHERE ID = ?";
        try {
            ps = con.prepareStatement(query);           
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
               Feedback  f = new Feedback(
                        rs.getInt("ID"),
                        rs.getInt("UserID"),
                        rs.getInt("ProductID"),
                        rs.getInt("Star"),
                        rs.getString("FeedbackDetail")
                );
                return f;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void addFeedback(Feedback feedback) {
        String sql = "insert into Feedback values (?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, feedback.getUserID());
            ps.setInt(2, feedback.getProductID());
            ps.setInt(3, feedback.getStar());
            ps.setString(4, feedback.getFeedbackDetail());
            ps.executeUpdate();
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void editFeedback(Feedback feedback) {
        String sql = "update into Feedback values (?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, feedback.getUserID());
            ps.setInt(2, feedback.getProductID());
            ps.setInt(3, feedback.getStar());
            ps.setString(4, feedback.getFeedbackDetail());
            ps.executeUpdate();
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
   
  