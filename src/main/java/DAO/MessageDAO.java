package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class MessageDAO {

    public Message insertMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());

            ps.executeUpdate();
            ResultSet pkResultSet = ps.getGeneratedKeys();
            if(pkResultSet.next()) {
                int generated_message_id = (int) pkResultSet.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Message";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Message message = new Message(rs.getInt("message_id"), 
                                              rs.getInt("posted_by"), 
                                              rs.getString("message_text"), 
                                              rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message getMessageById(int message_id) {
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM Message WHERE message_id = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, message_id);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), 
                                            rs.getInt("posted_by"),
                                            rs.getString("message_text"), 
                                            rs.getLong("time_posted_epoch"));
                return message;
            }

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /*public void deleteMessage(int message_id) {
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "DELETE FROM Message WHERE message_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            
            ps.setInt(1, message_id);

            ps.executeUpdate();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateMessage(int message_id, Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "UPDATE Message SET message_text=? WHERE message_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            
            ps.setString(1, message.getMessage_text());
            ps.setInt(2, message_id);

            ps.executeUpdate();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }*/

    public Message deleteMessageById(int messageId) {
        Message messageToDelete = getMessageById(messageId); 
        if (messageToDelete == null) {
            return null; 
        }
    
        String sql = "DELETE FROM Message WHERE message_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, messageId);
            ps.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    
        return messageToDelete; 
    }

    public Message updateMessageText(int messageId, String newMessageText) {
        Message existingMessage = getMessageById(messageId);
    
        if (existingMessage == null) {
            return null;
        }
    
        String sql = "UPDATE Message SET message_text = ? WHERE message_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
    
            ps.setString(1, newMessageText);
            ps.setInt(2, messageId);
    
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                return getMessageById(messageId); 
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    
        return null;
    }

    public List<Message> getMessageByUser (int posted_by) {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            String sql = "SELECT * FROM Message WHERE posted_by = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, posted_by);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), 
                                            rs.getInt("posted_by"),
                                            rs.getString("message_text"), 
                                            rs.getLong("time_posted_epoch"));
                messages.add(message);
            }

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }
}