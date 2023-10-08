package Service;

import Model.Message;
import Model.Account;
import DAO.MessageDAO;
import DAO.AccountDAO;

import java.util.List;

// This service class contains business logic that acts as an access point between the controller and the DAO. 
public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    // This no-args constructor creates a new MessageService with a new MessageDAO.
    public MessageService() {
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    // This constructor is for a MessageService when a MessageDAO is provided & allows for the testing of MessageService independent of MessageDAO.
    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    // This method creates a new message. The message_text should be between 1-255 char, posted_by refers to an existing user.
    public Message createNewMessage(Message message) {
        int posterId = message.getPosted_by();
        String body = message.getMessage_text();
        Account poster = accountDAO.getAccountById(posterId);
        if (poster != null) {
            if (body.length() > 0 && body.length() < 255) {
                return messageDAO.createNewMessage(message);
            }
        }
        return null;
    }

    // This method retrieves all messages.
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    // This method retrieves a message by its id.
    public Message getMessageById(int id) {
        return messageDAO.getMessageById(id);
    }

    // This method deletes a message given its id.

    // This method updates a message text given its id.

    // This method retrieves all messages posted by a particular user.
    public List<Message> getMessagesByAccount(int posterId) {
        return messageDAO.getMessageByAccount(posterId);
    }

}
