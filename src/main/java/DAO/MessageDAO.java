package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;

public class MessageDAO {

    public Message insertMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO Message (param posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
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
}