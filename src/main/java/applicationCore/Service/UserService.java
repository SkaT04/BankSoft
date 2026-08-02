package applicationCore.Service;

import applicationCore.Account;
import applicationCore.Repository.AccountRepository;
import applicationCore.Repository.UserRepository;
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


    public void createUser(String login) throws NoSuchElementException, NullPointerException{
            if(login == null || login.isBlank()){
                throw new NullPointerException("\n---Incorrect values---\n");
            }
            User newUser = new User(++userId, login);
            if(userRepository.getMapLoginUser().putIfAbsent(login, newUser) != null){
                throw new NoSuchElementException("\n---Такой пользователь уже есть---\n");
            }
            userRepository.getMapIdUser().put(userId, newUser);
            accountService.createAccount(userId);
    }

    public void showAllUsers(){
        Map<Long, User> map = userRepository.getMapIdUser();
        for(User ref : map.values()){
            System.out.println(ref.toString());
        }
    }

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

    public Long userLoginToUserId(String userLogin) throws NullPointerException{
        User user = userRepository.getMapLoginUser().get(userLogin);
        if(user == null) {
            throw new NullPointerException("\n---User not found---\n");
        }
        return user.getId();
    }

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
