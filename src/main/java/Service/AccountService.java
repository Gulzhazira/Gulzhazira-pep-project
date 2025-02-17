package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;

public class AccountService {

    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }
     public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account addAccount(Account account){
        if ( accountDAO.getAccountByUsername(account.getUsername()) != null)  
        {
            return null;
        }
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            return null;
        }
        if (account.getUsername().length() == 0) {
            return null;
        }           
        return accountDAO.insertAccount(account);
    }
    public Account login (Account account){
        if ( accountDAO.getAccountByUsername(account.getUsername()) == null) {
            return null;
        }
        if (account.getPassword().length() == 0 || account.getUsername().length() == 0) {
            return null;
        }
        if (account.getUsername() == account.username && account.getPassword() == account.password) {
            return accountDAO.loginAccount(account.username, account.password);
        }         
        return null;
    }
}
