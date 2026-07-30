package ApplicationCore.Service;

import ApplicationCore.Account;
import ApplicationCore.Repository.AccountRepository;
import ApplicationCore.Repository.UserRepository;
import ApplicationCore.User;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private long accountId;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository){
        this.accountRepository = accountRepository;
        accountId = 0;
        this.userRepository = userRepository;
    }

    public void createAccount(Long userId){
        User user = userRepository.getMapIdUser().get(userId);
        if(user == null){
            System.out.println("\n---User not found---\n");
            return;
        }
        Account newAccount = new Account(++accountId, userId);
        accountRepository.getMapIdAccount().put(accountId, newAccount);
        user.getAccounts().add(accountId);
        System.out.println("\n---Account successfully created---\n");
    }

    public void closeAccount(Long accountId){
        if(accountRepository.getMapIdAccount().remove(accountId) == null){
            System.out.println("\n---Account not found---\n");
        } else {
            System.out.println("\n---Account successfully close---\n");
        }
    }

    public void depositAccount(Long accountId, Double amount){
        Account account = accountRepository.getMapIdAccount().get(accountId);
        if(account == null){
            System.out.println("\n---Account not found\n---");
            return;
        }
        if(amount <= 0){
            System.out.println("\n---An incorrect amount was entered---\n");
            return;
        }
        account.changeAmountBy(amount);
    }

    public void withdrawAccount(Long accountId, Double amount){
        Account account = accountRepository.getMapIdAccount().get(accountId);
        if(account == null){
            System.out.println("\n---Account not found---\n");
            return;
        }
        if(amount <= 0){
            System.out.println("\n---An incorrect amount was entered---\n");
            return;
        }
        if(amount > account.getMoneyAmount()){
            System.out.println("\n---An amount exceeding the balance has been entered---\n");
            return;
        }
        account.changeAmountBy(-amount);
    }

    public void transferAccount(Long fromAccountId, Long toAccountId, Double amount){
        withdrawAccount(fromAccountId,amount);
        depositAccount(toAccountId, amount);
    }
}
