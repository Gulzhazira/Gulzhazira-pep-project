package Service;

import Model.Message;
import DAO.MessageDAO;
import DAO.AccountDAO;

import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public MessageService(MessageDAO messageDAO, AccountDAO accountDAO) {
        this.messageDAO = messageDAO;
        this.accountDAO = accountDAO;
    }

    public Message submitMessage(Message message) {
        if(message.getMessage_text().length() > 255 || message.getMessage_text().length() == 0) {
            return null;
        }
        if (accountDAO.getAccountByID(message.getPosted_by()) == null) {
            return null;
        }
        return messageDAO.insertMessage(message);
    }
    
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int message_id) {

        return messageDAO.getMessageById(message_id);
    }

    public Message deleteMessageById(int messageId) {
        return messageDAO.deleteMessageById(messageId);
    }

    public Message updateMessageText(int messageId, String newMessageText) {
        if (newMessageText == null || newMessageText.isBlank() || newMessageText.length() > 255) {
            return null; 
        }
        return messageDAO.updateMessageText(messageId, newMessageText);
    }

    public List<Message> getMessageByUser(int accountId) {

        return messageDAO.getMessageByUser(accountId);
    }

    
}
