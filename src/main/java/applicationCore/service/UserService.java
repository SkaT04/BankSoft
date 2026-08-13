package applicationCore.service;

import applicationCore.Account;
import applicationCore.aop.annotations.annotationLoggers.LoggerMark;
import applicationCore.repository.AccountRepository;
import applicationCore.repository.UserRepository;
import applicationCore.User;
import org.springframework.stereotype.Service;


import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private long userId;


    public UserService(UserRepository userRepository, AccountService accountService, AccountRepository accountRepository){
        this.userId = 0;
        this.userRepository = userRepository;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }


    @LoggerMark
    public void createUser(String login) throws NoSuchElementException, NullPointerException{
            if(login == null || login.isBlank()){
                throw new NullPointerException("\n---Incorrect values---\n");
            }
            User newUser = new User(++userId, login);
            if(userRepository.getMapLoginUser().putIfAbsent(login, newUser) != null){
                throw new NoSuchElementException("\n---A user with that name already exists---\n");
            }
            userRepository.getMapIdUser().put(userId, newUser);
            accountService.createAccount(userId);
    }

    @LoggerMark
    public void showAllUsers(){
        Map<Long, User> map = userRepository.getMapIdUser();
        for(User ref : map.values()){
            System.out.println(ref.toString());
        }
    }

    @LoggerMark
    public void removeUser(Long userId) throws NoSuchElementException, NullPointerException{
        if(userId == null){
            throw new NullPointerException("\n---Incorrect values---\n");
        }
        User user = userRepository.getMapIdUser().remove(userId);
        if(user == null){
            throw new NoSuchElementException("\n---User not found---\n");
        }
        for(Long accountId : user.getAccountsId()){
            accountService.closeAccount(userId, accountId);
        }
    }

    @LoggerMark
    private Long userLoginToUserId(String userLogin) throws NullPointerException{
        User user = userRepository.getMapLoginUser().get(userLogin);
        if(user == null) {
            throw new NullPointerException("\n---User not found---\n");
        }
        return user.getId();
    }

    @LoggerMark
    public Long getUserId(String login) throws NoSuchElementException, NullPointerException{
        if(login == null || login.isBlank()){
            throw new NullPointerException("\n---Login is empty---\n");
        }
        Long userId = userLoginToUserId(login);
        if(userId == null){
            throw new NoSuchElementException("\n---User not found---\n");
        }
        return userId;
    }

    @LoggerMark
    public String userAccounts(Long userId) throws NoSuchElementException, NullPointerException{
        if(userId == null){
            throw new NullPointerException("\n---Incorrect values---\n");
        }
        User user = userRepository.getMapIdUser().get(userId);
        if(user == null){
            throw new NoSuchElementException("\n---User not found---\n");
        }
        StringBuilder stringBuilder = new StringBuilder();
        for(Long accountId: user.getAccountsId()){
            Account account = accountRepository.getMapIdAccount().get(accountId);
            stringBuilder.append(account.toString()).append("\n");
        }
        return stringBuilder.toString();
    }
}
