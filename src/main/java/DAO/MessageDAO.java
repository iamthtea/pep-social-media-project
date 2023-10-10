package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** This DAO class mediates the transformation of data between the format of objects in Java to rows in a
 * database. 
 * 
 * This DAO should be able to: 
 * - Add a new message to the database, message table
 * - Remove an account from the database, account table?
 * 
 * The Database has a table called "Account", which contains similar values as the Account class:
 * message_id, int, primary key, auto increment
 * posted_by, int, foreign key ref account_id
 * message_text, varchar(255), unique
 * time_posted_epoch, bigint
 **/

public class MessageDAO {

    // This method adds a message from the signed in user to the database
    public Message createNewMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();
            ResultSet pSet = preparedStatement.getGeneratedKeys();
            if (pSet.next()) {
                int generated_message_id = (int) pSet.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    // This method retrieves all messages from the database. 
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rSet = preparedStatement.executeQuery();
            while (rSet.next()) {
                Message message = new Message(rSet.getInt("message_id"),
                     rSet.getInt("posted_by"), 
                     rSet.getString("message_text"), 
                     rSet.getLong("time_posted_epoch"));
                messages.add(message);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    // This method retrieves one message based on the message_id
    public Message getMessageById(int id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet rSet = preparedStatement.executeQuery();
            while (rSet.next()) {
                Message message = new Message(
                    rSet.getInt("message_id"), 
                    rSet.getInt("posted_by"), 
                    rSet.getString("message_text"), 
                    rSet.getLong("time_posted_epoch"));
                return message;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    // This method deletes a message from the database based on the message_id
    public void deleteMessage(int id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // This method updates the message text of a message in the database based on the message_id
    public void updateMessage(int id, Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, message.getMessage_text());
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // This method retrieves all messages written by a particular user.
    public List<Message> getMessagesByAccount(int posterId) {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, posterId);

            ResultSet rSet = preparedStatement.executeQuery();
            while (rSet.next()) {
                Message message = new Message(rSet.getInt("message_id"),
                     rSet.getInt("posted_by"), 
                     rSet.getString("message_text"), 
                     rSet.getLong("time_posted_epoch"));
                messages.add(message);
                return messages;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    
}
