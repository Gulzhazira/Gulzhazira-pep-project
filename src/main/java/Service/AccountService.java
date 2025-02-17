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
        if (account.getPassword() == null || account.getPassword().length() < 3) {
            return null;
        }
        if (account.getUsername() == null) {
            return null;
        }           
        return accountDAO.insertAccount(account);
    }
}
