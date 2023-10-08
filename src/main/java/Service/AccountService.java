package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;

/* This service class contains business logic that acts as an access point between the controller and the DAO. 
 * 
 * It sets up specific constraints around when specific methods in the DAO are called 
 * like checking that the input is valid.
 */

public class AccountService {
    private AccountDAO accountDAO;

    // This no-args constructor creates a new AccountService with a new AccountDAO.
    public AccountService() {
        accountDAO = new AccountDAO();
    }

    /* This constructor is for a AccountService when a AccountDAO is provided. 
     * It allows for the testing of AccountService independent of AccountDAO.
     */
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    // This method creates a new account. The new account must not have a blank or taken username, pword must be >4 char.
    public Account createAccount(Account account) {
        String userId = account.getUsername();
        String pWord = account.getPassword();
        List<Account> allAccounts = accountDAO.getAllAccounts();
        if (userId.length() > 1 && !allAccounts.contains(account)) {
            if (pWord.length() > 4) {
                return accountDAO.addAccount(account);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    // This method is to authenticate an account that should already exist. 
    public Account authAccount(String username, String password) {
        Account accountSearch = accountDAO.getAccountByUsername(username);
        if (accountSearch != null && password.equals(accountSearch.getPassword())) {
            return accountSearch;
        } else {
            return null;
        }
    }

} 
