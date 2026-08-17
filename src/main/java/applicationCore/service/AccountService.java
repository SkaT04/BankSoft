package applicationCore.service;
import applicationCore.objects.Account;

import applicationCore.aop.annotations.annotationLoggers.LoggerMark;
import applicationCore.repository.AccountRepository;
import applicationCore.repository.UserRepository;
import applicationCore.objects.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import applicationCore.exceptions.InsufficientFundsException;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private long accountId;
    @Value("${account.moneyAmount}")
    private double initialStartAmount;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository){
        this.accountRepository = accountRepository;
        accountId = 0;
        this.userRepository = userRepository;
    }

    @LoggerMark
    public void createAccount(Long userId) throws NoSuchElementException, NullPointerException{
        if(userId == null){
            throw new NullPointerException("\n---Incorrect values---\n");
        }
        User user = userRepository.getMapIdUser().get(userId);
        if(user == null){
            throw new NoSuchElementException("\n---User not found---\n");
        }
        Account newAccount = new Account(++accountId, userId, initialStartAmount);
        accountRepository.getMapIdAccount().put(accountId, newAccount);
        user.getAccountsId().add(accountId);
    }

    @LoggerMark
    public void closeAccount(Long userId, Long accountId) throws NoSuchElementException, NullPointerException {
        if (userId == null || accountId == null) {
            throw new NullPointerException("\n---Incorrect values---\n");
        }
        userRepository.getMapIdUser().get(userId).getAccountsId().remove(accountId);
        if (accountRepository.getMapIdAccount().remove(accountId) == null) {
            throw new NoSuchElementException("\n---Account not found---\n");
        }
    }

    @LoggerMark
    public void depositAccount(Long accountId, Double amount) throws NoSuchElementException, NullPointerException {
        if(accountId == null || amount <= 0){
            throw new NullPointerException("\n---Incorrect values---\n");
        }
        Account account = accountRepository.getMapIdAccount().get(accountId);
        if(account == null){
            throw new NoSuchElementException("\n---Account not found---\n");
        }
        account.changeAmountBy(amount);
    }

    @LoggerMark
    public void withdrawAccount(Long accountId, Double amount) throws NoSuchElementException, NullPointerException, InsufficientFundsException{
        if(accountId == null || amount <= 0){
            throw new NullPointerException("\n---Incorrect values---\n");
        }
        Account account = accountRepository.getMapIdAccount().get(accountId);
        if(account == null){
            throw new NoSuchElementException("\n---Account not found---\n");
        }
        if(amount > account.getMoneyAmount()){
            throw new InsufficientFundsException(account.getMoneyAmount(), amount);
        }
        account.changeAmountBy(-amount);
    }

    @LoggerMark
    public void transferAccount(Long fromAccountId, Long toAccountId, Double amount) throws NoSuchElementException, NullPointerException, InsufficientFundsException {
        if (fromAccountId == null || toAccountId == null || amount == null || amount <= 0 || fromAccountId.equals(toAccountId)) {
            throw new NullPointerException("\n---Incorrect values---\n");
        }
        withdrawAccount(fromAccountId, amount);
        depositAccount(toAccountId, amount);
    }
}
