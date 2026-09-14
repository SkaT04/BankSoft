package applicationCore.service;
import applicationCore.objects.Account;

import applicationCore.aop.annotations.annotationLoggers.LoggerMark;
import applicationCore.objects.AccountEntity;
import applicationCore.repository.AccountRepository;
import applicationCore.repository.UserRepository;
import applicationCore.objects.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import applicationCore.exceptions.InsufficientFundsException;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    @Value("${account.moneyAmount}")
    private double initialStartAmount;

    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @LoggerMark
    public void createAccount(Long userId) throws NoSuchElementException, NullPointerException, IllegalArgumentException{
        if(userId == null){
            throw new NullPointerException("\n---Incorrect values---\n");
        }
        AccountEntity newAccount = new AccountEntity(userId, initialStartAmount);
        accountRepository.createAccount(newAccount);
    }

    @LoggerMark
    public void closeAccount(Long accountId) throws NoSuchElementException, NullPointerException {
        if (accountId == null) {
            throw new NullPointerException("\n---Incorrect values---\n");
        }
        accountRepository.deleteById(accountId);
    }

    @LoggerMark
    public void depositAccount(Long accountId, Double amount) throws NoSuchElementException, NullPointerException {
        if(accountId == null || amount <= 0){
            throw new NullPointerException("\n---Incorrect values---\n");
        }
        AccountEntity accountEntity = accountRepository.getById(accountId);
        if(accountEntity == null){
            throw new NoSuchElementException("\n---Account not found---\n");
        }
        Account account = toAccount(accountEntity);
        account.changeAmountBy(amount);
        accountRepository.updateAccount(toAccountEntity(account));
    }

    @LoggerMark
    public void withdrawAccount(Long accountId, Double amount) throws NoSuchElementException, NullPointerException, InsufficientFundsException{
        if(accountId == null || amount <= 0){
            throw new NullPointerException("\n---Incorrect values---\n");
        }
        AccountEntity accountEntity = accountRepository.getById(accountId);
        if(accountEntity == null){
            throw new NoSuchElementException("\n---Account not found---\n");
        }
        Account account = toAccount(accountEntity);
        if(amount > account.getMoneyAmount()){
            throw new InsufficientFundsException(account.getMoneyAmount(), amount);
        }
        account.changeAmountBy(-amount);
        accountRepository.updateAccount(toAccountEntity(account));
    }

    @LoggerMark
    public void transferAccount(Long fromAccountId, Long toAccountId, Double amount) throws NoSuchElementException, NullPointerException, InsufficientFundsException {
        if (fromAccountId == null || toAccountId == null || amount == null || amount <= 0 || fromAccountId.equals(toAccountId)) {
            throw new NullPointerException("\n---Incorrect values---\n");
        }
        withdrawAccount(fromAccountId, amount);
        depositAccount(toAccountId, amount);
    }

    @LoggerMark
    public String userAccounts(Long userId) throws NoSuchElementException, NullPointerException{
        if(userId == null){
            throw new NullPointerException("\n---Incorrect values---\n");
        }
        List<AccountEntity> AccountEntityList = accountRepository.getAllAccounts(userId);
        StringBuilder stringBuilder = new StringBuilder();
        for(AccountEntity accountEntity: AccountEntityList){
            Account account = toAccount(accountEntity);
            stringBuilder.append(account.toString()).append("\n");
        }
        return stringBuilder.toString();
    }

    private AccountEntity toAccountEntity(Account account){
        return new AccountEntity(
                account.getId(),
                account.getUserId(),
                account.getMoneyAmount()
        );

    }

    private Account toAccount(AccountEntity accountEntity){
        return new Account(
                accountEntity.getId(),
                accountEntity.getUserId(),
                accountEntity.getMoneyAmount()
        );
    }
}
